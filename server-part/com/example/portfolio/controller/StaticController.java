package com.example.portfolio.controller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;

public class StaticController implements HttpHandler {
    private static final String STATIC_ROOT = "static";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if ("/".equals(path)) {
            path = "/index.html";
        }

        File file = new File(STATIC_ROOT + path);

        if (!file.exists() || !file.isFile()) {  // - Ошибка по стольку по скольку, он практически не будет вызываться, но по нормам, она должна быть
            sendResponse(exchange, "404 - Not Found", 404);
            return;
        }

        String mimeType = getMimeType(path);
        exchange.getResponseHeaders().set("Content-Type", mimeType);
        exchange.sendResponseHeaders(200, file.length());

        try (OutputStream os = exchange.getResponseBody();
             FileInputStream fis = new FileInputStream(file)) {
            fis.transferTo(os);
        }
    }

    private String getMimeType(String path) {
        if (path.endsWith(".css")) return "text/css";
        if (path.endsWith(".js")) return "application/javascript";
        if (path.endsWith(".html")) return "text/html";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".svg")) return "image/svg+xml";
        return "text/plain";
    }

    private void sendResponse(HttpExchange exchange, String response, int status) throws IOException {
        exchange.sendResponseHeaders(status, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}