import java.io.*;
import java.net.*;

public class Server {
    public static void main(String args[]) throws IOException {

        ServerSocket socketServidor = new ServerSocket(5000);
        System.out.println("Servidor iniciado...");

        while (true) {
            Socket socketCliente = socketServidor.accept();

            GeradorThread escuta = new GeradorThread(socketCliente);
            escuta.start();
        }

    }
}
