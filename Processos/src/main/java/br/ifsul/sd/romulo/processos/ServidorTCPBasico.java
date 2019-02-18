/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.processos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class ServidorTCPBasico {

    public void iniciarServer() {
        try {
            // Instancia o ServerSocket ouvindo a porta 12345
            ServerSocket servidor = new ServerSocket(12345);
            System.out.println("Servidor ouvindo a porta 12345");
            while (true) {
                // o método accept() bloqueia a execução até que
                // o servidor receba um pedido de conexão
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
                BufferedReader reader = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));

                String msgCliente = msgCliente = reader.readLine();

                System.out.println("Msg recebida de cliente: " + msgCliente);

                parserProtocolo(msgCliente, writer);
                writer.flush();
                
                Thread.sleep(5000);
                
                reader.close();
                cliente.close();
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
        }
    }

    private void parserProtocolo(String msgCliente, BufferedWriter writer) throws IOException {
        if (msgCliente.startsWith("OLA")) {
            writer.write("Ola recebido com sucesso em " + new Date() + "\n");
        } else {
            writer.write("Ola não recebido com sucesso em " + new Date() + "\n");
        }
    }

    public static void main(String[] args) {
        ServidorTCPBasico server = new ServidorTCPBasico();
        server.iniciarServer();
    }
}
