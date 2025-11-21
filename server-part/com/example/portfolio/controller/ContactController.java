package com.example.portfolio.controller;

import com.example.portfolio.model.ContactForm;
import com.example.portfolio.service.ContactService;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;

public class ContactController implements HttpHandler {
    private final ContactService contactService = new ContactService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            handlePost(exchange);
        } else {
            sendResponse(exchange, "Method Not Allowed", 405);
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes());
        ContactForm form = parseFormData(body);

        String response;
        int status;

        if (contactService.processContactForm(form)) {
            response = "{\"status\":\"success\",\"message\":\"Сообщение отправлено!\"}";
            status = 200;
        } else {
            response = "{\"status\":\"error\",\"message\":\"Неверные данные формы\"}";
            status = 400;
        }

        exchange.getResponseHeaders().set("Content-Type", "application/json");
        sendResponse(exchange, response, status);
    }

    private ContactForm parseFormData(String formData) {
        ContactForm form = new ContactForm();
        for (String pair : formData.split("&")) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = java.net.URLDecoder.decode(keyValue[1], java.nio.charset.StandardCharsets.UTF_8);

                switch (key) {
                    case "name": form.setName(value); break;
                    case "email": form.setEmail(value); break;
                    case "message": form.setMessage(value); break;
                }
            }
        }
        return form;
    }

    private void sendResponse(HttpExchange exchange, String response, int status) throws IOException {
        exchange.sendResponseHeaders(status, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}