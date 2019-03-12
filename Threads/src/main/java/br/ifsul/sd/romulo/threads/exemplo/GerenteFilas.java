/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads.exemplo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class GerenteFilas extends Thread {

    private List<String> fila1;
    private List<String> fila2;
    private List<String> fila3;
    private List<String> filaGeral;

    private ControleFila controleFila1;
    private ControleFila controleFila2;
    private ControleFila controleFila3;

    public GerenteFilas() {
        fila1 = new ArrayList<>();
        fila2 = new ArrayList<>();
        fila3 = new ArrayList<>();
        filaGeral = new ArrayList<>();

        controleFila1 = new ControleFila("Fila 1", fila1);
        controleFila2 = new ControleFila("Fila 2", fila2);
        controleFila3 = new ControleFila("Fila 3", fila3);
    }

    public void iniciarMonitorarFilas() {
        controleFila1.start();
        controleFila2.start();
        controleFila3.start();
    }

    public synchronized void insereFila(String name, int fila) {
        switch (fila) {
            case 0:
                fila1.add(name);
                break;
            case 1:
                fila2.add(name);
                break;
            default:
                fila3.add(name);
                break;
        }
    }

    public synchronized void insereFilaGeral(String valor) {
        this.filaGeral.add(valor);
    }

    @Override
    public void run() {
        System.out.println("GerenteFilas - Thread iniciada");
        while (true) {
            try {
                if (!filaGeral.isEmpty()) {
                    System.out.println("\n*** Fila Geral ***");
                }
                
                for (int i = 0; i < filaGeral.size(); i++) {
                    System.out.println(filaGeral.get(i));
                }
                
                if (!filaGeral.isEmpty()) {
                    String valor = filaGeral.get(0);
                    String[] temp = valor.split(",");
                    String nome = temp[0];
                    int fila = Integer.parseInt(temp[1]);
                    this.insereFila(nome, fila);
                    filaGeral.remove(0);
                }
                
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GerenteFilas.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
        }
    }

}
