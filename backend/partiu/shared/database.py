"""
    Database
"""

import os
from pymongo import MongoClient

if os.getenv('ENV') == 'production':
    _mongo_uri = os.environ['MONGODB_URI']
else:
    _mongo_uri = 'mongodb://localhost:27017/'

_client = MongoClient(_mongo_uri)

db = _client.partiu