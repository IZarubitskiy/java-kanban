import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {
    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getSubtasks();

    HashMap<Integer, Task> deleteTasks();

    HashMap<Integer, Epic> deleteEpics();

    HashMap<Integer, SubTask> deleteSubTasks();

    Task getTaskById(String id);

    Epic getEpicById(String id);

    SubTask getSubTaskById(String id);

    Task addTask(Task newSingleTask);

    Epic addEpic(Epic newEpic);

    SubTask addSubTask(SubTask newSubTask);

    Epic setLastEpicWithSubTask(ArrayList<Integer> subTaskList);

    Task updateTask(String id, Integer status);

    SubTask updateSubTask(String id, Integer status);

    HashMap<Integer, SubTask> deleteTaskById(String id);

    HashMap<Integer, Epic> deleteEpicById(String id);

    HashMap<Integer, SubTask> deleteSubTaskById(String id);

    ArrayList<Integer> getSubTasksListByEpicId(String id);

    List<Task> getHistory();

    Integer genId();

}
