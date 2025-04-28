Notification Webservice
=======================

[![API Doc Badge]][API Doc URL]
[![Apache License Badge]][Apache License, Version 2.0]

__Notification Webservice__ is a full-fledged Spring Boot application that lets us set up, with minimal effort, a
webservice that sends real-time notifications to various client. It, currently, supports the notifications to

- DingTalk (阿里钉钉)

The Notification Webservice is designed for:

- real-time messaging capabilities
- performance-wise optimization

It is NOT for:

- security aspect, such as Authentication or Authorization
- any business layer logics, such as formating a notification message

For this reason, Notification Webservice is suitable for a microservice architecture.

Documentation
-------------

```console
git clone git@github.com:QubitPi/notification-ws.git
cd notification-ws
mvn clean package

export NOTIFICATION_WS_DINGDING_ACCESS_TOKEN=<DingDing access token>

java -jar target/notification-ws-0.0.1-SNAPSHOT.jar
```

> [!TIP]
>
> For instructions on how to obtain the __Dingding access token__ used above, please refer to
> [the DingTalk documentation](https://open.dingtalk.com/document/orgapp/custom-robot-access)

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

The use and distribution terms for [notification-ws]() are covered by the [Apache License, Version 2.0].

[Apache License Badge]: https://img.shields.io/badge/Apache%202.0-F25910.svg?style=for-the-badge&logo=Apache&logoColor=white
[Apache License, Version 2.0]: https://www.apache.org/licenses/LICENSE-2.0

[API Doc Badge]: https://img.shields.io/badge/Open%20API-Swagger-85EA2D.svg?style=for-the-badge&logo=openapiinitiative&logoColor=white&labelColor=6BA539
[API Doc URL]: https://springdoc.org/
