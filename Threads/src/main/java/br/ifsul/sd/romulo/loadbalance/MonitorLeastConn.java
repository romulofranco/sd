/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.loadbalance;

import br.ifsul.sd.romulo.aula_12.Util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author romulo
 */
public class MonitorLeastConn extends Thread {
    
    private List<SimulateServer> servers;
    private Map<Integer, Long> map;
    private int leastServerPort = 0;
    
    public MonitorLeastConn(List<SimulateServer> servers) {
        this.servers = servers;
        map = new HashMap(servers.size());
    }
    
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < servers.size(); i++) {
                SimulateServer ss = servers.get(i);
                map.put(ss.getPort(), ss.getCurrentConn());
                //Arrays.sort(conns, 0, conns.length);                
            }
            
            map.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEach(System.out::println);
//            System.out.println("Map : "  + map.values());
            Util.aguardar(2000);
        }
    }

    public synchronized int getLeastServerPort() {
        return leastServerPort;
    }
    
    
}
