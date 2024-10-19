package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;

import java.io.IOException;

public class HistoryHttpHandler extends BaseHttpHandler  implements HttpHandler {
    public HistoryHttpHandler(InMemoryTaskManager manager) {
        super(manager);
    }
    Gson gson = CustomGson.getGson();

    @Override
    public void handle(HttpExchange e) throws IOException {
        Endpoint endpoint = getEndpoint(e.getRequestURI().getPath(), e.getRequestMethod());

        switch (endpoint) {
            case GET: {
                httpHystory(e , getManager());
                break;
            }
            default:
                sendHasInteractions(e, "Такого эндпоинта не существует");
        }
    }
    private void httpHystory(HttpExchange e, InMemoryTaskManager m) throws IOException{
        if(m.getHistoryTM().isEmpty()) {
            sendNotFound(e, "Задач пока нет.");
            return;
        }
        String resp = gson.toJson(m.getHistoryTM());
        sendText(e, resp, 200);
    }
}
