package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.InMemoryTaskManager;
import managers.TaskManager;

import java.io.IOException;

public class SubtasksHttpHandler extends BaseHttpHandler implements HttpHandler {
    public SubtasksHttpHandler(InMemoryTaskManager manager) {
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
            case GET_BY_ID: {
                //handleGetComments(exchange);
                break;
            }
            case POST: {
                //handlePostComments(exchange);
                break;
            }
            case DELETE: {
                //handlePostComments(exchange);
                break;
            }
            default:
                //hwriteResponse(exchange, "Такого эндпоинта не существует", 404);
        }

    }
}
