package managers;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class InMemoryTaskManagerTest extends TaskManagerTest{

    @Override
    TaskManager getManager() {
        return Managers.getDefault();
    }

    @Test
    void historyTests() {
        TaskManager manager = getManager();
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        manager.addTask(task4);
        manager.addTask(task5);
        manager.addTask(task6);
        manager.addTask(task7);
        manager.addTask(task8);
        manager.addTask(task9);
        manager.addTask(task10);
        for (int i = task1.getId(); i <= task10.getId(); i++) {
            manager.getTaskById(i);
        }
        assertEquals(10, manager.getHistoryTM().size(), "Количество задач не верно.");
        assertEquals(task10, manager.getHistoryTM().getFirst(), "Последняя добавленная задача не совпадает");

        manager.getTaskById(task9.getId());
        assertEquals(task9, manager.getHistoryTM().getFirst(), "Не обновляет последнюю добавленную задачу");
        manager.addTask(task11);
        manager.getTaskById(12);
        assertEquals(10, manager.getHistoryTM().size(), "Количество задач не верно.");

        manager.deleteTaskById(task5.getId());
        assertEquals(9, manager.getHistoryTM().size(), "Количество задач не верно.");

        assertNull(manager.getTaskById(task5.getId()), "Задача не удалена");

    }

}