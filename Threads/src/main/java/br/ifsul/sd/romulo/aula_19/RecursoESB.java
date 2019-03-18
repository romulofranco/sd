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
public class RecursoESB extends Thread {

    public static final int TIPO_CLIENTE = 0;
    public static final int TIPO_GERENTE = 1;
    public static final int TIPO_PRODUTOR = 2;    
    
    protected Socket socket;
    protected String message;

    public RecursoESB(String message, Socket socket) {
        this.socket = socket;
        this.message = message;
    }

    public RecursoESB(Socket socket) {
        this.socket = socket;
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public BufferedWriter getWriter() throws IOException {
        return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write("*** Seja bem vindo, envie o comando ou digite ajuda para iniciar ***\n\n\r-> ");
            out.flush();

            while (true) {

                String msgCliente = in.readLine();
                System.out.println("Msg recebida de cliente: " + msgCliente);
                if (msgCliente.startsWith("TESTE")) {
                    out.write("Servidor esta OK! \t" + new Date() + "\n\n\r-> ");
                } else if (msgCliente.startsWith("CHAT")) {
                    out.write("Aguarde - buscando gerente de atendimento\n");
                    //insere cliente na fila                    
                } else if (msgCliente.startsWith("PRODUTOR")) {
                    //insere produtor na fila 
                } else if (msgCliente.contains("STATUS")) {
                    out.write("Servidor Admin Console\r\n Endereco: localhost:12345\r\n Num. Conexoes: 5\r\n Num. Arquivos FTP: 12\n\r Servicos ativos: Chat | FTP | Console Admin\n\n\r-> ");
                } else if (msgCliente.contains("TCHAU")) {
                    out.write("OK Servidor encerrando sua conexao! Tchau...");
                    out.flush();
                    Thread.sleep(2000);
                    socket.close();
                }
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ex) {
            Logger.getLogger(RecursoESB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(RecursoESB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
