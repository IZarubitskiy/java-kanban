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

abstract class TaskManagerTest<T extends TaskManager>{

    abstract TaskManager getManager();

    Epic epic1 = new Epic("Test addNewEpic 1", "Test addNewEpic 1 description", getManager().genId(),
            TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
            Duration.ofMinutes(60), new ArrayList<Integer>(), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));

    SubTask subTask1 = new SubTask("Test addNewsubTask 1", "Test addNewTask 1 description", getManager().genId(),
            TaskStatus.NEW, LocalDateTime.parse("01.06.2020, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20),
            epic1.getId());

    SubTask subTask2 = new SubTask("Test addNewsubTask2", "Test addNewTask 2 description", getManager().genId(),
            TaskStatus.NEW, LocalDateTime.parse("02.07.2021, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25),
            epic1.getId());
    SubTask subTask3 = new SubTask("Test addNewsubTask 3", "Test addNewTask 3 description", getManager().genId(),
            TaskStatus.NEW, LocalDateTime.parse("03.08.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15),
            epic1.getId());

    @Test
    void historyTests() {}

    @Test
    void addNewTask() {
        TaskManager manager = getManager();
        Task task = new Task("Test addNewTask 0", "Test addNewTask 0 description", 0,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2024, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));

        manager.addTask(task);
        final int taskId = task.getId();

        final Task savedTask = manager.getTaskById(Integer.toString(taskId));

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
        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())));
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        manager.addEpic(epic1);
        assertEquals(TaskStatus.NEW, epic1.getStatusTask(), "Статус обновлен не верно");
    }


    @Test
    void epicStatusNewAndDone() {
        TaskManager manager = getManager();
        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())));
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        manager.addEpic(epic1);

        manager.updateSubTask(subTask2.getId().toString(), 2);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatusTask(), "Статус обновлен не верно");

    }

    @Test
    void epicStatusInProgress() {
        TaskManager manager = getManager();
        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())));
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        manager.addEpic(epic1);

        manager.updateSubTask(subTask1.getId().toString(), 1);
        manager.updateSubTask(subTask2.getId().toString(), 1);
        manager.updateSubTask(subTask3.getId().toString(), 1);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatusTask(), "Статус обновлен не верно");
    }

    @Test
    void epicStatusDone() {
        TaskManager manager = getManager();
        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())));
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        manager.addEpic(epic1);

        manager.updateSubTask(Integer.toString(subTask1.getId()), 2);
        manager.updateSubTask(subTask2.getId().toString(), 2);
        manager.updateSubTask(subTask3.getId().toString(), 2);
        assertEquals(TaskStatus.DONE, epic1.getStatusTask(), "Статус обновлен не верно");
    }

    @Test
    void epicToSubtaskRelation(){
        TaskManager manager = getManager();
        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())));
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        manager.addEpic(epic1);

        assertNotNull(subTask1.getEpicId(), "У подзадачи нет Эпика");
        assertEquals(manager.getLastEpicId(), subTask1.getEpicId(), "Номер эпика указан неверно");
        assertNotNull(epic1.getSubTasks(), "У эпика нет подзадач");
        assertEquals(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())), epic1.getSubTasks(), "Задачи у эпика не совпадают");
    }

    @Test
    void getLastEpicId() {
        TaskManager manager = getManager();
        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId())));
        manager.addSubTask(subTask1);
        manager.addEpic(epic1);

        assertEquals(manager.getLastEpicId(), epic1.getId(), "Последний номер эпика не совпадает");
    }

    @Test
    void genId() {
        TaskManager manager = getManager();
        assertNotEquals(manager.getIdTaskManager(), manager.genId(), "ID не создается");
    }
/*
    @Test
    void getTasks() {
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addTask(task3);
        assertEquals(new ArrayList<>(List.of(task1, task2, task3)), inMemoryTaskManager.getTasks(), "Не возвращает добавленные задачи");
        assertNotEquals(new ArrayList<>(List.of(task2, task2, task3)), inMemoryTaskManager.getTasks(), "Считает равным разное");
    }

    @Test
    void deleteTasks() {
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addTask(task3);
        assertNotEquals(new ArrayList<>(), inMemoryTaskManager.getTasks(), "Список пуст");
        inMemoryTaskManager.deleteTasks();
        assertEquals(new ArrayList<>(), inMemoryTaskManager.getTasks(), "Список пуст");
    }

    @Test
    void deleteTaskById() {
        inMemoryTaskManager.addTask(task1);
        inMemoryTaskManager.addTask(task2);
        inMemoryTaskManager.addTask(task3);
        assertEquals(task2, inMemoryTaskManager.getTaskById("19"), "Задача не существует");
        inMemoryTaskManager.deleteTaskById("19");
        assertNull(inMemoryTaskManager.getTaskById("19"), "Задача все еще существует");
    }
*/
}