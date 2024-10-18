import com.sun.net.httpserver.HttpServer;
import http.handler.*;
import managers.InMemoryTaskManager;
import managers.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        TaskManager manager = new InMemoryTaskManager();

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHttpHandler(manager));
        httpServer.createContext("/subtasks", new SubtasksHttpHandler(manager));
        httpServer.createContext("/epics", new EpicHttpHandler(manager));
        httpServer.createContext("/history", new HystoryHttpHandler(manager));
        httpServer.createContext("/prioritized", new PrioritizedHttpHandler(manager));

        start(httpServer);


    }
        public static void start(HttpServer httpServer) {
            httpServer.start();
            System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
        }

        public static void stop(HttpServer httpServer){
        httpServer.stop(1);
}
}
