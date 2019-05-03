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

    private int numServers = 5;
    private int numClients = 50;
    private int payload = 5;
    /*5 x 32bytes */
    private int initialServersPort = 1000;
    private int method = 1;
    private int intervalCallServers = 1000;
    /* milliseconds */

    private List<SimulateServer> servers;
    private List<SimulateClient> clients;
    private List<Integer> ports;
    private int currentPort;

    public SimulatorLoadBalancer(int numServers, int numClients, int payload, int initialServersPort, int method, int intervalCallServers) {
        this.numServers = numServers;
        this.numClients = numClients;
        this.payload = payload;
        this.initialServersPort = initialServersPort;
        this.method = method;
        this.intervalCallServers = intervalCallServers;

        servers = new ArrayList<SimulateServer>();
        clients = new ArrayList<SimulateClient>();
        ports = new ArrayList<>();
    }

    public SimulatorLoadBalancer() {
    }

    public int getPort() {
        int portReturn = 0;

        if (this.method == METHOD_ROUNDROBIN) {
            if (currentPort == ports.size()) {
                currentPort = 1;
                portReturn = 0;
            } else {
                portReturn = currentPort;
                currentPort++;
            }

        } else if (this.method == METHOD_LEAST_CONNECTIONS) {

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

        Util.aguardar(500);

        clients.forEach((client) -> {
            client.start();
        });

        System.out.println("Server\t\t\tPort\t\tCurrent\tTotal\tTime per client(ms)\tTotal Time ");

    }

    @Override
    public void run() {
        MonitorSimulator ms = new MonitorSimulator(servers, clients);
        ms.start();
        this.startSimulation();

    }

    public static void main(String args[]) {
        SimulatorLoadBalancer simulator = new SimulatorLoadBalancer(200, 5000, 5, 1000, METHOD_ROUNDROBIN, 0);
        simulator.start();
    }

}
