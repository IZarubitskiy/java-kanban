package managers;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private static int id = 0;
    private static int lastEpicId = 0;
    private HashMap<Integer, Task> singleTaskDesc = new HashMap<>();
    private HashMap<Integer, Epic> epicTaskDesc = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskDesc = new HashMap<>();
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    private Set<Task> prioritizedTasks = new TreeSet<>(new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            if (t1.getStartTime().isBefore(t2.getStartTime())) {
                return -1;
            } else {
                return 1;
            }
        }
    });

    @Override
    public Integer getLastEpicId() {
        return lastEpicId;
    }

    @Override
    public void setId(int value) {
        this.id = value;
    }

    @Override
    public void setLastEpicId(int value) {
        this.lastEpicId = value;
    }

    @Override
    public Integer genId() {
        id += 1;
        return id;
    }

    public int getIdTaskManager() {
        return id;
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (Integer i : singleTaskDesc.keySet()) {
            allTasks.add(singleTaskDesc.get(i));
        }
        return allTasks;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>();
        for (Integer i : epicTaskDesc.keySet()) {
            allEpics.add(epicTaskDesc.get(i));
        }
        return allEpics;
    }

    @Override
    public ArrayList<SubTask> getSubtasks() {
        ArrayList<SubTask> allSubTasks = new ArrayList<>();
        for (Integer i : subTaskDesc.keySet()) {
            allSubTasks.add(subTaskDesc.get(i));
        }
        return allSubTasks;
    }

    @Override
    public HashMap<Integer, Task> deleteTasks() {
        singleTaskDesc.clear();
        return singleTaskDesc;
    }

    @Override
    public HashMap<Integer, Epic> deleteEpics() {
        epicTaskDesc.clear();
        subTaskDesc.clear();
        return epicTaskDesc;
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTasks() {
        subTaskDesc.clear();
        for (Integer i : epicTaskDesc.keySet()) {
            epicTaskDesc.get(i).setStatusTask(TaskStatus.NEW);
        }
        return subTaskDesc;
    }

   @Override // remake
    public Task getTaskById(String id) {
        Task foundTask = singleTaskDesc.get(Integer.parseInt(id));
        if (foundTask != null) {
            inMemoryHistoryManager.add(foundTask);
        }
        return foundTask;
    }

   @Override  // remake
    public Epic getEpicById(String id) {
        Epic foundEpic = epicTaskDesc.get(Integer.parseInt(id));
        if (foundEpic != null) {
            inMemoryHistoryManager.add(foundEpic);
        }
        return foundEpic;
    }

   @Override // remake
    public SubTask getSubTaskById(String id) {
        SubTask foundSubTask = subTaskDesc.get(Integer.parseInt(id));
        if (foundSubTask != null) {
            inMemoryHistoryManager.add(foundSubTask);
        }
        return foundSubTask;
    }

    @Override
    public Task addTask(Task newSingleTask) {
        if (checkTaskDates(newSingleTask)) {
            prioritizedTasks.add(newSingleTask);
            singleTaskDesc.put(newSingleTask.getId(), newSingleTask);
        } else {
            System.out.println("Такое время уже существует");
            id -= 1;
        }
        return newSingleTask;
    }

    @Override
    public Epic addEpic(Epic newEpic) {
        LocalDateTime firstSubTask = LocalDateTime.now();
        LocalDateTime lastSubTask = LocalDateTime.now();
        Duration epicDuration = Duration.between(firstSubTask,firstSubTask);
        for (Integer subTaskId : newEpic.getSubTasks()) {
            epicDuration = epicDuration.plus(subTaskDesc.get(subTaskId).getDuration());
            if (firstSubTask.isAfter(subTaskDesc.get(subTaskId).getStartTime())) {
                firstSubTask = subTaskDesc.get(subTaskId).getStartTime();
            }
            if (lastSubTask.isBefore(subTaskDesc.get(subTaskId).getEndTime())) {
                lastSubTask = subTaskDesc.get(subTaskId).getEndTime();
            }
            newEpic.setStartTime(firstSubTask);
            newEpic.setDuration(epicDuration);
            newEpic.setEndTime(lastSubTask);
        }
        epicTaskDesc.put(newEpic.getId(), newEpic);
        setLastEpicId(newEpic.getId());
        return newEpic;
    }

        @Override
        public SubTask addSubTask(SubTask newSubTask) {
            if (checkTaskDates(newSubTask)) {
                prioritizedTasks.add(newSubTask);
                subTaskDesc.put(newSubTask.getId(), newSubTask);
            } else {
                System.out.println("такое время уже существует");
                id -= 1;
            }
            return  newSubTask;
    }

    @Override
    public void setLastEpicWithSubTask(ArrayList<Integer> subTaskList) {
        epicTaskDesc.get(lastEpicId).setSubTaskListId(subTaskList);
    }

    @Override
    public Task updateTask(String id, Integer status) {
        int updateId = Integer.parseInt(id);
            if (status == 1) {
                singleTaskDesc.get(updateId).setStatusTask(TaskStatus.IN_PROGRESS);
            } else if (status == 2) {
                singleTaskDesc.get(updateId).setStatusTask(TaskStatus.DONE);
            }
        return singleTaskDesc.get(updateId);
    }

    @Override
    public SubTask updateSubTask(String id, Integer status) {
        int updateId = Integer.parseInt(id);
        if (status == 1) {
            subTaskDesc.get(updateId).setStatusTask(TaskStatus.IN_PROGRESS);
        } else if (status == 2) {
            subTaskDesc.get(updateId).setStatusTask(TaskStatus.DONE);
        }
        int epicId = subTaskDesc.get(updateId).getEpicId();
        updateEpicStatus(epicId);
        return subTaskDesc.get(updateId);
    }

    @Override
    public HashMap<Integer, Task> deleteTaskById(String id) {
        int deletedId = Integer.parseInt(id);
        singleTaskDesc.remove(deletedId);
        inMemoryHistoryManager.remove(deletedId);
        return singleTaskDesc;
    }

    @Override
    public HashMap<Integer, Epic> deleteEpicById(String id) {
        int deletedId = Integer.parseInt(id);
        epicTaskDesc.remove(deletedId);
        inMemoryHistoryManager.remove(deletedId);
        for (Integer i : subTaskDesc.keySet()) {
            if (subTaskDesc.get(i).getEpicId() == deletedId) {
                subTaskDesc.remove(i);
                inMemoryHistoryManager.remove(i);
            }
        }
        return epicTaskDesc;
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTaskById(String id) {
        int deletedId = Integer.parseInt(id);
        int epicId = subTaskDesc.get(deletedId).getEpicId();
        subTaskDesc.remove(deletedId);
        inMemoryHistoryManager.remove(deletedId);
        ArrayList<Integer> updatedListSubTasks = new ArrayList<>();
        for (Integer i : subTaskDesc.keySet()) {
            if (subTaskDesc.get(i).getEpicId() == epicId) {
                updatedListSubTasks.add(subTaskDesc.get(i).getId());
            }
        }
        epicTaskDesc.get(epicId).setSubTaskListId(updatedListSubTasks);
        updateEpicStatus(epicId);
        return subTaskDesc;
    }

    @Override
    public ArrayList<Integer> getSubTasksListByEpicId(String id) {
        int epicId = Integer.parseInt(id);
        return epicTaskDesc.get(epicId).getSubTasks();
    }

    @Override
    public List<Task> getHistoryTM() {
        return inMemoryHistoryManager.getHistory();
    }

    public void updateEpicStatus(Integer epicId) {
        int epicSize = epicTaskDesc.get(epicId).getSubTasks().size();
        int counterNew = 0;
        int counterDone = 0;
        for (Integer i : epicTaskDesc.get(epicId).getSubTasks()) {
            if (subTaskDesc.get(i).getStatusTask().equals(TaskStatus.DONE)) {
                counterDone += 1;
            } else if (subTaskDesc.get(i).getStatusTask().equals(TaskStatus.NEW)) {
                counterNew += 1;
            }
        }
        if (counterDone == epicSize) {
            epicTaskDesc.get(epicId).setStatusTask(TaskStatus.DONE);
        } else if (counterNew == epicSize) {
            epicTaskDesc.get(epicId).setStatusTask(TaskStatus.NEW);
        } else {
            epicTaskDesc.get(epicId).setStatusTask(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
        }

    public boolean checkTaskDates(Task task) {
    boolean check = true;
        for (Integer i : singleTaskDesc.keySet()) {
            if (task.getStartTime().isAfter(singleTaskDesc.get(i).getStartTime()) && task.getStartTime().isBefore(singleTaskDesc.get(i).getEndTime()) ||
                    task.getEndTime().isAfter(singleTaskDesc.get(i).getStartTime()) && task.getEndTime().isBefore(singleTaskDesc.get(i).getEndTime()) ||
                    task.getStartTime().isBefore(singleTaskDesc.get(i).getStartTime()) && task.getEndTime().isAfter(singleTaskDesc.get(i).getEndTime()) ||
                    task.getStartTime().isEqual(singleTaskDesc.get(i).getStartTime()) && task.getEndTime().isEqual(singleTaskDesc.get(i).getEndTime())) {
                check = false;
            }
        }
        for (Integer i : subTaskDesc.keySet()) {
            if (task.getStartTime().isAfter(subTaskDesc.get(i).getStartTime()) && task.getStartTime().isBefore(subTaskDesc.get(i).getEndTime()) ||
                    task.getEndTime().isAfter(subTaskDesc.get(i).getStartTime()) && task.getEndTime().isBefore(subTaskDesc.get(i).getEndTime()) ||
                    task.getStartTime().isBefore(subTaskDesc.get(i).getStartTime()) && task.getEndTime().isAfter(subTaskDesc.get(i).getEndTime()) ||
                    task.getStartTime().isEqual(subTaskDesc.get(i).getStartTime()) && task.getEndTime().isEqual(subTaskDesc.get(i).getEndTime())) {
                check = false;
            }

        }
    return check;
    }

}
