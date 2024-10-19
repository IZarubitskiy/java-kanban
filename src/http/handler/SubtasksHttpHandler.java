package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;
import tasks.SubTask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Optional;

public class SubtasksHttpHandler extends BaseHttpHandler implements HttpHandler {
    public SubtasksHttpHandler(InMemoryTaskManager manager) {
        super(manager);
    }

    Gson gson = CustomGson.getGson();

    @Override
    public void handle(HttpExchange e) throws IOException {

        Endpoint endpoint = getEndpoint(e.getRequestURI().getPath(), e.getRequestMethod());

        switch (endpoint) {

            case GET: {
                httpGetSubTasks(e , getManager());
                break;
            }
            case GET_BY_ID: {
                httpGetSubTasksById(e , getManager());
                break;
            }
            case POST: {
                httpUpdOrCreateSubTask(e, getManager());
                break;
            }
            case DELETE: {
                httpDeleteSubTaskById(e, getManager());
                break;
            }
            default:
                sendHasInteractions(e, "Такого эндпоинта не существует");
        }
    }

    private void httpGetSubTasks(HttpExchange e, InMemoryTaskManager m) throws IOException{
        ArrayList<SubTask> tasks = m.getSubtasks();
        if(tasks.isEmpty()) {
            sendNotFound(e, "Задач пока нет.");
            return;
        }
        String resp = gson.toJson(m.getSubtasks());
        sendText(e, resp, 200);
    }

    private void httpGetSubTasksById(HttpExchange e, InMemoryTaskManager m) throws IOException{
        Optional<Integer> taskID = httpGetId(e);
        if(taskID.isEmpty()) {
            sendNotFound(e, "Такого ID не существует");
            return;
        }
        int id = taskID.get();
        Task task = m.getSubTaskById(Integer.toString(id));
        if(task == null) {
            sendNotFound(e, "Задача не найдена.");
            return;
        }
        String resp = gson.toJson(task);
        sendText(e, resp, 200);
    }

    private void httpUpdOrCreateSubTask(HttpExchange e, InMemoryTaskManager m) throws IOException{
        InputStream inputStream = e.getRequestBody();
        String request = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Task taskFromRequest = gson.fromJson(request, SubTask.class);
        if (taskFromRequest.getId() != null){
            TaskStatus newStatus = taskFromRequest.getStatusTask();
            int statusIdentifier = 0;
            if (newStatus == TaskStatus.IN_PROGRESS){
                statusIdentifier = 1;
            }
            if (newStatus == TaskStatus.DONE){
                statusIdentifier = 2;
            }
            if (statusIdentifier == 0){
                sendNotFound(e, "Невозможно обновить задачу.");
                return;
            }
            m.updateSubTask(Integer.toString(taskFromRequest.getId()), statusIdentifier);
            sendText(e, "Задача успешно обновлена", 201);
            return;
        }

        if (taskFromRequest.getId() == null) {
            if (m.checkTaskDates(taskFromRequest)) {
                m.addSubTask(new  SubTask(taskFromRequest.getTitle(), taskFromRequest.getDescription(), m.genId(), TaskStatus.NEW, taskFromRequest.getStartTime(), taskFromRequest.getDuration(), 1));
                sendText(e, "Задача успешно добавлена", 201);
                return;
            }
            sendHasInteractions(e, "Пересечение с существующей задачей");
            return;
        }

        sendHasInteractions(e, "Ошибка сервера - некорректный запрос");
    }

    private void httpDeleteSubTaskById(HttpExchange e, InMemoryTaskManager m) throws IOException{
        Optional<Integer> taskID = httpGetId(e);
        if(taskID.isEmpty()) {
            sendNotFound(e, "Некорректный идентификатор задачи");
            return;
        }
        int id = taskID.get();

        if(m.getSubTaskById(Integer.toString(id)) != null) {
            m.deleteSubTaskById(Integer.toString(id));
            sendText(e, "Задача успешно удалена", 200);
            return;
        }
        sendNotFound(e, "Задача не найдена.");
    }
}