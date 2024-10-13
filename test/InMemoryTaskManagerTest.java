import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class InMemoryTaskManagerTest extends TaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask 1", "Test addNewTask 1 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));

        inMemoryTaskManager.addTask(task);
        final int taskId = task.getId();

        final Task savedTask = inMemoryTaskManager.getTaskById(Integer.toString(taskId));

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void historyTests() {
        Task task1 = new Task("Test addNewTask 1", "Test addNewTask 1 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2020, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));
        Task task2 = new Task("Test addNewTask 2", "Test addNewTask 2 description", inMemoryTaskManager.genId(),
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2021, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25));
        Task task3 = new Task("Test addNewTask 3", "Test addNewTask 3 description", inMemoryTaskManager.genId(),
                TaskStatus.DONE, LocalDateTime.parse("03.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15));
        Task task4 = new Task("Test addNewTask 4", "Test addNewTask 4 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2023, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task5 = new Task("Test addNewTask 5", "Test addNewTask 5 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2024, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task6 = new Task("Test addNewTask 6", "Test addNewTask 6 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2025, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10));
        Task task7 = new Task("Test addNewTask 7", "Test addNewTask 7 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2026, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));
        Task task8 = new Task("Test addNewTask 8", "Test addNewTask 8 description", inMemoryTaskManager.genId(),
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2027, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25));
        Task task9 = new Task("Test addNewTask 9", "Test addNewTask 9 description", inMemoryTaskManager.genId(),
                TaskStatus.DONE, LocalDateTime.parse("03.02.2028, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15));
        Task task10 = new Task("Test addNewTask 10", "Test addNewTask 10 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2029, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task11 = new Task("Test addNewTask 11", "Test addNewTask 11 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2030, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));

        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addTask(task3);
        inMemoryTaskManager.addTask(task4);
        inMemoryTaskManager.addTask(task5);
        inMemoryTaskManager.addTask(task6);
        inMemoryTaskManager.addTask(task7);
        inMemoryTaskManager.addTask(task8);
        inMemoryTaskManager.addTask(task9);
        inMemoryTaskManager.addTask(task10);

        for (int i = 2; i < 13; i++) {
            String id = Integer.toString(i);
            inMemoryTaskManager.getTaskById(id);
            if (i == 3) {
                assertEquals(2, inMemoryTaskManager.getHistory().size(), "Количество задач не верно.");
            }
            if (i == 4) {
                assertEquals(task4, inMemoryTaskManager.getHistory().getFirst(), "Последняя добавленная задача не совпадает");
            }

        }
        inMemoryTaskManager.getTaskById("9");
        assertEquals(task9, inMemoryTaskManager.getHistory().getFirst(), "Не обновляет последнюю добавленную задачу");
        inMemoryTaskManager.addTask(task11);
        inMemoryTaskManager.getTaskById("12");
        assertEquals(9, inMemoryTaskManager.getHistory().size(), "Количество задач не верно.");


    }
}