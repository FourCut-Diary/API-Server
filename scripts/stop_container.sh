#!/bin/bash

# Stop the specified container
stop_container() {
    local CONTAINER_NAME=$1

    echo "▶️ Stopping ${CONTAINER_NAME} Container"
    docker-compose stop ${CONTAINER_NAME}
}