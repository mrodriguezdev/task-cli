package com.mrodriguezdev.taskcli.util;

import com.mrodriguezdev.taskcli.exception.JsonSerializerException;
import com.mrodriguezdev.taskcli.model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class JsonUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH");
    public static String toJson(Object o) {
        if (Objects.isNull(o)) {
            throw new JsonSerializerException("The object to serialize cannot be null");
        }

        if (!(o instanceof Task task)) {
            throw new JsonSerializerException("Only Task objects can be serialized");
        }

        String createdAt = task.getCreatedAt() != null ? task.getCreatedAt().toString() : "null";
        String updatedAt = task.getUpdatedAt() != null ? task.getUpdatedAt().toString() : "null";

        return String.format(
                "{\"id\":%d,\"description\":\"%s\",\"status\":\"%s\",\"createdAt\":\"%s\",\"updatedAt\":\"%s\"}",
                task.getId(),
                task.getDescription(),
                task.getStatus(),
                createdAt,
                updatedAt
        );
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            throw new JsonSerializerException("The provided JSON cannot be null or empty");
        }

        if (!clazz.equals(Task.class)) {
            throw new JsonSerializerException("Only Task objects can be deserialized");
        }

        Task task = new Task();

        json = json.trim();
        if (json.startsWith("{")) {
            json = json.substring(1);
        }
        if (json.endsWith("}")) {
            json = json.substring(0, json.length() - 1);
        }

        String[] keyValuePairs = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            String key = entry[0].trim().replaceAll("^\"|\"$", ""); // Remove surrounding quotes from key
            String value = entry[1].trim().replaceAll("^\"|\"$", ""); // Remove surrounding quotes from value

            switch (key) {
                case "id" -> task.setId(Long.parseLong(value));
                case "description" -> task.setDescription(value);
                case "status" -> task.setStatus(value);
                case "createdAt" -> {
                    if ("null".equals(value)) {
                        task.setCreatedAt(null);
                    } else {
                        task.setCreatedAt(LocalDateTime.parse(value, formatter));
                    }
                }
                case "updatedAt" -> {
                    if ("null".equals(value)) {
                        task.setUpdatedAt(null);
                    } else {
                        task.setUpdatedAt(LocalDateTime.parse(value, formatter));
                    }
                }
            }
        }

        return clazz.cast(task);
    }
}
