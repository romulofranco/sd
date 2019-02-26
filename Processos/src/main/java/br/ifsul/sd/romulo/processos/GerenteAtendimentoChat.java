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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulo
 */
public class GerenteAtendimentoChat extends RecursoESB {

    private boolean ocupado = false;

    public GerenteAtendimentoChat(Socket socket) {
        super(socket);
        ocupado = true;
    }

    @Override
    public void run() {
        try {

            System.out.println("Cliente Chat conectado: " + socket.getInetAddress().getHostAddress());

            // Create character streams for the socket.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {
                String msgCliente = in.readLine();
                System.out.println("Msg recebida de cliente: " + msgCliente);
                if (msgCliente.startsWith("OLA")) {
                    out.write("Ola recebido com sucesso em " + new Date() + "\n");
                } else {
                    out.write("Ola não recebido com sucesso em " + new Date() + "\n");
                }
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(RecursoESB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
