package com.chatsd.eleicao;

public class Eleicao {

    public static void main(String[] args) {
        Central eleicao = new Central();
        eleicao.startVotacao(2);
        eleicao.close();
    }
}
