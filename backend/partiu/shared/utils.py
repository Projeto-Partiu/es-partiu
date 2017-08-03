"""
    Utils
"""

from datetime import datetime
import simplejson as json

def default_parser(obj):
    """ Default Parser
        Argument function to simplejson knows how to convert
        compound objects to json.
    """
    if getattr(obj, "__dict__", None):
        return obj.__dict__
    elif isinstance(obj, datetime):
        if obj.utcoffset() is None:
            return obj.isoformat() + 'Z'
        return obj.isoformat()
    else:
        return str(obj)

def error(status):
    return json.dumps({}, default=default_parser), status
