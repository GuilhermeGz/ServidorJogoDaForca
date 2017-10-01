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

}
