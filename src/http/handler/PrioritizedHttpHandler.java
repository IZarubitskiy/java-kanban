package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;
import managers.TaskManager;

import java.io.IOException;

public class PrioritizedHttpHandler extends BaseHttpHandler implements HttpHandler {
    public PrioritizedHttpHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange e) throws IOException {
        Endpoint endpoint = getEndpoint(e.getRequestURI().getPath(), e.getRequestMethod());

        switch (endpoint) {
            case GET: {
                httpPrioritizedTask(e);
                break;
            }
            default:
                wrongEndpoint(e);
        }
    }

    private void httpPrioritizedTask(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        if (m.getPrioritizedTasks().isEmpty()) {
            sendNotFound(e, "Задач пока нет.");
            return;
        }
        String resp = gson.toJson(m.getPrioritizedTasks());
        sendText(e, resp, 200);
    }
}
