package com.edu.planner.unit.service;

import com.edu.planner.dto.task.TaskResponse;
import com.edu.planner.entity.Task;
import com.edu.planner.entity.UserEntity;
import com.edu.planner.repositories.TaskRepository;
import com.edu.planner.services.TaskService;
import com.edu.planner.utils.Enums;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceTest {
    
    @Mock
    private TaskRepository taskRepository;
    
    @InjectMocks
    private TaskService taskService;
    
    @Test
    void testGetUserTasks_ShouldReturnTasks () {
        // Arrange (prepare test data)
        UserEntity user = new UserEntity("Eduardo", "edu@gmail.com", "123456");
        List<Task> mockTasks = List.of(new Task("teste task", Enums.TaskType.ONE_TIME, user));
        
        when(taskRepository.findAllByOwner(user)).thenReturn(mockTasks);
        
        // Act (execute the method)
        List<TaskResponse> result = taskService.getUserTasks(user);
        
        // Assert (verify the result)
        assertEquals(1, result.size());
    }
}
