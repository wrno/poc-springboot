#!/bin/bash

set -e
set -u

function create_user_and_database() {
	local db_info=$1
	IFS=':' read -ra db_array <<< "$db_info"
	local database=${db_array[0]}
	local password=${db_array[1]}
	echo "  Creating user and database '$database'"
	psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
	    CREATE USER $database WITH PASSWORD '$password';
	    CREATE DATABASE $database OWNER $database;
EOSQL
}

if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
	echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
	for db_info in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
		create_user_and_database $db_info
	done
	echo "Multiple databases created"
fi
