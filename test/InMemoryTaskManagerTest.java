import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class InMemoryTaskManagerTest extends TaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask 0", "Test addNewTask 0 description", inMemoryTaskManager.genId(),
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

        for (int i = 1; i < 13; i++) {
            String id = Integer.toString(i);
            inMemoryTaskManager.getTaskById(id);
            if (i == 3) {
                assertEquals(3, inMemoryTaskManager.getHistory().size(), "Количество задач не верно.");
            }
            if (i == 4) {
                assertEquals(task4, inMemoryTaskManager.getHistory().getFirst(), "Последняя добавленная задача не совпадает");
            }

        }
        inMemoryTaskManager.getTaskById("9");
        assertEquals(task9, inMemoryTaskManager.getHistory().getFirst(), "Не обновляет последнюю добавленную задачу");
        inMemoryTaskManager.addTask(task11);
        inMemoryTaskManager.getTaskById("12");
        assertEquals(10, inMemoryTaskManager.getHistory().size(), "Количество задач не верно.");

        inMemoryTaskManager.deleteTaskById("5");
        assertEquals(9, inMemoryTaskManager.getHistory().size(), "Количество задач не верно.");

        assertNull(inMemoryTaskManager.getTaskById("5"), "Задача не удалена");
    }
    @Test
    void updateEpicStatus(){

        Epic epic1 = new Epic("Test addNewEpic 1", "Test addNewEpic 1 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
                Duration.ofMinutes(60),  new ArrayList<Integer>(List.of(13,14,15)), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));

        SubTask subTask1 = new SubTask("Test addNewsubTask 1", "Test addNewTask 1 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.06.2020, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20),
                12);
        SubTask subTask2 = new SubTask("Test addNewsubTask2", "Test addNewTask 2 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("02.07.2021, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25),
                12);
        SubTask subTask3 = new SubTask("Test addNewsubTask 3", "Test addNewTask 3 description", inMemoryTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("03.08.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15),
                12);

        inMemoryTaskManager.addSubTask(subTask1);
        inMemoryTaskManager.addSubTask(subTask2);
        inMemoryTaskManager.addSubTask(subTask3);
        inMemoryTaskManager.addEpic(epic1);

        inMemoryTaskManager.updateEpicStatus(inMemoryTaskManager.getLastEpicId());
        assertEquals(TaskStatus.NEW, epic1.getStatusTask(), "Статус обновлен не верно");

        inMemoryTaskManager.updateSubTask(subTask1.getId().toString(), 1);
        inMemoryTaskManager.updateEpicStatus(inMemoryTaskManager.getLastEpicId());
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatusTask(), "Статус обновлен не верно");

        inMemoryTaskManager.updateSubTask(subTask1.getId().toString(), 2);
        inMemoryTaskManager.updateSubTask(subTask2.getId().toString(), 1);
        inMemoryTaskManager.updateEpicStatus(inMemoryTaskManager.getLastEpicId());
        assertEquals(TaskStatus.IN_PROGRESS, epic1.getStatusTask(), "Статус обновлен не верно");

        inMemoryTaskManager.updateSubTask(subTask1.getId().toString(), 2);
        inMemoryTaskManager.updateSubTask(subTask2.getId().toString(), 2);
        inMemoryTaskManager.updateSubTask(subTask3.getId().toString(), 2);
        inMemoryTaskManager.updateEpicStatus(inMemoryTaskManager.getLastEpicId());
        assertEquals(TaskStatus.DONE, epic1.getStatusTask(), "Статус обновлен не верно");

        assertNotNull(subTask1.getEpicId(), "У подзадачи нет Эпика");
        assertEquals(12, subTask1.getEpicId(), "Номер эпика указан неверно");
        assertNotNull(epic1.getSubTasks(), "У эпика нет подзадач");
        assertEquals(new ArrayList<>(List.of(13,14,15)), epic1.getSubTasks(), "Задачи у эпика не совпадают");
    }

}