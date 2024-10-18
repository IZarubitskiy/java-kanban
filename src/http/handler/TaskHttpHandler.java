package http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import managers.TaskManager;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.io.IOException;

public class TaskHttpHandler extends BaseHttpHandler implements HttpHandler {
    public TaskHttpHandler(TaskManager manager) {
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
                sendHasInteractions(e, "Такого эндпоинта не существует");
        }
    }
}

