package managers;

public class Managers {
    static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    static TaskManager getDefauld() {
        return new InMemoryTaskManager();
    }
}
