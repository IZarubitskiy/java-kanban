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

import javax.crypto.spec.PSource;
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

class EpicHttpHandlerTest {
    InMemoryTaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = CustomGson.getGson();
    Epic epicTestNoId = new Epic("Epic Test 1", "Testing subtasks 1",
            NEW, LocalDateTime.now(), Duration.ofMinutes(0), null, LocalDateTime.now());
    Epic epicTest1 = new Epic("Epic Test 1", "Testing subtasks 1", 1,
            NEW, LocalDateTime.now(), Duration.ofMinutes(0), null, LocalDateTime.now());
    Epic epicTest2 = new Epic("Epic Test 1", "Testing subtasks 1", 2,
            NEW, LocalDateTime.now(), Duration.ofMinutes(0), null, LocalDateTime.now());
    SubTask subTasklWithId1  = new SubTask("Test 1", "Testing SubTask 2" ,2,
            NEW, LocalDateTime.parse("03.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5),1);
    SubTask subTasklWithId2  = new SubTask("Test 2", "Testing SubTask 2" ,3,
            NEW, LocalDateTime.parse("04.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5),1);

    public EpicHttpHandlerTest() throws IOException {
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
    public void testAddEpic() throws IOException, InterruptedException {

        String taskJson = gson.toJson(epicTestNoId);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());

        List<Epic> tasksFromManager = manager.getEpics();

        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(1, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("Epic Test 1", tasksFromManager.get(0).getTitle(), "Некорректное имя задачи");

    }

    @Test
    public void testSearchEpicByID() throws IOException, InterruptedException {
        manager.addEpic(epicTest1);
        manager.addEpic(epicTest2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/2");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        Task responseTask = gson.fromJson(response.body(), Epic.class);

        assertEquals(responseTask, epicTest2, "Вернулась некорректная задача");
    }

    @Test
    public void testGetEpics() throws IOException, InterruptedException {
        manager.addEpic(epicTest1);
        manager.addEpic(epicTest2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        class TaskListTypeToken extends TypeToken<ArrayList<Epic>> {
        }
        ArrayList<Epic> tasks = gson.fromJson(response.body(), new TaskListTypeToken().getType());

        List<Epic> tasksFromManager = manager.getEpics();
        assertEquals(2, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals(epicTest1,  tasks.get(0), "Некорректная задача");
        assertEquals(epicTest2,  tasks.get(1), "Некорректное имя задачи");
    }

    @Test
    public void testDeleteEpicByID() throws IOException, InterruptedException {
        manager.addEpic(epicTest1);
        manager.addEpic(epicTest2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/2");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());

        List<Epic> epicFromManager = manager.getEpics();

        assertEquals(1, epicFromManager.size(), "Некорректное количество задач");
        assertEquals(epicTest1,  epicFromManager.get(0), "Некорректная задача");

    }

    @Test
    public void testGetSubTasksByEpicID() throws IOException, InterruptedException {
        manager.addEpic(epicTest1);
        manager.addSubTask(subTasklWithId1);
        manager.addSubTask(subTasklWithId2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics/1/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        class TaskListTypeToken extends TypeToken<ArrayList<Integer>> {
        }
        ArrayList<Integer> responseList =  gson.fromJson(response.body(), new TaskListTypeToken());
        System.out.println(responseList);

        assertEquals(2, responseList.size(), "Некорректное количество подзадач");
        assertEquals(2,  responseList.get(0), "Некорректная задача");
        assertEquals(3,  responseList.get(1), "Некорректная задача");
    }
}