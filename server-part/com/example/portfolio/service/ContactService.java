package com.example.portfolio.service;

import com.example.portfolio.model.ContactForm;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContactService {
    private static final String LOG_FILE = "logs/application.log";

    public boolean processContactForm(ContactForm form) {
        if (!form.isValid()) {
            return false;
        }

        logContact(form);
        return true;
    }

    private void logContact(ContactForm form) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String logEntry = String.format("[%s] Сообщение от: %s (%s)%nТекст: %s%n---%n",
                timestamp, form.getName(), form.getEmail(), form.getMessage());

        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(logEntry);
            System.out.println("📨 Логировано новое сообщение");
        } catch (IOException e) {
            System.err.println("❌ Ошибка записи в лог: " + e.getMessage());
        }
    }
}