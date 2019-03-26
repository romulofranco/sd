/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_12;

import br.ifsul.sd.romulo.aula_19.RecursoESB;
import java.util.List;

/**
 *
 * @author romulo
 */
public class ExecutaFila extends Thread {

    private List<RecursoESB> fila;
    private int tipo;

    public ExecutaFila(String name) {
        super(name);
    }

    public ExecutaFila(String name, List<RecursoESB> fila, int tipo) {
        super(name);
        this.fila = fila;
        this.tipo = tipo;
    }

    @Override
    public synchronized void run() {
        System.out.println("Execução Fila - Thread " + this.getName() + " iniciada");

        while (true) {
            if (!fila.isEmpty()) {
                System.out.println("Recurso: " + fila.get(0).toString() + " Tamanho: " + fila.size());
                for (int i = 0; i < fila.size(); i++) {
                    RecursoESB recurso = fila.get(i);
                    if (!recurso.getState().equals(Thread.State.TERMINATED)) {
                        try {
                            if (recurso.isEmExecucao()) {
                                try {
                                    recurso.start();
                                } catch (Exception e) {
                                    fila.remove(i);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            Util.aguardar(3000);
        }
    }
}
