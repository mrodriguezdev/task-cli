package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.model.Status;
import com.mrodriguezdev.taskcli.model.Task;

import java.util.List;

public class CommandImpl implements Command {
    String path = System.getProperty("user.dir");
    String tasksPath = path + "/tasks.json";
    String taskIdsPath = path + "/task_ids.txt";
    AddCommand addCommand = new AddCommand();
    @Override
    public Task add(String description) {
        return addCommand.add(description, tasksPath, taskIdsPath);
    }

    @Override
    public void update(String description, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void modifyStatus(Status status, Long id) {

    }

    @Override
    public List<Task> list() {
        return null;
    }

    @Override
    public List<Task> listBy(Status status) {
        return null;
    }
}
