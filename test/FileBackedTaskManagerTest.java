import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    private int id = 0;
    private int lastEpicId = 0;
    private HashMap<Integer, Task> singleTaskDesc = new HashMap<>();
    private HashMap<Integer, Epic> epicTaskDesc = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskDesc = new HashMap<>();
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();

    @Test
    void save() {
        Task task1 = new Task("Test addNewTask 1", "Test addNewTask 1 description", fileBackedTaskManager.genId(), TaskStatus.NEW);
        Task task2 = new Task("Test addNewTask 2", "Test addNewTask 2 description", fileBackedTaskManager.genId(), TaskStatus.NEW);
        Task task3 = new Task("Test addNewTask 3", "Test addNewTask 3 description", fileBackedTaskManager.genId(), TaskStatus.NEW);
        Task task4 = new Task("Test addNewTask 4", "Test addNewTask 4 description", fileBackedTaskManager.genId(), TaskStatus.NEW);
        Task task5 = new Task("Test addNewTask 5", "Test addNewTask 5 description", fileBackedTaskManager.genId(), TaskStatus.NEW);
        Task task6 = new Task("Test addNewTask 6", "Test addNewTask 6 description", fileBackedTaskManager.genId(), TaskStatus.NEW);
        Task task7 = new Task("Test addNewTask 7", "Test addNewTask 7 description", fileBackedTaskManager.genId(), TaskStatus.NEW);
        Task task8 = new Task("Test addNewTask 8", "Test addNewTask 8 description", fileBackedTaskManager.genId(), TaskStatus.NEW);
        Task task9 = new Task("Test addNewTask 9", "Test addNewTask 9 description", fileBackedTaskManager.genId(), TaskStatus.NEW);
        Task task10 = new Task("Test addNewTas 10", "Test addNewTask 10 description", fileBackedTaskManager.genId(), TaskStatus.NEW);

        fileBackedTaskManager.addTask(task1);
        fileBackedTaskManager.addTask(task2);
        fileBackedTaskManager.addTask(task3);
        fileBackedTaskManager.addTask(task4);
        fileBackedTaskManager.addTask(task5);
        fileBackedTaskManager.addTask(task6);
        fileBackedTaskManager.addTask(task7);
        fileBackedTaskManager.addTask(task8);
        fileBackedTaskManager.addTask(task9);
        fileBackedTaskManager.addTask(task10);

        fileBackedTaskManager.reader();



    }
}