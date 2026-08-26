package com.chatsd.eleicao;

public enum CandidatoEnum {
    CANDIDATO_1(1),
    CANDIDATO_2(2),
    CANDIDATO_3(3);

    private final int valor;

    CandidatoEnum(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
    
    public static CandidatoEnum fromValor(int valor) {
        for (CandidatoEnum candidato : values()) {
            if (candidato.getValor() == valor) {
                return candidato;
            }
        }

        return null;
    }
}