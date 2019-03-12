/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads.exemplo;

class Multi extends Thread {

    @Override
    public void run() {
        System.out.println("thread is running...");
    }

    public static void main(String args[]) {
        Multi t1 = new Multi();
        t1.start();
    }
}
