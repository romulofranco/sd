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

    public MonitorServer(SimulateServer ss) {
        this.tasks = ss.getTasks();
        this.serverName = ss.getServerName();
        this.port = ss.getPort();
        this.ss = ss;
    }

    private synchronized void monitor() {
        long timeElapsed = 0;
        long currentConnections = 0;
        long totalTime = 0;
        long keepRunning = 0;

        while (true) {
            timeElapsed = 0;
            currentConnections = 0;
            totalTime = 0;
            keepRunning = 0;

            for (SimulateTask st : tasks) {
                if (!st.isAlive()) {
                    totalTime = totalTime + st.getTimeElapsed();
                    keepRunning++;
                } else {
                    currentConnections++;
                }
            }

            if (tasks.size() > 0) {
                timeElapsed = totalTime / tasks.size();
            }
            
            if (keepRunning >= tasks.size()) {
//                ss.setRunning(false);
//                break;
            }

            System.out.println(serverName + "\t\t\t" + port + "\t\t" + currentConnections + "\t" + tasks.size() + "\t" + timeElapsed + "\t" + totalTime);
            Util.aguardar(2000);

            

        }
    }

    @Override
    public void run() {
        this.monitor();
    }
}
