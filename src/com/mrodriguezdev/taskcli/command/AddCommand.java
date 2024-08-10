package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.model.Task;
import com.mrodriguezdev.taskcli.util.FileUtil;
import com.mrodriguezdev.taskcli.util.JsonUtil;

public class AddCommand {
    public Task add(String description, String tasksPath, String taskIdsPath) {
        Task newTask = new Task(description);
        String content = FileUtil.readTxt(taskIdsPath);

        long newId;
        if (content.isEmpty()) {
            newId = 1;
        } else {
            newId = Long.parseLong(content.trim()) + 1;
        }

        newTask.setId(newId);
        String json = JsonUtil.toJson(newTask);
        FileUtil.createJson(tasksPath, json);
        FileUtil.createTxt(taskIdsPath, String.valueOf(newId));
        return newTask;
    }

}
