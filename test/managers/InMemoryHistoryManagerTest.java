package managers;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    @Test
    void checkEmptyHistory(){
        assertEquals(new ArrayList<>(),inMemoryHistoryManager.getHistory(), "История не пуста");
    }

    @Test
    void checkDuplicates(){
        inMemoryHistoryManager.add(new Task("Test addNewTask 1", "Test addNewTask 1 description", 29,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2031, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60)));
        inMemoryHistoryManager.add(new Task("Test addNewTask 1", "Test addNewTask 1 description", 29,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2031, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60)));

        assertEquals(1, inMemoryHistoryManager.getHistory().size(), "Задачи дублируются");
    }

    @Test
    void checkRemove() {

        inMemoryHistoryManager.add(new Task("Test addNewTask 1", "Test addNewTask 1 description", 30,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2031, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60)));

        inMemoryHistoryManager.add(new Task("Test addNewTask 2", "Test addNewTask 2 description", 31,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2032, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60)));
        inMemoryHistoryManager.add(new Task("Test addNewTask 3", "Test addNewTask 3 description", 32,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2033, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60)));
        inMemoryHistoryManager.add(new Task("Test addNewTask 4", "Test addNewTask 4 description", 33,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2031, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60)));
        inMemoryHistoryManager.add(new Task("Test addNewTask 5", "Test addNewTask 5 description", 34,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2031, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60)));

        assertEquals(5, inMemoryHistoryManager.getHistory().size(), "Задачи дублируются");

        inMemoryHistoryManager.remove(30);
        assertNotEquals(30, inMemoryHistoryManager.getHistory().getLast().getId(), "задача не удалена с конца");
        inMemoryHistoryManager.remove(34);
        assertNotEquals(34, inMemoryHistoryManager.getHistory().getLast().getId(), "задача не удалена с начала");

        inMemoryHistoryManager.remove(32);
        assertEquals(2, inMemoryHistoryManager.getHistory().size(), "Задача удалена");
        assertEquals(33, inMemoryHistoryManager.getHistory().getFirst().getId(), "Удалена не та задача");
        assertEquals(31, inMemoryHistoryManager.getHistory().getLast().getId(), "задача не удалена с конца");
    }




}