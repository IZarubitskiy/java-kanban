import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

}