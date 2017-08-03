"""
    Auth
"""

import simplejson as json

from functools import wraps
from flask import request
from partiu.shared.utils import default_parser
from partiu.shared.database import db

def with_user(f):
    @wraps(f)
    def with_user_decoration(*args, **kwargs):
        token = request.headers['Authorization']
        if not token:
            return f(*args, **kwargs)

        user_id = db.session.find_one({'token': token})['user']
        user = db.user.find_one({'_id': user_id})

        return f(*args, **kwargs, logged_user=user)
    return with_user_decoration

def requires_auth(f):
    @wraps(f)
    def requires_auth_decoration(*args, **kwargs):
        try:
            token = request.headers['Authorization']
            if not token or not _verify_auth(token):
                return _authenticate()
            return f(*args, **kwargs)
        except Exception as e:
            print('Error in authentication: %s' % e)
            return _authenticate()
    return requires_auth_decoration

def _authenticate():
    return json.dumps({}, default=default_parser), 401

def _verify_auth(token):
    session = db.session.find_one({'token': token})
    return session is not None
