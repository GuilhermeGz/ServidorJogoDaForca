package br.edu.ifpe.servidorjogodaforca.Server;

import br.edu.ifpe.servidorjogodaforca.ParteLogica.Jogo;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Servidor {

    private int porta;
    private ArrayList<PrintStream> clientes;
    private ServerSocket servidor;
    private ArrayList<Scanner> teclados;

    public Servidor(int porta) throws IOException {
        this.porta = porta;
        this.clientes = new ArrayList<PrintStream>();
        this.servidor = new ServerSocket(this.porta);
        this.teclados = new ArrayList<Scanner>();
    }

    public void distribuirMensagens(String msg) {
        for (PrintStream cliente : this.clientes) {
            cliente.println(msg);
        }
    }

    public static void main(String[] args) throws IOException {
        Jogo j = new Jogo();
        Servidor server = new Servidor(32154);
        System.out.println("Porta 32154 aberta!");
        while (true) {

            Socket cliente = server.servidor.accept();
            System.out.println("Nova conexão com o cliente "
                    + cliente.getInetAddress().getHostAddress());
            PrintStream ps = new PrintStream(cliente.getOutputStream());
            Scanner teclado = new Scanner(cliente.getInputStream());
            server.teclados.add(teclado);
            server.clientes.add(ps);

            Thread t1 = new Thread(new Runnable() {

                public void run() {
                    try {
                        Scanner sc = new Scanner(cliente.getInputStream());
                        PrintStream saida = new PrintStream(cliente.getOutputStream());
                        saida.println("Você está na fila automática do multiplayer, para sair dela"
                                + " escolha a opção: Sair do modo online");
                        do {

                            if (server.clientes.size() == 2) {
                                server.distribuirMensagens("\nIniciado Modo de 2 Jogadores!! Surprise");
                                j.setErros(0);
                                j.setAcertos(0);
                                j.inicializarAtributos(1, j);
                                int i = 0;

                                do {
                                    server.distribuirMensagens(j.criarCampo(j.getLetras(), j.getMarcasao()));
                                    server.distribuirMensagens("\nEscolha uma letra");
                                    server.distribuirMensagens("Erros :" + j.getErros());
                                    i += 15;

                                    if (i % 2 == 0) {
                                        i = 0;
                                    } else {
                                        i = 1;
                                    }
                                } while (j.escolherLetra(server.teclados.get(i).next(), j.getLetrasRepetidas()) == true);
                                server.distribuirMensagens(j.verificarRsultadoDaPartida(j.getAcertos(), j.getLetras()));
                            }
                            saida.println(j.mostrarMenu());
                            int escrita = Integer.parseInt(sc.next());
                            j.inicializarAtributos(escrita, j);

                            if (j.getOpcao() == 1) {
                                j.setErros(0);
                                j.setAcertos(0);

                                do {
                                    saida.println(j.criarCampo(j.getLetras(), j.getMarcasao()));
                                    saida.println("\nEscolha uma letra");
                                    saida.println("Erros :" + j.getErros());

                                } while (j.escolherLetra(sc.next(), j.getLetrasRepetidas()) == true);
                                saida.println(j.verificarRsultadoDaPartida(j.getAcertos(), j.getLetras()));

                            } else if (j.getOpcao() == 2) {
                                saida.println("Digite a palavra");
                                saida.println(j.adicionarPalavra(sc.next()));
                            } else if (j.getOpcao() == 3) {
                                saida.println("Digite a palavra");
                                j.removerPalavra(sc.next());
                            } else if (j.getOpcao() == 4) {
                                saida.println(j.mostrarLista());
                            } else if (j.getOpcao() == 5) {
                                if (server.clientes.get(0) == null) {
                                    saida.print("\nNão é possível realizar a operação\n");
                                }
                                saida.println("\nRetirado da fila de multiplayer\n");
                                server.clientes.remove(0);
                                server.teclados.remove(0);
                            } else if (j.getOpcao() == 6) {
                                saida.println("\nValor inapropriado\n");
                            }

                        } while (j.getOpcao() != 6);

                        sc.close();
                        cliente.close();
                    } catch (Exception e) {
                    }
                }
            });
            t1.start();

        }
    }

}
