/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.loadbalance;

import br.ifsul.sd.romulo.threads.ftpimportar.CSVConsumer;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulo
 */
public class DatabaseController extends Thread {

    private static DatabaseController instance;

    private static CSVConsumer consumer;
    private static Cache mainCache;

    private DatabaseController() {
        consumer = new CSVConsumer();
        mainCache = new Cache();
    }

    public static DatabaseController getInstance() {
        if (instance == null) {
            instance = new DatabaseController();
        }
        return instance;
    }

    public Cache initializeCache() {
        try {
            mainCache.sync(consumer.createCacheMap("C:\\ftp\\legado_1_arquivo_1.csv"));
            return mainCache;
        } catch (InterruptedException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void storeMainCache(Integer key, String value, boolean triggerUpdateAll) {
        mainCache.store(value, key);

        if (triggerUpdateAll) {
            this.updateAllCaches();
        }
    }

    public void updateAllCaches() {

    }

}
