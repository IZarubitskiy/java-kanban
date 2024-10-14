import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class FileBackedTaskManagerTest {

    File test;
    {
        try {
            test = File.createTempFile("geeks", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(test);

    @Test
    void save() {

        Task task1 = new Task("Test addNewTask 1", "Test addNewTask 1 description", fileBackedTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));
        Task task2 = new Task("Test addNewTask 2", "Test addNewTask 2 description", fileBackedTaskManager.genId(),
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25));
        Task task3 = new Task("Test addNewTask 3", "Test addNewTask 3 description", fileBackedTaskManager.genId(),
                TaskStatus.DONE, LocalDateTime.parse("03.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15));
        Task task4 = new Task("Test addNewTask 4", "Test addNewTask 4 description", fileBackedTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task5 = new Task("Test addNewTask 5", "Test addNewTask 5 description", fileBackedTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task6 = new Task("Test addNewTask 6", "Test addNewTask 6 description", fileBackedTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10));
        Epic epic1 = new Epic("Test addNewEpic 1", "Test addNewEpic 1 description", fileBackedTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
                Duration.ofMinutes(60),  new ArrayList<Integer>(), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));
        Epic epic2 = new Epic("Test addNewEpic 2", "Test addNewEpic 2 description", fileBackedTaskManager.genId(),
                TaskStatus.IN_PROGRESS,LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
                Duration.ofMinutes(60),  new ArrayList<Integer>(), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));
        Epic epic3 = new Epic("Test addNewEpic 3", "Test addNewEpic 3 description", fileBackedTaskManager.genId(),
                TaskStatus.DONE, LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
                Duration.ofMinutes(60),  new ArrayList<Integer>(), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));
        SubTask subTask1 = new SubTask("Test addNewSubTask 1", "Test addNewSubTask 1 description", fileBackedTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2024, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10) ,4);
        SubTask subTask2 = new SubTask("Test addNewSubTask 2", "Test addNewSubTask 1 description", fileBackedTaskManager.genId(),
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2025, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10), 5);
        SubTask subTask3 = new SubTask("Test addNewSubTask 3", "Test addNewSubTask 3 description", fileBackedTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("03.02.2026, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10), 6);
        SubTask subTask4 = new SubTask("Test addNewSubTask 4", "Test addNewSubTask 4 description", fileBackedTaskManager.genId(),
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("04.02.2027, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10), 5);

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
        Assertions.assertDoesNotThrow(() -> {fileBackedTaskManager.reader();} );
        FileBackedTaskManager fileBackedTaskManager2 = FileBackedTaskManager.loadFromFile(test);
        System.out.println(fileBackedTaskManager2.getEpics());

        System.out.println(fileBackedTaskManager.getPrioritizedTasks());
    }
}