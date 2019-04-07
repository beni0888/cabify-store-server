#!/bin/bash

export SERVER_IP=$(ifconfig | grep 'inet ' | grep -m1 -Fv 127.0.0.1 | awk '{print $2}' | awk -F':' 'm = /:/ {print $2} !m {print $1}')
docker run --rm -i -t -e SERVER_IP=$SERVER_IP jbenitoc/cabify-store-client:latest
