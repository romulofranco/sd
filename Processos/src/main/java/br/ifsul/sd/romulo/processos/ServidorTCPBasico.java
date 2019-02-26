/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.processos;

import java.net.ServerSocket;

public class ServidorTCPBasico {

    public void iniciarServer() {
        try {
            // Instancia o ServerSocket ouvindo a porta 12345
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Servidor ouvindo a porta 12345");
            while (true) {
                new RecursoESB(servidor.accept()).start();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
        }
    }

    public static void main(String[] args) {
        ServidorTCPBasico server = new ServidorTCPBasico();
        server.iniciarServer();
    }
}
