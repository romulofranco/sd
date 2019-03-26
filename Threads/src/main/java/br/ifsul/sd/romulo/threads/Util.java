/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads;

import br.ifsul.sd.romulo.aula_26.ArquivoFTP;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author admin
 */
public class Util {

    public static long getTimestamp() {
        Timestamp t = new Timestamp(System.currentTimeMillis());
        System.out.println("Tempo: " + t.getTime());
        return t.getTime();
    }

    public static void waitALittle(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
}
