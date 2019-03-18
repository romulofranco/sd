/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads.exemplo.basico;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulofranco
 */
public class MinhaPrimeiraThread extends Thread {

    private int ms = 500;

    public MinhaPrimeiraThread(String name) {
        super(name);
    }

    public MinhaPrimeiraThread(String name, int ms) {
        super(name);
        this.ms = ms;
    }

    @Override
    public void run() {
        System.out.println(this.getName());
        while (true) {
            try {
                System.out.print("\n" + this.getName() + " - ");
                for (int i = 0; i < 10; i++) {
                    System.out.print(".");
                    System.out.flush();
                    Thread.sleep(ms);
                }

//                System.out.println(this.getName() + " - thread is running...");
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(MinhaPrimeiraThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String args[]) {
        MinhaPrimeiraThread t1 = new MinhaPrimeiraThread("T1");
        t1.start();

        MinhaPrimeiraThread t2 = new MinhaPrimeiraThread("T2");
        t2.start();

        MinhaPrimeiraThread t3 = new MinhaPrimeiraThread("T3");
        t3.start();
    }

}
