package com.example.portfolio;

import com.example.portfolio.config.ServerConfig;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(ServerConfig.getServerAddress(), 0);

        ServerConfig.configureRoutes(server);
        ServerConfig.configureExecutor(server);

        server.start();
        System.out.println("✅ Сервер запущен на http://localhost:8080");
    }
}