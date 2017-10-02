/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpe.servidorjogodaforca.Server;

import br.edu.ifpe.servidorjogodaforca.ParteLogica.Jogo;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author gon
 */
public class Servidor {

    public static void main(String[] args) throws IOException {
        Jogo j = new Jogo();
        ServerSocket servidor = new ServerSocket(32154);
        System.out.println("Porta 32154 aberta!");
        while (true) {

            Socket cliente = servidor.accept();
            System.out.println("Nova conexão com o cliente "
                    + cliente.getInetAddress().getHostAddress());
            Thread t1 = new Thread(new Runnable() {

                public void run() {
                    try {
                        Scanner teclado = new Scanner(cliente.getInputStream());
                        PrintStream saida = new PrintStream(cliente.getOutputStream());

                        do {
                            saida.println(j.mostrarMenu());
                            int escrita = Integer.parseInt(teclado.next());
                            j.inicializarAtributos(escrita, j);

                            if (j.getOpcao() == 1) {
                                j.setErros(0);
                                j.setAcertos(0);

                                do {
                                    saida.println(j.criarCampo(j.getLetras(), j.getMarcasao()));
                                    saida.println("\nEscolha uma letra");
                                    saida.println("Erros :" + j.getErros());

                                } while (j.escolherLetra(teclado.next(), j.getLetrasRepetidas()) == true);
                                saida.println(j.verificarRsultadoDaPartida(j.getAcertos(), j.getLetras()));

                            } else if (j.getOpcao() == 2) {
                                saida.println("Digite a palavra");
                                saida.println(j.adicionarPalavra(teclado.next()));
                            } else if (j.getOpcao() == 3) {
                                saida.println("Digite a palavra");
                                j.removerPalavra(teclado.next());
                            } else if (j.getOpcao() == 4) {
                                saida.println(j.mostrarLista());
                            } else if (j.getOpcao() != 5) {
                                saida.println("\nValor inapropriadoz\n");
                            }

                        } while (j.getOpcao() != 5);

                        teclado.close();
                        servidor.close();
                        cliente.close();
                    } catch (Exception e) {
                    }
                }
            });
            t1.start();

        }
    }

}
