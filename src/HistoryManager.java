import java.util.List;

public interface HistoryManager {
    abstract List<Task> add (Task task);
    abstract List<Task> getHistory();
    abstract List<Task> removeTask (Task task);
}
