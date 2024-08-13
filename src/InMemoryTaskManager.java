import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class InMemoryTaskManager implements TaskManager {
    private static int id = 0;
    private static int lastEpicId = 0;
    Scanner scanner = new Scanner(System.in);
    private HashMap<Integer, Task> singleTaskDesc = new HashMap<>();
    private HashMap<Integer, Epic> epicTaskDesc = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskDesc = new HashMap<>();

    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    public static int getLastId() {
        return id;
    }
    public static int getLastEpicId() {
        return lastEpicId;
    }

    @Override
    public Integer getId (Task task){
        Integer foundId = null;
        for (Integer i : singleTaskDesc.keySet()) {
            if (task.equals(singleTaskDesc.get(i))) {
                foundId = i;
                break;
            }
        }
        for (Integer i : epicTaskDesc.keySet()) {
            if (task.equals(epicTaskDesc.get(i))) {
                foundId = i;
                break;
            }
        }
        for (Integer i : subTaskDesc.keySet()) {
            if (task.equals(subTaskDesc.get(i))) {
                foundId = i;
                break;
            }
        }
        return foundId;
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> allSingleTasks = new ArrayList<>();
        for (Integer i : singleTaskDesc.keySet()) {
            allSingleTasks.add(singleTaskDesc.get(i));
        }
        return allSingleTasks;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> allEpicTasks = new ArrayList<>();
        for (Integer i : epicTaskDesc.keySet()) {
            allEpicTasks.add(epicTaskDesc.get(i));
        }
        return allEpicTasks;
    }

    @Override
    public ArrayList<SubTask> getEpicSubtasks() {
        ArrayList<SubTask> allSubTasks = new ArrayList<>();
        for (Integer i : subTaskDesc.keySet()) {
            allSubTasks.add(subTaskDesc.get(i));
        }
        return allSubTasks;
    }

    @Override
    public HashMap<Integer, Task> deleteTasks() {
        for (Task task : singleTaskDesc.values()) {
         inMemoryHistoryManager.removeTask(task);
        }
        singleTaskDesc.clear();
        return singleTaskDesc;
    }

    @Override
    public HashMap<Integer, Epic> deleteEpics() {
        for (Task task : epicTaskDesc.values()) {
            inMemoryHistoryManager.removeTask(task);
        }
        for (Task task : subTaskDesc.values()) {
            inMemoryHistoryManager.removeTask(task);
        }
        epicTaskDesc.clear();
        subTaskDesc.clear();
        return epicTaskDesc;
    }

    @Override
    public HashMap<Integer, SubTask> deleteEpicSubtasks() {
        for (Task task : subTaskDesc.values()) {
            inMemoryHistoryManager.removeTask(task);
        }
        subTaskDesc.clear();
        for (Integer i : epicTaskDesc.keySet()) {
            epicTaskDesc.get(i).setStatusTask(TaskStatus.NEW);
        }
        return subTaskDesc;
    }

    @Override
    public Task getTaskById(String id) {
        Task foundTask = null;
        int searchId = Integer.parseInt(id);
        for (Integer i : singleTaskDesc.keySet()) {
            if (i == searchId) {
                foundTask = singleTaskDesc.get(i);
                inMemoryHistoryManager.add(foundTask);
                break;
            }
        }
        return foundTask;
    }

    @Override
    public Epic getEpicById(String id) {
        ArrayList<Integer> emptyListID = new ArrayList<>();
        Epic foundEpic = null;
        int searchId = Integer.parseInt(id);
        for (Integer i : epicTaskDesc.keySet()) {
            if (i == searchId) {
                foundEpic = epicTaskDesc.get(i);
                inMemoryHistoryManager.add(foundEpic);
                break;
            }
        }
        System.out.println(foundEpic);
        return foundEpic;
    }

    @Override
    public SubTask getSubTaskById(String id) {
        SubTask foundSubTask = null;
        int searchId = Integer.parseInt(id);
        for (Integer i : subTaskDesc.keySet()) {
            if (i == searchId) {
                foundSubTask = subTaskDesc.get(i);
                inMemoryHistoryManager.add(foundSubTask);
                break;
            }
        }
        System.out.println(foundSubTask);
        return foundSubTask;
    }

    @Override
    public Task addTask(Task newSingleTask) {
        id += 1;
        singleTaskDesc.put(id, newSingleTask);
        return newSingleTask;
    }

    @Override
    public Epic addEpic(Epic newEpic) {
        id += 1;
        lastEpicId = id;
        epicTaskDesc.put(id, newEpic);
        return newEpic;
    }
        @Override
        public SubTask addSubTask(SubTask newSubTask){
        id += 1;
        subTaskDesc.put(id, newSubTask);
        return  newSubTask;
    }

    @Override
    public Epic setLastEpicWithSubTask(ArrayList<Integer> subTaskList){
        epicTaskDesc.get(lastEpicId).setIdSubtasklist(subTaskList);
        return  epicTaskDesc.get(lastEpicId);
    }

    @Override
    public Task updateTask(String id) {
        Task updatedTask = new Task("empty", "empty", TaskStatus.NEW);
        int updateId = Integer.parseInt(id);
        if (singleTaskDesc.containsKey(updateId)) {
            System.out.println("Выберите статус задачи:");
            System.out.println("1. Взята в работу");
            System.out.println("2. Выполнена");
            int newStatus = scanner.nextInt();
            if (newStatus == 1) {
                singleTaskDesc.get(updateId).setStatusTask(TaskStatus.IN_PROGRESS);
            } else if (newStatus == 2) {
                singleTaskDesc.get(updateId).setStatusTask(TaskStatus.DONE);
            }
            updatedTask = singleTaskDesc.get(updateId);
            System.out.println(updatedTask);
        } else {
            updatedTask = null;
            System.out.println("Задача не найдена");
        }
        return updatedTask;
    }

    @Override
    public SubTask updateSubTask(String id) {
        SubTask updatedSubTask = new SubTask("empty", "empty", TaskStatus.NEW, 0);
        int updateId = Integer.parseInt(id);
        if (singleTaskDesc.containsKey(updateId)) {
            System.out.println("Выберите статус задачи:");
            System.out.println("1. Взята в работу");
            System.out.println("2. Выполнена");
            int newStatus = scanner.nextInt();
            if (newStatus == 1) {
                subTaskDesc.get(updateId).setStatusTask(TaskStatus.IN_PROGRESS);
            } else if (newStatus == 2) {
                subTaskDesc.get(updateId).setStatusTask(TaskStatus.DONE);
            }
            updatedSubTask = subTaskDesc.get(updateId);
            System.out.println(updatedSubTask);
            int counter = 0;
            int result = epicTaskDesc.get(subTaskDesc.get(updateId).getEpicId()).getSubTasks().size();
            for (Integer i : epicTaskDesc.get(subTaskDesc.get(updateId).getEpicId()).getSubTasks()) {
                if (subTaskDesc.get(i).getStatusTask().equals(TaskStatus.DONE)) {
                    counter += 1;
                }
            }
            if (counter == result) {
                epicTaskDesc.get(subTaskDesc.get(updateId).getEpicId()).setStatusTask(TaskStatus.DONE);
            } else {
                epicTaskDesc.get(subTaskDesc.get(updateId).getEpicId()).setStatusTask(TaskStatus.IN_PROGRESS);
            }
        } else {
            updatedSubTask = null;
        }
        return updatedSubTask;
    }

    @Override
    public HashMap<Integer, SubTask> deleteTaskById(String id) {
        int deletedId = Integer.parseInt(id);
        if (singleTaskDesc.containsKey(deletedId)) {
            Task taskToRemove = singleTaskDesc.get(deletedId);
            inMemoryHistoryManager.removeTask(taskToRemove);
            singleTaskDesc.remove(deletedId);
        } else {
            System.out.println("Задача не найдена");
        }
        return subTaskDesc;

    }

    @Override
    public HashMap<Integer, Epic> deleteEpicById(String id) {
        int deletedId = Integer.parseInt(id);
        if (epicTaskDesc.containsKey(deletedId)) {
            for (Integer subTask : epicTaskDesc.get(deletedId).getSubTasks()) {
                for (Integer i : subTaskDesc.keySet()) {
                    if (subTask == i) {
                        SubTask taskToRemove = subTaskDesc.get(deletedId);
                        inMemoryHistoryManager.removeTask(taskToRemove);
                        subTaskDesc.remove(i);
                    }
                    Epic taskToRemove = epicTaskDesc.get(deletedId);
                    inMemoryHistoryManager.removeTask(taskToRemove);
                    epicTaskDesc.remove(deletedId);
                }
            }
        } else {
            System.out.println("Задача не найдена");
        }
        return epicTaskDesc;
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTaskById(String id) {
        int deletedId = Integer.parseInt(id);
        int identifiedID = 0;
        if (subTaskDesc.containsKey(deletedId)) {
            for (Integer i : epicTaskDesc.keySet()) {
                for (Integer subTask : epicTaskDesc.get(i).getSubTasks()) {
                    if (deletedId == subTask) {
                        identifiedID = i;
                    }
                }
            }
        } else {
            System.out.println("Задача не найдена в Sub");
        }
        if (identifiedID == 0) {
            System.out.println("Задача не найдена в Epic");
        } else {
            ArrayList<Integer> updatedListSubTasks = new ArrayList<>();
            updatedListSubTasks = epicTaskDesc.get(identifiedID).getSubTasks();
            int gotIndex = 0;
            for (int i = 0; i < updatedListSubTasks.size(); i++) {
                if (deletedId == updatedListSubTasks.get(i)) {
                    gotIndex = i;
                }
            }
            System.out.println(gotIndex);
            updatedListSubTasks.remove(gotIndex);
            epicTaskDesc.get(identifiedID).setIdSubtasklist(updatedListSubTasks);
            SubTask taskToRemove = subTaskDesc.get(deletedId);
            inMemoryHistoryManager.removeTask(taskToRemove);
            subTaskDesc.remove(deletedId);
        }
        return subTaskDesc;
    }

    @Override
    public ArrayList<Integer> getSubTasksListByEpicId(String id) {
        int epicId = Integer.parseInt(id);
        ArrayList<Integer> subTasksByEpicId = new ArrayList<>();
        if (epicTaskDesc.containsKey(epicId)) {
            subTasksByEpicId = epicTaskDesc.get(epicId).getSubTasks();
            for (Integer subTask : epicTaskDesc.get(epicId).getSubTasks()) {
                System.out.println(subTaskDesc.get(subTask));
            }
        } else {
            System.out.println("Задача не найдена");
        }
        return subTasksByEpicId;
    }

    @Override
    public List<Task> getHistory(){
        return inMemoryHistoryManager.getHistory();
    }
}
