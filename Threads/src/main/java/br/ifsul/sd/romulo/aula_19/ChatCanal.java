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

/**
 *
 * @author admin
 */
public class ChatCanal extends Thread {
    
    private RecursoESB recursoCliente;
    private RecursoESB recursoGerente;
    
    private String nomeGerente;
    private String nomeCliente;
    
    public ChatCanal(RecursoESB recursoCliente, RecursoESB recursoGerente) {
        this.recursoCliente = recursoCliente;
        this.recursoGerente = recursoGerente;
    }
    
    public void iniciarNasPontas() {
        this.recursoCliente.start();
        this.recursoGerente.start();
    }
    
    @Override
    public void run() {
        try {
            String iniciar = "Iniciando chat entre\n\rCliente: " + recursoCliente + "\n\rGerente: " + recursoGerente;
            System.out.println(iniciar);
            
            nomeGerente = ((ChatGerente) recursoGerente).getNomeGerente();
            nomeCliente = ((ChatCliente) recursoCliente).getNomeCliente();
            
            BufferedWriter outCliente = new BufferedWriter(new OutputStreamWriter(recursoCliente.getSocket().getOutputStream()));
            BufferedWriter outGerente = new BufferedWriter(new OutputStreamWriter(recursoGerente.getSocket().getOutputStream()));
            BufferedReader inCliente = new BufferedReader(new InputStreamReader(recursoCliente.getSocket().getInputStream()));
            BufferedReader inGerente = new BufferedReader(new InputStreamReader(recursoGerente.getSocket().getInputStream()));
            
            enviaMsg(outCliente, iniciar);
            enviaMsg(outGerente, iniciar);
            
            enviaMsg(outCliente, "\n\rGerente: " + ((ChatGerente) recursoGerente).getNomeGerente() + "\n\r" + ((ChatGerente) recursoGerente).getMsgBoasVindas());
            enviaMsg(outGerente, "\n\rNome do cliente a ser atendido: " + ((ChatCliente) recursoCliente).getNomeCliente());
            
            while (true) {
                recebeMsgEnvia(inCliente, outGerente, nomeCliente);
                recebeMsgEnvia(inGerente, outCliente, nomeGerente);
//                outCliente.write(ConexaoCliente.PROMPT);
//                outGerente.write(ConexaoCliente.PROMPT);
                Util.aguardar(2000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void recebeMsgEnvia(BufferedReader in, BufferedWriter out, String nome) throws IOException {
        String msg;
        msg = recebeMsg(in);
        
        if (!msg.isEmpty() && !msg.isBlank()) {
            msg = nome + ": " + msg + "\n\r";
            enviaMsg(out, msg);
        }
    }
    
    private void enviaMsg(BufferedWriter out, String msg) throws IOException {
        out.write(msg);
        out.write(ConexaoCliente.PROMPT);
        out.flush();
    }
    
    private String recebeMsg(BufferedReader in) throws IOException {
        String msg = "";
        if (in.ready()) {
            msg = in.readLine();
        }
        return msg;
    }
    
}
