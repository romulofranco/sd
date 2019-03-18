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
public class ChatGerente extends RecursoESB {

    private String nomeGerente;
    private String msgBoasVindas;

    public ChatGerente(String message, Socket socket) {
        super(message, socket);
    }

    @Override
    public void run() {
        try {

            System.out.println("SOCKET GERENTE: " + socket);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write("*** Seja bem vindo Gerente ***\n\n\r\tEntre com seu nome: ");
            out.flush();

            int gerente = 1;
            while (true) {

                String msgCliente = in.readLine();
                System.out.println("Recebido: " + msgCliente);

                if (gerente == 1) {
                    nomeGerente = msgCliente;
                    out.write("\r\tMensagem de boas vindas: ");
                    out.flush();
                    gerente++;
                    continue;
                }

                if (gerente == 2) {
                    msgBoasVindas = msgCliente;
                    out.write("\n\r\tConfirme os dados [S|n] \n\r\tNome: " + nomeGerente + " \n\r\tMensagem: " + msgBoasVindas);
                    out.write(ConexaoCliente.PROMPT);
                    out.flush();
                    gerente++;
                    continue;
                }
                if (gerente == 3) {
                    if (msgCliente.startsWith("s")) {
                        gerente = 0;
                        out.write("OK " + nomeGerente + " - Aguarde inicio de atendimento com o cliente");
                        out.write(ConexaoCliente.PROMPT);
                        out.flush();
                        return;
                    }
                }
                if (msgCliente.startsWith("TESTE")) {
                    out.write("Servidor esta OK! \t" + new Date() + "\n\n\r-> ");
                } else if (msgCliente.contains("sair")) {
                    out.write("OK Servidor encerrando sua conexao! Tchau...");
                    out.flush();
                    Thread.sleep(2000);
                    socket.close();
                } else {
                    out.write("\n\r-> " + this.getClass().getSimpleName() + " - " + msgCliente + " - Comando nao encontrado\n" + ConexaoCliente.PROMPT);
                }
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ex) {
            Logger.getLogger(ChatGerente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getNomeGerente() {
        return nomeGerente;
    }

    public void setNomeGerente(String nomeGerente) {
        this.nomeGerente = nomeGerente;
    }

    public String getMsgBoasVindas() {
        return msgBoasVindas;
    }

    public void setMsgBoasVindas(String msgBoasVindas) {
        this.msgBoasVindas = msgBoasVindas;
    }

    @Override
    public String toString() {
        return "ChatGerente{" + "nomeGerente=" + nomeGerente + ", msgBoasVindas=" + msgBoasVindas + ", Socket=" + socket + "}";
    }

}
