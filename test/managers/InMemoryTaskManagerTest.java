package managers;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class InMemoryTaskManagerTest extends TaskManagerTest{

    @Override
    TaskManager getManager() {
        return new InMemoryTaskManager();
    }
/*
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
*/
    /*
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

    }

    @Test
    void epicStatusInProgress() {


    }

    @Test
    void epicStatusDone() {


    }

    @Test
    void epicToSubtaskRelation(){

    }

    @Test
    void getLastEpicId() {

    }

   */
}