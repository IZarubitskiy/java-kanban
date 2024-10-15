package managers;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

abstract class TaskManagerTest<T extends TaskManager>{
    @Test
    void historyTests() {}

    @Test
    void addNewTask() {}

    @Test
    void epicStatusNew() {}

    @Test
    void epicStatusNewAndDone() {}

    @Test
    void epicStatusInProgress() {}

    @Test
    void epicStatusDone() {}

    @Test
    void epicToSubtaskRelation(){}

    @Test
    void getLastEpicId() {}

    @Test
    void genId() {}

    @Test
    void getTasks() {}

    @Test
    void deleteTasks() {}

    @Test
    void deleteTaskById() {}

}