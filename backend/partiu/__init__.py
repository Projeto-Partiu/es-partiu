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

# The method bellow is only for example porpose.
# @app.route('/version/<string:id>', methods=['POST']) # Using query params
# def version_with_arg(id): # Query params goes here
#     print(str(id))
#     headers = request.headers # Getting general headers
#     print(str(headers))
#     esp_headers = request.headers['MeuHeaders'] if request.headers['MeuHeaders'] else None # Getting MeuHeaders from headers
#     print(str(esp_headers))
#     data = None
#     if request.data:
#         data = json.loads(request.data.decode('utf-8')) # Getting data from request
#     print(str(data))
#
#     return "Example", 404 # returning not found.

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
