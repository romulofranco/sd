/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads.exemplo;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class Servidor extends Thread {

    private GerenteFilas gerente;

    public Servidor() {
        gerente = new GerenteFilas();
    }

    public void iniciarMonitorarFilas() {
        gerente.start();
        gerente.iniciarMonitorarFilas();
    }

    public synchronized void insereFilaGerente(String valor) {
        gerente.insereFilaGeral(valor);
    }

    public static void main(String args[]) {
        Servidor servidor = new Servidor();
        servidor.iniciarMonitorarFilas();
        servidor.start();

    }

    @Override
    public void run() {
        while (true) {
            try {
                this.insereFilaGerente(this.getRandomName() + ",1");
                this.insereFilaGerente(this.getRandomName() + ",2");
                this.insereFilaGerente(this.getRandomName() + ",3");
                this.insereFilaGerente(this.getRandomName() + ",1");
                this.insereFilaGerente(this.getRandomName() + ",2");
                this.insereFilaGerente(this.getRandomName() + ",3");
                Thread.sleep(20000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getRandomName() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }
}
