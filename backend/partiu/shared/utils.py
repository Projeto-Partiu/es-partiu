"""
    Utils
"""

from datetime import datetime
from math import radians, cos, sin, asin, sqrt
import simplejson as json
import random

random = random.SystemRandom()



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


def haversine(lon1, lat1, lon2, lat2):
    """
    Calculate the great circle distance between two points
    on the earth (specified in decimal degrees)
    """
    # convert decimal degrees to radians
    lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])
    # haversine formula
    dlon = lon2 - lon1
    dlat = lat2 - lat1
    a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
    c = 2 * asin(sqrt(a))
    km = 6367 * c
    return km

def get_random_string(length=12,
                      allowed_chars='abcdefghijklmnopqrstuvwxyz'
                                    'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'):
    """
    Returns a securely generated random string.

    The default length of 12 with the a-z, A-Z, 0-9 character set returns
    a 71-bit value. log_2((26+26+10)^12) =~ 71 bits.

    Taken from the django.utils.crypto module.
    """

    return ''.join(random.choice(allowed_chars) for i in range(length))
