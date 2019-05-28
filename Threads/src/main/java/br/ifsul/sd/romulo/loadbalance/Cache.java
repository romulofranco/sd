/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.loadbalance;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author romulo
 */
public class Cache {

    private Map<Integer, String> memory;

    public Cache() {
        this.memory = new HashMap<>();
    }

    public Cache(Map<Integer, String> memory) {
        this.memory = memory;
    }

    public String getValue(Integer key) {
        return memory.get(key);
    }

    public void store(String value, Integer key) {
        memory.put(key, value);
    }

    public Map<Integer, String> getMemory() {
        return memory;
    }

    public void sync(Map<Integer, String> memory) {
        this.memory = memory;
    }

    void printAllEntries() {
        System.out.println("Chave \t Valor");
        for (Map.Entry<Integer, String> entry : memory.entrySet()) {
            Integer key = entry.getKey();
            String value = entry.getValue();
            System.out.println(key + " \t " + value);

        }
    }

}
