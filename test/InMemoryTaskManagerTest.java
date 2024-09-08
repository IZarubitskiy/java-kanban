import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

class InMemoryTaskManagerTest {

    InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask 0", "Test addNewTask 0 description", inMemoryTaskManager.genId(), TaskStatus.NEW);

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
   /* void history() {
        Task task1 = new Task("Test addNewTask 1", "Test addNewTask 1 description", inMemoryTaskManager.genId(), TaskStatus.NEW);
        Task task2 = new Task("Test addNewTask 2", "Test addNewTask 2 description", inMemoryTaskManager.genId(), TaskStatus.NEW);
        Task task3 = new Task("Test addNewTask 3", "Test addNewTask 3 description", inMemoryTaskManager.genId(), TaskStatus.NEW);
        Task task4 = new Task("Test addNewTask 4", "Test addNewTask 4 description", inMemoryTaskManager.genId(), TaskStatus.NEW);
        Task task5 = new Task("Test addNewTask 5", "Test addNewTask 5 description", inMemoryTaskManager.genId(), TaskStatus.NEW);
        Task task6 = new Task("Test addNewTask 6", "Test addNewTask 6 description", inMemoryTaskManager.genId(), TaskStatus.NEW);
        Task task7 = new Task("Test addNewTask 7", "Test addNewTask 7 description", inMemoryTaskManager.genId(), TaskStatus.NEW);
        Task task8 = new Task("Test addNewTask 8", "Test addNewTask 8 description", inMemoryTaskManager.genId(), TaskStatus.NEW);
        Task task9 = new Task("Test addNewTask 9", "Test addNewTask 9 description", inMemoryTaskManager.genId(), TaskStatus.NEW);
        Task task10 = new Task("Test addNewTas 10k", "Test addNewTask 10 description", inMemoryTaskManager.genId(), TaskStatus.NEW);
        Task task11 = new Task("Test addNewTas 10k", "Test addNewTask 10 description", inMemoryTaskManager.genId(), TaskStatus.NEW);

        assertEquals(0, inMemoryTaskManager.getHistory().size(), "Количество задач не верно.");

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
        inMemoryTaskManager.addTask(task11);

        for (int i = 2; i < 13; i++) {
            String id =Integer.toString(i);
            inMemoryTaskManager.getTaskById(id);
            if (i == 3){
                assertEquals(2, inMemoryTaskManager.getHistory().size(), "Количество задач не верно.");
            }

            if (i == 5){
                assertEquals(task4, inMemoryTaskManager.getHistory().getLast(), "Последняя добавленная задача не совпадает");
            }

        }
        assertEquals(task2, inMemoryTaskManager.getHistory().getFirst(), "Первая задача, после превышения лимита не совпадает");

    */

}