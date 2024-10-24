package managers;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface TaskManager {

    void setId(int value);

    void setLastEpicId(int value);

    int getIdTaskManager();

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getSubtasks();

    HashMap<Integer, Task> deleteTasks();

    HashMap<Integer, Epic> deleteEpics();

    HashMap<Integer, SubTask> deleteSubTasks();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    SubTask getSubTaskById(int id);

    Task addTask(Task newSingleTask);

    Epic addEpic(Epic newEpic);

    SubTask addSubTask(SubTask newSubTask);

    void setLastEpicWithSubTask(ArrayList<Integer> subTaskList);

    Task updateTask(int id, Integer status);

    SubTask updateSubTask(int id, Integer status);

    HashMap<Integer, Task> deleteTaskById(int id);

    HashMap<Integer, Epic> deleteEpicById(int id);

    HashMap<Integer, SubTask> deleteSubTaskById(int id);

    ArrayList<Integer> getSubTasksListByEpicId(int id);

    List<Task> getHistoryTM();

    Integer genId();

    Integer getLastEpicId();

    Set<Task> getPrioritizedTasks();

    LocalDateTime getEpicStartTime(Epic epic);

    LocalDateTime getEpicEndTime(Epic epic);

    Duration getEpicDuratioon(Epic epic);

    boolean checkTaskDates(Task task);
}
