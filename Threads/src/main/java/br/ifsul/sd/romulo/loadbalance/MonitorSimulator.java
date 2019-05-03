/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.loadbalance;

import br.ifsul.sd.romulo.aula_12.Util;
import java.util.List;

/**
 *
 * @author admin
 */
public class MonitorSimulator extends Thread {

    private List<SimulateServer> servers;
    private List<SimulateClient> clients;

    public MonitorSimulator(List<SimulateServer> servers, List<SimulateClient> clients) {
        this.servers = servers;
        this.clients = clients;
    }

    public synchronized void monitor() {
        while (true) {
            int numServerCompletedLoad = 0;
            int numServerFailure = 0;
            int numClientsCompleted = 0;
            int numClientsRecovered = 0;
            int numClientsLostConn = 0;

            for (int i = 0; i < servers.size(); i++) {
                SimulateServer ss = servers.get(i);
                if (ss.getCompletedLoad() == 1) {
                    numServerCompletedLoad++;
                } else if (ss.getCompletedLoad() == 2) {
                    numServerFailure++;
                }
            }

            for (int i = 0; i < clients.size(); i++) {
                SimulateClient sc = clients.get(i);
                if (sc.getTries() == 0 && !sc.isRunning()) {
                    numClientsCompleted++;
                } else if (sc.getTries() == 1 && !sc.isRunning()) {
                    numClientsRecovered++;
                } else if (sc.getTries() > 1 && !sc.isRunning()) {
                    numClientsLostConn++;
                }
            }

            System.out.println("Total Servers - Launched: " + servers.size() + " Completed Load: " + numServerCompletedLoad + " Failure Servers: " + numServerFailure);
            System.out.println("Total Clients - Launched: " + clients.size() + " Completed Task: " + numClientsCompleted + " Recovered Clientes: " + numClientsRecovered + " Lost Conn.:" + (clients.size() - (numClientsCompleted + numClientsRecovered)));
            Util.aguardar(5000);
        }
    }

    public void run() {
        this.monitor();
    }

}
