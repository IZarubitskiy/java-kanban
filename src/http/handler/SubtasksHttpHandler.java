package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;
import managers.TaskManager;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SubtasksHttpHandler extends BaseHttpHandler implements HttpHandler {
    public SubtasksHttpHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange e) throws IOException {

        Endpoint endpoint = getEndpoint(e.getRequestURI().getPath(), e.getRequestMethod());

        switch (endpoint) {

            case GET: {
                httpGetSubTasks(e);
                break;
            }
            case GET_BY_ID: {
                httpGetSubTaskById(e);
                break;
            }
            case POST: {
                httpUpdOrAddSubTask(e);
                break;
            }
            case DELETE: {
                httpDeleteSubTaskById(e);
                break;
            }
            default:
                wrongEndpoint(e);
        }
    }

    private void httpGetSubTasks(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        if (m.getSubtasks().isEmpty()) {
            sendNotFound(e, "Задач пока нет.");
            return;
        }
        String resp = gson.toJson(m.getSubtasks());
        sendText(e, resp, 200);
    }

    private void httpGetSubTaskById(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        if (httpGetId(e).isEmpty()) {
            sendNotFound(e, "Такого ID не существует");
            return;
        }
        int id = httpGetId(e).get();
        SubTask task = m.getSubTaskById(Integer.toString(id));
        if (task == null) {
            sendNotFound(e, "Задача не найдена.");
            return;
        }
        String resp = gson.toJson(task);
        sendText(e, resp, 200);
    }

    private void httpUpdOrAddSubTask(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        InputStream inputStream = e.getRequestBody();
        String request = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Task taskFromRequest = gson.fromJson(request, SubTask.class);
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
            m.updateSubTask(Integer.toString(taskFromRequest.getId()), statusIdentifier);
            sendText(e, "Задача успешно обновлена", 201);
            return;
        }

        if (taskFromRequest.getId() == null) {
            if (m.checkTaskDates(taskFromRequest)) {
                m.addSubTask(new SubTask(taskFromRequest.getTitle(), taskFromRequest.getDescription(), m.genId(), TaskStatus.NEW, taskFromRequest.getStartTime(), taskFromRequest.getDuration(), 1));
                sendText(e, "Задача успешно добавлена", 201);
                return;
            }
            sendHasInteractions(e, "Пересечение с существующей задачей");
            return;
        }

        serverProblem(e, "Ошибка сервера - некорректный запрос");
    }

    private void httpDeleteSubTaskById(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        if (httpGetId(e).isEmpty()) {
            sendNotFound(e, "Некорректный идентификатор задачи");
            return;
        }
        int id = httpGetId(e).get();

        if (m.getSubTaskById(Integer.toString(id)) != null) {
            m.deleteSubTaskById(Integer.toString(id));
            sendText(e, "Задача успешно удалена", 200);
            return;
        }
        sendNotFound(e, "Задача не найдена.");
    }
}