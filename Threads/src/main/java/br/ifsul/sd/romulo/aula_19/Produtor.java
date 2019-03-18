/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_19;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulo
 */
public class Produtor extends RecursoESB {

    private Socket socket;
    private String message;
    

    public Produtor(String message, Socket socket) {
        super(message, socket);
    }

    
    @Override
    public void run() {
        try {
            System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write("*** Seja bem vindo, envie o comando ou digite ajuda para iniciar ***\n\n\r-> ");
            out.flush();

            int gerente = 0;
            boolean cliente = false;

            String nomeGerente = "";
            String msgWelcome = "";
            while (true) {

                String msgCliente = in.readLine();
                System.out.println("Msg recebida de cliente: " + msgCliente);

                if (gerente == 1) {
                    nomeGerente = msgCliente;
                    out.write("Entre com msg de boas vindas: ");
                    gerente++;
                }
                
                if (gerente == 2) {
                    msgWelcome = msgCliente;
                    out.append("Confirme os dados [S|n] \nNome: " + nomeGerente + " \nMensagem: " + msgWelcome);
                    gerente++;
                }
                if (gerente ==3 ){
                    if (msgCliente.startsWith("S")) {
                        
                    }
                }
                if (msgCliente.startsWith("TESTE")) {
                    out.write("Servidor esta OK! \t" + new Date() + "\n\n\r-> ");
                }  else if (msgCliente.contains("sair")) {
                    out.write("OK Servidor encerrando sua conexao! Tchau...");
                    out.flush();
                    Thread.sleep(2000);
                    socket.close();
                } else {
                    out.write("Comando não encontrado\n");
                }
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ex) {
            Logger.getLogger(Produtor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Produtor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
