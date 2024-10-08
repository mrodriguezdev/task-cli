package com.mrodriguezdev.taskcli.util;

import com.mrodriguezdev.taskcli.exception.JsonSerializerException;
import com.mrodriguezdev.taskcli.model.Task;
import com.mrodriguezdev.taskcli.model.TaskJsonWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtil {
    public static String toJson(Task task) {
        if (Objects.isNull(task)) {
            throw new JsonSerializerException("El objeto a serializar no puede ser nulo.");
        }

        String createdAt = task.getCreatedAt() != null ? LocalDateTimeUtil.format(task.getCreatedAt()) : "null";
        String updatedAt = task.getUpdatedAt() != null ? LocalDateTimeUtil.format(task.getUpdatedAt()) : "null";

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
            throw new JsonSerializerException("El objeto a serializar no puede ser nulo.");
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
            throw new JsonSerializerException("El JSON proporcionado no puede ser nulo o vacío.");
        }

        if (!clazz.equals(Task.class)) {
            throw new JsonSerializerException("Solo se pueden deserializar objetos de tipo Task.");
        }

        Task task = new Task();

        json = json.trim();
        if (json.startsWith("{")) {
            json = json.substring(1);
        }
        if (json.endsWith("}")) {
            json = json.substring(0, json.length() - 1);
        }

        Pattern pattern = Pattern.compile("\"(.*?)\":(\\s*\"(.*?)\"|\\s*([^,}]*))");
        Matcher matcher = pattern.matcher(json);

        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);
            value = value.replaceAll("^\"|\"$", "").trim();

            switch (key) {
                case "id" -> task.setId(Long.parseLong(value));
                case "description" -> task.setDescription(value);
                case "status" -> task.setStatus(value);
                case "createdAt" -> {
                    if ("null".equals(value)) {
                        task.setCreatedAt(null);
                    } else {
                        task.setCreatedAt(LocalDateTimeUtil.parse(value));
                    }
                }
                case "updatedAt" -> {
                    if ("null".equals(value)) {
                        task.setUpdatedAt(null);
                    } else {
                        task.setUpdatedAt(LocalDateTimeUtil.parse(value));
                    }
                }
            }
        }

        return clazz.cast(task);
    }

    public static TaskJsonWrapper fromJson(String json) {
        if (Objects.isNull(json) || json.isEmpty()) {
            throw new JsonSerializerException("La cadena JSON no puede ser nula o vacía.");
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
            throw new JsonSerializerException("Error al deserializar la cadena JSON.");
        }

        return taskJsonWrapper;
    }
}
