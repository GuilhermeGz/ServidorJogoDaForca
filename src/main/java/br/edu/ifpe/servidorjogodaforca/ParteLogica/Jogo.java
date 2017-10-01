package br.edu.ifpe.servidorjogodaforca.ParteLogica;

import java.util.ArrayList;

public class Jogo {

    int erros;
    int acertos;
    BancoDePalavras bd;
    char[] letras;
    int[] marcasao;
    ArrayList<Character> letrasRepetidas;

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
}
