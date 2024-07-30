import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TaskManager {
    private static int id = 0;
    private static int lastEpicId = 0;
    Scanner scanner = new Scanner(System.in);
    private HashMap<Integer, Task> singleTaskDesc = new HashMap<>();
    private HashMap<Integer, Epic> epicTaskDesc = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskDesc = new HashMap<>();

    public static int getLastId() {
        return id;
    }
    public static int getLastEpicId() {
        return lastEpicId;
    }

    public HashMap<Integer, Task> printSingleTaskList() {
        System.out.println(singleTaskDesc);
        return singleTaskDesc;
    }

    public HashMap<Integer, Epic> printEpicTaskList() {
        System.out.println(epicTaskDesc);
        return epicTaskDesc;
    }

    public HashMap<Integer, SubTask> printSubTaskList() {
        System.out.println(subTaskDesc);
        return subTaskDesc;
    }

    public HashMap<Integer, Task> clearSingleTaskList() {
        singleTaskDesc.clear();
        return singleTaskDesc;
    }

    public HashMap<Integer, Epic> clearEpicTaskList() {
        epicTaskDesc.clear();
        subTaskDesc.clear();
        return epicTaskDesc;
    }

    public HashMap<Integer, SubTask> clearSubTaskList() {
        subTaskDesc.clear();
        for (Integer i : epicTaskDesc.keySet()) {
            epicTaskDesc.get(i).setStatusTask(TaskStatus.NEW);
        }
        return subTaskDesc;
    }

    public Task searchSingleTaskById(String id) {
        Task foundTask = new Task("empty", "empty", TaskStatus.NEW);
        int searchId = Integer.parseInt(id);
        for (Integer i : singleTaskDesc.keySet()) {
            if (i == searchId) {
                foundTask = singleTaskDesc.get(i);
                break;
            } else {
                System.out.println("Задача не обнаружена");
                return null;
            }
        }
        System.out.println(foundTask);
        return foundTask;
    }

    public Epic searchEpicById(String id) {
        ArrayList<Integer> emptyListID = new ArrayList<>();
        Epic foundEpic = new Epic("empty", "empty", TaskStatus.NEW, emptyListID);
        int searchId = Integer.parseInt(id);
        for (Integer i : epicTaskDesc.keySet()) {
            if (i == searchId) {
                foundEpic = epicTaskDesc.get(i);
                break;
            } else {
                System.out.println("Задача не обнаружена");
                return null;
            }
        }
        System.out.println(foundEpic);
        return foundEpic;
    }

    public SubTask searchSubTaskById(String id) {
        int emptyEpicID = 0;
        SubTask foundSubTask = new SubTask("empty", "empty", TaskStatus.NEW, emptyEpicID);
        int searchId = Integer.parseInt(id);
        for (Integer i : subTaskDesc.keySet()) {
            if (i == searchId) {
                foundSubTask = subTaskDesc.get(i);
                break;
            } else {
                System.out.println("Задача не обнаружена");
                return null;
            }
        }
        System.out.println(foundSubTask);
        return foundSubTask;
    }

    public Task createSingleTask(String title, String description) {
        Task newTask = new Task(title, description, TaskStatus.NEW);
        id += 1;
        singleTaskDesc.put(id, newTask);
        return newTask;
    }

    public Epic createEpic(String title, String description) {
        ArrayList<Integer> newSubTaskList = new ArrayList<>();
        Epic newEpic = new Epic(title, description, TaskStatus.NEW, newSubTaskList);
        id += 1;
        lastEpicId = id;
        epicTaskDesc.put(id, newEpic);
        return newEpic;
    }
        public SubTask createSubTask (String title, String description){
        SubTask newSubTask = new SubTask( title, description, TaskStatus.NEW, lastEpicId);
        id += 1;
        subTaskDesc.put(id, newSubTask);
        return  newSubTask;
    }

    public Epic fillEpicWithSubTask (ArrayList<Integer> subTaskList){
        epicTaskDesc.get(lastEpicId).setIdSubtasklist(subTaskList);
        return  epicTaskDesc.get(lastEpicId);
    }

    public Task updateSingleTask(String id) {
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

    public HashMap<Integer, SubTask> deleteSingleTask(String id) {
        int deletedId = Integer.parseInt(id);
        if (singleTaskDesc.containsKey(deletedId)) {
            singleTaskDesc.remove(deletedId);
        } else {
            System.out.println("Задача не найдена");
        }
        return subTaskDesc;

    }

    public HashMap<Integer, Epic> deleteEpic(String id) {
        int deletedId = Integer.parseInt(id);
        if (epicTaskDesc.containsKey(deletedId)) {
            for (Integer subTask : epicTaskDesc.get(deletedId).getSubTasks()) {
                for (Integer i : subTaskDesc.keySet()) {
                    if (subTask == i) {
                        subTaskDesc.remove(i);
                    }
                    epicTaskDesc.remove(deletedId);
                }
            }
        } else {
            System.out.println("Задача не найдена");
        }
        return epicTaskDesc;
    }

    public HashMap<Integer, SubTask> deleteSubTask(String id) {
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
            subTaskDesc.remove(deletedId);
        }
        return subTaskDesc;
    }

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
}
