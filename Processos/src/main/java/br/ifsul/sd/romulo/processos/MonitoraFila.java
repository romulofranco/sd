/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.processos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author romulo
 */
public class MonitoraFila extends Thread {

    private List<RecursoESB> fila;
    private boolean continuarExecutando = true;
    private int proximo = 0;
    private String nomeFila;

    public MonitoraFila(String nomeFila) {
        fila = new ArrayList<>();
        this.nomeFila = nomeFila;
        System.out.println(nomeFila + " iniciada");
    }

    public void checarFila() {
        RecursoESB recurso;

        if (fila.size() > 0) {
            recurso = fila.get(0);
            fila.remove(0);

            if (!recurso.foiExecutado()) {
                recurso.start();
            }
        }
    }

    @Override
    public void run() {

        while (continuarExecutando) {
            this.checarFila();
            Util.waitALittle(5000);
            System.out.println(nomeFila + " - Num clientes " + fila.size());
        }

    }

    public void pararMonitor() {
        this.continuarExecutando = false;
    }

    public void adicionar(RecursoESB recurso) {
        this.fila.add(recurso);
    }

}
