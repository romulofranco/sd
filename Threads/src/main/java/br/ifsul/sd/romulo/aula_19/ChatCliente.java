/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_19;

import br.ifsul.sd.romulo.aula_12.Util;
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
public class ChatCliente extends RecursoESB {
    
    private String nomeCliente;

    public ChatCliente(String message, Socket socket) {
        super(message, socket);
    }

    @Override
    public synchronized void run() {
        System.out.println("SOCKET CLIENTE: " + socket);
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write("*** Seja bem vindo Cliente ***\n\n\r\t Entre com seu nome\n\r\t Nome: ");
            out.flush();

            int cliente = 1;
            String msg = "";

            while (true) {

                msg = in.readLine();
                System.out.println(this.getName() + " - Recebida: " + msg);

                if (cliente == 1) {
                    nomeCliente = msg;
                    out.write("\n\n\rOK " + msg + " - Aguarde voce sera atendido em breve\n\n\r-> ");
                    out.flush();
                    cliente = 0;
                    return;
                } else {
                    parserProtocolo(msg, out);
                }
                Util.aguardar(1000);
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ex) {
            Logger.getLogger(ChatCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parserProtocolo(String msg, BufferedWriter out) throws InterruptedException, IOException {

        if (msg.startsWith("teste")) {
            out.write("Servidor esta OK! \t" + new Date() + "\n\n\r-> ");
        } else if (msg.contains("sair")) {
            out.write("OK Servidor encerrando sua conexao! Tchau...");
            out.flush();
            Thread.sleep(2000);
            socket.close();
        } else {
            out.write("\n\r-> " + this.getClass().getSimpleName() + " - " + msg + " - Comando nao encontrado\n" + ConexaoCliente.PROMPT);
        }
        out.flush();
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    @Override
    public String toString() {
        return "ChatCliente{" + "nomeCliente=" + nomeCliente + ", Socket=" + socket + "}";
    }
    
    
}
