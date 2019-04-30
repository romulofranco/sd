/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads.ftpimportar;

import br.ifsul.sd.romulo.threads.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVConsumer {

    public static int COUNT_THREAD = 0;
    public static long MAX_THREAD_PER_FILE = 5;

    public List<Thread> threadList = new ArrayList<Thread>();

    public void initialReader(String file) throws InterruptedException, IOException {

        String pathToCSV = file;
        CSVResourceHandler csvResHandler = new CSVResourceHandler(pathToCSV);
        long totalLinhas = csvResHandler.getCSVFileHandler().lines().count();
        long startLine = 0;
        long tamLine = csvResHandler.getCSVFileHandler().readLine().length();
        long offset = totalLinhas / MAX_THREAD_PER_FILE;

        while (startLine < totalLinhas) {
            CSVReader reader = new CSVReader(csvResHandler.getCSVFileHandler(), startLine, offset, tamLine);
            startLine = startLine + offset + 1;

            Thread t = new Thread(reader);
            t.setName("T" + COUNT_THREAD);
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
//                System.out.println("Current Number of Threads running: " + COUNT_THREAD);
                Util.waitALittle(20);
            }
        }
    }

    public static void main(String args[]) {
        try {
//            while (true) {
            long initialTime = Util.getTimestamp();

            CSVConsumer consumer = new CSVConsumer();
            List<String> files = new ArrayList<>();

            System.out.println("Arquivos a importar:");
            for (int i = 1; i < 4; i++) {
                for (int j = 1; j < 4; j++) {
                    String filePath = "c:\\ftp\\legado_" + i + "_arquivo_" + j + ".csv";
                    files.add(filePath);
                    System.out.println(filePath);
                }
            }

            for (String file : files) {
                consumer.initialReader(file);
            }

            consumer.startThreads();

            long finalTime = 0;

            if (checkIsFinishedAllThreads()) {
                finalTime = Util.getTimestamp();
            }

            System.out.println("Tempo de processamento: " + (finalTime - initialTime) + "ms");
//            }

        } catch (InterruptedException ex) {
            Logger.getLogger(CSVConsumer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CSVConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
