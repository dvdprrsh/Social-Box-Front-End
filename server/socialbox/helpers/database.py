from socialbox import mongo
from socialbox.helpers.passwords import hash_password, generate_api_key
from time import time
from bson.objectid import ObjectId

users = mongo.db.users
trips = mongo.db.trips

def create_user(username, firstname, surname, email, password):
	api_key = generate_api_key()

	user = {
		'username': username,
		'firstname': firstname,
		'surname': surname,
		'email': email,
		'password_hash': hash_password(password),
		'api_key': api_key,
	}

	users.insert_one(user)
	return api_key


def get_user(username):
	result = users.find_one({'username': username})
	return result

def verify_api_key(api_key):
	result = users.find_one({'api_key': api_key})
	return result

def create_trip(username):
	trip = {
		'username': username,
		'start_time': time(),
		'lat': [],
		'long': [],
	}

	_trip = trips.insert_one(trip)
	return str(_trip.inserted_id)

def get_trip(trip_id):
	result = trips.find_one({'_id': ObjectId(trip_id)})
	return result

def does_user_own_trip(username, trip_id):
	trip = trips.find_one({'_id': ObjectId(trip_id)})

	if trip:
		return username == trip['username']
	else:
		return False

def update_trip(trip_id, lat, long):
	trip = trips.find_one({'_id': ObjectId(trip_id)})

	lat = lat.split(',')
	long = long.split(',')

	trips.update(
		{'_id': ObjectId(trip_id)},
		{'$push': {'lat': {'$each': lat}}}
	)

	trips.update(
		{'_id': ObjectId(trip_id)},
		{'$push': {'long': {'$each': long}}}
	)
