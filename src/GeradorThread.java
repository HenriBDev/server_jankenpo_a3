import java.io.*;
import java.net.Socket;
import java.util.Calendar;

public class GeradorThread extends Thread {

    private Socket socketCliente;
    private DataOutputStream outputResposta;
    private DataInputStream inputCliente;
    private String dadosRequisicao;
    private String dadosResposta;

    public GeradorThread(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    public void run() {
        try{
            inputCliente = new DataInputStream(socketCliente.getInputStream());
            do{
                this.dadosRequisicao = inputCliente.readUTF();
                switch(this.dadosRequisicao){

                    case "Conectar":
                        if(Partida.enderecoJogador1 == null){
                            Partida.enderecoJogador1 = socketCliente.getInetAddress();
    
                            while (Partida.enderecoJogador2 == null){
                                System.out.print("");
                            }
    
                        }
                        else{
                            Partida.enderecoJogador2 = socketCliente.getInetAddress();
                        }
                        outputResposta = new DataOutputStream(socketCliente.getOutputStream());
                        outputResposta.writeUTF("Conexão Estabelecida!");
                        outputResposta.flush();
                    break;
    
                    case "Atualizar Movimento Pedra":
                    case "Atualizar Movimento Papel":
                    case "Atualizar Movimento Tesoura":
                        if(socketCliente.getInetAddress() == Partida.enderecoJogador1){
                            Partida.movimentoJogador1 = dadosRequisicao.split("\\s+")[2];
                            System.out.println("[ " + Calendar.getInstance().getTime() + " ] Jogador 1 mudou movimento para " + Partida.movimentoJogador1);
                        }
                        else if (socketCliente.getInetAddress() == Partida.enderecoJogador2){
                            Partida.movimentoJogador2 = dadosRequisicao.split("\\s+")[2];
                            System.out.println("[ " + Calendar.getInstance().getTime() + " ] Jogador 2 mudou movimento para " + Partida.movimentoJogador2);
                        }
                    break;

                    case "Decidir Vencedor":

                        if(socketCliente.getInetAddress().equals(Partida.enderecoJogador2)){
                            System.out.println("[ " + Calendar.getInstance().getTime() + " ] Delay Jogador 2");
                        }

                        if(Partida.decidindoVencedor == false){

                            Partida.decidindoVencedor = true;
                            if(Partida.movimentoJogador1.equals(Partida.movimentoJogador2)){
                                Partida.pontuacaoJogador1++;
                                Partida.pontuacaoJogador2++;
                            }
                            else{
                                switch(Partida.movimentoJogador1){
                                    case "Pedra":
                                        switch(Partida.movimentoJogador2){
                                            case "Papel":
                                                Partida.vencedorAtual = "Jogador 2";
                                                Partida.pontuacaoJogador2++;
                                            break;
                                            case "Tesoura":
                                                Partida.vencedorAtual = "Jogador 1";
                                                Partida.pontuacaoJogador1++;
                                            break;
                                        }
                                    break;

                                    case "Papel":
                                        switch(Partida.movimentoJogador2){
                                            case "Pedra":
                                                Partida.vencedorAtual = "Jogador 1";
                                                Partida.pontuacaoJogador1++;
                                            break;
                                            case "Tesoura":
                                                Partida.vencedorAtual = "Jogador 2";
                                                Partida.pontuacaoJogador2++;
                                            break;
                                        }
                                    break;

                                    case "Tesoura":
                                        switch(Partida.movimentoJogador2){
                                            case "Papel":
                                                Partida.vencedorAtual = "Jogador 1";
                                                Partida.pontuacaoJogador1++;
                                            break;
                                            case "Pedra":
                                                Partida.vencedorAtual = "Jogador 2";
                                                Partida.pontuacaoJogador2++;
                                            break;
                                        }
                                    break;
                                }
                            }

                        }else{
                            Partida.decidindoVencedor = false;
                        }
                        if(socketCliente.getInetAddress() == Partida.enderecoJogador1){
                            if(Partida.movimentoJogador1.equals(Partida.movimentoJogador2)){
                                this.dadosResposta = "Empate ";
                            }
                            else if(Partida.pontuacaoJogador1 - Partida.pontuacaoJogador2 == 3){
                                this.dadosResposta = "Venceu ";
                                this.dadosRequisicao = "Desconectar";
                            }
                            else if(Partida.pontuacaoJogador2 - Partida.pontuacaoJogador1 == 3){
                                this.dadosResposta = "Perdeu ";
                                this.dadosRequisicao = "Desconectar";
                            }
                            else if(Partida.vencedorAtual == "Jogador 1"){
                                this.dadosResposta = "Pontuou ";
                            }
                            else if(Partida.vencedorAtual == "Jogador 2"){
                                this.dadosResposta = "Deixou ";
                            }
                            this.dadosResposta += String.valueOf(Partida.pontuacaoJogador1) + "-" + String.valueOf(Partida.pontuacaoJogador2) + " " + Partida.movimentoJogador2;
                        }
                        else if (socketCliente.getInetAddress() == Partida.enderecoJogador2){
                            if(Partida.movimentoJogador1.equals(Partida.movimentoJogador2)){
                                this.dadosResposta = "Empate ";
                            }
                            else if(Partida.pontuacaoJogador2 - Partida.pontuacaoJogador1 == 3){
                                this.dadosResposta = "Venceu ";
                                this.dadosRequisicao = "Desconectar";
                            }
                            else if(Partida.pontuacaoJogador1 - Partida.pontuacaoJogador2 == 3){
                                this.dadosResposta = "Perdeu ";
                                this.dadosRequisicao = "Desconectar";
                            }
                            else if(Partida.vencedorAtual == "Jogador 2"){
                                this.dadosResposta = "Pontuou ";
                            }
                            else if(Partida.vencedorAtual == "Jogador 1"){
                                this.dadosResposta = "Deixou ";
                            }
                            this.dadosResposta += String.valueOf(Partida.pontuacaoJogador2) + "-" + String.valueOf(Partida.pontuacaoJogador1) + " " + Partida.movimentoJogador1;
                        }
                        this.outputResposta.writeUTF(this.dadosResposta);
                    break;
                
                }
            }while(this.dadosRequisicao != "Desconectar");

            if(socketCliente.getInetAddress() == Partida.enderecoJogador1){
                Partida.enderecoJogador1 = null;
                System.out.println("[ " + Calendar.getInstance().getTime() + " ] Jogador 1 desconectou.");
            }

            else if (socketCliente.getInetAddress() == Partida.enderecoJogador2){
                Partida.enderecoJogador2 = null;
                System.out.println("[ " + Calendar.getInstance().getTime() + " ] Jogador 2 desconectou");
            }

            socketCliente.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
}