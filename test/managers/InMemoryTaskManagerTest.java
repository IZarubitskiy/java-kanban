package managers;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import javax.xml.transform.Source;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class InMemoryTaskManagerTest extends TaskManagerTest{

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Epic epic1 = new Epic("Test addNewEpic 1", "Test addNewEpic 1 description", 14,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
                Duration.ofMinutes(60), new ArrayList<Integer>(), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));

        SubTask subTask1 = new SubTask("Test addNewsubTask 1", "Test addNewTask 1 description", 15,
                TaskStatus.NEW, LocalDateTime.parse("01.06.2020, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20),
                epic1.getId());

        SubTask subTask2 = new SubTask("Test addNewsubTask2", "Test addNewTask 2 description", 16,
                TaskStatus.NEW, LocalDateTime.parse("02.07.2021, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25),
                epic1.getId());
        SubTask subTask3 = new SubTask("Test addNewsubTask 3", "Test addNewTask 3 description", 17,
                TaskStatus.NEW, LocalDateTime.parse("03.08.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15),
                epic1.getId());
        Task task1 = new Task("Test addNewTask 1", "Test addNewTask 1 description", 18,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2020, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));
        Task task2 = new Task("Test addNewTask 2", "Test addNewTask 2 description", 19,
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2021, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25));
        Task task3 = new Task("Test addNewTask 3", "Test addNewTask 3 description", 20,
                TaskStatus.DONE, LocalDateTime.parse("03.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15));
        Task task4 = new Task("Test addNewTask 4", "Test addNewTask 4 description", 21,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2023, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task5 = new Task("Test addNewTask 5", "Test addNewTask 5 description", 22,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2024, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task6 = new Task("Test addNewTask 6", "Test addNewTask 6 description", 23,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2025, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10));
        Task task7 = new Task("Test addNewTask 7", "Test addNewTask 7 description", 24,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2026, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));
        Task task8 = new Task("Test addNewTask 8", "Test addNewTask 8 description", 25,
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2027, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25));
        Task task9 = new Task("Test addNewTask 9", "Test addNewTask 9 description", 26,
                TaskStatus.DONE, LocalDateTime.parse("03.02.2028, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15));
        Task task10 = new Task("Test addNewTask 10", "Test addNewTask 10 description", 27,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2029, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task11 = new Task("Test addNewTask 11", "Test addNewTask 11 description", 28,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2030, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));


    @Test
    void addNewTask() {

        Task task = new Task("Test addNewTask 0", "Test addNewTask 0 description", 0,
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
        for (int i = task1.getId(); i <= task10.getId(); i++) {
            String id = Integer.toString(i);
            inMemoryTaskManager.getTaskById(id);
        }
        assertEquals(10, inMemoryTaskManager.getHistoryTM().size(), "Количество задач не верно.");
        assertEquals(task10, inMemoryTaskManager.getHistoryTM().getFirst(), "Последняя добавленная задача не совпадает");

        inMemoryTaskManager.getTaskById(Integer.toString(task9.getId()));
        assertEquals(task9, inMemoryTaskManager.getHistoryTM().getFirst(), "Не обновляет последнюю добавленную задачу");
        inMemoryTaskManager.addTask(task11);
        inMemoryTaskManager.getTaskById("12");
        assertEquals(10, inMemoryTaskManager.getHistoryTM().size(), "Количество задач не верно.");

        inMemoryTaskManager.deleteTaskById(Integer.toString(task5.getId()));
        assertEquals(9, inMemoryTaskManager.getHistoryTM().size(), "Количество задач не верно.");

        assertNull(inMemoryTaskManager.getTaskById(Integer.toString(task5.getId())), "Задача не удалена");
    }

    @Test
    void epicStatusNew() {
        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())));
        inMemoryTaskManager.addSubTask(subTask1);
        inMemoryTaskManager.addSubTask(subTask2);
        inMemoryTaskManager.addSubTask(subTask3);
        inMemoryTaskManager.addEpic(epic1);
        assertEquals(TaskStatus.NEW, epic1.getStatusTask(), "Статус обновлен не верно");
    }

    @Test
        void epicStatusNewAndDone() {
        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())));
        inMemoryTaskManager.addSubTask(subTask1);
        inMemoryTaskManager.addSubTask(subTask2);
        inMemoryTaskManager.addSubTask(subTask3);
        inMemoryTaskManager.addEpic(epic1);

        inMemoryTaskManager.updateSubTask(subTask2.getId().toString(), 2);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatusTask(), "Статус обновлен не верно");
    }

    @Test
    void epicStatusInProgress() {

        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())));
        inMemoryTaskManager.addSubTask(subTask1);
        inMemoryTaskManager.addSubTask(subTask2);
        inMemoryTaskManager.addSubTask(subTask3);
        inMemoryTaskManager.addEpic(epic1);

        inMemoryTaskManager.updateSubTask(subTask1.getId().toString(), 1);
        inMemoryTaskManager.updateSubTask(subTask2.getId().toString(), 1);
        inMemoryTaskManager.updateSubTask(subTask3.getId().toString(), 1);
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatusTask(), "Статус обновлен не верно");
    }

    @Test
    void epicStatusDone() {

        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())));
        inMemoryTaskManager.addSubTask(subTask1);
        inMemoryTaskManager.addSubTask(subTask2);
        inMemoryTaskManager.addSubTask(subTask3);
        inMemoryTaskManager.addEpic(epic1);

        inMemoryTaskManager.updateSubTask(Integer.toString(subTask1.getId()), 2);
        inMemoryTaskManager.updateSubTask(subTask2.getId().toString(), 2);
        inMemoryTaskManager.updateSubTask(subTask3.getId().toString(), 2);
        assertEquals(TaskStatus.DONE, epic1.getStatusTask(), "Статус обновлен не верно");
    }

    @Test
    void epicToSubtaskRelation(){
        epic1.setSubTaskListId(new ArrayList<>(List.of(subTask1.getId(), subTask2.getId(), subTask3.getId())));
        inMemoryTaskManager.addSubTask(subTask1);
        inMemoryTaskManager.addSubTask(subTask2);
        inMemoryTaskManager.addSubTask(subTask3);
        inMemoryTaskManager.addEpic(epic1);

        assertNotNull(subTask1.getEpicId(), "У подзадачи нет Эпика");
        assertEquals(14, subTask1.getEpicId(), "Номер эпика указан неверно");
        assertNotNull(epic1.getSubTasks(), "У эпика нет подзадач");
        assertEquals(new ArrayList<>(List.of(15,16,17)), epic1.getSubTasks(), "Задачи у эпика не совпадают");
    }

    @Test
    void getLastEpicId() {
        assertEquals(14, inMemoryTaskManager.getLastEpicId(), "Последний номер эпика не совпадает");
    }

    @Test
    void genId() {
        assertEquals(1, inMemoryTaskManager.genId(), "ID не создается");
    }

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
}