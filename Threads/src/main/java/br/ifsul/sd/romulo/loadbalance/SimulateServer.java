/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.loadbalance;

import br.ifsul.sd.romulo.aula_12.Util;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author romulo
 */
public class SimulateServer extends Thread {

    private int port;
    private List<SimulateTask> tasks;
    private int currentConnections = 0;
    private int totalConnectionsReceived = 0;
    private long timeForEachTask = 0;
    private String serverName;
    private boolean running  = true;
    private int completedLoad = 0;
    private boolean completedLaunch;
    private MonitorServer monitorServer;

    public SimulateServer(String name, int port) {
        this.port = port;
        this.tasks = new ArrayList<SimulateTask>();
        this.serverName = name;
        this.running = true;
        this.completedLoad = 0;
    }

    public void iniciarServer() {
        try {
            // Instancia o ServerSocket ouvindo a porta 12345
            ServerSocket servidor = new ServerSocket(port);
            System.out.println(serverName + " listening on " + port);
            completedLaunch = true;
            monitorServer.start();
            while (true) {
                SimulateTask st = new SimulateTask(servidor.accept());
                st.start();
                tasks.add(st);
                totalConnectionsReceived++;
                
                Util.aguardar(100);
                
//                if (!running) {
//                    System.out.println(serverName + " " + port + " has been done");        
//                    return;
//                }
            }
        } catch (IOException e) {
            System.out.println("Falha SimulateServer: " + e.getMessage());
            running = false;
            totalConnectionsReceived = -1;
        } finally {
        }

    }

    @Override
    public void run() {
        monitorServer = new MonitorServer(this);

        this.iniciarServer();
    }

    public static void main(String args[]) {
        SimulateServer servidor = new SimulateServer("Server1", 1234);
//        servidor.iniciarServer();
        servidor.start();

    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<SimulateTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<SimulateTask> tasks) {
        this.tasks = tasks;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getCompletedLoad() {
        return completedLoad;
    }

    public void setCompletedLoad(int completedLoad) {
        this.completedLoad = completedLoad;
    }

    public boolean isCompletedLaunch() {
        return completedLaunch;
    }

    public void setCompletedLaunch(boolean completedLaunch) {
        this.completedLaunch = completedLaunch;
    }

    @Override
    public void interrupt() {
        this.monitorServer.setRunning(false);
        super.interrupt();

    }

    public int getTotalConnectionsReceived() {
        return totalConnectionsReceived;
    }

    public void setTotalConnectionsReceived(int totalConnectionsReceived) {
        this.totalConnectionsReceived = totalConnectionsReceived;
    }

}
