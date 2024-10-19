package http.handler;

import com.sun.net.httpserver.HttpServer;
import managers.InMemoryTaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static InMemoryTaskManager manager = new InMemoryTaskManager();

    public HttpTaskServer(InMemoryTaskManager manager) throws IOException {
        HttpTaskServer.manager = manager;
    }

    public static HttpServer httpServer;

    public void stop() {
        httpServer.stop(0);
    }

    public static void start() throws IOException {
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHttpHandler(manager));
        httpServer.createContext("/subtasks", new SubtasksHttpHandler(manager));
        httpServer.createContext("/epics", new EpicHttpHandler(manager));
        httpServer.createContext("/history", new HistoryHttpHandler(manager));
        httpServer.createContext("/prioritized", new PrioritizedHttpHandler(manager));
        httpServer.start();
    }

    public static void main(String[] args) throws IOException {
        start();
    }


}
