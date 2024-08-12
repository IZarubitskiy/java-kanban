import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

class InMemoryTaskManagerTest {
    Task task = new Task("Test addNewTask", "Test addNewTask description", TaskStatus.NEW);
    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
    @Test
    void addNewTask() {

        inMemoryTaskManager.addTask(task);
        final int taskId = inMemoryTaskManager.getId(task);

        final Task savedTask = inMemoryTaskManager.getTaskById(Integer.toString(taskId));

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void add() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        inMemoryHistoryManager.add(task);
        final List<Task> history = inMemoryHistoryManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

}