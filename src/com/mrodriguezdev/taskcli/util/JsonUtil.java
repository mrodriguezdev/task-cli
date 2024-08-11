package com.mrodriguezdev.taskcli.util;

import com.mrodriguezdev.taskcli.exception.JsonSerializerException;
import com.mrodriguezdev.taskcli.model.Task;
import com.mrodriguezdev.taskcli.model.TaskJsonWrapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonUtil {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH");
    public static String toJson(Task task) {
        if (Objects.isNull(task)) {
            throw new JsonSerializerException("The object to serialize cannot be null");
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

    public static String toJson(TaskJsonWrapper taskJsonWrapper) {
        if (Objects.isNull(taskJsonWrapper)) {
            throw new JsonSerializerException("The object to serialize cannot be null");
        }

        List<Task> tasks = taskJsonWrapper.getTasks();
        StringBuilder jsonBuilder = new StringBuilder("{")
                .append("\n")
                .append("\t")
                .append("\"tasks\": [")
                .append("\n");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            jsonBuilder.append("\t\t")
                    .append(toJson(task));
            if (i < tasks.size() - 1) {
                jsonBuilder.append(",\n");
            }
        }
        jsonBuilder.append("\n\t]")
                .append("\n")
                .append("}");
        return jsonBuilder.toString();
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
            String key = entry[0].trim().replaceAll("^\"|\"$", "");
            String value = entry[1].trim().replaceAll("^\"|\"$", "");

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

    public static TaskJsonWrapper fromJson(String json) {
        if (Objects.isNull(json) || json.isEmpty()) {
            throw new JsonSerializerException("The JSON string cannot be null or empty");
        }

        TaskJsonWrapper taskJsonWrapper = new TaskJsonWrapper();
        List<Task> tasks = new ArrayList<>();

        try {
            json = json.trim();
            if (json.startsWith("{") && json.endsWith("}")) {
                json = json.substring(1, json.length() - 1);

                int tasksIndex = json.indexOf("\"tasks\":");
                if (tasksIndex != -1) {
                    String tasksArray = json.substring(tasksIndex + 8).trim();

                    if (tasksArray.startsWith("[") && tasksArray.endsWith("]")) {
                        tasksArray = tasksArray.substring(1, tasksArray.length() - 1);
                        String[] taskJsons = tasksArray.split("\\},\\{");

                        for (String taskJson : taskJsons) {
                            taskJson = taskJson.startsWith("{") ? taskJson : "{" + taskJson;
                            taskJson = taskJson.endsWith("}") ? taskJson : taskJson + "}";

                            Task task = fromJson(taskJson, Task.class);
                            tasks.add(task);
                        }
                    }
                }
            }

            taskJsonWrapper.setTasks(tasks);
        } catch (Exception e) {
            throw new JsonSerializerException("Error deserializing the JSON string");
        }

        return taskJsonWrapper;
    }
}
