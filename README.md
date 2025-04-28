Notification Webservice
=======================

__Notification Webservice__ is a full-fledged Spring Boot application that lets us set up, with minimal effort, a
webservice that sends real-time notifications to various client. It, currently, supports the notifications to

- DingTalk (阿里钉钉)

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
- Sending a DingTalk notification:

  ```console
  curl --location 'localhost:8080/dingding/createNotification' --header 'Content-Type: application/json' --data '{
      "my notification"
  }' -v
  ```

Development
-----------

Running tests:

```console
mvn clean verify
```
