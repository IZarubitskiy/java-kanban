package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;

import java.io.IOException;
import java.util.Objects;

public class PrioritizedHttpHandler extends BaseHttpHandler implements HttpHandler {
    public PrioritizedHttpHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange e) throws IOException {
        Endpoint endpoint = getEndpoint(e.getRequestURI().getPath(), e.getRequestMethod());

        if (Objects.requireNonNull(endpoint) == Endpoint.GET) {
            httpPrioritizedTask(e);
        } else {
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
