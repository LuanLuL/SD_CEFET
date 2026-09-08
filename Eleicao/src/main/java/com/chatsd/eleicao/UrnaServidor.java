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
    
    public String[] calcularResultado() {
        int totalVotos = this.getVotos();
        String[] resultado = new String[CandidatoEnum.values().length];

        int index = 0;

        for (CandidatoEnum candidato : CandidatoEnum.values()) {
            int quantidadeVotos = votos.get(candidato);
            double percentual = 0;
            
            if (totalVotos > 0) {
                percentual = ((double) quantidadeVotos / totalVotos) * 100;
            }

            resultado[index] = candidato.name()
                    + ": "
                    + quantidadeVotos
                    + " votos "
                    + String.format("%.2f", percentual)
                    + "%";

            index++;
        }
        
        return resultado;
    }
}