/*
 * Copyright 2025 Jiaqi Liu. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qubitpi.ws.notification.dingding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Responsible for the real-time message feed generation sent to DingDing.
 */
@RestController
@RequestMapping("/dingding")
public class Controller {

    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private static final String FAILED_NOTIFICATION_MESSAGE = "The notification failed. Please contact backend team";

    @Value("${notification-ws.dingding.access-token}")
    private String accessToken;

    /**
     * Endpoint for creating a new notification.
     *
     * @param notification  An object that specifies all the fields of a notification
     *
     * @return  The created appointment entity
     */
    @Operation(
            tags = "DingTalk",
            summary = "Sends a notification to DingDing"
    )
    @PostMapping(value = "/createNotification", produces = "application/json")
    String createNotification(@RequestBody(required = true) final String notification) {
        final ObjectNode text = JSON_MAPPER.createObjectNode();
        text.put("title", "New Notification");
        text.put("text", notification);

        final ObjectNode message = JSON_MAPPER.createObjectNode();

        final String markdown = "markdown";
        message.put("msgtype", markdown);
        message.set(markdown, text);

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format("https://oapi.dingtalk.com/robot/send?access_token=%s", accessToken)))
                    .POST(HttpRequest.BodyPublishers.ofString(JSON_MAPPER.writeValueAsString(message)))
                    .setHeader("Content-Type", "application/json")
                    .build();
        } catch (final JsonProcessingException exception) {
            final String errorMessage = String.format("'%s' is not a valid JSON object", message);
            LOG.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        try {
            return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (final IOException exception) {
            final String errorMessage = String.format(
                    "I/O error while sending Dingding webhook request: %s",
                    exception.getMessage()
            );
            LOG.error(errorMessage, exception);
            throw new IllegalStateException(FAILED_NOTIFICATION_MESSAGE, exception);
        } catch (final InterruptedException exception) {
            final String errorMessage = String.format(
                    "Unexpected abort on Dingding webhook request: %s",
                    exception.getMessage()
            );
            LOG.error(errorMessage, exception);
            throw new IllegalStateException(FAILED_NOTIFICATION_MESSAGE, exception);
        }
    }
}
