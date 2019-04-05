from flask import Flask, request, jsonify
from socialbox import app, mongo
from socialbox.helpers import database, passwords
#from pprint import pprint

@app.route("/api/register", methods=['POST'])
def register():
    """Endpoint to register users."""

    # Get POST information from request
    username = request.values.get('username', None)
    firstname = request.values.get('firstname', None)
    surname = request.values.get('surname', None)
    email = request.values.get('email', None)
    password = request.values.get('password', None)

    # Checks that all required information to sign up has been provided
    if not all([username, firstname, surname, email, password]):
        return jsonify({"ok": False, "message": "All fields need a value."})

    # Checks that the username provided doesn't already have an account
    if database.get_user(username):
        return jsonify({"ok": False, "message": "Username already exists in system."})

    # Creates a user in the database, returning the new API Key
    api_key = database.create_user(username, firstname, surname, email, password)

    # Return a success message with the new API Key
    return jsonify({"ok": True, "api_key": api_key})


@app.route("/api/login", methods=['POST'])
def login():
    """Endpoint to login users."""

    # Get POST information from request
    username = request.values.get('username', None)
    password = request.values.get('password', None)

    # Checks that both a username and password has been provided
    if not all([username, password]):
        return jsonify({"ok": False, "message": "Both username and password need a value."})

    # Get's the user object from the database
    user = database.get_user(username)

    # If the user cannot be found, an error message is returned
    if not user:
        return jsonify({"ok": False, "message": "Username not found in system."})

    password_hash = user["password_hash"]

    # Verifies that supplied password matches the stored password hash
    if not passwords.verify_password(password, password_hash):
        return jsonify({"ok": False, "message": "Incorrect password."})

    # Return success message with the user's API Key
    return jsonify({"ok": True, "api_key": user["api_key"]})

@app.route("/api/begin_trip", methods=['POST'])
def begin_trip():
    """Endpoint to begin a trip."""

    # Get POST information from request
    api_key = request.values.get('api_key', None)

    # Checks that supplied API Key belongs to a user
    user = database.verify_api_key(api_key)

    if not user:
        return jsonify({"ok": False, "message": "API Key provided is not valid."})

    # Create a new trip belonging to the user with the supplied API Key
    trip_id = database.create_trip(user["username"])

    # Return success message with supplied API Key
    return jsonify({"ok": True, "trip_id": trip_id})



@app.route("/api/update_trip", methods=['POST'])
def update_trip():
    """Endpoint to update a trip's data."""

    # Get POST information from request
    api_key = request.values.get('api_key', None)
    trip_id = request.values.get('trip_id', None)
    lat = request.values.get('lat', None)
    long = request.values.get('long', None)

    # Checks that all required information to begin a trip has been provided
    if not all([api_key, trip_id, lat, long]):
        return jsonify({"ok": False, "message": "API Key, Lat and Long must be provided."})

    # Ensures that the same number of lat and long ordinates has been passed
    if len(lat.split(',')) != len(long.split(',')):
        return jsonify({"ok": False, "message": "Number of lat ordinates must match number of long co-ordinates."})

    # Gets the user with the supplied API Key
    user = database.verify_api_key(api_key)

    # If no user is found, an error message is passed back to the client
    if not user:
        return jsonify({"ok": False, "message": "API Key provided is not valid."})

    # Gets the trip object with the supplied trip ID
    trip = database.get_trip(trip_id)

    # If the trip_id supplied doesn't represent a real trip, an error message is returned
    if not trip:
        return jsonify({"ok": False, "message": "Trip ID provided is not valid."})

    # If the user making the request doesn't own the trip, an error message is returned
    if not database.does_user_own_trip(user['username'], trip_id):
        return jsonify({"ok": False, "message": "Trip does not belong to you."})

    # Append the new lat and long data to the object
    database.update_trip(trip_id, lat, long)

    # Return a success response
    return jsonify({"ok": True})


'''@app.route("/api/end_trip", methods=['POST'])
def end_trip():
    """Endpoint to end a trip."""

    # Get POST information from request
    api_key = request.values.get('api_key', None)
    trip_id = request.values.get('trip_id', None)
    lat = request.values.get('lat', None)
    long = request.values.get('long', None)

    # Ensures that both an API Key and trip ID is provided
    if not all([api_key, trip_id]):
        return jsonify({"ok": False, "message": "API Key, Lat and Long must be provided."})

    if len(lat.split(',')) != len(long.split(',')):
        return jsonify({"ok": False, "message": "Number of lat ordinates must match number of long co-ordinates."})

    # Gets the user with the supplied API Key
    user = database.verify_api_key(api_key)

    # If no user is found, an error message is passed back to the client
    if not user:
        return jsonify({"ok": False, "message": "API Key provided is not valid."})


    return jsonify({"ok": True})
'''