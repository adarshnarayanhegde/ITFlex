from flask import Flask, jsonify, request, url_for
import json
import pprint
from flask import render_template
from flask_cors import CORS
from flask_pymongo import PyMongo
from bson.json_util import dumps
from pymongo import MongoClient
from ZooKeeperHandler import ZookeeperHandler

#create an instance of the Flask class for our web app
app=Flask(__name__)
CORS(app)

#Configurations for MongoDB
projectsTableURI = 'mongodb+srv://adhegde:adhegde@itflex.8kob9.mongodb.net/add-project.projects?retryWrites=true&w=majority'
UsermanagementURI= 'mongodb+srv://adhegde:adhegde@itflex.8kob9.mongodb.net/user-management?retryWrites=true&w=majority'

client = MongoClient(projectsTableURI)
client1= MongoClient(UsermanagementURI)

#connecting to databases
db = client.get_database('add-project')
db1=client1.get_database('user-management')

#creation of indexes
db.project.create_index([('$**', 'text')])
db1.users.create_index([('$**', 'text')])

#Create Routes
@app.route('/find/')
def find():
    ftext=request.args.get('ftext')
    if(ftext):
        user = db1.users
        user1 = db.project
        ResultsfromUsers = user.find({'$text': {'$search': ftext}})
        ResultsfromProjects = user1.find({'$text': {'$search': ftext}})
        resultsfromProjects = dumps(ResultsfromProjects)
        resultsfromUsers = dumps(ResultsfromUsers)

        ListfromProjects = json.loads(resultsfromProjects)
        ListfromUsers = json.loads(resultsfromUsers)

        contentsfromBothDB = {"ListfromProjects": ListfromProjects, "ListfromUsers": ListfromUsers}
        return json.dumps(contentsfromBothDB)


    else:
        projects = db.project
        users = db1.users
        ResultsfromProjects = projects.find()
        ResultsfromUsers = users.find()
        resultfromProjects = dumps(ResultsfromProjects)
        resultfromUsers = dumps(ResultsfromUsers)
        ListfromProjects = json.loads(resultfromProjects)
        ListfromUsers = json.loads(resultfromUsers)
        contentsfromBothDB = {"ListfromProjects": ListfromProjects, "ListfromUsers": ListfromUsers}
        return json.dumps(contentsfromBothDB)


if __name__ == '__main__':
    zk=ZookeeperHandler();
    zk.registerAuthService('149.165.171.39','5000');
    app.run(debug=True,host='0.0.0.0')
