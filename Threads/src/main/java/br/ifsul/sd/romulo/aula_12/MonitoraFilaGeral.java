/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_12;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author romulo
 */
public class MonitoraFilaGeral extends Thread {

    private List<String> filaGeral;
    private List<String> fila1;
    private List<String> fila2;
    private List<String> fila3;

    private boolean somenteImprime = true;

    public MonitoraFilaGeral(List<String> filaGeral, boolean somenteImprime) {
        this.filaGeral = filaGeral;

        fila1 = new ArrayList<>();
        fila2 = new ArrayList<>();
        fila3 = new ArrayList<>();

        this.somenteImprime = somenteImprime;
    }

    public void iniciarExecutaFila() {
        ExecutaFila executaFila1 = new ExecutaFila("Fila 1", fila1);
        ExecutaFila executaFila2 = new ExecutaFila("Fila 2", fila2);
        ExecutaFila executaFila3 = new ExecutaFila("Fila 3", fila3);

        executaFila1.start();
        executaFila2.start();
        executaFila3.start();
    }

    @Override
    public void run() {
        while (true) {
            if (somenteImprime) {
                imprimirFila();
                Util.aguardar(5000);
            } else {
                definirDestinoFila();
                Util.aguardar(1000);
            }

        }
    }

    private synchronized void inserirFila(String valor, int fila) {
        if (fila == 1) {
            fila1.add(valor);
        } else if (fila == 2) {
            fila2.add(valor);
        } else {
            fila3.add(valor);
        }
    }

    private synchronized void definirDestinoFila() {
        if (!filaGeral.isEmpty()) {
            System.out.println(filaGeral.get(0));

            String valor = filaGeral.get(0);
            String temp[] = valor.split(",");
            int fila = Integer.parseInt(temp[1]);
            this.inserirFila(valor, fila);

            filaGeral.remove(0);

        }
    }

    private synchronized void imprimirFila() {
        if (!filaGeral.isEmpty()) {
            System.out.println("\n*** FILA GERAL ***");

            for (int i = 0; i < filaGeral.size(); i++) {
                System.out.println(filaGeral.get(i));
            }
        }
    }

}
