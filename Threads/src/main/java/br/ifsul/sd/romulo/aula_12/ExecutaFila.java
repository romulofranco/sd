/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_12;

import br.ifsul.sd.romulo.aula_19.ChatCliente;
import br.ifsul.sd.romulo.aula_19.ChatGerente;
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
                RecursoESB recurso = fila.get(0);
                if (!recurso.isAlive()) {
                    recurso.start();
                }
            }
            Util.aguardar(3000);
        }
    }
}
