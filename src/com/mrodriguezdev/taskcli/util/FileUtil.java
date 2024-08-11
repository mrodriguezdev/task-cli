package com.mrodriguezdev.taskcli.util;

import com.mrodriguezdev.taskcli.exception.FileUtilException;
import com.mrodriguezdev.taskcli.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static void createTxt(String name, String content) {
        File file = new File(name);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print(content);
        } catch (IOException e) {
            throw new FileUtilException("Error creating the file: " + name, e);
        }
    }

    public static void create(String name, String content) {
        File file = new File(name);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print(content);
        } catch (IOException e) {
            throw new FileUtilException("Error creating the file: " + name, e);
        }
    }

    public static String readTxt(String name) {
        StringBuilder sb = new StringBuilder();
        File file = new File(name);

        if (!file.exists()) {
            FileUtil.createTxt(name, "");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new FileUtilException("Error reading the file: " + name, e);
        }

        return sb.toString().trim();
    }

    public static List<Task> readJson(String name) {
        File file = new File(name);
        List<Task> tasks = new ArrayList<>();

        try {
            if (file.exists() && file.length() > 0) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (!line.isEmpty()) {
                            Task task = JsonUtil.fromJson(line, Task.class);
                            tasks.add(task);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new FileUtilException("Error reading the file: " + name, e);
        }

        return tasks;
    }

    public static String readJsonAsString(String name) {
        File file = new File(name);
        StringBuilder jsonContent = new StringBuilder();

        try {
            if (file.exists() && file.length() > 0) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonContent.append(line.trim());
                    }
                }
            }
        } catch (IOException e) {
            throw new FileUtilException("Error reading the file: " + name, e);
        }

        return jsonContent.toString();
    }

}
