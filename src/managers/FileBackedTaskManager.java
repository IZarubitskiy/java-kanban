package managers;

import tasks.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTaskManager {

    File dbTaskManager;

    public FileBackedTaskManager(File dbTaskManager) {
        this.dbTaskManager = dbTaskManager;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManagerFromFile = new FileBackedTaskManager(file);

        List<String> tasksList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                tasksList.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Ошибка чтения.");
        }

        tasksList.removeFirst();

        int lastIdFromFile = 0;
        int lastEpicIdFromFile = 0;
        List<Task> tasksListFormFile = new ArrayList<>();
        List<SubTask> subTasksListFormFile = new ArrayList<>();
        List<Epic> epicsListFormFile = new ArrayList<>();

        for (String str : tasksList) {
            String[] split = str.split(",");
            if (Integer.parseInt(split[0]) >= lastIdFromFile) {
                lastIdFromFile = Integer.parseInt(split[0]);
            }
            if (split[1].equals("EPIC") && Integer.parseInt(split[0]) >= lastEpicIdFromFile) {
                lastEpicIdFromFile = Integer.parseInt(split[0]);
                epicsListFormFile.add((Epic)fileBackedTaskManagerFromFile.fromString(str));
            }
            if (split[1].equals("TASK")) {
                tasksListFormFile.add(fileBackedTaskManagerFromFile.fromString(str));
            }

            if (split[1].equals("SUBTASK")) {
                subTasksListFormFile.add((SubTask) fileBackedTaskManagerFromFile.fromString(str));
            }
        }

        fileBackedTaskManagerFromFile.setId(lastIdFromFile);
        fileBackedTaskManagerFromFile.setLastEpicId(lastEpicIdFromFile);


        for (Task task : tasksListFormFile) {
            fileBackedTaskManagerFromFile.addTask(task);
        }
        for (Epic epic : epicsListFormFile) {
            fileBackedTaskManagerFromFile.addEpic(epic);
        }
        for (SubTask subTask : subTasksListFormFile) {
            fileBackedTaskManagerFromFile.addSubTask(subTask);
        }

        return fileBackedTaskManagerFromFile;
    }


    public void save() throws IOException {
        Writer fileWriter = null;
        try {
            fileWriter = new FileWriter(dbTaskManager);
            fileWriter.write("");
            fileWriter = new FileWriter(dbTaskManager, true);
            fileWriter.write("id,type,name,status,description,epic" + "\n");

            if (!getTasks().isEmpty()) {
                for (Task task : getTasks()) {
                    fileWriter.write(toString(task));
                }
            }

            if (!getEpics().isEmpty()) {
                for (Task epic : getEpics()) {
                    fileWriter.write(toString(epic));
                }
            }

            if (!getSubtasks().isEmpty()) {
                for (Task subTask : getSubtasks()) {
                    fileWriter.write(toString(subTask));
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка записи");
        } finally {
            assert fileWriter != null;
            fileWriter.close();
        }
    }

    public void reader(){

        try (Reader fileReader = new FileReader(dbTaskManager)) {

            int data = fileReader.read();
            while (data != -1) {
                System.out.print((char) data);
                data = fileReader.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }

    private String toString(Task task) {
        return task.getId() + "," + task.getType() + "," + task.getTitle() + "," + task.getStatusTask().name() + ","
                + task.getDescription() + "," + task.getEpicId() + "," + task.getStartTime() + "," + task.getDuration() + ","
                + task.getEndTime() + "\n";
    }

    private Task fromString(String value) {
        String[] split = value.split(",");
        return switch (TaskTypes.valueOf(split[1])) {
            case TASK -> new Task(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]),
                    LocalDateTime.parse(split[6]), Duration.parse(split[7]));
            case EPIC -> new Epic(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]),
                    LocalDateTime.parse(split[6]), Duration.parse(split[7]),
                    new ArrayList<>(), LocalDateTime.parse(split[8]));
            case SUBTASK -> new SubTask(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]),
                    LocalDateTime.parse(split[6]), Duration.parse(split[7]),
                    Integer.parseInt(split[5]));
        };
    }


    @Override
    public Integer getLastEpicId() {
        return super.getLastEpicId();
    }

    @Override
    public Integer genId() {
        return super.genId();
    }


    @Override
    public ArrayList<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public HashMap<Integer, Task> deleteTasks() {
        return super.deleteTasks();
    }

    @Override
    public HashMap<Integer, Epic> deleteEpics() {
        return super.deleteEpics();
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTasks() {
        return super.deleteSubTasks();
    }

    @Override
    public Task addTask(Task newSingleTask) {
        Task task = super.addTask(newSingleTask);
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return task;
    }

    @Override
    public Epic addEpic(Epic newEpic) {
        Epic epic = super.addEpic(newEpic);
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return epic;
    }

    @Override
    public SubTask addSubTask(SubTask newSubTask) {
        SubTask subtask = super.addSubTask(newSubTask);
        try {
            save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return subtask;
    }

    @Override
    public Task updateTask(int id, Integer status) {
        return super.updateTask(id, status);
    }

    @Override
    public SubTask updateSubTask(int id, Integer status) {
        return super.updateSubTask(id, status);
    }

    @Override
    public HashMap<Integer, Task> deleteTaskById(int id) {
        return super.deleteTaskById(id);
    }

    @Override
    public HashMap<Integer, Epic> deleteEpicById(int id) {
        return super.deleteEpicById(id);
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTaskById(int id) {
        return super.deleteSubTaskById(id);
    }

}
