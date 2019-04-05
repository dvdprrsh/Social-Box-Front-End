import os
basedir = os.path.abspath(os.path.dirname(__file__))

class Config:
    debug = True

    SECRET_KEY = 'you-will-never-guess'
    timezone = 'Europe/London'

    MONGO_URI = "mongodb://admin:admin123@51.75.163.34:27017/socialbox?authSource=admin"