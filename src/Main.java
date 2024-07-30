import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
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
                            taskManager.printSingleTaskList();
                            break;
                        case "2":
                            taskManager.printEpicTaskList();
                            break;
                        case "3":
                            taskManager.printSubTaskList();
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
                            taskManager.clearSingleTaskList();
                            break;
                        case "2":
                            taskManager.clearEpicTaskList();
                            break;
                        case "3":
                            taskManager.clearSubTaskList();
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
                            taskManager.searchSingleTaskById(cmd);
                            break;
                        case "2":
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            taskManager.searchEpicById(cmd);
                            break;
                        case "3":
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            taskManager.searchSubTaskById(cmd);
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
                            taskManager.createSingleTask(title, description);
                            break;
                        case "2":
                            System.out.println("Введите название эпика:");
                            title = scanner.nextLine();
                            System.out.println("Введите описание эпика:");
                            description = scanner.nextLine();
                            taskManager.createEpic(title, description);
                            System.out.println("Наполните Эпик задачами, введите название подзадачи:");
                            String nextSubTaskName = scanner.nextLine();
                            System.out.println("Введите описание подзадачи:");
                            String nextSubTaskDescription = scanner.nextLine();
                            ArrayList<Integer> subTaskList = new ArrayList<>();
                            while (!nextSubTaskName.equals("")) {
                                taskManager.createSubTask(nextSubTaskName, nextSubTaskDescription);
                                subTaskList.add(taskManager.getLastId());
                                System.out.println("Введите название подзадачи:");
                                nextSubTaskName = scanner.nextLine();
                                System.out.println("Введите описание подзадачи:");
                                nextSubTaskDescription = scanner.nextLine();
                            }
                            taskManager.fillEpicWithSubTask(subTaskList);
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
                            taskManager.updateSingleTask(cmd);
                            break;
                        case "2":
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            taskManager.updateSubTask(cmd);
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
                            taskManager.printSingleTaskList();
                            System.out.println("Выберите ID");
                            cmd = scanner.nextLine();
                            taskManager.deleteSingleTask(cmd);
                            break;
                        case "2":
                            System.out.println("Выберите ID");
                            taskManager.printSubTaskList();
                            cmd = scanner.nextLine();
                            taskManager.deleteSubTask(cmd);
                            break;
                        case "3":
                            System.out.println("Выберите ID");
                            taskManager.printEpicTaskList();
                            cmd = scanner.nextLine();
                            taskManager.deleteEpic(cmd);
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
                    taskManager.printEpicTaskList();
                    cmd = scanner.nextLine();
                    taskManager.getSubTasksListByEpicId(cmd);
                case "8":
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
        System.out.println("8. Выход");
    }
}

