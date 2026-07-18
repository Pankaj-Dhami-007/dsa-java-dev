package systemdesign.basics.docker;

public class DockerAndContainerization {

}

/*

========================================================================
            DOCKER AND CONTAINERIZATION
========================================================================

Docker is one of MOST IMPORTANT
technologies in modern backend
and DevOps ecosystems.

========================================================================

Almost every modern scalable system uses:
        Containers

========================================================================
EXAMPLES
========================================================================

- :contentReference[oaicite:0]{index=0}
- :contentReference[oaicite:1]{index=1}
- :contentReference[oaicite:2]{index=2}
- :contentReference[oaicite:3]{index=3}

========================================================================
MAIN QUESTION
========================================================================

Why Docker created?

========================================================================
PROBLEM BEFORE DOCKER
========================================================================

Applications worked on:
developer machine

========================================================================

But failed on:
production server.

========================================================================
FAMOUS PROBLEM
========================================================================

"It works on my machine"

========================================================================
WHY THIS HAPPENED?
========================================================================

Different environments:
- OS differences
- Java versions
- library versions
- dependency conflicts
- configuration mismatch

========================================================================
EXAMPLE
========================================================================

Developer machine:
Java 21

========================================================================

Production server:
Java 17

========================================================================

Application crashes.

========================================================================
THIS IS WHY
========================================================================

Containerization created.

========================================================================
WHAT IS CONTAINERIZATION?
========================================================================

Packaging application
with all dependencies
into isolated unit.

========================================================================
SIMPLE IDEA
========================================================================

Application runs same everywhere.

========================================================================

No environment mismatch.

========================================================================
WHAT IS DOCKER?
========================================================================

Docker is containerization platform
used to create,
run,
and manage containers.

========================================================================
WHAT IS CONTAINER?
========================================================================

Container is lightweight isolated environment
containing:
- application
- runtime
- libraries
- dependencies
- configurations

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Container includes everything needed
to run application.

========================================================================
MAIN GOALS OF DOCKER
========================================================================

1. Consistent environments
2. Easy deployment
3. Isolation
4. Scalability
5. Fast startup
6. Portability

========================================================================
TRADITIONAL DEPLOYMENT
========================================================================

Application
   ↓
Directly on OS

========================================================================

Dependency conflicts common.

========================================================================
DOCKER DEPLOYMENT
========================================================================

Application
   ↓
Container
   ↓
Docker Engine
   ↓
OS

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Containers isolate applications
from each other.

========================================================================
EXAMPLE
========================================================================

Server runs:
- Java app
- Node.js app
- Python app

========================================================================

All independently inside containers.

========================================================================
DOCKER IMAGE
========================================================================

Blueprint/template for container.

========================================================================

Contains:
- application code
- dependencies
- runtime setup

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Image is:
read-only template.

========================================================================

Container is:
running instance of image.

========================================================================
ANALOGY
========================================================================

Class
------
Docker Image

========================================================================

Object
--------
Docker Container

========================================================================
DOCKERFILE
========================================================================

Configuration file
used to create Docker image.

========================================================================

Defines:
- base image
- dependencies
- commands
- startup instructions

========================================================================
EXAMPLE FLOW
========================================================================

Dockerfile
   ↓
Docker Image
   ↓
Docker Container

========================================================================
BASE IMAGE
========================================================================

Starting environment for container.

========================================================================
EXAMPLES
========================================================================

- openjdk
- ubuntu
- node
- alpine

========================================================================
ALPINE IMAGE
========================================================================

Very lightweight Linux image.

========================================================================

Popular for:
small containers.

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Smaller containers:
- faster deployment
- lower storage
- faster startup

========================================================================
DOCKER ENGINE
========================================================================

Software responsible for:
running containers.

========================================================================
CONTAINER ISOLATION
========================================================================

Each container has:
- separate filesystem
- separate processes
- separate networking

========================================================================

Applications do not interfere.

========================================================================
WHY CONTAINERS LIGHTWEIGHT?
========================================================================

Containers share:
host OS kernel.

========================================================================

Unlike virtual machines.

========================================================================
VIRTUAL MACHINE vs CONTAINER
========================================================================

VIRTUAL MACHINE
----------------
Includes full OS.

========================================================================

CONTAINER
-----------
Shares host OS kernel.

========================================================================
ADVANTAGE OF CONTAINERS
========================================================================

1. Faster startup
2. Lower memory usage
3. Higher density
4. Better portability

========================================================================
MICROSERVICES + DOCKER
========================================================================

Microservices heavily use:
Docker containers.

========================================================================

Each microservice packaged
as separate container.

========================================================================
EXAMPLE
========================================================================

User Service Container
Payment Service Container
Order Service Container

========================================================================
DOCKER NETWORKING
========================================================================

Containers communicate using:
virtual networks.

========================================================================

Supports:
- service communication
- isolation
- distributed deployment

========================================================================
DOCKER VOLUMES
========================================================================

Containers are temporary.

========================================================================

Need persistent storage for:
- databases
- uploads
- logs

========================================================================

Volumes provide:
persistent data storage.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Without volumes:
container deletion loses data.

========================================================================
DOCKER REGISTRY
========================================================================

Storage for Docker images.

========================================================================
POPULAR REGISTRIES
========================================================================

- :contentReference[oaicite:4]{index=4}
- :contentReference[oaicite:5]{index=5}

========================================================================
FLOW
========================================================================

Developer builds image
   ↓
Push image to registry
   ↓
Servers pull image
   ↓
Run containers

========================================================================
IMPORTANT BENEFIT
========================================================================

Same image runs:
everywhere consistently.

========================================================================
SCALING WITH DOCKER
========================================================================

Need more traffic handling?

========================================================================

Start more containers.

========================================================================

Very fast scaling possible.

========================================================================
THIS IS WHY
========================================================================

Containers ideal for:
cloud-native systems.

========================================================================
CLOUD + DOCKER
========================================================================

Cloud platforms heavily support:
containers.

========================================================================
EXAMPLES
========================================================================

- :contentReference[oaicite:6]{index=6}
- :contentReference[oaicite:7]{index=7}
- :contentReference[oaicite:8]{index=8}

========================================================================
DOCKER COMPOSE
========================================================================

Tool for running
multiple containers together.

========================================================================
EXAMPLE
========================================================================

Run:
- Spring Boot
- MySQL
- Redis
- Kafka

========================================================================

Together using single configuration.

========================================================================
IMPORTANT SYSTEM DESIGN UNDERSTANDING
========================================================================

Containers simplify:
deployment and scaling massively.

========================================================================
COMMON USE CASES
========================================================================

1. Microservices deployment
2. CI/CD pipelines
3. Local development
4. Cloud deployments
5. Testing environments

========================================================================
CI/CD + DOCKER
========================================================================

Containers support:
consistent automated deployments.

========================================================================
CHALLENGES OF DOCKER
========================================================================

1. Container orchestration
2. Networking complexity
3. Monitoring containers
4. Security management

========================================================================
THIS LEADS TO
========================================================================

Kubernetes.

========================================================================
WHY KUBERNETES NEEDED?
========================================================================

Managing few containers easy.

========================================================================

Managing thousands:
very difficult.

========================================================================

Kubernetes automates:
- deployment
- scaling
- recovery
- orchestration

========================================================================
REAL WORLD ARCHITECTURE
========================================================================

Users
   ↓
Load Balancer
   ↓
Docker Containers
   ↓
Microservices
   ↓
Redis / Kafka / Databases

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is Docker?

→ Containerization platform.

========================================================================

2. What is container?

→ Isolated lightweight application environment.

========================================================================

3. Difference between image and container?

Image
-------
template

Container
-----------
running instance

========================================================================

4. Why containers lightweight?

→ Share host OS kernel.

========================================================================

5. Difference between VM and container?

VM
----
full OS included

Container
-----------
shared kernel

========================================================================

6. Why Docker useful in microservices?

→ Consistent isolated deployments.

========================================================================

7. What is Dockerfile?

→ File defining image creation steps.

========================================================================

8. Why volumes important?

→ Persistent storage for containers.

========================================================================

9. Why Kubernetes needed?

→ Managing large-scale containers.

========================================================================
MOST IMPORTANT INTERVIEW LINE
========================================================================

Docker enables consistent,
portable,
and isolated application deployment
using lightweight containers
across different environments.

========================================================================

*/