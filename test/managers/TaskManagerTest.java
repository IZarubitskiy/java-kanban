package managers;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

    abstract T getManager();

    Epic epic1 = new Epic("Test addNewEpic 1", "Test addNewEpic 1 description", 1, TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60), new ArrayList<>(), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));

    SubTask subTask1 = new SubTask("Test add NewSubTask 1", "Test addNewTask 1 description", 2, TaskStatus.NEW, LocalDateTime.parse("01.06.2020, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20), epic1.getId());

    SubTask subTask2 = new SubTask("Test add NewSubTask 2", "Test addNewTask 2 description", 3, TaskStatus.NEW, LocalDateTime.parse("02.07.2021, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25), epic1.getId());
    SubTask subTask3 = new SubTask("Test add NewSubTask 3", "Test addNewTask 3 description", 4, TaskStatus.NEW, LocalDateTime.parse("03.08.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15), epic1.getId());

    Task task1 = new Task("Test addNewTask 1", "Test addNewTask 1 description", 5, TaskStatus.NEW, LocalDateTime.parse("01.02.2020, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));
    Task task2 = new Task("Test addNewTask 2", "Test addNewTask 2 description", 6, TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2021, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25));
    Task task3 = new Task("Test addNewTask 3", "Test addNewTask 3 description", 7, TaskStatus.IN_PROGRESS, LocalDateTime.parse("03.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15));
    Task task4 = new Task("Test addNewTask 4", "Test addNewTask 4 description", 8, TaskStatus.NEW, LocalDateTime.parse("01.02.2023, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
    Task task5 = new Task("Test addNewTask 5", "Test addNewTask 5 description", 9, TaskStatus.NEW, LocalDateTime.parse("01.02.2024, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
    Task task6 = new Task("Test addNewTask 6", "Test addNewTask 6 description", 10, TaskStatus.NEW, LocalDateTime.parse("01.02.2025, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10));
    Task task7 = new Task("Test addNewTask 7", "Test addNewTask 7 description", 11, TaskStatus.NEW, LocalDateTime.parse("01.02.2026, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));
    Task task8 = new Task("Test addNewTask 8", "Test addNewTask 8 description", 12, TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2027, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25));
    Task task9 = new Task("Test addNewTask 9", "Test addNewTask 9 description", 13, TaskStatus.DONE, LocalDateTime.parse("03.02.2028, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15));
    Task task10 = new Task("Test addNewTask 10", "Test addNewTask 10 description", 14, TaskStatus.NEW, LocalDateTime.parse("01.02.2029, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
    Task task11 = new Task("Test addNewTask 11", "Test addNewTask 11 description", 15, TaskStatus.NEW, LocalDateTime.parse("01.02.2030, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));

    @Test
    void historyTests() {
    }

    @Test
    void addNewTask() {
        TaskManager manager = getManager();
        Task task = new Task("Test addNewTask 0", "Test addNewTask 0 description", 16, TaskStatus.NEW, LocalDateTime.parse("01.02.2024, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));

        manager.addTask(task);
        final int taskId = task.getId();

        final Task savedTask = manager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = manager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void epicStatusNew() {
        TaskManager manager = getManager();
        manager.addEpic(epic1);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        assertEquals(TaskStatus.NEW, epic1.getStatusTask(), "Статус обновлен не верно");
    }


    @Test
    void epicStatusNewAndDone() {
        TaskManager manager = getManager();
        manager.addEpic(epic1);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        manager.updateSubTask(subTask2.getId(), 2);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatusTask(), "Статус обновлен не верно");

    }

    @Test
    void epicStatusInProgress() {
        TaskManager manager = getManager();
        manager.addEpic(epic1);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        manager.updateSubTask(subTask1.getId(), 1);
        manager.updateSubTask(subTask2.getId(), 1);
        manager.updateSubTask(subTask3.getId(), 1);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatusTask(), "Статус обновлен не верно");
    }

    @Test
    void epicStatusDone() {
        TaskManager manager = getManager();
        manager.addEpic(epic1);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);


        manager.updateSubTask(subTask1.getId(), 2);
        manager.updateSubTask(subTask2.getId(), 2);
        manager.updateSubTask(subTask3.getId(), 2);
        assertEquals(TaskStatus.DONE, epic1.getStatusTask(), "Статус обновлен не верно");
    }

    @Test
    void epicToSubtaskRelation() {
        TaskManager manager = getManager();
        manager.addEpic(epic1);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);


        assertEquals(manager.getLastEpicId(), subTask1.getEpicId(), "Номер эпика указан неверно");
        assertNotNull(epic1.getSubTasks(), "У эпика нет подзадач");
        assertEquals(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())), epic1.getSubTasks(), "Задачи у эпика не совпадают");
    }

    @Test
    void getLastEpicId() {
        TaskManager manager = getManager();
        manager.addEpic(epic1);
        manager.addSubTask(subTask1);


        assertEquals(manager.getLastEpicId(), epic1.getId(), "Последний номер эпика не совпадает");
    }

    @Test
    void genId() {
        TaskManager manager = getManager();
        assertNotEquals(manager.getIdTaskManager(), manager.genId(), "ID не создается");
    }

    @Test
    void getTasks() {
        TaskManager manager = getManager();
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        assertEquals(new ArrayList<>(List.of(task1, task2, task3)), manager.getTasks(), "Не возвращает добавленные задачи");
        assertNotEquals(new ArrayList<>(List.of(task2, task2, task3)), manager.getTasks(), "Считает равным разное");
    }

    @Test
    void deleteTasks() {
        TaskManager manager = getManager();
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        assertNotEquals(new ArrayList<>(), manager.getTasks(), "Список пуст");
        manager.deleteTasks();
        assertEquals(new ArrayList<>(), manager.getTasks(), "Список пуст");
    }

    @Test
    void deleteTaskById() {
        TaskManager manager = getManager();
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        assertEquals(task2, manager.getTaskById(task2.getId()), "Задача не существует");
        manager.deleteTaskById(task2.getId());
        assertNull(manager.getTaskById(task2.getId()), "Задача все еще существует");
    }

}