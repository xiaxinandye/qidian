from flask import Flask, jsonify
from my_flask.db import MySqlClient
from json import JSONEncoder

app = Flask(__name__)
app.json_encoder = JSONEncoder

@app.route('/')
def get_novelsId():
    mysql = MySqlClient()
    return jsonify(mysql.get_novelsId())
    


@app.route('/chapters')
def get_list_chapter_id():
    mysql = MySqlClient()
    return jsonify(mysql.get_chaptersId())
     
   
if __name__ == '__main__':
    app.run( host='0.0.0.0', port=5000)