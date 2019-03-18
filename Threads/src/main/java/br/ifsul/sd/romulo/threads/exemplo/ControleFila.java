/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads.exemplo;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class ControleFila extends Thread {

    private List<String> fila;

    public ControleFila(String name, List<String> fila) {
        this.fila = fila;
        this.setName(name);
    }

    @Override
    public void run() {
        System.out.println("ControleFila - " + this.getName() + " iniciada...");
        while (true) {
            if (!fila.isEmpty()) {
                System.out.println("\n*** Controle - " + this.getName() + "***");
            }

            for (int i = 0; i < fila.size(); i++) {
                System.out.println(fila.get(i));
            }

            if (!fila.isEmpty()) {
                System.out.println("Atendendo a Fila " + this.getName() + " Nome: " + fila.get(0));
                fila.remove(0);
            }
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControleFila.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
