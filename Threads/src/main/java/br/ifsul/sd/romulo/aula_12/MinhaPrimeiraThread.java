/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_12;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulo
 */
public class MinhaPrimeiraThread extends Thread {

    private int ms = 1000;
    private boolean ativada = true;

    public MinhaPrimeiraThread(String name) {
        super(name);
    }

    public MinhaPrimeiraThread(String name, int ms) {
        super(name);
        this.ms = ms;
    }

    public boolean isAtivada() {
        return ativada;
    }

    public void setAtivada(boolean ativada) {
        this.ativada = ativada;
    }

    @Override
    public void run() {
        while (true) {

            if (ativada) {
                System.out.println(this.getName() + "\n");
                for (int i = 0; i < 10; i++) {
                    System.out.print(".");
                    System.out.flush();
                    Util.aguardar(ms);
                    System.out.print(this.getName());
                }
                Util.aguardar(100);
            } else {
                System.out.println(this.getName() + " var ativada é falso");
                Util.aguardar(ms);
            }
        }
    }

    public static void main(String[] args) {
        MinhaPrimeiraThread t1 = new MinhaPrimeiraThread("T1", 300);
        MinhaPrimeiraThread t2 = new MinhaPrimeiraThread("T2", 1000);
        t1.setAtivada(false);
        t2.start();
        t1.start();

    }
}
