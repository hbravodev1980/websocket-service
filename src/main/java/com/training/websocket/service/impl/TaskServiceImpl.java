package com.training.websocket.service.impl;

import com.training.websocket.dto.TaskChartDto;
import com.training.websocket.exception.BusinessException;
import com.training.websocket.model.Task;
import com.training.websocket.repository.TaskRepository;
import com.training.websocket.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskChartDto groupByStatus() {
        List<Task> tasks = taskRepository.findAll();

        Map<String, Long> quantityByStatus = tasks.stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));

        return TaskChartDto.builder()
                .status(quantityByStatus.keySet().stream().toList())
                .quantity(quantityByStatus.values().stream().toList())
                .build();
    }

    @Override
    public Task findById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Task not found"));
    }

    @Override
    public Task create(Task task) {
        Task taskCreate = taskRepository.save(task);
        simpMessagingTemplate.convertAndSend(
                "/topic/notifications",
                "Task with id [" + taskCreate.getId() + "] was created");
        return taskCreate;
    }

    @Override
    public Task update(Task task) {
        Task taskUpdate = taskRepository.save(task);
        simpMessagingTemplate.convertAndSend(
                "/topic/notifications",
                "Task with id [" + taskUpdate.getId() + "] was updated");
        return taskUpdate;
    }

    @Override
    public void deleteById(String id) {
        taskRepository.deleteById(id);
        simpMessagingTemplate.convertAndSend(
                "/topic/notifications",
                "Task with id [" + id + "] was deleted");
    }

}
