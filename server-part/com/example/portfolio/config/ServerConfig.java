package com.example.portfolio.config;

import com.example.portfolio.controller.ContactController;
import com.example.portfolio.controller.StaticController;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class ServerConfig {
    private static final int PORT = 8081;  // - Порт 8081, т.к порт 8080 занят qb torrent (увы)
    private static final int THREAD_POOL_SIZE = 10;

    public static InetSocketAddress getServerAddress() {
        return new InetSocketAddress(PORT);
    }

    public static void configureRoutes(HttpServer server) {
        server.createContext("/", new StaticController());
        server.createContext("/api/contact", new ContactController());
        server.createContext("/css/", new StaticController());
        server.createContext("/js/", new StaticController());
        server.createContext("/images/", new StaticController());
    }

    public static void configureExecutor(HttpServer server) {
        server.setExecutor(Executors.newFixedThreadPool(THREAD_POOL_SIZE));
    }
}