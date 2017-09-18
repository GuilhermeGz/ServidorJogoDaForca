package br.edu.ifpe.servidorjogodaforca.ParteLogica;

import java.util.ArrayList;

public class BancoDePalavras {

    protected ArrayList<String> Palavras = new ArrayList<String>();

    public BancoDePalavras() {
        Palavras.add("LEITE");
        Palavras.add("QUEIJO");
        Palavras.add("BANANA");
        Palavras.add("CAJU");
        Palavras.add("GOAIBA");
        Palavras.add("TOMATE");
        Palavras.add("ARROZ");
        Palavras.add("LARANJA");
        Palavras.add("CARNE");
        Palavras.add("BISCOITO");
        Palavras.add("BOLACHA");
    }

    public ArrayList<String> getPalavras() {
        return Palavras;
    }

    public void setPalavras(ArrayList<String> palavras) {
        Palavras = palavras;
    }

    public void adicionarPalavra(String palavra) {
        Palavras.add(palavra);
    }

    public String removerPalavra(String palavra) {
        Palavras.remove(palavra);
    }

}
