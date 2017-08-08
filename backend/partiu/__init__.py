"""
    Server
"""

import simplejson as json
import os
import re

from flask import Flask, request
from datetime import datetime
from dateutil import parser as date_parser
from .shared.database import db
from .shared.utils import default_parser, error, get_random_string
from .shared.auth import requires_auth, with_user
from bson import ObjectId

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

        actions = list(db.action.find({ 'user.id': { '$in': logged_user['following'] } }))

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
#@requires_auth
def get_events():
    return json.dumps(list(db.event.find()), default=default_parser), 200
