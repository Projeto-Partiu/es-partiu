import datetime
"""
    Utils
"""

def default_parser(obj):
    """ Default Parser
        Argument function to simplejson knows how to convert
        compound objects to json.
    """
    if getattr(obj, "__dict__", None):
        return obj.__dict__
    elif type(obj) == datetime:
        return obj.isoformat()
    else:
        return str(obj)
