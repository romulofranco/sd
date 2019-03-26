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
public class Produtor extends RecursoESB {

    public Produtor(String message, Socket socket) {
        super(message, socket, RecursoESB.TIPO_PRODUTOR);
    }

    @Override
    public void run() {
        try {
            System.out.println("Produtor conectado: " + socket);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write("Aguarde PRODUTOR - servidor ira atender sua requisicao\n" + ConexaoCliente.PROMPT);
            out.flush();

            while (true) {

                if (in.ready()) {
                    String msg = in.readLine();
                    System.out.println("Produtor Thread " + this.getName() + " - Recebida: " + msg);
                    parserProtocolo(msg, out);

                    Util.aguardar(1000);
                    emExecucao = false;
                }
//                socket.close();
            }

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(Produtor.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
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
    }
}
