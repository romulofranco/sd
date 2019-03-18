/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_12;

import java.util.List;

/**
 *
 * @author romulo
 */
public class ExecutaFila extends Thread {

    private List<String> fila;

    public ExecutaFila(String name, List<String> fila) {
        super(name);
        this.fila = fila;
    }

    @Override
    public synchronized void run() {
        System.out.println("Execução Fila - Thread " + this.getName() + " iniciada");

        while (true) {
            if (!fila.isEmpty()) {
                
                System.out.println("Thread: " +  this.getName()  + " - Fila para Executar requisição\n");
                
                for (int i=0;i<fila.size();i++) {
                    if (i == 0) {
                        System.out.print("-->  ");
                    }
                    System.out.print(fila.get(i));
                    if (i == 0) {
                        System.out.print(" removendo\n");
                    }
                }
                Util.aguardar(1000);
                fila.remove(0);
            }
            Util.aguardar(3000);
        }
    }
}
