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

}
