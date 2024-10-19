package http.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import managers.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

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

import static org.junit.jupiter.api.Assertions.*;
import static tasks.TaskStatus.*;

class SubtasksHttpHandlerTest {
    InMemoryTaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = CustomGson.getGson();
    Epic epicTest = new Epic("Epic Test 1", "Testing subtasks 1", 1,
            NEW, LocalDateTime.now(), Duration.ofMinutes(0), null, LocalDateTime.now());
    SubTask subTasklNoId1  = new SubTask("Test 1", "Testing SubTask 1" ,
            NEW, LocalDateTime.parse("01.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5),1);
    SubTask subTasklNoId2  = new SubTask("Test 2", "Testing SubTask 2" ,
            NEW, LocalDateTime.parse("02.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5),1);
    SubTask subTasklWithId1  = new SubTask("Test 3", "Testing SubTask 2" ,2,
            NEW, LocalDateTime.parse("03.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5),1);
    SubTask subTasklWithId2  = new SubTask("Test 4", "Testing SubTask 2" ,3,
            NEW, LocalDateTime.parse("04.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5),1);
    SubTask subTasklWithId1Upd  = new SubTask("Test 3", "Testing SubTask 2" ,2,
            DONE, LocalDateTime.parse("03.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5),1);
    SubTask subTasklWithId1Interrupted  = new SubTask("Test 3", "Testing SubTask 2" ,
            NEW, LocalDateTime.parse("03.02.2010, 14:02", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5),1);

     public SubtasksHttpHandlerTest() throws IOException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        manager.deleteTasks();
        taskServer.start();
    }

    @AfterEach
    public void shutDown() {
        taskServer.stop();
    }

    @Test
    public void testAddSubTask() throws IOException, InterruptedException {
        manager.addEpic(epicTest);
        manager.genId();

        String taskJson = gson.toJson(subTasklNoId1);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<SubTask> tasksFromManager = manager.getSubtasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Test 1", tasksFromManager.get(0).getTitle(), "Некорректное имя задачи");

        String taskJson2 = gson.toJson(subTasklNoId2);
        HttpClient client2 = HttpClient.newHttpClient();
        URI url2 = URI.create("http://localhost:8080/subtasks");
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(url2)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson2))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<SubTask> tasksFromManager2 = manager.getSubtasks();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(2, tasksFromManager2.size(), "Некорректное количество задач");
        assertEquals("Test 2", tasksFromManager2.get(1).getTitle(), "Некорректное имя задачи");

    }

    @Test
    public void testUpdateSubTask() throws IOException, InterruptedException {
        manager.addEpic(epicTest);
        manager.addSubTask(subTasklWithId1);
        manager.addSubTask(subTasklWithId2);

        String taskJson = gson.toJson(subTasklWithId1Upd);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<SubTask> tasksFromManager = manager.getSubtasks();
        List<Epic> epicFromManager = manager.getEpics();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(2, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals(DONE, tasksFromManager.get(0).getStatusTask(), "Некорректный статус подзадачи");
        assertEquals(IN_PROGRESS, epicFromManager.get(0).getStatusTask(), "Некорректный статус эпика");
    }

    @Test
    public void testSearchSubTaskByID() throws IOException, InterruptedException {
        manager.addEpic(epicTest);
        manager.addSubTask(subTasklWithId1);
        manager.addSubTask(subTasklWithId2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Task responseTask = gson.fromJson(response.body(), SubTask.class);

        assertEquals(responseTask, subTasklWithId1, "Вернулась некорректная задача");
    }

    @Test
    public void testGetSubTasks() throws IOException, InterruptedException {
        manager.addEpic(epicTest);
        manager.addSubTask(subTasklWithId1);
        manager.addSubTask(subTasklWithId2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());


        class TaskListTypeToken extends TypeToken<ArrayList<SubTask>> {
        }
        ArrayList<Task> tasks = gson.fromJson(response.body(), new TaskListTypeToken().getType());

        List<SubTask> tasksFromManager = manager.getSubtasks();
        assertEquals(2, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals(subTasklWithId1,  tasks.get(0), "Некорректная задача");
        assertEquals(subTasklWithId2,  tasks.get(1), "Некорректное имя задачи");
    }

    @Test
    public void testDeleteSubTaskByID() throws IOException, InterruptedException {
        manager.addEpic(epicTest);
        manager.addSubTask(subTasklWithId1);
        manager.addSubTask(subTasklWithId2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Epic> epicFromManager = manager.getEpics();

        List<SubTask> tasksFromManager = manager.getSubtasks();
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals(subTasklWithId2,  tasksFromManager.get(0), "Некорректная задача");
        assertEquals(NEW, epicFromManager.get(0).getStatusTask(), "Некорректный статус эпика");

    }

    @Test
    public void testAddInterruptedTask() throws IOException, InterruptedException {
        manager.addEpic(epicTest);
        manager.addSubTask(subTasklWithId1);
        manager.addSubTask(subTasklWithId2);
        String taskJson = gson.toJson(subTasklWithId1Interrupted);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(406, response.statusCode());
    }
}
