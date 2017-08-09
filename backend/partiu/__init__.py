"""
    Server
"""

import pymongo
import simplejson as json
import os
import re

from flask import Flask, request
from datetime import datetime
from dateutil import parser as date_parser
from bson.objectid import ObjectId
from .shared.database import db
from .shared.utils import default_parser, error, get_random_string, haversine
from .shared.auth import requires_auth, with_user
from bson import ObjectId
from math import sqrt

app = Flask(__name__)

@app.route('/version', methods=['GET'])
def version():
    """ Get version
    """
    version_json = {
        'partiu':
        {
            'version': 0.1
        }
    }
    return json.dumps(version_json, default=default_parser), 200

@app.route('/user/', methods=['POST'])
def login_user():
    try:
        if request.data:
            user = json.loads(request.data.decode('utf-8'))
        else:
            return error(403)

        db_user = db.user.find_one({ 'id': user['id'] })

        newSession = {'token': get_random_string(20)}

        if db_user is not None:
            newSession['user'] = db_user['_id']
            db.session.insert(newSession)
            db_user['token'] = newSession['token']
            return json.dumps(db_user, default=default_parser), 202
        else:
            user['following'] = []

            inserted_id = db.user.insert(user)
            if inserted_id:
                user['_id'] = inserted_id
                newSession['user'] = inserted_id
                db.session.insert(newSession)
                user['token'] = newSession['token']
                return json.dumps(user, default=default_parser), 202
            else:
                return error(501)
    except Exception as e:
        print(e)
        return error(500)

@app.route('/user/logout', methods=['POST'])
@requires_auth
@with_user
def logout_user(logged_user=None):
    try:
        if not logged_user:
            return error(401)

        result = db.session.delete_one({ 'token': logged_user['token'], 'user': logged_user['_id'] })

        if not result or result.deleted_count != 1:
            return error(500)

        return '', 204
    except Exception as e:
        print(e)
        return error(500)

"""
    Find and Update Users
"""

@app.route('/users', methods=['GET'])
@requires_auth
@with_user
def find_users(query="", logged_user=None):
    query = request.args.get('query')
    print(query)
    return json.dumps(list(db.user.find({ 'name': { '$regex': re.compile("^" + query, re.IGNORECASE) } })), default=default_parser), 200

@app.route('/user/update/<string:_id>', methods=['PUT'])
@requires_auth
@with_user
def update_user(_id):
    updated_user = json.loads(request.data.decode('utf-8'))
    old_user = db.session.find_one({'_id': ObjectId(_id)})
    if old_user is None:
        return error(404)
    db.user.update_one( {'_id': ObjectId(old_user['user'])},
        {
            '$set': {'name': 'kkkk'}
        }, upsert=False)
    return "done", 200

"""
   Actions
"""

@app.route('/action/', methods=['POST'])
@requires_auth
@with_user
def add_action(logged_user=None):
    try:
        if not logged_user and not request.data:
            return error(400)

        action = json.loads(request.data.decode('utf-8'))

        inserted_action = db.action.insert_one({
            'date': str(datetime.now()),
            'type': action['type'],
            'user': logged_user,
            'arguments': action['arguments']
        })

        return json.dumps(inserted_action, default=default_parser), 201
    except:
        return error(500)

@app.route('/action/', methods=['GET'])
@requires_auth
@with_user
def find_all_actions(logged_user=None):
    try:
        if not logged_user:
            return error(400)

        actions = list(db.action.find({ 'user.id': { '$in': logged_user['following'] } }).sort('date', pymongo.DESCENDING))

        return json.dumps(actions, default=default_parser)
    except Exception as e:
        print(e)
        return error(500)

"""
    Events
"""

@app.route('/event/new', methods=['POST'])
@requires_auth
@with_user
def create_event(logged_user=None):
    try:
        if request.data:
            event = json.loads(request.data.decode('utf-8'))
        else:
            return error(400)

        del logged_user['token']

        event['startDate'] = date_parser.parse(event['startDate'])
        event['owner'] = logged_user
        event['interestedUsers'] = []
        event['confirmedUsers'] = []
        event['comments'] = []

        inserted_id = db.event.insert(event)

        if inserted_id:
            return json.dumps(db.event.find_one({ '_id': inserted_id }), default=default_parser), 201
        else:
            return error(501)

    except Exception as e:
        print(e)
        return error(500)

@app.route('/event/<string:event_id>', methods=['GET'])
@requires_auth
def find_event(event_id):
    event = db.event.find_one({ '_id': ObjectId(event_id) })

    if not event:
        return '', 404

    event['comments'] = _find_event_comments(event)

    return json.dumps(event, default=default_parser), 201


@app.route('/events', methods=['GET'])
@requires_auth
def get_events():
    return json.dumps(list(db.event.find().limit(20)), default=default_parser), 200

@app.route('/events/by_time', methods=['GET'])
@requires_auth
def get_events_by_time():
    events = db.event.find({'startDate': {'$gt': datetime.now()}}) \
        .limit(20)                                                 \
        .sort([("startDate", pymongo.ASCENDING)])

    for event in events:
        event['comments'] = _find_event_comments(event)

    return json.dumps(list(events), default=default_parser), 200

@app.route('/events/by_distance', methods=['POST'])
@requires_auth
def get_events_by_distance():
    try:
        if request.data:
            position = json.loads(request.data.decode('utf-8'))
            if position['latitude'] == -1 and position['longitude'] == -1:
                position = {'latitude': -7.219447, 'longitude': -35.884460}  # Centro de CG
        else:
            position = {'latitude': -7.219447, 'longitude': -35.884460} # Centro de CG

        events = db.event.find({'startDate': {'$gt': datetime.now()}}).limit(20)

        list_next_events = []

        for event in events:
            if its_close(event, position):
                event['comments'] = _find_event_comments(event)
                list_next_events.append(event)


        return json.dumps(list_next_events, default=default_parser), 200

    except Exception as e:
        print(e)
        return error(500)

@app.route('/events/confirm-presense/<string:_id>', methods=['PUT'])
@requires_auth
@with_user
def confirm_presence(_id, logged_user=None):
    if logged_user is None:
        return error(400)
    confirmed_user = {
        'name': logged_user['name'],
        'urlPhoto': logged_user['urlPhoto'],
        '_id': logged_user['_id']
    }
    curr_event = db.event.find_one({ '_id': ObjectId(_id) })
    interestedUsers = curr_event['interestedUsers']
    for index in range(len(interestedUsers)):
        if interestedUsers[index]['_id'] == confirmed_user['_id']:
            curr_event['interestedUsers'].pop(index)
            break

    curr_event['confirmedUsers'].append(confirmed_user)
    db.event.find_one_and_update(
        {'_id': ObjectId(_id)},
        {'$set': curr_event}
    )
    return '', 204

@app.route('/events/disconfirm-presense/<string:_id>', methods=['PUT'])
@requires_auth
@with_user
def disconfirm_presence(_id, logged_user=None):
    if logged_user is None:
        return error(400)
    curr_event = db.event.find_one({'_id': ObjectId(_id)})
    confirmedUsers = curr_event['confirmedUsers']
    for index in range(len(confirmedUsers)):
        if confirmedUsers[index]['_id'] == logged_user['_id']:
            curr_event['confirmedUsers'].pop(index)
            break

    db.event.find_one_and_update(
        {'_id': ObjectId(_id)},
        {'$set': curr_event}
    )
    return '', 204

@app.route('/comment/<string:comment_id>', methods=['DELETE'])
@requires_auth
@with_user
def delete_comment(comment_id, logged_user=None):
    try:
        comment = db.comment.find_one({ '_id': ObjectId(comment_id) })

        if comment['user']['id'] != logged_user['id']:
            return error(401)

        db.comment.delete_one(comment)

        return '', 204
    except:
        return error(500)


@app.route('/comment/<string:event_id>', methods=['POST'])
@requires_auth
@with_user
def add_comment(event_id, logged_user=None):
    try:
        if not request.data:
            return error(400)

        comment = json.loads(request.data.decode('utf-8'))

        del logged_user['token']
        comment['user'] = logged_user
        comment['date'] = datetime.now()

        inserted_id = db.comment.insert(comment)

        if inserted_id:
            comment['_id'] = inserted_id

            db.event.update_one({ '_id': ObjectId(event_id) }, { '$push': { 'comments': str(inserted_id) }})

            return json.dumps(comment, default=default_parser), 201
        else:
            return error(501)
    except:
        return error(500)


def _find_event_comments(event):
    return sorted(
        list(db.comment.find({ '_id': { '$in': [ObjectId(id) for id in event['comments']] } })),
        key=lambda x: x['date'],
        reverse=True
    )

def its_close(event, position):
    lat_user = position['latitude']
    long_user = position['longitude']

    lat_event = event['latitude']
    long_event = event['longitude']

    dt = haversine(long_user, lat_user, long_event, lat_event)

    if dt < 20: # 20km
        return True

    return False

