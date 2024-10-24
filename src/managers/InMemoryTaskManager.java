package managers;

import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    private static int id = 0;
    private static int lastEpicId = 0;
    private final HashMap<Integer, Task> singleTaskDesc = new HashMap<>();
    private final HashMap<Integer, Epic> epicTaskDesc = new HashMap<>();
    private final HashMap<Integer, SubTask> subTaskDesc = new HashMap<>();
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    private final Set<Task> prioritizedTasks = new TreeSet<>((Task t1, Task t2) -> {
        if (t1.getStartTime().isBefore(t2.getStartTime())) {
            return -1;
        } else {
            return 1;
        }
    });

    @Override
    public Integer getLastEpicId() {
        return lastEpicId;
    }

    @Override
    public void setId(int value) {
        id = value;
    }

    @Override
    public void setLastEpicId(int value) {
        lastEpicId = value;
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
    public Task getTaskById(int id) {
        Task foundTask = singleTaskDesc.get(id);
        if (foundTask != null) {
            inMemoryHistoryManager.add(foundTask);
        }
        return foundTask;
    }

    @Override  // remake
    public Epic getEpicById(int id) {
        Epic foundEpic = epicTaskDesc.get(id);
        if (foundEpic != null) {
            inMemoryHistoryManager.add(foundEpic);
        }
        return foundEpic;
    }

    @Override // remake
    public SubTask getSubTaskById(int id) {
        SubTask foundSubTask = subTaskDesc.get(id);
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
        epicTaskDesc.put(newEpic.getId(), newEpic);
        setLastEpicId(newEpic.getId());
        return newEpic;
    }

    @Override
    public LocalDateTime getEpicStartTime(Epic epic) {
        LocalDateTime firstSubTaskTime = subTaskDesc.get(epic.getSubTasks().getFirst()).getStartTime();

        for (Integer subTaskId : epic.getSubTasks()) {
            if (firstSubTaskTime.isAfter(subTaskDesc.get(subTaskId).getStartTime())) {
                firstSubTaskTime = subTaskDesc.get(subTaskId).getStartTime();
            }
        }
        return firstSubTaskTime;
    }

    @Override
    public LocalDateTime getEpicEndTime(Epic epic) {
        LocalDateTime lastSubTaskTime = subTaskDesc.get(epic.getSubTasks().getFirst()).getEndTime();
        for (Integer subTaskId : epic.getSubTasks()) {
            if (lastSubTaskTime.isBefore(subTaskDesc.get(subTaskId).getEndTime())) {
                lastSubTaskTime = subTaskDesc.get(subTaskId).getEndTime();
            }
        }
        return lastSubTaskTime;
    }

    @Override
    public Duration getEpicDuratioon(Epic epic) {
        Duration epicDuration = Duration.ofMinutes(0);
        for (Integer subTaskId : epic.getSubTasks()) {
            epicDuration = epicDuration.plus(subTaskDesc.get(subTaskId).getDuration());
        }
        return epicDuration;
    }

    @Override
    public SubTask addSubTask(SubTask newSubTask) {
        if (checkTaskDates(newSubTask)) {
            prioritizedTasks.add(newSubTask);
            subTaskDesc.put(newSubTask.getId(), newSubTask);

            ArrayList<Integer> newSubTaskListId = epicTaskDesc.get(newSubTask.getEpicId()).getSubTasks();
            if (epicTaskDesc.get(newSubTask.getEpicId()).getSubTasks() == null) {
                newSubTaskListId = new ArrayList<>();
            }
            newSubTaskListId.add(newSubTask.getId());
            epicTaskDesc.get(newSubTask.getEpicId()).setSubTaskListId(newSubTaskListId);
            epicTaskDesc.get(newSubTask.getEpicId()).setStartTime(getEpicStartTime(epicTaskDesc.get(newSubTask.getEpicId())));
            epicTaskDesc.get(newSubTask.getEpicId()).setEndTime(getEpicEndTime(epicTaskDesc.get(newSubTask.getEpicId())));
            epicTaskDesc.get(newSubTask.getEpicId()).setDuration(getEpicDuratioon(epicTaskDesc.get(newSubTask.getEpicId())));

        } else {
            System.out.println("Такое время уже существует");
            id -= 1;
        }
        return newSubTask;
    }

    @Override
    public void setLastEpicWithSubTask(ArrayList<Integer> subTaskList) {
        epicTaskDesc.get(lastEpicId).setSubTaskListId(subTaskList);
    }

    @Override
    public Task updateTask(int id, Integer status) {
        if (status == 1) {
            singleTaskDesc.get(id).setStatusTask(TaskStatus.IN_PROGRESS);
        } else if (status == 2) {
            singleTaskDesc.get(id).setStatusTask(TaskStatus.DONE);
        }
        return singleTaskDesc.get(id);
    }

    @Override
    public SubTask updateSubTask(int id, Integer status) {
        if (status == 1) {
            subTaskDesc.get(id).setStatusTask(TaskStatus.IN_PROGRESS);
        } else if (status == 2) {
            subTaskDesc.get(id).setStatusTask(TaskStatus.DONE);
        }
        int epicId = subTaskDesc.get(id).getEpicId();
        updateEpicStatus(epicId);
        return subTaskDesc.get(id);
    }

    @Override
    public HashMap<Integer, Task> deleteTaskById(int id) {
        singleTaskDesc.remove(id);
        inMemoryHistoryManager.remove(id);
        return singleTaskDesc;
    }

    @Override
    public HashMap<Integer, Epic> deleteEpicById(int id) {
        epicTaskDesc.remove(id);
        inMemoryHistoryManager.remove(id);
        subTaskDesc.keySet().stream()
                .filter(i -> subTaskDesc.get(i).getEpicId() == id)
                .forEach(i -> {
                    subTaskDesc.remove(i);
                    inMemoryHistoryManager.remove(i);
                });
        return epicTaskDesc;
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTaskById(int id) {
        int epicId = subTaskDesc.get(id).getEpicId();
        subTaskDesc.remove(id);
        inMemoryHistoryManager.remove(id);
        ArrayList<Integer> updatedListSubTasks = subTaskDesc.keySet().stream()
                .filter(i -> subTaskDesc.get(i).getEpicId() == epicId)
                .map(i -> subTaskDesc.get(i).getId())
                .collect(Collectors.toCollection(ArrayList::new));
        epicTaskDesc.get(epicId).setSubTaskListId(updatedListSubTasks);
        updateEpicStatus(epicId);
        return subTaskDesc;
    }

    @Override
    public ArrayList<Integer> getSubTasksListByEpicId(int id) {
        return epicTaskDesc.get(id).getSubTasks();
    }

    @Override
    public List<Task> getHistoryTM() {
        return inMemoryHistoryManager.getHistory();
    }

    public void updateEpicStatus(int epicId) {
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

    @Override
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
