package com.training.websocket.service;

import com.training.websocket.dto.TaskChartDto;
import com.training.websocket.model.Task;

import java.util.List;
import java.util.Map;

public interface TaskService {
    List<Task> findAll();

    TaskChartDto groupByStatus();

    Task findById(String id);

    Task create(Task task);

    Task update(Task task);

    void deleteById(String id);

}
