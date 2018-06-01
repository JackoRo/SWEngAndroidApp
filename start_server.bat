@echo off
echo Initializing server...
set FLASK_APP=app/src/main/python/server.py
flask run
echo --- SERVER CLOSED ---