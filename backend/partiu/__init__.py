"""
    Server
"""

import simplejson as json
import os

from flask import Flask, request
from datetime import datetime
from dateutil import parser as date_parser
from .shared.database import db
from .shared.utils import default_parser, error, get_random_string, haversine
from .shared.auth import requires_auth, with_user
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
            'comments': [],
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

        actions = list(db.action.find({ 'user.id': {'$in': logged_user['following'] } }))

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

        inserted_id = db.event.insert(event)

        if inserted_id:
            return json.dumps(db.event.find_one({ '_id': inserted_id }), default=default_parser), 201
        else:
            return error(501)

    except Exception as e:
        print(e)
        return error(500)


@app.route('/events', methods=['GET'])
@requires_auth
def get_events():
    return json.dumps(list(db.event.find().limit(20)), default=default_parser), 200

@app.route('/events/by_time', methods=['GET'])
@requires_auth
def get_events_by_time():
    events = db.event.find({'startDate': {'$gt': datetime.now()}}).limit(20)

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
                list_next_events.append(event)


        return json.dumps(list_next_events, default=default_parser), 200

    except Exception as e:
        print(e)
        return error(500)


def its_close(event, position):
    lat_user = position['latitude']
    long_user = position['longitude']

    lat_event = event['latitude']
    long_event = event['longitude']

    print(position)

    dt = haversine(long_user, lat_user, long_event, lat_event)
    print(dt)

    if dt < 20: # 20km
        return True

    return False


