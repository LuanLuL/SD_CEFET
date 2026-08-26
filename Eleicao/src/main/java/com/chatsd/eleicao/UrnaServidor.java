package com.chatsd.eleicao;

import java.util.HashMap;

public class UrnaServidor {

    private HashMap<CandidatoEnum, Integer> votos;

    public UrnaServidor() {
        votos = new HashMap<>();

        for (CandidatoEnum candidato : CandidatoEnum.values()) {
            votos.put(candidato, 0);
        }
    }

    public void computaVoto(CandidatoEnum candidato) {
        votos.put(candidato, votos.get(candidato) + 1);
    }
    
     public int getVotos() {
        int total = 0;

        for (int quantidade : votos.values()) {
            total += quantidade;
        }

        return total;
    }

    public int getVotos(CandidatoEnum candidato) {
        return votos.get(candidato);
    }
    
    public CandidatoEnum calcularVencedor() {
        CandidatoEnum vencedor = null;
        int maiorQuantidade = -1;
        boolean empate = false;

        for (CandidatoEnum candidato : votos.keySet()) {
            int quantidade = votos.get(candidato);

            if (quantidade > maiorQuantidade) {
                maiorQuantidade = quantidade;
                vencedor = candidato;
                empate = false;

            } else if (quantidade == maiorQuantidade) {
                empate = true;
            }
        }

        return empate ? null : vencedor;
    }
}