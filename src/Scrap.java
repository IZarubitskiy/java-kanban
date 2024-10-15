public class Scrap {
    /*



        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Epic epic1 = new Epic("Test addNewEpic 1", "Test addNewEpic 1 description", 14,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")),
                Duration.ofMinutes(60), new ArrayList<Integer>(), LocalDateTime.parse("01.02.2022, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")));

        SubTask subTask1 = new SubTask("Test addNewsubTask 1", "Test addNewTask 1 description", 15,
                TaskStatus.NEW, LocalDateTime.parse("01.06.2020, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20),
                epic1.getId());

        SubTask subTask2 = new SubTask("Test addNewsubTask2", "Test addNewTask 2 description", 16,
                TaskStatus.NEW, LocalDateTime.parse("02.07.2021, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25),
                epic1.getId());
        SubTask subTask3 = new SubTask("Test addNewsubTask 3", "Test addNewTask 3 description", 17,
                TaskStatus.NEW, LocalDateTime.parse("03.08.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15),
                epic1.getId());
        Task task1 = new Task("Test addNewTask 1", "Test addNewTask 1 description", 18,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2020, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));
        Task task2 = new Task("Test addNewTask 2", "Test addNewTask 2 description", 19,
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2021, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25));
        Task task3 = new Task("Test addNewTask 3", "Test addNewTask 3 description", 20,
                TaskStatus.DONE, LocalDateTime.parse("03.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15));
        Task task4 = new Task("Test addNewTask 4", "Test addNewTask 4 description", 21,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2023, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task5 = new Task("Test addNewTask 5", "Test addNewTask 5 description", 22,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2024, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task6 = new Task("Test addNewTask 6", "Test addNewTask 6 description", 23,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2025, 14:20", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(10));
        Task task7 = new Task("Test addNewTask 7", "Test addNewTask 7 description", 24,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2026, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));
        Task task8 = new Task("Test addNewTask 8", "Test addNewTask 8 description", 25,
                TaskStatus.IN_PROGRESS, LocalDateTime.parse("02.02.2027, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(25));
        Task task9 = new Task("Test addNewTask 9", "Test addNewTask 9 description", 26,
                TaskStatus.DONE, LocalDateTime.parse("03.02.2028, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(15));
        Task task10 = new Task("Test addNewTask 10", "Test addNewTask 10 description", 27,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2029, 14:10", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));
        Task task11 = new Task("Test addNewTask 11", "Test addNewTask 11 description", 28,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2030, 13:50", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(60));


//



        Task task = new Task("Test addNewTask 0", "Test addNewTask 0 description", 0,
                TaskStatus.NEW, LocalDateTime.parse("01.02.2022, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(20));

        inMemoryTaskManager.addTask(task);
        final int taskId = task.getId();

        final Task savedTask = inMemoryTaskManager.getTaskById(Integer.toString(taskId));

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final ArrayList<Task> tasks = inMemoryTaskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");

     */
}
