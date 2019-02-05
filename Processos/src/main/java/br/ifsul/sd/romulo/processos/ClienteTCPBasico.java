/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.processos;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ClienteTCPBasico {

    public static void main(String[] args) {
        try {
            Socket cliente = new Socket("localhost", 12345);
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
            
            saida.flush();
            saida.writeObject("OLA");
            
            String msgRecebida = (String) entrada.readObject();
            JOptionPane.showMessageDialog(null, "Mensagem recebida do servidor:" + msgRecebida.toString());
            entrada.close();
            saida.close();
            
            System.out.println("Conexão encerrada");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
