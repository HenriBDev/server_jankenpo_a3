import java.io.*;
import java.net.Socket;

public class GeradorThread extends Thread {

    private Socket socketCliente;
    private DataOutputStream outputResposta;
    private DataInputStream inputCliente;

    public GeradorThread(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    public void run() {
        try{
            inputCliente = new DataInputStream(socketCliente.getInputStream());
            if(inputCliente.readUTF().equals("Conexão")){
                if(Partida.ipJogador1 == null){
                    Partida.ipJogador1 = socketCliente.getInetAddress();
                    Partida.portaJogador1 = socketCliente.getPort();

                    while (Partida.portaJogador2 == 0){
                        System.out.print("");
                    }

                    outputResposta = new DataOutputStream(socketCliente.getOutputStream());
                    outputResposta.writeUTF("Conexão Estabelecida!");
                    outputResposta.flush();
                }
                else{
                    Partida.ipJogador2 = socketCliente.getInetAddress();
                    Partida.portaJogador2 = socketCliente.getPort();

                    outputResposta = new DataOutputStream(socketCliente.getOutputStream());
                    outputResposta.writeUTF("Conexão Estabelecida!");
                    outputResposta.flush();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
}