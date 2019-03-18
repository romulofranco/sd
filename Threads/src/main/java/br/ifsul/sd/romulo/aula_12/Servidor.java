/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_12;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author romulo
 */
public class Servidor {
    
    private List<String> filaGeral;
    
    public Servidor() {
        filaGeral = new ArrayList<>();
    }
    
    public void iniciarMonitorFila() {
        MonitoraFilaGeral imprimeFilaGeral = new MonitoraFilaGeral(filaGeral, true);
        imprimeFilaGeral.start();
        
        MonitoraFilaGeral monitoraFilaGeral = new MonitoraFilaGeral(filaGeral, false);
        monitoraFilaGeral.start();
        monitoraFilaGeral.iniciarExecutaFila();
    }
    
    public void insereFila(String valor) {
        filaGeral.add(valor);
    }
    
    public static void main(String args[]) {
        Servidor servidor = new Servidor();
        servidor.iniciarMonitorFila();
        
        int i = 0;
        while (true) {
            servidor.insereFila("romulo" + i + ",1");
            servidor.insereFila("turmasd" + i + ",2");
            servidor.insereFila("threads" + i + ",3");
            i++;
            Util.aguardar(3000);
        }
    }
    
}
