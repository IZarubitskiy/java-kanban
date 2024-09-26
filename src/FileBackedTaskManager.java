import java.util.ArrayList;
import java.util.HashMap;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.io.FileReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.File;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager  {

    private static final String HOME = System.getProperty("user.home");

    File dir = new File("C:\\Users\\Igor\\java-kanban\\dbTaskManager");
    File dbTaskManager = new File("dbTaskManager.csv");
    private int id = 0;
    private int lastEpicId = 0;
    private HashMap<Integer, Task> singleTaskDesc;
    private HashMap<Integer, Epic> epicTaskDesc;
    private HashMap<Integer, SubTask> subTaskDesc;
    InMemoryHistoryManager inMemoryHistoryManager;

    public FileBackedTaskManager(File dbTaskManager, int id, int lastEpicId, HashMap<Integer, Task> singleTaskDesc, HashMap<Integer, Epic> epicTaskDesc, HashMap<Integer, SubTask> subTaskDesc, InMemoryHistoryManager inMemoryHistoryManager) {
        this.dbTaskManager = dbTaskManager;
        this.id = id;
        this.lastEpicId = lastEpicId;
        this.singleTaskDesc = singleTaskDesc;
        this.epicTaskDesc = epicTaskDesc;
        this.subTaskDesc = subTaskDesc;
        this.inMemoryHistoryManager = inMemoryHistoryManager;
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
        save();
        return task;
    }

    @Override
    public Epic addEpic(Epic newEpic) {
        Epic epic = super.addEpic(newEpic);
        save();
        return epic;
    }

    @Override
    public SubTask addSubTask(SubTask newSubTask) {
        SubTask subtask = super.addSubTask(newSubTask);
        save();
        return subtask;
    }

    @Override
    public Task updateTask(String id, Integer status) {
        return super.updateTask(id, status);
    }

    @Override
    public SubTask updateSubTask(String id, Integer status) {
        return super.updateSubTask(id, status);
    }

    @Override
    public HashMap<Integer, Task> deleteTaskById(String id) {
        return super.deleteTaskById(id);
    }

    @Override
    public HashMap<Integer, Epic> deleteEpicById(String id) {
        return super.deleteEpicById(id);
    }

    @Override
    public HashMap<Integer, SubTask> deleteSubTaskById(String id) {
        return super.deleteSubTaskById(id);
    }

    public void save() {
        Path dbTaskManagerDirectoryPath = Paths.get(HOME, "java-kanban", "dbTaskManager");
        Writer fileWriter = null;
        try {

            if (!Files.exists(dbTaskManagerDirectoryPath)) {
                Path dbTaskManagerDirectory =
                        Files.createDirectory(dbTaskManagerDirectoryPath);
            }
        if (Files.exists(Paths.get(String.valueOf(dbTaskManagerDirectoryPath),"dbTaskManager.csv"))) {
            Path dbTaskManagerPath = Paths.get(HOME, "java-kanban", "dbTaskManager", "dbTaskManager.csv");
            Files.delete(dbTaskManagerPath);
        }
        Path dbTaskManager =
                Files.createFile(Paths.get(String.valueOf(dbTaskManagerDirectoryPath), "dbTaskManager.csv"));
        Path dbTaskManagerPath = Paths.get(HOME, "java-kanban", "dbTaskManager", "dbTaskManager.csv");
        fileWriter = new FileWriter(dbTaskManagerPath.toFile(), true);
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
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException exp) { // ловим исключение NullPointerException
            System.out.println("Ошибка: передан неинициализированный объект!");
        }
        }

    }

    void reader() {

        Reader fileReader = null;
        try {
            Path dbTaskManagerDirectoryPath = Paths.get(HOME, "java-kanban", "dbTaskManager", "dbTaskManager.csv");
            fileReader = new FileReader(dbTaskManagerDirectoryPath.toFile());

            int data = fileReader.read();
            while (data != -1) {
                System.out.print((char) data);
                data = fileReader.read();
            }

            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException exp) { // ловим исключение NullPointerException
                System.out.println("Ошибка: передан неинициализированный объект!");
            }
        }
    }

    static FileBackedTaskManager loadFromFile(File file) {

        FileReader reader;
        BufferedReader br;
        List<String> tasksList = new ArrayList<>();
        Reader fileReader = null;
        try {
            Path dbTaskManagerDirectoryPath = Paths.get(HOME, "java-kanban", "dbTaskManager", "dbTaskManager.csv");
            reader = new FileReader(file);
            br = new BufferedReader(reader);
            while (br.ready()) {
                String line = br.readLine();
                tasksList.add(line);
            }
            reader.close();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException exp) { // ловим исключение NullPointerException
                System.out.println("Ошибка: передан неинициализированный объект!");
            }
        }
        int lastIdFromFile = 0;
        int lastEpicIdFromFile = 0;
        HashMap<Integer, Task> sglTasksFromFile = new HashMap<>();
        HashMap<Integer, Epic> epicTasksFromFile = new HashMap<>();
        HashMap<Integer, SubTask> subTasksFromFile = new HashMap<>();
        InMemoryHistoryManager inMemoryHistoryManagerFromFile = new InMemoryHistoryManager();

        for (String str : tasksList) {
            String[] split = str.split(",");
            if (Integer.parseInt(split[0]) >= lastIdFromFile) {
                lastIdFromFile = Integer.parseInt(split[0]);
            }
            if (split[1].equals("EPIC") && Integer.parseInt(split[0]) >= lastEpicIdFromFile) {
                lastEpicIdFromFile = Integer.parseInt(split[0]);
            }
            if (split[1].equals("TASK")) {
                sglTasksFromFile.put(Integer.parseInt(split[0]), new Task(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3])));
            }

            if (split[1].equals("SUBTASK")) {
                subTasksFromFile.put(Integer.parseInt(split[0]), new SubTask(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]),
                        Integer.parseInt(split[5])));
            }
        }
        for (String str : tasksList) {
            String[] split = str.split(",");
            if (split[1].equals("EPIC")) {
                ArrayList<Integer> idSubtasklistFromFile = new ArrayList<>();
                for (Integer i : subTasksFromFile.keySet()) {
                    if (subTasksFromFile.get(i).getEpicId() == Integer.parseInt(split[0])) {
                        idSubtasklistFromFile.add(subTasksFromFile.get(i).getId());
                    }
                }
                epicTasksFromFile.put(Integer.parseInt(split[0]), new Epic(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]),
                        idSubtasklistFromFile));
            }
        }

        FileBackedTaskManager fileBackedTaskManagerFromFile = new FileBackedTaskManager(file, lastIdFromFile, lastEpicIdFromFile,
                sglTasksFromFile, epicTasksFromFile, subTasksFromFile, inMemoryHistoryManagerFromFile);
        return fileBackedTaskManagerFromFile;
    }

    private String toString(Task task) {
    String strTask = task.getId() + "," + task.getType() + "," + task.getTitle() + "," + task.getStatusTask().name() + ","
            + task.getDescription() + "," + task.getEpicId() + "\n";
    return strTask;
    }

    private Task fromString(String value) {
        String[] split = value.split(",");
        Task taskFromString;
        switch (TaskTypes.valueOf(split[1])) {
            case TASK:
                return taskFromString = new Task(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]));

            case EPIC:
                return taskFromString = new Epic(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]),
                        new ArrayList<Integer>());

            case SUBTASK:
                return taskFromString = new SubTask(split[2], split[4], Integer.parseInt(split[0]), TaskStatus.valueOf(split[3]),
                        Integer.parseInt(split[5]));
        }
        return null;
    }

}
