import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.nio.file.Path;
import java.nio.file.Files;


class FileBackedTaskManagerTest {

    File testDir = new File("C:\\Users\\Igor\\java-kanban\\dbTaskManager");
    private int id = 0;
    private int lastEpicId = 0;
    private HashMap<Integer, Task> singleTaskDesc = new HashMap<>();
    private HashMap<Integer, Epic> epicTaskDesc = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskDesc = new HashMap<>();
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();



    File test;

    {
        try {
            test = File.createTempFile("geeks", null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(testDir, test, id, lastEpicId, singleTaskDesc, epicTaskDesc, subTaskDesc, inMemoryHistoryManager );

    @Test
    void save() {
        Task task1 = new Task("Test addNewTask 1", "Test addNewTask 1 description", fileBackedTaskManager.genId(), TaskStatus.NEW);
        Task task2 = new Task("Test addNewTask 2", "Test addNewTask 2 description", fileBackedTaskManager.genId(), TaskStatus.IN_PROGRESS);
        Task task3 = new Task("Test addNewTask 3", "Test addNewTask 3 description", fileBackedTaskManager.genId(), TaskStatus.DONE);
        Epic epic1 = new Epic("Test addNewEpic 1", "Test addNewEpic 1 description", fileBackedTaskManager.genId(), TaskStatus.NEW, new ArrayList<Integer>());
        Epic epic2 = new Epic("Test addNewEpic 2", "Test addNewEpic 2 description", fileBackedTaskManager.genId(), TaskStatus.IN_PROGRESS, new ArrayList<Integer>());
        Epic epic3 = new Epic("Test addNewEpic 3", "Test addNewEpic 3 description", fileBackedTaskManager.genId(), TaskStatus.DONE, new ArrayList<Integer>());
        SubTask subTask1 = new SubTask("Test addNewEpic 1", "Test addNewEpic 1 description", fileBackedTaskManager.genId(), TaskStatus.NEW, 1);
        SubTask subTask2 = new SubTask("Test addNewEpic 2", "Test addNewEpic 1 description", fileBackedTaskManager.genId(), TaskStatus.IN_PROGRESS, 2);
        SubTask subTask3 = new SubTask("Test addNewEpic 3", "Test addNewEpic 3 description", fileBackedTaskManager.genId(), TaskStatus.NEW, 3);

        fileBackedTaskManager.addTask(task1);
        fileBackedTaskManager.addTask(task2);
        fileBackedTaskManager.addTask(task3);
        fileBackedTaskManager.addEpic(epic1);
        fileBackedTaskManager.addEpic(epic2);
        fileBackedTaskManager.addEpic(epic3);
        fileBackedTaskManager.addSubTask(subTask1);
        fileBackedTaskManager.addSubTask(subTask3);
        fileBackedTaskManager.addSubTask(subTask2);

        fileBackedTaskManager.reader();
    }
}