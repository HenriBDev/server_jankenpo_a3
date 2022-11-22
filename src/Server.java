import java.io.*;
import java.net.*;

public class Server {
    public static void main(String args[]) throws IOException {

        int PORTA_SERVER = 8081;
        int PORTA_CLIENTE = 8000;
        ServerSocket socketServidor = new ServerSocket(PORTA_SERVER);
        Socket socketJogador1 = new Socket();
        socketJogador1.close();
        System.out.println("Servidor iniciado...");
        int totalJogadoresConectados = 0;
        while (true) {
            Socket socketCliente = socketServidor.accept();
            System.out.println(socketCliente.getInetAddress().getHostAddress());
            DataInputStream inputCliente = new DataInputStream(socketCliente.getInputStream());
            if(inputCliente.readUTF().equals("Conexão")){
                totalJogadoresConectados += 1;
                if(totalJogadoresConectados < 2){
                    System.out.println("Conectado Jogador 1!");
                    socketJogador1 = socketCliente;
                    continue;
                }
                else{
                    System.out.println("Conectado Jogador 2!");
                    Socket socketResposta = new Socket(socketJogador1.getInetAddress(), PORTA_CLIENTE);
                    socketJogador1.close();
                    DataOutputStream outputResposta = new DataOutputStream(socketResposta.getOutputStream());
                    outputResposta.writeUTF("Conexão Estabelecida!");
                    outputResposta.flush();
                    socketResposta.close();

                    System.out.println(socketCliente);
                    System.out.println(socketJogador1);
                    socketResposta = new Socket(socketCliente.getInetAddress(), PORTA_CLIENTE);
                    socketCliente.close();
                    outputResposta = new DataOutputStream(socketResposta.getOutputStream());
                    outputResposta.writeUTF("Conexão Estabelecida");
                    outputResposta.flush();
                    socketResposta.close();
                }
            }else{
                GeradorThread escuta = new GeradorThread(socketCliente);
                escuta.start();
            }
        }
    }
}
