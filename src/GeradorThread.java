import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class GeradorThread extends Thread {
    Socket socketClient;
    PrintWriter out;
    BufferedReader in;

    public GeradorThread(Socket socketCliente) {
        this.socketClient = socketCliente;
    }

    public void run() {
        try {
            System.out.println("Thread criada!");
            socketClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}