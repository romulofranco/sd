/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads.exemplo.basico;

import java.util.logging.Level;
import java.util.logging.Logger;

class TestMultiPriority1 extends Thread {

    static boolean invertPriority = false;

    public TestMultiPriority1(String nome) {
        super(nome);
    }

    public void run() {
        while (true) {
            for (int i = 0; i < 10; i++) {
                System.out.println("Nome thread: " + Thread.currentThread().getName() + " Prioridade é: " + Thread.currentThread().getPriority());
            }

            System.out.println("Trocando prioridades aguarde...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TestMultiPriority1.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (invertPriority) {
                if (Thread.currentThread().getName().startsWith("Baixa")) {
                    Thread.currentThread().setPriority(invertPriority ? MAX_PRIORITY : MIN_PRIORITY);
                } else {
                    Thread.currentThread().setPriority(!invertPriority ? MIN_PRIORITY : MAX_PRIORITY);
                }
            }

            for (int i = 0; i < 10; i++) {
                System.out.println("Nome thread: " + Thread.currentThread().getName() + " Prioridade é: " + Thread.currentThread().getPriority());
            }
        }

    }

    public static void main(String args[]) throws InterruptedException {
        TestMultiPriority1 m1 = new TestMultiPriority1("Baixa");
        TestMultiPriority1 m2 = new TestMultiPriority1("Alta");
        m1.setPriority(Thread.MIN_PRIORITY);
        m2.setPriority(Thread.MAX_PRIORITY);
        m1.start();
        m2.start();

        while (true) {
            Thread.sleep(3000);
            invertPriority = invertPriority ? false : true;
        }
    }
}
