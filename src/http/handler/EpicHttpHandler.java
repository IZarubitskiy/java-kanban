package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import tasks.Epic;
import tasks.Task;
import tasks.TaskStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EpicHttpHandler extends BaseHttpHandler implements HttpHandler {

    public EpicHttpHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange e) throws IOException {
        Endpoint endpoint = getEndpoint(e.getRequestURI().getPath(), e.getRequestMethod());

        switch (endpoint) {
            case GET: {
                httpGetEpics(e);
                break;
            }
            case GET_BY_ID: {
                httpGetEpicById(e);
                break;
            }
            case GET_SUB_BY_EPIC: {
                httpGetSubsByEpicID(e);
                break;
            }
            case POST: {
                httpAddEpic(e);
                break;
            }
            case DELETE: {
                httpDeleteEpicById(e);
                break;
            }
            default:
                wrongEndpoint(e);
        }
    }

    private void httpGetEpics(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        if (m.getEpics().isEmpty()) {
            sendNotFound(e, "Задач пока нет.");
            return;
        }
        String resp = gson.toJson(m.getEpics());
        sendText(e, resp, 200);
    }

    private void httpGetEpicById(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        if (httpGetId(e).isEmpty()) {
            sendNotFound(e, "Такого ID не существует");
            return;
        }
        int id = httpGetId(e).get();
        Epic task = m.getEpicById(id);
        if (task == null) {
            sendNotFound(e, "Задача не найдена.");
            return;
        }
        String resp = gson.toJson(task);
        sendText(e, resp, 200);
    }

    private void httpGetSubsByEpicID(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        if (httpGetId(e).isEmpty()) {
            sendNotFound(e, "Такого ID не существует");
            return;
        }
        int id = httpGetId(e).get();
        ArrayList<Integer> subsList = m.getSubTasksListByEpicId(id);
        if (subsList == null) {
            sendNotFound(e, "Задачи не найдена.");
            return;
        }
        String resp = gson.toJson(subsList);
        sendText(e, resp, 200);
    }

    private void httpAddEpic(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        InputStream inputStream = e.getRequestBody();
        String request = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Task taskFromRequest = gson.fromJson(request, Epic.class);
        if (taskFromRequest.getId() != null) {
            sendHasInteractions(e, "Обновлять эпик нельзя.");
            return;
        }
        if (taskFromRequest.getId() == null) {
            if (m.checkTaskDates(taskFromRequest)) {
                m.addEpic(new Epic(taskFromRequest.getTitle(), taskFromRequest.getDescription(), m.genId(), TaskStatus.NEW,
                        taskFromRequest.getStartTime(), taskFromRequest.getDuration(), new ArrayList<Integer>(), taskFromRequest.getEndTime()));
                sendText(e, "Задача успешно добавлена", 201);
                return;
            }
        }
        serverProblem(e, "Ошибка сервера - некорректный запрос");
    }

    private void httpDeleteEpicById(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        if (httpGetId(e).isEmpty()) {
            sendNotFound(e, "Некорректный идентификатор задачи");
            return;
        }
        int id = httpGetId(e).get();

        if (m.getEpicById(id) != null) {
            m.deleteEpicById(id);
            sendText(e, "Задача успешно удалена", 200);
            return;
        }
        sendNotFound(e, "Задача не найдена.");
    }
}