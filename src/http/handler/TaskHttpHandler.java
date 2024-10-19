package http.handler;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;
import tasks.Task;

import java.io.IOException;

import tasks.TaskStatus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class TaskHttpHandler extends BaseHttpHandler implements HttpHandler {

    public TaskHttpHandler(InMemoryTaskManager manager) {
        super(manager);
    }

    Gson gson = CustomGson.getGson();

    @Override
    public void handle(HttpExchange e) throws IOException {

        Endpoint endpoint = getEndpoint(e.getRequestURI().getPath(), e.getRequestMethod());
        switch (endpoint) {
            case GET: {
                httpGetTasks(e, getManager());
                break;
            }
            case GET_BY_ID: {
                httpGetTasksById(e, getManager());
                break;
            }
            case POST: {
                httpUpdOrAddTask(e, getManager());
                break;
            }
            case DELETE: {
                httpDeleteTaskById(e, getManager());
                break;
            }
            default:
                sendHasInteractions(e, "Такого эндпоинта не существует");
        }
    }

    private void httpGetTasks(HttpExchange e, InMemoryTaskManager m) throws IOException {
        if (m.getTasks().isEmpty()) {
            sendNotFound(e, "Задач пока нет.");
            return;
        }
        String resp = gson.toJson(m.getTasks());
        sendText(e, resp, 200);
    }

    private void httpUpdOrAddTask(HttpExchange e, InMemoryTaskManager m) throws IOException {
        InputStream inputStream = e.getRequestBody();
        String request = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Task taskFromRequest = gson.fromJson(request, Task.class);
        if (taskFromRequest.getId() != null) {
            TaskStatus newStatus = taskFromRequest.getStatusTask();
            int statusIdentifier = 0;
            if (newStatus == TaskStatus.IN_PROGRESS) {
                statusIdentifier = 1;
            }
            if (newStatus == TaskStatus.DONE) {
                statusIdentifier = 2;
            }
            if (statusIdentifier == 0) {
                sendNotFound(e, "Невозможно обновить задачу.");
                return;
            }
            m.updateTask(Integer.toString(taskFromRequest.getId()), statusIdentifier);
            sendText(e, "Задача успешно обновлена", 201);
            return;
        }

        if (taskFromRequest.getId() == null) {
            if (m.checkTaskDates(taskFromRequest)) {
                m.addTask(new Task(taskFromRequest.getTitle(), taskFromRequest.getDescription(), m.genId(), TaskStatus.NEW, taskFromRequest.getStartTime(), taskFromRequest.getDuration()));
                sendText(e, "Задача успешно добавлена", 201);
                return;
            }
            sendHasInteractions(e, "Пересечение с существующей задачей");
            return;
        }

        sendHasInteractions(e, "Ошибка сервера - некорректный запрос");
    }

    private void httpGetTasksById(HttpExchange e, InMemoryTaskManager m) throws IOException {
        if (httpGetId(e).isEmpty()) {
            sendNotFound(e, "Такого ID не существует");
            return;
        }
        int id = httpGetId(e).get();
        Task task = m.getTaskById(Integer.toString(id));
        if (task == null) {
            sendNotFound(e, "Задача не найдена.");
            return;
        }
        String resp = gson.toJson(task);
        sendText(e, resp, 200);
    }

    private void httpDeleteTaskById(HttpExchange e, InMemoryTaskManager m) throws IOException {
        Optional<Integer> taskID = httpGetId(e);
        if (taskID.isEmpty()) {
            sendNotFound(e, "Некорректный идентификатор задачи");
            return;
        }
        int id = taskID.get();

        if (m.getTaskById(Integer.toString(id)) != null) {
            m.deleteTaskById(Integer.toString(id));
            sendText(e, "Задача успешно удалена", 200);
            return;
        }
        sendNotFound(e, "Задача не найдена.");
    }
}

