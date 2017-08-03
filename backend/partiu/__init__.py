"""
    Server
"""

import simplejson as json
import os

from functools import wraps
from flask import Flask, request
from .shared.utils import default_parser, error, get_random_string
from pymongo import MongoClient
from datetime import datetime

app = Flask(__name__)

if os.getenv('ENV') == 'production':
    app.mongodb_uri = os.environ['MONGODB_URI']
else:
    app.mongodb_uri = 'mongodb://localhost:27017/'

client = MongoClient(app.mongodb_uri)
db = client.partiu

def requires_auth(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        try:
            token = request.headers['Authorization']
            if not token or not verify_auth(token):
                return authenticate()
            return f(*args, **kwargs)
        except Exception:
            return authenticate()
    return decorated

def authenticate():
    return json.dumps({}, default=default_parser), 401

def verify_auth(token):
    session = db.session.find_one({'token': token})
    return session is not None

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
            user['followers'] = []

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

"""
    Actions
"""


@app.route('/action/<string:user_id>', methods=['POST'])
@requires_auth
def add_action(user_id):
    try:
        user = db.user.find_one({ 'id': user_id }, ['_id', 'id', 'name'])

        if not user and not request.data:
            return error(400)

        action = json.loads(request.data.decode('utf-8'))

        inserted_action = db.action.insert_one({
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
@requires_auth
def find_all_actions(user_id):
    try:
        user = db.user.find_one({ 'id': user_id })

        if not user:
            return error(400)

        actions = list(db.action.find({ 'user.id': { '$in': user['following'] } }))

        return json.dumps(actions, default=default_parser)
    except Exception as e:
        print(e)
        return error(500)

"""
    Events
"""


@app.route('/event/new', methods=['POST'])
@requires_auth
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
@requires_auth
def get_events():
    return json.dumps({
        'events': list(db.event.find())
    }, default=default_parser), 200