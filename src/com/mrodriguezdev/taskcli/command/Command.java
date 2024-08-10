package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.model.Status;
import com.mrodriguezdev.taskcli.model.Task;

import java.util.List;

public interface Command {
    Task add(String description);
    void update(String description, Long id);
    void delete(Long id);
    void modifyStatus(Status status, Long id);
    List<Task> list();
    List<Task> listBy(Status status);
}
