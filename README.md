# Gym and Workload Microservices with Docker Compose

This repository contains the Docker Compose configuration for deploying the Gym and Workload microservices.

## Prerequisites

- Docker installed and running on your machine. If not installed, follow the official [Docker installation guide](https://docs.docker.com/get-docker/) for your operating system.

## Setup

1. Clone both the Gym and Workload service repositories into the same directory:

    ```bash
    git clone https://github.com/eshonkulov-asliddin/gym.git
    git clone https://github.com/eshonkulov-asliddin/workload-service.git
    ```

2. Copy the `docker-compose.yml` file from the Gym service repository to the root folder where both services are located:

    ```bash
    cp gym/docker-compose.yml .
    ```

3. Copy the environment files from the Gym service repository to the root folder:

    ```bash
    cp gym/.env .
    ```   
   
4. Start the Docker Compose services:

    ```bash
    docker-compose up -d
    ```

## Services

### Gym Service

- Image: gym:latest
- Port: 8080
- Depends On: MySQL, RabbitMQ
- Volume: gym:/var/lib/gym

### Workload Service

- Image: workload:latest
- Port: 8082
- Environment File: .env
- Depends On: MongoDB, RabbitMQ
- Volume: workload:/var/lib/workload

## Volumes

- gym
- workload

## Networks

- gym_network
