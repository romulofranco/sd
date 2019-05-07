/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.loadbalance;

import br.ifsul.sd.romulo.aula_12.Util;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class SimulatorLoadBalancer extends Thread {

    private static final int METHOD_ROUNDROBIN = 1;
    private static final int METHOD_LEAST_CONNECTIONS = 2;
    private static final int METHOD_RANDOM = 3;

    private int numServers = 5;
    private int numClients = 50;
    private int payload = 5;
    private int intervalTimeClient = 50;
    /*5 x 32bytes */
    private int initialServersPort = 1000;
    private int method = 1;

    /* milliseconds */
    private List<SimulateServer> servers;
    private List<SimulateClient> clients;
    private List<Integer> ports;
    private int currentPort;
    private long timeElapsedForDistribution = 0;

    private MonitorSimulator monitorSimulator;

    public SimulatorLoadBalancer(int numServers, int numClients, int payload, int initialServersPort, int method, int intervalTimeClient) {
        this.numServers = numServers;
        this.numClients = numClients;
        this.payload = payload;
        this.initialServersPort = initialServersPort;
        this.method = method;
        this.intervalTimeClient = intervalTimeClient;

        servers = new ArrayList<SimulateServer>();
        clients = new ArrayList<SimulateClient>();
        ports = new ArrayList<>();
    }

    public SimulatorLoadBalancer() {
    }

    public int getPort() {
        long initialTime = Util.getTimestamp();
        int portReturn = 0;

        switch (this.method) {
            case METHOD_ROUNDROBIN:
                portReturn = roundRobin();
                break;
            case METHOD_LEAST_CONNECTIONS:
                portReturn = this.leastConnection();
                break;
            default:
                portReturn = this.random();
                break;
        }

        long finalTime = Util.getTimestamp();
        timeElapsedForDistribution = timeElapsedForDistribution + (finalTime - initialTime); 
        System.out.println("Tempo metodo + port : " + timeElapsedForDistribution + ": " + portReturn);
        return portReturn;
    }

    private int random() {
        return 1000;
    }

    private int leastConnection() {
        return 1000;
    }

    private int roundRobin() {
        int portReturn;
        if (currentPort == ports.size()) {
            currentPort = 1;
            portReturn = 0;
        } else {
            portReturn = currentPort;
            currentPort++;
        }
        return ports.get(portReturn);
    }

    public void startSimulation() {

        for (int i = 0; i < numServers; i++) {
            SimulateServer ss = new SimulateServer("Server" + i, initialServersPort);
            ports.add(initialServersPort);
            initialServersPort++;
            servers.add(ss);
        }

        for (int i = 0; i < numClients; i++) {
            SimulateClient sc = new SimulateClient(this, "localhost", this.getPort(), payload);
            clients.add(sc);
        }

        for (int i = 0; i < numServers; i++) {
            SimulateServer ss = servers.get(i);
            ss.start();
        }

        monitorSimulator.start();

        System.out.println("Aguardando carga total de servidores...");

        while (true) {
            if (monitorSimulator.getCompleted() == servers.size()) {
                break;
            }

            Util.aguardar(100);
        }

        System.out.println("Todos servidores lançados...");
        int j = 0;
        for (int i = 0; i < numClients; i++) {
            SimulateClient sc = clients.get(i);
            sc.start();
            j++;
            if (j == intervalTimeClient) {
                j = 0;
                Util.aguardar(1000);
            }
            
            Util.aguardar(50);
        }

        System.out.println("Todos clientes iniciados...");

        while (monitorSimulator.isRunning()) {
            System.out.println("Server\t\t#Port\t\t #Current\tCompleted\t #Requests \t#Failures \tTime/cli(ms)\tTotal Time ");
            Util.aguardar(5000);
        }

        System.out.println("\n\nSimulação encerrada com sucesso!");
        for (int i = 0; i < numServers; i++) {
            SimulateServer ss = servers.get(i);
            ss.interrupt();

        }

    }

    @Override
    public void run() {
        monitorSimulator = new MonitorSimulator(servers, clients);
        this.startSimulation();

    }

    public static void main(String args[]) {
        SimulatorLoadBalancer simulator = new SimulatorLoadBalancer(10, 40000, 5, 
                                    1000, METHOD_ROUNDROBIN, 4000);
        simulator.start();
    }

}
