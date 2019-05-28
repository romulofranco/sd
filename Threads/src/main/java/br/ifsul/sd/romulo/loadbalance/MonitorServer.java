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
public class MonitorServer extends Thread {

    private List<SimulateTask> tasks;
    private String serverName;
    private int port;
    private SimulateServer ss;
    private boolean running = true;
    private long currentConnections;

    public MonitorServer(SimulateServer ss) {
        this.tasks = ss.getTasks();
        this.serverName = ss.getServerName();
        this.port = ss.getPort();
        this.ss = ss;
    }

    private synchronized void monitor() {
        long timeElapsed;
        long totalRequests;
        long totalTime;
        long keepRunning;
        long totalFailures;

        while (true) {
            timeElapsed = 0;
            currentConnections = 0;
            totalTime = 0;
            keepRunning = 0;
            totalRequests = 0;
            totalFailures = 0;

            for (SimulateTask st : tasks) {
                if (!st.isAlive()) {
                    totalTime = totalTime + st.getTimeElapsed();
                    totalRequests = totalRequests + st.getNumberRequests();
                    totalFailures = totalFailures + st.getNumberFailures();
                    keepRunning++;
                } else {
                    currentConnections++;
                }
            }

            if (tasks.size() > 0) {
                timeElapsed = totalTime / tasks.size();
            }

            if (keepRunning >= tasks.size()) {
                ss.setRunning(false);
                //break;
            }

            System.out.println(serverName + "\t\t\t" + port + "\t\t" + currentConnections + "\t" + tasks.size() + "\t" + totalRequests + "\t" + totalFailures + "\t" + timeElapsed + "\t" + totalTime);
            Util.aguardar(3000);

            if (!running) {
                break;
            }

        }
    }

    @Override
    public void run() {
        this.monitor();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getCurrentConnections() {
        return currentConnections;
    }

}
