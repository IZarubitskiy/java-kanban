import java.util.ArrayList;
import java.util.HashMap;

public class FileBackedTaskManager extends InMemoryTaskManager {
    @Override
    public HashMap<Integer, Task> deleteTasks() {
        super.deleteTasks();
        save();
        return super.deleteTasks();

    }

    @Override
    public HashMap<Integer, Epic> deleteEpics() {
        save();
        return super.deleteEpics();
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTasks() {
        save();
        return super.deleteSubTasks();
    }

    @Override
    public Task addTask(Task newSingleTask) {
        save();
        return super.addTask(newSingleTask);
    }

    @Override
    public Epic addEpic(Epic newEpic) {
        save();
        return super.addEpic(newEpic);
    }

    @Override
    public SubTask addSubTask(SubTask newSubTask) {
        save();
        return super.addSubTask(newSubTask);
    }

    @Override
    public Task updateTask(String id, Integer status) {
        save();
        return super.updateTask(id, status);
    }

    @Override
    public SubTask updateSubTask(String id, Integer status) {
        save();
        return super.updateSubTask(id, status);
    }

    @Override
    public HashMap<Integer, Task> deleteTaskById(String id) {
        save();
        return super.deleteTaskById(id);
    }

    @Override
    public HashMap<Integer, Epic> deleteEpicById(String id) {
        save();
        return super.deleteEpicById(id);
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTaskById(String id) {
        save();
        return super.deleteSubTaskById(id);
    }

    public void save(){

    }

    private String toString(Task task) {
    String strTask = task.getId() + "," + task.getType() + "," + task.getTitle() + "," + task.getStatusTask().name() + ","
            + task.getDescription() + "," + task.getEpicId();
    return strTask;
    }

    private Task fromString(String value) {
        String[] split = value.split(",");
        Task taskFromString;
        switch (TaskTypes.valueOf(split[1])){
            case TASK:
                return taskFromString = new Task(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]));

            case EPIC:
                return taskFromString = new Epic(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]),
                        new ArrayList<Integer>() );

            case SUBTASK:
                return taskFromString = new SubTask(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]),
                        Integer.parseInt(split[5]));
        }
        return null;
    }

}
