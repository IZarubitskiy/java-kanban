import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class InMemoryTaskManagerTest {
    Task task = new Task("Test addNewTask 0", "Test addNewTask 0 description", TaskStatus.NEW);
    Task task1 = new Task("Test addNewTask 1", "Test addNewTask 1 description", TaskStatus.NEW);
    Task task2 = new Task("Test addNewTask 2", "Test addNewTask 2 description", TaskStatus.NEW);
    Task task3 = new Task("Test addNewTask 3", "Test addNewTask 3 description", TaskStatus.NEW);
    Task task4 = new Task("Test addNewTask 4", "Test addNewTask 4 description", TaskStatus.NEW);
    Task task5 = new Task("Test addNewTask 5", "Test addNewTask 5 description", TaskStatus.NEW);
    Task task6 = new Task("Test addNewTask 6", "Test addNewTask 6 description", TaskStatus.NEW);
    Task task7 = new Task("Test addNewTask 7", "Test addNewTask 7 description", TaskStatus.NEW);
    Task task8 = new Task("Test addNewTask 8", "Test addNewTask 8 description", TaskStatus.NEW);
    Task task9 = new Task("Test addNewTask 9", "Test addNewTask 9 description", TaskStatus.NEW);
    Task task10 = new Task("Test addNewTas 10k", "Test addNewTask 10 description", TaskStatus.NEW);

    @Test
    void addNewTask() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        inMemoryTaskManager.addTask(task);
        final int taskId = inMemoryTaskManager.getId(task);

        final Task savedTask = inMemoryTaskManager.getTaskById(Integer.toString(taskId));

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void history (){
        InMemoryTaskManager taskManagerHystory = new InMemoryTaskManager();
        assertEquals(0, taskManagerHystory.getHistory().size(), "Количество задач не верно.");
        taskManagerHystory.addTask(task);
        taskManagerHystory.addTask(task1);
        taskManagerHystory.addTask(task2);
        taskManagerHystory.addTask(task3);
        taskManagerHystory.addTask(task4);
        taskManagerHystory.addTask(task5);
        taskManagerHystory.addTask(task6);
        taskManagerHystory.addTask(task7);
        taskManagerHystory.addTask(task8);
        taskManagerHystory.addTask(task9);
        taskManagerHystory.addTask(task10);

        for (int i = 2; i < 13; i++) {
            System.out.println(i);
            String id =Integer.toString(i);
            taskManagerHystory.getTaskById(id);
            if (i == 3){
                assertEquals(2, taskManagerHystory.getHistory().size(), "Количество задач не верно.");
            }

            if (i == 6){
                assertEquals(task4, taskManagerHystory.getHistory().getLast(), "Последняя добавленная задача не совпадает");
            }

        }
        assertEquals(task1, taskManagerHystory.getHistory().getFirst(), "Первая задача, после превышения лимита не совпадает");
    }

}