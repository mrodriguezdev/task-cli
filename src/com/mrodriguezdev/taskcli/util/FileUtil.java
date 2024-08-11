package com.mrodriguezdev.taskcli.util;

import com.mrodriguezdev.taskcli.exception.FileUtilException;

import java.io.*;

public class FileUtil {
    public static void create(String name, String content) {
        File file = new File(name);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print(content);
        } catch (IOException e) {
            throw new FileUtilException("Error creating the file: " + name, e);
        }
    }

    public static String readTxtAsString(String name) {
        StringBuilder sb = new StringBuilder();
        File file = new File(name);

        if (!file.exists()) {
            FileUtil.create(name, "");
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
