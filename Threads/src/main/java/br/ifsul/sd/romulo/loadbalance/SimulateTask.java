/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.loadbalance;

import br.ifsul.sd.romulo.aula_12.Util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 *
 * @author romulo
 */
public class SimulateTask extends Thread {

    protected Socket socket;

    public static final String PROMPT = "\n\n\r-> ";
    public static final String SAIR = "sair";
    
    private boolean running;
    private long timeElapsed;
    private long numberRequests = 0;
    private long numberFailures = 0;
    
    public SimulateTask(Socket socket) {
        this.socket = socket;
        this.running = true;
    }

    @Override
    public synchronized void run() {
        try {
            long initialTime = Util.getTimestamp();
            //System.out.println("Cliente conectado: " + socket);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.defaultCharset()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write("*** Seja bem vindo, envie o comando ou digite ajuda para iniciar ***\n\n\r-> ");
            out.flush();

            while (true) {
                String msgCliente = in.readLine();
                numberRequests++;
//                System.out.println("Client: " + msgCliente +  " - Requests: " +numberRequests);
    
                int timeWait  = Util.getRandomNumberInRange(50, 200);
//                System.out.println("Task time: " + timeWait);
                Util.aguardar(timeWait);
                
                if (msgCliente.startsWith(SAIR)) {
                    this.interrupt();
                    break;
                }

                out.write("OK" + PROMPT);
                out.flush();
                Util.aguardar(50);
            }
            
            long finalTime = Util.getTimestamp();
            timeElapsed = finalTime - initialTime;
            
            running = false;
        } catch (IOException e) {
            timeElapsed = 0;
            numberFailures=6-numberRequests;
//            System.out.println("Falha SimulateTask: " + socket +  " Failures: " + numberFailures +  " / " + timeElapsed);
            
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public long getNumberRequests() {
        return numberRequests;
    }

    public void setNumberRequests(long numberRequests) {
        this.numberRequests = numberRequests;
    }

    public long getNumberFailures() {
        return numberFailures;
    }

    public void setNumberFailures(long numberFailures) {
        this.numberFailures = numberFailures;
    }

}
