package com.chatsd.eleicao;

public class Eleicao {

    public static void main(String[] args) {
        Central eleicao = new Central();
        eleicao.iniciarVotacao(2);
        eleicao.showResults();
        eleicao.close();
    }
}
