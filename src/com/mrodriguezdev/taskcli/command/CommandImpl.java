package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.model.Status;
import com.mrodriguezdev.taskcli.model.Task;

public class CommandImpl implements Command {
    String path = System.getProperty("user.dir");
    String tasksPath = path + "/tasks.json";
    String taskIdsPath = path + "/task_ids.txt";
    AddCommand addCommand = new AddCommand();
    UpdateCommand updateCommand = new UpdateCommand();
    MarkInProgressCommand markInProgressCommand = new MarkInProgressCommand();
    MarkDoneCommand markDoneCommand = new MarkDoneCommand();
    DeleteCommand deleteCommand = new DeleteCommand();
    ListCommand listCommand = new ListCommand();

    @Override
    public Task add(String description) {
        return addCommand.add(description, tasksPath, taskIdsPath);
    }

    @Override
    public void update(String description, Long id) {
        updateCommand.update(description, id, tasksPath);
    }

    @Override
    public void delete(Long id) {
        deleteCommand.delete(id, tasksPath);
    }

    @Override
    public void modifyStatus(Status status, Long id) {
        if (status.equals(Status.IN_PROGRESS)) markInProgressCommand.modify(status.get(), id, tasksPath);
        else if (status.equals(Status.DONE)) markDoneCommand.modify(status.get(), id, tasksPath);
    }

    @Override
    public String list() {
        return listCommand.list(tasksPath);
    }

    @Override
    public String listBy(Status status) {
        return listCommand.listBy(tasksPath, status);
    }
}
