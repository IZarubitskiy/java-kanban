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

    public ArrayList<Task> getAllSingleTasks() {
        ArrayList<Task> allSingleTasks = new ArrayList<>();
        for (Integer i : singleTaskDesc.keySet()) {
            allSingleTasks.add(singleTaskDesc.get(i));
        }
        return allSingleTasks;
    }

    public ArrayList<Epic> getAllEpicTasks() {
        ArrayList<Epic> allEpicTasks = new ArrayList<>();
        for (Integer i : epicTaskDesc.keySet()) {
            allEpicTasks.add(epicTaskDesc.get(i));
        }
        return allEpicTasks;
    }

    public ArrayList<SubTask> getAllSubTasks() {
        ArrayList<SubTask> allSubTasks = new ArrayList<>();
        for (Integer i : subTaskDesc.keySet()) {
            allSubTasks.add(subTaskDesc.get(i));
        }
        return allSubTasks;
    }

    public HashMap<Integer, Task> deleteAllSingleTasks() {
        singleTaskDesc.clear();
        return singleTaskDesc;
    }

    public HashMap<Integer, Epic> deleteAllEpicTasks() {
        epicTaskDesc.clear();
        subTaskDesc.clear();
        return epicTaskDesc;
    }

    public HashMap<Integer, SubTask> deleteAllSubTasks() {
        subTaskDesc.clear();
        for (Integer i : epicTaskDesc.keySet()) {
            epicTaskDesc.get(i).setStatusTask(TaskStatus.NEW);
        }
        return subTaskDesc;
    }

    public Task getSingleTaskById(String id) {
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

    public Epic getEpicById(String id) {
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

    public SubTask getSubTaskById(String id) {
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

    public Task createSingleTask(Task newSingleTask) {
        id += 1;
        singleTaskDesc.put(id, newSingleTask);
        return newSingleTask;
    }

    public Epic createEpic(Epic newEpic) {
        id += 1;
        lastEpicId = id;
        epicTaskDesc.put(id, newEpic);
        return newEpic;
    }
        public SubTask createSubTask (SubTask newSubTask){
        id += 1;
        subTaskDesc.put(id, newSubTask);
        return  newSubTask;
    }

    public Epic setLastEpicWithSubTask(ArrayList<Integer> subTaskList){
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
