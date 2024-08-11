package com.mrodriguezdev.taskcli.command;

import com.mrodriguezdev.taskcli.model.Status;
import com.mrodriguezdev.taskcli.model.Task;


public interface Command {
    Task add(String description);
    void update(String description, Long id);
    void delete(Long id);
    void modifyStatus(Status status, Long id);
    String list();
    String listBy(Status status);
}
