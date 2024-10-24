package http.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import managers.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

import static http.handler.HttpTaskServer.*;
import static org.junit.jupiter.api.Assertions.*;
import static tasks.TaskStatus.NEW;

class HistoryHttpHandlerTest {

    InMemoryTaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    Gson gson = CustomGson.getGson();

    Task task1 = new Task("Test 1", "Testing task 1",1,
            NEW, LocalDateTime.parse("01.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5));
    Task task2 = new Task("Test 2", "Testing task 2",2,
            NEW, LocalDateTime.parse("02.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5));
    Task task3 = new Task("Test 1", "Testing task 1",3,
            NEW, LocalDateTime.parse("03.02.2010, 14:00", DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")), Duration.ofMinutes(5));
    public HistoryHttpHandlerTest() {
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
    public void testGetHistory() throws IOException, InterruptedException {
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/history");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());


        class TaskListTypeToken extends TypeToken<ArrayList<Task>> {
        }
        ArrayList<Task> historyFromResponse = gson.fromJson(response.body(), new TaskListTypeToken().getType());

        List<Task> historyFromManager = manager.getHistoryTM();
        assertEquals(3, historyFromManager.size(), "Некорректное количество задач");
        assertEquals(task3,  historyFromResponse.get(0), "Некорректная задача");
        assertEquals(task1,  historyFromResponse.get(2), "Некорректное имя задачи");
    }
}