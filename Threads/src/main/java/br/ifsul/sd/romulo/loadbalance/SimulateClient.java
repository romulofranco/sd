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

/**
 *
 * @author romulo
 */
public class SimulateClient extends Thread {

    public static final String PROMPT = "\n\n\r-> ";
    public static final String SAIR = "sair";
    private String address;
    private int port;
    private int payload;
    private int tries = 0;
    private SimulatorLoadBalancer simulator;
    private int oldPort;

    private boolean running;

    public SimulateClient(String address, int port, int payload) {
        super();
        this.address = address;
        this.port = port;
        this.payload = payload;

    }

    public SimulateClient(SimulatorLoadBalancer sim, String address, int port, int payload) {
        super();
        this.address = address;
        this.port = port;
        this.payload = payload;
        this.simulator = sim;
        this.oldPort = port;
        this.running = true;
    }

    private String createString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < payload; i++) {
            for (int j = 0; j < 32; j++) {
                sb.append("A");
            }
            sb.append(i);
        }

        return sb.toString();
    }

    public void handleConnectionFailures() {
        oldPort = port;
        port = simulator.getPort();
        if (oldPort == port) {
            port = simulator.getPort();
            if (oldPort == port) {
                tries++;
                return;
            } else {
                tries++;
                this.run();
            }
        } else {
            tries++;
            this.run();
        }
        //  System.out.println("Port: " + port);

    }

    @Override
    public synchronized void run() {
        try {
//            System.out.println("Cliente iniciando conexao com: " + this.address + " Porta: " + this.port);

            Socket cliente = new Socket(this.address, this.port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));

            // String strPayload = this.createString();
//            System.out.println("Payload string: " + strPayload);
            String msgCliente = reader.readLine();

            simulateCache(writer, reader);

            Util.aguardar(50);

            msgCliente = reader.readLine();

            writer.write(SAIR + PROMPT);
            writer.flush();

            Util.aguardar(50);

            if (tries > 0) {
                System.out.println("Client recovered and redirected from " + oldPort + " to another port " + port);
            }

            running = false;

        } catch (IOException e) {
            System.out.println("Falha SimulateClient " + port + " - " + e);
            if (tries < 10) {
                this.handleConnectionFailures();
            }

        }

    }

    private void simulateCache(BufferedWriter writer, BufferedReader reader) throws IOException {
        String msgCliente = "";
        int randomID = Util.getRandomNumberInRange(0, 10000);
        String msgServer = "CACHE-CHECK;" + randomID + "\r\n";
        System.out.println("Enviando " + msgServer);
        writer.write(msgServer);
        writer.flush();

        msgCliente = receberMsg(reader);

        System.out.println("Server: " + msgCliente);

        if (msgCliente.startsWith("-> NOTFOUND")) {
            msgServer = "CACHE-STORE;" + randomID + ";" + this.address + "-" + this.port + "\r\n";
            System.out.println("Enviando " + msgServer);
            writer.write(msgServer);
            writer.flush();

            msgCliente = receberMsg(reader);

            if (msgCliente.startsWith("OK")) {
                writer.write("VER-CACHE\r\n");
                writer.flush();
            }
        }

        if (msgCliente.startsWith("FOUND")) {
            System.out.println("Found in Cache: " + msgCliente);
        }
    }

    private String receberMsg(BufferedReader reader) throws IOException {
        String msg = "";

        while (msg.isEmpty()) {
            msg = reader.readLine();
            Util.aguardar(500);
        }
        return msg;
    }

    public static void main(String args[]) {
        SimulateClient sc = new SimulateClient("localhost", 1234, 5);
        sc.start();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTries() {
        return tries;
    }

}
