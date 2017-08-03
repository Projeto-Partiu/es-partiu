"""
    Utils
"""

from datetime import datetime
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
