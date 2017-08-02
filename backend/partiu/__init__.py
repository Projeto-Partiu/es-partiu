"""
    Server
"""

import simplejson as json
import os

from flask import Flask, request
from .shared.utils import default_parser, error
from pymongo import MongoClient
from datetime import datetime

app = Flask(__name__)

if os.getenv('ENV') == 'production':
    app.mongodb_uri = os.environ['MONGODB_URI']
else:
    app.mongodb_uri = 'mongodb://localhost:27017/'

client = MongoClient(app.mongodb_uri)
db = client.partiu

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

        if db_user is not None:
            return json.dumps(db_user, default=default_parser), 202
        else:
            user['following'] = []
            user['followers'] = []

            db_user = db.user.insert(user)
            if db_user:
                return json.dumps(db_user, default=default_parser), 202
            else:
                return error(501)
    except Exception as e:
        print(e)
        return error(500)

"""
    Actions
"""

@app.route('/action/<string:user_id>', methods=['POST'])
def add_action(user_id):
    try:
        user = db.user.find_one({ 'id': user_id }, ['_id', 'id', 'name'])

        if not user and not request.data:
            return error(400)

        action = json.loads(request.data.decode('utf-8'))

        inserted_action = db.action.insert({
            'date': str(datetime.now()),
            'type': action['type'],
            'user': user,
            'comments': [],
            'arguments': action['arguments']
        })

        return json.dumps(inserted_action, default=default_parser), 201
    except:
        return error(500)

@app.route('/action/<string:user_id>', methods=['GET'])
def find_all_actions(user_id):
    try:
        user = db.user.find_one({ 'id': user_id })

        if not user:
            return error(400)

        actions = db.action.find({ 'user.id': { '$in': user['following'] } })

        return json.dumps(actions, default=default_parser)
    except:
        return error(500)

"""
    Events
"""

@app.route('/event/new', methods=['POST'])
def create_event():
    try:
        if request.data:
            event = json.loads(request.data.decode('utf-8'))
        else:
            return error(400)

        inserted_id = db.event.insert(event)

        if inserted_id:
                return json.dumps(db.event.find_one({ '_id': inserted_id }), default=default_parser), 201
        else:
            return error(501)

    except Exception as e:
        print(e)
        return error(500)

@app.route('/events', methods=['GET'])
def get_events():
    return json.dumps({
        'events': list(db.event.find())
    }, default=default_parser), 200