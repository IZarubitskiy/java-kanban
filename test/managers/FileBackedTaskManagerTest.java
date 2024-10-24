package managers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.TaskStatus;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("rawtypes")
class FileBackedTaskManagerTest extends TaskManagerTest {

    File test;
    {
        try {
            test = File.createTempFile("geeks", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    TaskManager getManager() {
        return new FileBackedTaskManager(test);
    }

    @Test
    void save() {

        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(test);


        Epic epic1 = new Epic("Test addNewEpic 1", "Test addNewEpic 1 description", 16,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
                Duration.ofMinutes(60),  new ArrayList<>(), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));
        Epic epic2 = new Epic("Test addNewEpic 2", "Test addNewEpic 2 description", 17,
                TaskStatus.IN_PROGRESS,LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
                Duration.ofMinutes(60),  new ArrayList<>(), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));
        Epic epic3 = new Epic("Test addNewEpic 3", "Test addNewEpic 3 description", 18,
                TaskStatus.DONE, LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
                Duration.ofMinutes(60),  new ArrayList<>(), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));
        SubTask subTask1 = new SubTask("Title Subtask test 1", "Description Subtask test 1", 19,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2024, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10) ,16);
        SubTask subTask2 = new SubTask("Title Subtask test 2", "Description Subtask test 1", 20,
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2025, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10), 17);
        SubTask subTask3 = new SubTask("Title Subtask test 3", "Description Subtask test 3", 21,
                TaskStatus.NEW, LocalDateTime.parse("03.02.2026, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10), 18);
        SubTask subTask4 = new SubTask("Title Subtask test 4", "Description Subtask test", 22,
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("04.02.2027, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10), 18);

        fileBackedTaskManager.addTask(task1);
        fileBackedTaskManager.addTask(task2);
        fileBackedTaskManager.addTask(task3);
        fileBackedTaskManager.addTask(task4);
        fileBackedTaskManager.addTask(task5);
        fileBackedTaskManager.addTask(task6);
        fileBackedTaskManager.addEpic(epic1);
        fileBackedTaskManager.addEpic(epic2);
        fileBackedTaskManager.addEpic(epic3);
        fileBackedTaskManager.addSubTask(subTask1);
        fileBackedTaskManager.addSubTask(subTask2);
        fileBackedTaskManager.addSubTask(subTask3);
        fileBackedTaskManager.addSubTask(subTask4);

        fileBackedTaskManager.reader();
        Assertions.assertDoesNotThrow(fileBackedTaskManager::reader);
        System.out.println(test);

        FileBackedTaskManager loadedFromFile = FileBackedTaskManager.loadFromFile(test);

        assertEquals(task1, loadedFromFile.getTaskById(5), "Возврат неверной задачи.");
        assertEquals(6, loadedFromFile.getTasks().size(), "Возврат неверного количества задач.");
    }
}