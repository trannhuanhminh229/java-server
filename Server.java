
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            int port = 443;
//            int port = Integer.parseInt(System.getenv().getOrDefault("port", "443 "));
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
