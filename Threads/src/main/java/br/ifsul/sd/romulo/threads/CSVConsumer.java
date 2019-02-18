/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVConsumer {

    public static int COUNT_THREAD = 0;
    public static int MAX_THREAD_PER_FILE = 3;

    public List<Thread> threadList = new ArrayList<Thread>();

    public void initialReader(String file, int numThreads) throws InterruptedException {

        String pathToCSV = file;
        CSVResourceHandler csvResHandler = new CSVResourceHandler(pathToCSV);
        CSVReader reader = new CSVReader(csvResHandler.getCSVFileHandler());
        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(reader);
            t.setName("T" + i);
            threadList.add(t);
            COUNT_THREAD++;
        }
    }

    public void startThreads() {
        for (Thread t : threadList) {
            t.start();
        }

        System.out.println("Current Number of Threads running: " + COUNT_THREAD);
    }

    public static synchronized boolean checkIsFinishedAllThreads() {
        while (true) {
            if (COUNT_THREAD == 0) {
                return true;
            } else {
                System.out.println("Current Number of Threads running: " + COUNT_THREAD);
                Util.waitALittle(20);
            }
        }
    }

    public static void main(String args[]) {
        try {
            while (true) {
                long initialTime = Util.getTimestamp();

                CSVConsumer consumer = new CSVConsumer();
                List<String> files = new ArrayList<>();

                System.out.println("Arquivos a importar:");
                for (int i = 1; i < 4; i++) {
                    for (int j = 1; j < 4; j++) {
                        String filePath = "c:\\ftp\\arquivo_" + j + "_legado_" + i + ".csv";
                        files.add(filePath);
                        System.out.println(filePath);
                    }
                }

                for (String file : files) {
                    consumer.initialReader(file, MAX_THREAD_PER_FILE);
                }

                consumer.startThreads();

                long finalTime = 0;

                if (checkIsFinishedAllThreads()) {
                    finalTime = Util.getTimestamp();
                }

                System.out.println("Tempo de processamento: " + (finalTime - initialTime) + "ms");
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(CSVConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
