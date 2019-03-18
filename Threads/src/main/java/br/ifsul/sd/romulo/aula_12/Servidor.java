/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_12;

import br.ifsul.sd.romulo.aula_19.ConexaoCliente;
import br.ifsul.sd.romulo.aula_19.RecursoESB;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author romulo
 */
public class Servidor {
    
    private List<RecursoESB> filaGeral;
    
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
    
    
     public void iniciarServer() {
        try {
            // Instancia o ServerSocket ouvindo a porta 12345
            ServerSocket servidor = new ServerSocket(1234);
            System.out.println("Servidor ouvindo a porta 1234");
            while (true) {
                new ConexaoCliente(filaGeral, servidor.accept()).start();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
        }
    }

    
    public static void main(String args[]) {
        Servidor servidor = new Servidor();
        servidor.iniciarMonitorFila();
        servidor.iniciarServer();
        
//        int i = 0;
//        while (true) {
//            servidor.insereFila("romulo" + i + ",1");
//            servidor.insereFila("turmasd" + i + ",2");
//            servidor.insereFila("threads" + i + ",3");
//            i++;
//            Util.aguardar(3000);
//        }
    }
    
}
