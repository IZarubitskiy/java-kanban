package http.handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class BaseHttpHandler {

    private final TaskManager manager;
    protected final Gson gson = CustomGson.getGson();

    public BaseHttpHandler(TaskManager manager) {
        this.manager = manager;
    }

    // сюда можно ставить код 200 и 201
    protected void sendText(HttpExchange h, String text, int code) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(code, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendNotFound(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(404, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendHasInteractions(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(406, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void serverProblem(HttpExchange h) throws IOException {
        byte[] resp = "Ошибка сервера - некорректный запрос".getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(500, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void wrongEndpoint(HttpExchange h) throws IOException {
        byte[] resp = "Такого эндпоинта не существует.".getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(405, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }


    protected Endpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");

        if (pathParts.length == 4) {
            return Endpoint.GET_SUB_BY_EPIC;
        }
        if (pathParts.length == 3) {
            if (requestMethod.equals("DELETE")) {
                return Endpoint.DELETE;
            }
            if (requestMethod.equals("GET")) {
                return Endpoint.GET_BY_ID;
            }
        }
        if (pathParts.length == 2) {
            if (requestMethod.equals("GET")) {
                return Endpoint.GET;
            }
            if (requestMethod.equals("POST")) {
                return Endpoint.POST;
            }
        }
        return Endpoint.UNKNOWN;
    }

    protected Optional<Integer> httpGetId(HttpExchange e) {
        String[] pathParts = e.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[2]));
        } catch (NumberFormatException exception) {
            return Optional.empty();
        }
    }

    public TaskManager getManager() {
        return manager;
    }
}