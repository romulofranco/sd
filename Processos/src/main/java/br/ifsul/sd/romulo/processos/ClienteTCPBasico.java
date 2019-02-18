/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.processos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

public class ClienteTCPBasico {
    
    public static void main(String[] args) {
        try {
            Socket cliente = new Socket("localhost", 12345);
            BufferedReader reader = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
            
            writer.write("OLA\n");
            writer.flush();
            
            String msgRecebida = reader.readLine();
            JOptionPane.showMessageDialog(null, "Mensagem recebida do servidor:" + msgRecebida.toString());
            writer.close();
            reader.close();
            
            System.out.println("Conexão encerrada");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
