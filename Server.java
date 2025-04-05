
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
//            int port = 1503;
            int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "1503"));
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ProcessServer processServer = new ProcessServer(clientSocket);
                processServer.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
