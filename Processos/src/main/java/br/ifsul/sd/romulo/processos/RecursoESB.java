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
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulo
 */
public class RecursoESB extends Thread {

    private Socket socket;
    public static final String PRODUTOR = "P";
    public static final String CHAT_CLIENTE = "C";
    public static final String CHAT_GERENTE = "G";

    private boolean executado;
    private String requisicaoInicial;

    public RecursoESB(String requisicaoInicial, Socket socket) {
        this.requisicaoInicial = requisicaoInicial;
        this.socket = socket;
        this.executado = false;
    }

    @Override
    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (!executado) {
                System.out.println("Executou a requisicao: " + requisicaoInicial);

                out.write("\n\rExecutou a requisicao: " + requisicaoInicial);
                out.write("\n\rFinalizando a sessao!");
                out.flush();

                Util.waitALittle(2000);
                socket.close();
                this.executado = true;
            }
        } catch (IOException ex) {
            Logger.getLogger(RecursoESB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
    }

    public boolean foiExecutado() {
        return executado;
    }
}
