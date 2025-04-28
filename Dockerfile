# Copyright 2025 Paion Data. All rights reserved.
FROM maven:3.8.3-openjdk-17 as build

RUN mkdir /notification-ws
COPY . /notification-ws
RUN cd /notification-ws && mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-jdk-alpine

LABEL maintainer="Jiaqi (Jack) Liu"
LABEL maintainer-email="jack20220723@gmail.com"

RUN apk add curl

COPY --from=build /notification-ws/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
