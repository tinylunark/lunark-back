# Lunark

![Build Status](https://github.com/tinylunark/lunark-back/actions/workflows/maven.yml/badge.svg?branch=develop)

Lunark is a rental accommodation booking application.

This repo contains the backend of the application.

## Prerequisites

- Java 19
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/community-edition#/download)
- [Docker Compose](https://docs.docker.com/compose/install/) (already included with some Docker installations)

## Getting started

To start the app run:
```
mvn spring-boot:run
```

## Features

- JWT-based authentication
- Email verification
- Accommodation reviews
- Host reviews
- Notifications
- Moderation
    - Hosts can report inappropriate comments
    - Hosts can report their guests for bad behaviour
    - Guests can report hosts of properties they have stayed at
    - Reviews are approved by an admin before they are shown
- Revenue reports for hosts

## Built with

- Spring Boot
- PostgresSQL (primary database)
- H2 (testing database)
- WebSocket

## Team members
| Role | Name | Id 
---|---|---
|Student 1| Momir Milutinović | SV 39/2021 
|Student 2| Dušan Lečić | SV 80/2021 
|Student 3| Vladislav Radović | SV 27/2021 
