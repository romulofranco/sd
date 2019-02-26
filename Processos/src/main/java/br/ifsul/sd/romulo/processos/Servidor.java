/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.processos;

import java.net.ServerSocket;

public class Servidor extends Thread {

    private MonitoraFila filaCliente;
    private MonitoraFila filaGerente;
    private MonitoraFila filaProdutor;

    public void iniciarServer() {
        try {
            // Instancia o ServerSocket ouvindo a porta 12345
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("OK Servidor ouvindo a porta 12345");

            this.monitorarFilas();
            while (true) {
                new ConexaoCliente(filaCliente, filaGerente, filaProdutor, servidor.accept()).start();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
        }
    }

    public void monitorarFilas() {
        filaCliente = new MonitoraFila("Fila de clientes para chat");
        filaGerente = new MonitoraFila("Fila de gerentes para chat");
        filaProdutor = new MonitoraFila("Fila Produtor - Importar arquivos");

        filaCliente.start();
        filaGerente.start();
        filaProdutor.start();

        System.out.println("OK Filas criadas...");
    }

    public static void main(String[] args) {
        Servidor server = new Servidor();
        server.iniciarServer();
    }
}
