package http.handler;

import static http.handler.HttpTaskServer.*;
import static org.junit.jupiter.api.Assertions.*;
import static tasks.TaskStatus.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import managers.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import tasks.Task;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskHttpHandlerTest {
    InMemoryTaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = CustomGson.getGson();
    Task taskNoId = new Task("Test 1", "Testing task 1",
            NEW, LocalDateTime.parse("01.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5));
    Task taskWithID = new Task("Test 1", "Testing task 1",1,
            NEW, LocalDateTime.parse("01.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5));
    Task taskWithID2 = new Task("Test 2", "Testing task 2",2,
            NEW, LocalDateTime.parse("02.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5));
    Task taskWithIDUpd = new Task("Test 1", "Testing task 1",1,
            IN_PROGRESS, LocalDateTime.parse("01.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5));
    Task taskWithIDInterrupted = new Task("Test 3", "Testing task 3",
            NEW, LocalDateTime.parse("01.02.2010, 14:02", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5));


    public TaskHttpHandlerTest() {
    }

    @BeforeEach
    public void setUp() throws IOException {
        manager.deleteTasks();
        start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {

        String taskJson = gson.toJson(taskNoId);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Task> tasksFromManager = manager.getTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 1", tasksFromManager.getFirst().getTitle(), "Некорректное имя задачи");
    }

    @Test
    public void testUpdateTask() throws IOException, InterruptedException {
        manager.addTask(taskWithID);
        String taskJson = gson.toJson(taskWithIDUpd);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Task> tasksFromManager = manager.getTasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals(IN_PROGRESS, tasksFromManager.getFirst().getStatusTask(), "Некорректный статус");
    }

    @Test
    public void testSearchTaskByID() throws IOException, InterruptedException {
        manager.addTask(taskWithID);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Task responseTask = gson.fromJson(response.body(), Task.class);

        assertEquals(responseTask, taskWithID, "Вернулась некорректная задача");
    }

    @Test
    public void testGetTasks() throws IOException, InterruptedException {
        manager.addTask(taskWithID);
        manager.addTask(taskWithID2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());


        class TaskListTypeToken extends TypeToken<ArrayList<Task>> {
        }
        ArrayList<Task> tasks = gson.fromJson(response.body(), new TaskListTypeToken().getType());

        List<Task> tasksFromManager = manager.getTasks();
        assertEquals(2, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals(taskWithID,  tasks.get(0), "Некорректная задача");
        assertEquals(taskWithID2,  tasks.get(1), "Некорректное имя задачи");
    }

@Test
    public void testDeleteTaskByID() throws IOException, InterruptedException {
        manager.addTask(taskWithID);
        manager.addTask(taskWithID2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        ArrayList<Task> tasks = manager.getTasks();

        List<Task> tasksFromManager = manager.getTasks();
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals(taskWithID2,  tasks.getFirst(), "Некорректная задача");
    }
    @Test
    public void testAddInterruptedTask() throws IOException, InterruptedException {
        manager.addTask(taskWithID);
        String taskJson = gson.toJson(taskWithIDInterrupted);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(406, response.statusCode());
    }
}