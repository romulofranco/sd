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
    private int numServerCompletedLoad = 0;
    private int numServerCompletedLaunch = 0;
    private int numServerFailure = 0;
    private int numClientsRunning = 0;
    private int numClientsCompleted = 0;
    private int numClientsRecovered = 0;
    private int numClientsLostConn = 0;

    private boolean running = true;

    public MonitorSimulator(List<SimulateServer> servers, List<SimulateClient> clients) {
        this.servers = servers;
        this.clients = clients;
    }

    public int getPort() {
        return 0;
    }

    public synchronized void monitor() {
        while (true) {
            numServerCompletedLoad = 0;
            numServerFailure = 0;
            numServerCompletedLaunch = 0;
            numClientsCompleted = 0;
            numClientsRecovered = 0;
            numClientsLostConn = 0;
            numClientsRunning = 0;

            for (int i = 0; i < servers.size(); i++) {
                SimulateServer ss = servers.get(i);
                if (ss.isCompletedLaunch()) {
                    numServerCompletedLaunch++;
                }

                if (ss.getTotalConnectionsReceived() > 0) {
                    //if (ss.isRunning()) {
                    numServerCompletedLoad = numServerCompletedLoad + ss.getTotalConnectionsReceived();
                    //}
                } else if (ss.getTotalConnectionsReceived() == -1) {
                    numServerFailure++;
                }
            }

            for (int i = 0; i < clients.size(); i++) {
                SimulateClient sc = clients.get(i);
                if (sc.getTries() == 0 && !sc.isRunning()) {
                    numClientsCompleted++;
                } else if (sc.getTries() >= 1 && !sc.isRunning()) {
                    numClientsRecovered++;
                } else if (sc.getTries() > 15 && !sc.isRunning()) {
                    numClientsLostConn++;
                }

                if (sc.isRunning()) {
                    numClientsRunning++;
                }
            }

            System.out.println("Total Servers - Launched: " + numServerCompletedLaunch + " Completed Load: " + numServerCompletedLoad + " Failure Servers: " + numServerFailure);
            System.out.println("Total Clients - Running: " + numClientsRunning + " Completed Task: " + numClientsCompleted + " Recovered Clientes: " + numClientsRecovered + " Lost Conn.:" + (clients.size() - (numClientsCompleted + numClientsRecovered)));

            Util.aguardar(5000);

            if (clients.size() == numClientsCompleted) {
                System.out.println("Total Servers - Launched: " + numServerCompletedLaunch + " Completed Load: " + numServerCompletedLoad + " Failure Servers: " + numServerFailure);
                System.out.println("Total Clients - Running/Launched: " + numClientsRunning + "/" + clients.size() + " Completed Task: " + numClientsCompleted + " Recovered Clientes: " + numClientsRecovered + " Lost Conn.:" + (clients.size() - (numClientsCompleted + numClientsRecovered)));
                running = false;
                break;
            }

        }
    }

    public void run() {
        this.monitor();
    }

    public int getNumServerCompletedLoad() {
        return numServerCompletedLoad;
    }

    public void setNumServerCompletedLoad(int numServerCompletedLoad) {
        this.numServerCompletedLoad = numServerCompletedLoad;
    }

    public int getNumServerFailure() {
        return numServerFailure;
    }

    public void setNumServerFailure(int numServerFailure) {
        this.numServerFailure = numServerFailure;
    }

    public int getNumClientsCompleted() {
        return numClientsCompleted;
    }

    public void setNumClientsCompleted(int numClientsCompleted) {
        this.numClientsCompleted = numClientsCompleted;
    }

    public int getNumClientsRecovered() {
        return numClientsRecovered;
    }

    public void setNumClientsRecovered(int numClientsRecovered) {
        this.numClientsRecovered = numClientsRecovered;
    }

    public int getNumClientsLostConn() {
        return numClientsLostConn;
    }

    public void setNumClientsLostConn(int numClientsLostConn) {
        this.numClientsLostConn = numClientsLostConn;
    }

    public int getNumServerCompletedLaunch() {
        return numServerCompletedLaunch;
    }

    public void setNumServerCompletedLaunch(int numServerCompletedLaunch) {
        this.numServerCompletedLaunch = numServerCompletedLaunch;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getCompleted() {
        return this.numServerCompletedLaunch + this.numServerFailure;
    }

}
