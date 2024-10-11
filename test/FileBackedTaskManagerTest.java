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
        System.out.println("Пустая строка перед файлом");
        fileBackedTaskManager.reader();
        System.out.println("Пустая строка после файла");

        Task task1 = new Task("Test addNewTask 1", "Test addNewTask 1 description", fileBackedTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));
        Task task2 = new Task("Test addNewTask 2", "Test addNewTask 2 description", fileBackedTaskManager.genId(),
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25));
        Task task3 = new Task("Test addNewTask 3", "Test addNewTask 3 description", fileBackedTaskManager.genId(),
                TaskStatus.DONE, LocalDateTime.parse("03.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15));
        Task task4 = new Task("Test addNewTask 4", "Test addNewTask 4 description", fileBackedTaskManager.genId(),
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 18:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));


        fileBackedTaskManager.addTask(task3);
        fileBackedTaskManager.addTask(task4);
        fileBackedTaskManager.addTask(task2);
        fileBackedTaskManager.addTask(task1);


        System.out.println(fileBackedTaskManager.getPrioritizedTasks());
/*
        Epic epic1 = new Epic("Test addNewEpic 1", "Test addNewEpic 1 description", fileBackedTaskManager.genId(), TaskStatus.NEW, new ArrayList<Integer>());
        Epic epic2 = new Epic("Test addNewEpic 2", "Test addNewEpic 2 description", fileBackedTaskManager.genId(), TaskStatus.IN_PROGRESS, new ArrayList<Integer>());
        Epic epic3 = new Epic("Test addNewEpic 3", "Test addNewEpic 3 description", fileBackedTaskManager.genId(), TaskStatus.DONE, new ArrayList<Integer>());
        SubTask subTask1 = new SubTask("Test addNewEpic 1", "Test addNewEpic 1 description", fileBackedTaskManager.genId(), TaskStatus.NEW, 4);
        SubTask subTask2 = new SubTask("Test addNewEpic 2", "Test addNewEpic 1 description", fileBackedTaskManager.genId(), TaskStatus.IN_PROGRESS, 5);
        SubTask subTask3 = new SubTask("Test addNewEpic 3", "Test addNewEpic 3 description", fileBackedTaskManager.genId(), TaskStatus.NEW, 6);
        SubTask subTask4 = new SubTask("Test addNewEpic 4", "Test addNewEpic 4 description", fileBackedTaskManager.genId(), TaskStatus.IN_PROGRESS, 5);

        fileBackedTaskManager.addTask(task1);
        fileBackedTaskManager.addTask(task2);
        fileBackedTaskManager.addTask(task3);
        fileBackedTaskManager.addEpic(epic1);
        fileBackedTaskManager.addEpic(epic2);
        fileBackedTaskManager.addEpic(epic3);
        fileBackedTaskManager.addSubTask(subTask1);
        fileBackedTaskManager.addSubTask(subTask2);
        fileBackedTaskManager.addSubTask(subTask3);
        fileBackedTaskManager.addSubTask(subTask4);

      FileBackedTaskManager fileBackedTaskManager2 = FileBackedTaskManager.loadFromFile(test);
       System.out.println(fileBackedTaskManager2.getEpics());
        fileBackedTaskManager.reader();
*/
    }
}