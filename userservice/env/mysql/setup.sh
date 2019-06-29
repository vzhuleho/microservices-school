#!/bin/bash

if [ -z "$1" ]
  then
    echo "Password should be proveded"
fi

MYSQL_USER="dbuser"
MYSQL_DATABASE="userservice-db"
MYSQL_CONTAINER_NAME="userservice-mysql"

MYSQL_ROOT_PASSWORD=$(cat /dev/urandom | LC_CTYPE=C tr -dc 'a-zA-Z0-9' | fold -w 32 | sed 1q)
MYSQL_PASSWORD=$1 # MYSQL_PASSWORD should be provided

echo "Start the Oracle MySQL container:"

docker \
  run \
  --detach \
  --env MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} \
  --env MYSQL_USER=${MYSQL_USER} \
  --env MYSQL_PASSWORD=${MYSQL_PASSWORD} \
  --env MYSQL_DATABASE=${MYSQL_DATABASE} \
  --name ${MYSQL_CONTAINER_NAME} \
  --publish 3306:3306 \
  mysql:latest;

for i in `seq 1 10`;
do
  echo "."
  sleep 1
done

echo "Database '${MYSQL_DATABASE}' running."
echo "  Username: ${MYSQL_USER}"
echo "  Password: ${MYSQL_PASSWORD}"