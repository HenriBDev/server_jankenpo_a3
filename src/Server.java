import java.io.*;
import java.net.*;
import java.util.Calendar;

public class Server {
    public static void main(String args[]) throws IOException {

        int PORTA_SERVER = 8081;
        ServerSocket socketServidor = new ServerSocket(PORTA_SERVER);
        Socket socketCliente;
        GeradorThread geradorThread;

        System.out.println("[ " + Calendar.getInstance().getTime() + " ] Servidor iniciado na porta " + PORTA_SERVER);
        while (true) {
            socketCliente = socketServidor.accept();
            Partida.pontuacaoJogador1 = 0;
            Partida.pontuacaoJogador2 = 0;
            System.out.println("[ " + Calendar.getInstance().getTime() + " ] Conexão recebida de " + socketCliente.getInetAddress());
            geradorThread = new GeradorThread(socketCliente);
            geradorThread.start();
        }
    }
}
