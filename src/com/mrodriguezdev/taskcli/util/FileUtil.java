package com.mrodriguezdev.taskcli.util;

import java.io.*;

public class FileUtil {
    public static void createTxt(String name, String content) {
        File file = new File(name);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.print(content);
        } catch (IOException e) {
            throw new RuntimeException("Error creating the file: " + name, e);
        }
    }

    public static void createJson(String name, String content) {
        File file = new File(name);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(content);
        } catch (IOException e) {
            throw new RuntimeException("Error creating the file: " + name, e);
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
            throw new RuntimeException("Error reading the file: " + name, e);
        }

        return sb.toString().trim();
    }

}
