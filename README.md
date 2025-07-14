Horten
======

![Java Version Badge][Java Version Badge]
[![API Doc Badge]][API Doc URL]
[![Docker Hub][Docker Pulls Badge]][Docker Hub URL]
[![Apache License Badge]][Apache License, Version 2.0]

__Horten__ is a full-fledged Spring Boot application that lets us set up, with minimal effort, a webservice that sends
real-time notifications to various client. It, currently, supports the notifications to

- DingTalk (阿里钉钉)
- Email SMTP server

Horten is designed for:

- real-time messaging capabilities
- performance-wise optimization

It is NOT for:

- security aspect, such as Authentication or Authorization
- any business layer logics, such as formating a notification message

For this reason, Horten is suitable for a microservice architecture.

Documentation
-------------

### Running in Docker

Please make sure Docker is installed
([_Installing Docker_](https://docker.qubitpi.org/desktop/setup/install/mac-install/)) first. Then 

> [!TIP]
>
> - ⚠️ Those surrounded by `<` and `>` have to be replaced by the actual values
> - For instructions on how to obtain the __Dingding access token__ used above, please refer to
>   [the DingTalk documentation](https://open.dingtalk.com/document/orgapp/custom-robot-access)
> - In the email notification setup, we assume
> 
>   - [relaying emails via Gmail's SMTP server](https://support.google.com/a/answer/2956491)
>   - the relaying port is one of the supported Gmail SMTP ports, such as _587 for STARTTLS_
>   - the email of the relay user, i.e. the email sender, is _mysender@gmail.com_
>   - the relay password is _myGoogleAppPassword_. Note that this should be the relay user's
>     [App Password](https://support.google.com/accounts/answer/185833), not their personal gmail account password
>   - the email of the person receiving the email notification is _myreceiver@gmail.com_

```console
# Dingding notification
export HORTEN_DINGDING_ACCESS_TOKEN=<DingDing access token>

# Email notification via SMTP
export SMTP_SERVER_DOMAIN=smtp.gmail.com
export SMTP_SERVER_PORT=587
export SENDER_EMAIL=mysender@gmail.com
export SENDER_EMAIL_PASSWORD=myGoogleAppPassword
export RECEIVER_EMAIL=myreceiver@gmail.com
```

then spin up Docker container with:

```console
docker run -it -p 8080:8080 \
    -e HORTEN_DINGDING_ACCESS_TOKEN=$HORTEN_DINGDING_ACCESS_TOKEN \
    -e SMTP_SERVER_DOMAIN=$SMTP_SERVER_DOMAIN \
    -e SMTP_SERVER_PORT=$SMTP_SERVER_PORT \
    -e SENDER_EMAIL=$SENDER_EMAIL \
    -e SENDER_EMAIL_PASSWORD=$SENDER_EMAIL_PASSWORD \
    -e RECEIVER_EMAIL=$RECEIVER_EMAIL \
    jack20191124/horten
```

The default port is 8080.

- Healthcheck: http://localhost:8080/actuator/health
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Sending a DingTalk notification:

  ```console
  curl --location 'localhost:8080/dingding/createNotification' --header 'Content-Type: application/json' --data '{
      "my notification"
  }' -v
  ```

> [!CAUTION]
>
> When the webservice shall be contacted through a
> [Docker Compose internal network](https://docker.qubitpi.org/compose/how-tos/networking/), it's URL must be prefixed
> with __http://__. For example
>
> ```yaml
> version: "3.9"
> services:
>   my-service:
>     image: my-image
>     depends_on:
>       horten:
>         condition: service_healthy
>     ...
>
>   horten:
>     image: jack20191124/horten
>     expose:
>       - 8080
>     ...
> ```
>
> Then in the `my-service`, the `horten` API URL has to be "__http://horten:8080__"

### Running from Code

```console
git clone git@github.com:QubitPi/Horten.git
cd Horten
mvn clean package

export HORTEN_DINGDING_ACCESS_TOKEN=<DingDing access token>

export SMTP_SERVER_DOMAIN=smtp.gmail.com
export SMTP_SERVER_PORT=587
export SENDER_EMAIL=mysender@gmail.com
export SENDER_EMAIL_PASSWORD=myGoogleAppPassword
export RECEIVER_EMAIL=myreceiver@gmail.com

java -jar target/horten-0.0.1-SNAPSHOT.jar
```

> [!TIP]
>
> - ⚠️ Those surrounded by `<` and `>` have to be replaced by the actual values
> - For instructions on how to obtain the __Dingding access token__ used above, please refer to
>   [the DingTalk documentation](https://open.dingtalk.com/document/orgapp/custom-robot-access)
> - In the email notification setup, we assume
>
>   - [relaying emails via Gmail's SMTP server](https://support.google.com/a/answer/2956491)
>   - the relaying port is one of the supported Gmail SMTP ports, such as _587 for STARTTLS_
>   - the email of the relay user, i.e. the email sender, is _mysender@gmail.com_
>   - the relay password is _myGoogleAppPassword_. Note that this should be the relay user's
>     [App Password](https://support.google.com/accounts/answer/185833), not their personal gmail account password
>   - the email of the person receiving the email notification is _myreceiver@gmail.com_

The default port is 8080.

- Healthcheck: http://localhost:8080/actuator/health
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Sending a DingTalk notification:

  ```console
  curl --location 'localhost:8080/dingding/createNotification' --header 'Content-Type: application/json' --data '{
      "my notification"
  }' -v
  ```

Development
-----------

### Prerequisites

- JDK 17
- Maven
- Docker

### Running tests

```console
mvn clean verify
```

License
-------

The use and distribution terms for [Horten]() are covered by the [Apache License, Version 2.0].

[Apache License Badge]: https://img.shields.io/badge/Apache%202.0-F25910.svg?style=for-the-badge&logo=Apache&logoColor=white
[Apache License, Version 2.0]: https://www.apache.org/licenses/LICENSE-2.0
[API Doc Badge]: https://img.shields.io/badge/Open%20API-Swagger-85EA2D.svg?style=for-the-badge&logo=openapiinitiative&logoColor=white&labelColor=6BA539
[API Doc URL]: https://springdoc.org/

[Docker Pulls Badge]: https://img.shields.io/docker/pulls/jack20191124/horten?style=for-the-badge&logo=docker&color=2596EC
[Docker Hub URL]: https://hub.docker.com/r/jack20191124/horten

[Java Version Badge]: https://img.shields.io/badge/Java-17-brightgreen?style=for-the-badge&logo=OpenJDK&logoColor=white
