package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;

import java.io.IOException;

public class HistoryHttpHandler extends BaseHttpHandler implements HttpHandler {
    public HistoryHttpHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange e) throws IOException {
        Endpoint endpoint = getEndpoint(e.getRequestURI().getPath(), e.getRequestMethod());

        switch (endpoint) {
            case GET: {
                httpHystory(e);
                break;
            }
            default:
                wrongEndpoint(e);
        }
    }

    private void httpHystory(HttpExchange e) throws IOException {
        TaskManager m = getManager();
        if (m.getHistoryTM().isEmpty()) {
            sendNotFound(e, "Задач пока нет.");
            return;
        }
        String resp = gson.toJson(m.getHistoryTM());
        sendText(e, resp, 200);
    }
}
