package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;

import java.io.IOException;

public class PrioritizedHttpHandler extends BaseHttpHandler implements HttpHandler {
    public PrioritizedHttpHandler(InMemoryTaskManager manager) {
        super(manager);
    }

    Gson gson = CustomGson.getGson();

    @Override
    public void handle(HttpExchange e) throws IOException {
        Endpoint endpoint = getEndpoint(e.getRequestURI().getPath(), e.getRequestMethod());

        switch (endpoint) {
            case GET: {
                httpPrioritizedTask(e, getManager());
                break;
            }
            default:
                sendHasInteractions(e, "Такого эндпоинта не существует");
        }
    }

    private void httpPrioritizedTask(HttpExchange e, InMemoryTaskManager m) throws IOException {
        if (m.getPrioritizedTasks().isEmpty()) {
            sendNotFound(e, "Задач пока нет.");
            return;
        }
        String resp = gson.toJson(m.getPrioritizedTasks());
        sendText(e, resp, 200);
    }
}
