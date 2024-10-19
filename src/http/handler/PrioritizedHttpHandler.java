package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;
import managers.TaskManager;

import java.io.IOException;

public class PrioritizedHttpHandler extends BaseHttpHandler  implements HttpHandler {
    public PrioritizedHttpHandler(InMemoryTaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange e) throws IOException {
        Endpoint endpoint = getEndpoint(e.getRequestURI().getPath(), e.getRequestMethod());

        switch (endpoint) {
            case GET: {
                //handleGetPosts(exchange);
                break;
            }
            default:
                //hwriteResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }
}
