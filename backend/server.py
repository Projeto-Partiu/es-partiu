""" Server
"""

from flask import Flask, request
import simplejson as json
from shared.utils import default_parser

app = Flask(__name__)

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
@app.route('/version/<string:id>', methods=['POST']) # Using query params
def version_with_arg(id): # Query params goes here
    print(str(id))
    headers = request.headers # Getting general headers
    print(str(headers))
    esp_headers = request.headers['MeuHeaders'] if request.headers['MeuHeaders'] else None # Getting MeuHeaders from headers
    print(str(esp_headers))
    data = None
    if request.data:
        data = json.loads(request.data.decode('utf-8')) # Getting data from request
    print(str(data))

    return "Example", 404 # returning not found.

if __name__ == '__main__':
    app.run(port=8080)
