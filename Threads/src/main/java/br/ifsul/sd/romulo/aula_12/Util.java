/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_12;

import java.sql.Timestamp;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulo
 */
public class Util {

    public static void aguardar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException ex) {
            //ex.printStackTrace();
        }
    }

    public static long getTimestamp() {
        Timestamp t = new Timestamp(System.currentTimeMillis());
//        System.out.println("Tempo: " + t.getTime());
        return t.getTime();
    }

    public static void waitALittle(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            return 1;
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
