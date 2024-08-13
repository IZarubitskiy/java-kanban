import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private List<Task> history = new ArrayList<>();

    @Override
    public List<Task> add(Task task) {
        if (history.size() >= 10) {
            history.remove(0);
            }
            history.add(task);
        return history;
    }


    @Override
    public List<Task> getHistory() {
        return history;
    }
}
