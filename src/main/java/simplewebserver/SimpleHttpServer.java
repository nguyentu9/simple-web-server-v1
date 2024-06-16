package simplewebserver;

import simplewebserver.core.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHttpServer {
    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 10;


    public static void main(String[] args) throws InterruptedException, IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server is listening on port " + PORT);

//        while (true) {
//            Thread.sleep(1000);
//            Socket socket = serverSocket.accept();
//            new Thread(new simplewebserver.core.ClientHandler(socket)).start();
//        }

        // Improve performance by using a thread pool to handle client connections.
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        while(true) {
           Socket socket = serverSocket.accept();
           threadPool.execute(new ClientHandler(socket));
        }
    }

}

