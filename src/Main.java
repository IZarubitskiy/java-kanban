import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Scanner scanner = new Scanner(System.in);
        String cmd;
        String title;
        String description;

        while (true) {
            menu();
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1":
                    System.out.println("Выберите список задач:");
                    System.out.println("1. Задачи.");
                    System.out.println("2. Эпик.");
                    System.out.println("3. Подзадачи.");
                    System.out.println("4. Выход.");
                    cmd = scanner.nextLine();
                    switch (cmd) {
                        case "1":
                            inMemoryTaskManager.getTasks();
                            break;
                        case "2":
                            inMemoryTaskManager.getEpics();
                            break;
                        case "3":
                            inMemoryTaskManager.getEpicSubtasks();
                            break;
                        case "4":
                            break;
                        default:
                            System.out.println("Такой команды нет");
                            break;
                    }
                    break;
                case "2":
                    System.out.println("Выберите список, который необходимо очистить:");
                    System.out.println("1. Задачи.");
                    System.out.println("2. Эпики.");
                    System.out.println("3. Подзадачи.");
                    System.out.println("4. Выход.");
                    cmd = scanner.nextLine();
                    switch (cmd) {
                        case "1":
                            inMemoryTaskManager.deleteTasks();
                            break;
                        case "2":
                            inMemoryTaskManager.deleteEpics();
                            break;
                        case "3":
                            inMemoryTaskManager.deleteEpicSubtasks();
                            break;
                        case "4":
                            break;
                        default:
                            System.out.println("Такой команды нет");
                            break;
                    }
                    break;
                case "3":
                    System.out.println("Выберите список, в котором необходимо искать задачу:");
                    System.out.println("1. Задачи.");
                    System.out.println("2. Эпики.");
                    System.out.println("3. Подзадачи.");
                    System.out.println("4. Выход.");
                    cmd = scanner.nextLine();
                    switch (cmd) {
                        case "1":
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            inMemoryTaskManager.getTaskById(cmd);
                            break;
                        case "2":
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            inMemoryTaskManager.getEpicById(cmd);
                            break;
                        case "3":
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            inMemoryTaskManager.getSubTaskById(cmd);
                            break;
                        case "4":
                            break;
                        default:
                            System.out.println("Такой команды нет");
                            break;
                    }
                    break;
                case "4":
                    System.out.println("Выберите задачу, которую хотите создать:");
                    System.out.println("1. Задача.");
                    System.out.println("2. Эпик.");
                    System.out.println("3. Выход.");
                    cmd = scanner.nextLine();
                    switch (cmd) {
                        case "1":
                            System.out.println("Введите название задачи:");
                            title = scanner.nextLine();
                            System.out.println("Введите описание задачи:");
                            description = scanner.nextLine();
                            Task newSingleTask = new Task(title, description, TaskStatus.NEW);
                            inMemoryTaskManager.addTask(newSingleTask);

                            break;
                        case "2":
                            System.out.println("Введите название эпика:");
                            title = scanner.nextLine();
                            System.out.println("Введите описание эпика:");
                            description = scanner.nextLine();
                            ArrayList<Integer> newSubTaskList = new ArrayList<>();
                            Epic newEpic = new Epic(title, description, TaskStatus.NEW, newSubTaskList);
                            inMemoryTaskManager.addEpic(newEpic);

                            System.out.println("Наполните Эпик задачами, введите название подзадачи:");
                            String nextSubTaskName = scanner.nextLine();
                            System.out.println("Введите описание подзадачи:");
                            String nextSubTaskDescription = scanner.nextLine();
                            while (!nextSubTaskName.isEmpty()) {
                                SubTask nextSubTask = new SubTask(nextSubTaskName,
                                        nextSubTaskDescription,
                                        TaskStatus.NEW,
                                        inMemoryTaskManager.getLastEpicId());
                                inMemoryTaskManager.addSubTask(nextSubTask);
                                newSubTaskList.add(inMemoryTaskManager.getLastId());
                                System.out.println("Введите название подзадачи:");
                                nextSubTaskName = scanner.nextLine();
                                System.out.println("Введите описание подзадачи:");
                                nextSubTaskDescription = scanner.nextLine();
                            }
                            inMemoryTaskManager.setLastEpicWithSubTask(newSubTaskList);
                            break;
                        case "3":
                            return;
                        default:
                            System.out.println("Такой команды нет");
                            break;
                    }
                    break;
                case "5":
                    System.out.println("Выберите список, в котором необходимо обновить задачу:");
                    System.out.println("1. Задачи.");
                    System.out.println("2. Подзадачи.");
                    System.out.println("3. Выход.");
                    cmd = scanner.nextLine();
                    switch (cmd) {
                        case "1":
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            inMemoryTaskManager.updateTask(cmd);
                            break;
                        case "2":
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            inMemoryTaskManager.updateSubTask(cmd);
                            break;
                        case "3":
                            break;
                        default:
                            System.out.println("Такой команды нет");
                            break;
                    }
                    break;
                case "6":
                    System.out.println("Выберите список, в котором необходимо удалить задачу:");
                    System.out.println("1. Задачи.");
                    System.out.println("2. Подзадачи.");
                    System.out.println("3. Эпик.");
                    System.out.println("4. Выход.");
                    cmd = scanner.nextLine();
                    switch (cmd) {
                        case "1":
                            inMemoryTaskManager.getTasks();
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            inMemoryTaskManager.deleteTaskById(cmd);
                            break;
                        case "2":
                            System.out.println("Выберите ID");
                            inMemoryTaskManager.getEpicSubtasks();
                            cmd = scanner.nextLine();
                            inMemoryTaskManager.deleteSubTaskById(cmd);
                            break;
                        case "3":
                            System.out.println("Выберите ID");
                            inMemoryTaskManager.getEpics();
                            cmd = scanner.nextLine();
                            inMemoryTaskManager.deleteEpicById(cmd);
                            break;
                        case "4":
                            break;
                        default:
                            System.out.println("Такой команды нет");
                            break;
                    }
                    break;
                case "7":
                    System.out.println("Выберите ID");
                    inMemoryTaskManager.getEpics();
                    cmd = scanner.nextLine();
                    inMemoryTaskManager.getSubTasksListByEpicId(cmd);
                    break;
                case "8":
                    inMemoryTaskManager.getHistory();
                    break;
                case "9":
                    return;
                default:
                    System.out.println("Такой команды нет");
                    break;
            }
        }
    }
    public static void menu() {
        System.out.println("Действия:");
        System.out.println("1. Печать");
        System.out.println("2. Очистка");
        System.out.println("3. Поиск");
        System.out.println("4. Добавить");
        System.out.println("5. Обновление");
        System.out.println("6. Удаление");
        System.out.println("7. Печать Эпика");
        System.out.println("8. История");
        System.out.println("9. Выход");
    }
}

