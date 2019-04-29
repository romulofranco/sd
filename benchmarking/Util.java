/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package benchmarking;

import java.sql.Timestamp;

/**
 *
 * @author romulo
 */
public class Util {

    public static void aguardar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static long getTimestamp() {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        System.out.println("Tempo: " + t.getTime());
        return t.getTime();
    }

}
