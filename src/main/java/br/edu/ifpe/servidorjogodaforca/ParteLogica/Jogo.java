package br.edu.ifpe.servidorjogodaforca.ParteLogica;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Jogo {

    int erros;
    int acertos;
    Random gerador = new Random();
    int opcao;
    BancoDePalavras bd;
    char[] letras;
    int[] marcasao;
    ArrayList<Character> letrasRepetidas;

    public char[] getLetras() {
        return letras;
    }

    public Random getGerador() {
        return gerador;
    }

    public void setGerador(Random gerador) {
        this.gerador = gerador;
    }

    public BancoDePalavras getBd() {
        return bd;
    }

    public void setBd(BancoDePalavras bd) {
        this.bd = bd;
    }

    public int[] getMarcasao() {
        return marcasao;
    }

    public ArrayList<Character> getLetrasRepetidas() {
        return letrasRepetidas;
    }

    public int getErros() {
        return erros;
    }

    public void setErros(int erros) {
        this.erros = erros;
    }

    public int getAcertos() {
        return acertos;
    }

    public void setAcertos(int acertos) {
        this.acertos = acertos;
    }

    public int getOpcao() {
        return opcao;
    }

    public void setOpcao(int opcao) {
        this.opcao = opcao;
    }

    public void setLetras(char[] letras) {
        this.letras = letras;
    }

    public void setMarcasao(int[] marcasao) {
        this.marcasao = marcasao;
    }

    public void setLetrasRepetidas(ArrayList<Character> letrasRepetidas) {
        this.letrasRepetidas = letrasRepetidas;
    }

    public String criarCampo(char[] lista, int[] marcador) {
        String campo = new String();
        for (int i = 0; i < lista.length; i++) {
            if (marcador[i] == 1) {
                campo += lista[i] + " ";
            } else {
                campo += "_ ";
            }
        }
        return campo;
    }

    public boolean verificarAcerto(char letra) {
        boolean cont = false;
        for (int i = 0; i < letras.length; i++) {
            if (letra == letras[i]) {
                acertos++;
                marcasao[i] = 1;
                cont = true;
            }
        }
        if (cont != true) {
            erros++;
        }
        return acertos < letras.length && erros < 7;
    }

    public String mostrarMenu() {
        return "1 - Jogar\n2 - Adicionar palavra\n3 - Remover palavra"
                + "\n4 - Mostrar lista de palvras\n5 - Sair do jogo";
    }

    public String verificarRsultadoDaPartida(int acertos, char[] lista) {
        if (acertos == lista.length) {
            return "\nVitoria\n";
        } else {
            return "\nDerrota\n";
        }
    }

    public boolean verificarRepeticaoDeLetra(char pl, ArrayList<Character> letrasRepetidas) {
        if (letrasRepetidas.contains(pl)) {
            return true;
        }
        letrasRepetidas.add(pl);
        return verificarAcerto(pl);
    }

    public String adicionarPalavra(String palavra) {
        palavra = palavra.toUpperCase();
        if (bd.Palavras.contains(palavra) == true) {
            return "\nPalavra já se encontra na lista";
        } else {
            bd.adicionarPalavra(palavra);
            return "\nPalavra adicionada";
        }
    }

    public String removerPalavra(String palavra) {
        palavra = palavra.toUpperCase();
        if (bd.Palavras.remove(palavra) == true) {
            return "\nPalavra removida";
        } else {
            return "\nPalavra não existe na lista";
        }
    }

    public String mostrarLista() {
        String lista = new String();
        for (int i = 0; i < bd.getPalavras().size(); i++) {
            lista += " " + bd.getPalavras().get(i);
        }
        return lista;
    }

    public boolean escolherLetra(String caracter, ArrayList<Character> letrasRepetidas) {
        char letra = caracter.toUpperCase().charAt(0);
        return verificarRepeticaoDeLetra(letra, letrasRepetidas);
    }

    public void inicializarAtributos(int op, Jogo j) {
        bd = new BancoDePalavras();
        int posicao = gerador.nextInt(bd.getPalavras().size());
        String palavra = bd.getPalavras().get(posicao);
        letras = palavra.toCharArray();
        marcasao = new int[letras.length];
        letrasRepetidas = new ArrayList<Character>();
        setOpcao(op);
    }

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
                                boolean result;
                                j.setErros(0);
                                j.setAcertos(0);

                                do {
                                    saida.println(j.criarCampo(j.getLetras(), j.getMarcasao()));
                                    saida.println("\nEscolha uma letra");
                                    saida.println("Erros :" + j.getErros());

                                } while (j.escolherLetra(teclado.next(), j.getLetrasRepetidas()) == true);
                                saida.println(j.verificarRsultadoDaPartida(j.getAcertos(), j.getLetras()));

                            } else if (j.getOpcao() == 2) {
                                saida.println(j.adicionarPalavra(teclado.next()));

                                saida.println("Digite a palavra");
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
