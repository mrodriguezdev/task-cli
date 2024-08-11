package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.model.Task;
import com.mrodriguezdev.taskcli.model.TaskJsonWrapper;
import com.mrodriguezdev.taskcli.util.FileUtil;
import com.mrodriguezdev.taskcli.util.JsonUtil;

public class AddCommand {
    public Task add(String description, String tasksPath, String taskIdsPath) {
        Task newTask = new Task(description);

        String content = FileUtil.readTxtAsString(taskIdsPath);
        long newId = content.isEmpty() ? 1 : Long.parseLong(content.trim()) + 1;
        newTask.setId(newId);

        String contentTasks = FileUtil.readJsonAsString(tasksPath);
        TaskJsonWrapper taskJsonWrapper = contentTasks.isEmpty()
                ? new TaskJsonWrapper()
                : JsonUtil.fromJson(contentTasks);

        taskJsonWrapper.getTasks().add(newTask);
        String json = JsonUtil.toJson(taskJsonWrapper);
        FileUtil.create(tasksPath, json);

        FileUtil.create(taskIdsPath, String.valueOf(newId));

        return newTask;
    }

}
