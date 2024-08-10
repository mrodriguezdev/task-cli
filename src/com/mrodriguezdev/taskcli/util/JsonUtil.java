package com.mrodriguezdev.taskcli.util;

import com.mrodriguezdev.taskcli.exception.JsonSerializerException;
import com.mrodriguezdev.taskcli.model.Task;

import java.time.LocalDateTime;
import java.util.Objects;

public class JsonUtil {
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

        json = json.replaceAll("[{}\"]", "");
        String[] keyValuePairs = json.split(",");

        Task task = new Task();
        for (String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            String key = entry[0].trim();
            String value = entry[1].trim();

            switch (key) {
                case "id" -> task.setId(Long.parseLong(value));
                case "description" -> task.setDescription(value);
                case "status" -> task.setStatus(value);
                case "createdAt" -> {
                    if ("null".equals(value)) {
                        task.setCreatedAt(null);
                    } else {
                        task.setCreatedAt(LocalDateTime.parse(value));
                    }
                }
                case "updatedAt" -> {
                    if ("null".equals(value)) {
                        task.setUpdatedAt(null);
                    } else {
                        task.setUpdatedAt(LocalDateTime.parse(value));
                    }
                }
            }
        }

        return clazz.cast(task);
    }
}
