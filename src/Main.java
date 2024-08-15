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
                            inMemoryTaskManager.getSubtasks();
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
                            inMemoryTaskManager.deleteSubTasks();
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
                            Task newSingleTask = new Task(title, description, inMemoryTaskManager.genId(), TaskStatus.NEW);
                            inMemoryTaskManager.addTask(newSingleTask);

                            break;
                        case "2":
                            System.out.println("Введите название эпика:");
                            title = scanner.nextLine();
                            System.out.println("Введите описание эпика:");
                            description = scanner.nextLine();
                            ArrayList<Integer> newSubTaskList = new ArrayList<>();
                            Epic newEpic = new Epic(title, description, inMemoryTaskManager.genId(), TaskStatus.NEW,  newSubTaskList);
                            inMemoryTaskManager.addEpic(newEpic);

                            System.out.println("Наполните Эпик задачами, введите название подзадачи:");
                            String nextSubTaskName = scanner.nextLine();
                            System.out.println("Введите описание подзадачи:");
                            String nextSubTaskDescription = scanner.nextLine();
                            while (!nextSubTaskName.isEmpty()) {
                                SubTask nextSubTask = new SubTask(nextSubTaskName,
                                        nextSubTaskDescription,
                                        inMemoryTaskManager.genId(),
                                        TaskStatus.NEW,
                                        inMemoryTaskManager.getLastEpicId());
                                inMemoryTaskManager.addSubTask(nextSubTask);
                                newSubTaskList.add(inMemoryTaskManager.getLastEpicId());
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
                            if (inMemoryTaskManager.getTaskById(cmd).equals(null)) {
                                System.out.println("Задача не найдена.");
                                break;
                            } else {
                                System.out.println("Выберите статус задачи:");
                                System.out.println("1. Взята в работу");
                                System.out.println("2. Выполнена");
                                int newStatus = scanner.nextInt();
                                inMemoryTaskManager.updateTask(cmd, newStatus);
                            }
                            break;
                        case "2":
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            if (inMemoryTaskManager.getSubTaskById(cmd).equals(null)){
                                System.out.println("Подзадача не найдена.");
                                break;
                            } else {
                                System.out.println("Выберите статус подзадачи:");
                                System.out.println("1. Взята в работу");
                                System.out.println("2. Выполнена");
                                int newStatus = scanner.nextInt();
                                inMemoryTaskManager.updateSubTask(cmd, newStatus);
                            }
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
                            if (inMemoryTaskManager.getTaskById(cmd).equals(null)) {
                                System.out.println("Задача не найдена.");
                                break;
                            } else {
                                inMemoryTaskManager.deleteTaskById(cmd);
                            }
                            break;
                        case "2":
                            System.out.println("Выберите ID");
                            inMemoryTaskManager.getSubtasks();
                            cmd = scanner.nextLine();
                            if (inMemoryTaskManager.getSubTaskById(cmd).equals(null)) {
                                System.out.println("Подзадача не найдена.");
                                break;
                            } else {
                                inMemoryTaskManager.deleteSubTaskById(cmd);
                            }
                            break;
                        case "3":
                            System.out.println("Выберите ID");
                            inMemoryTaskManager.getEpics();
                            cmd = scanner.nextLine();
                            if (inMemoryTaskManager.getEpicById(cmd).equals(null)) {
                                System.out.println("Эпик не найден.");
                                break;
                            } else {
                                inMemoryTaskManager.deleteEpicById(cmd);
                            }
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
                    if (inMemoryTaskManager.getEpicById(cmd).equals(null)) {
                        System.out.println("Эпик не найден.");
                        break;
                    } else {
                        inMemoryTaskManager.getSubTasksListByEpicId(cmd);
                    }
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

