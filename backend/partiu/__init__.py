"""
    Server
"""

import simplejson as json
import os

from flask import Flask, request
from .shared.utils import default_parser, error
from pymongo import MongoClient

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

        if db.user.find({"id": user['id']}).count() > 0:
            return json.dumps({}, default=default_parser), 202
        else:
            if db.user.insert(user):
                return json.dumps({}, default=default_parser), 202
            else:
                return error(501)
    except Exception as e:
        print(e)
        return error(500)

@app.route('/activity/<int:user_id>', methods=['GET'])
def user_following_activities(user_id):
    try:
        if request.data:
            return json.dumps({}, default=default_parser)
        else:
            return error(400)

    except:
        return error(500)

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