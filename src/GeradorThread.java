import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class GeradorThread extends Thread {

    private Socket socketCliente;
    private Socket socketResposta;
    private DataOutputStream outputResposta;
    private DataInputStream inputCliente;


    public GeradorThread(Socket socketCliente) {
        try{
            this.socketCliente = socketCliente;
            this.inputCliente = new DataInputStream(this.socketCliente.getInputStream());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            System.out.println(inputCliente.readUTF());            
            socketResposta = new Socket(this.socketCliente.getInetAddress(), 5002);
            this.socketCliente.close();
            outputResposta = new DataOutputStream(socketResposta.getOutputStream());
            outputResposta.writeUTF("Respondi");
            outputResposta.flush();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}