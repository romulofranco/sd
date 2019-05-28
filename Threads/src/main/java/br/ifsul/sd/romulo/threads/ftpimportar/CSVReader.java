/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads.ftpimportar;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class CSVReader implements Runnable {

    private long startLine = 1;
    private long offset = 3000;
    private long tamLine = 0;
    private long limit = 0;
    ;

    BufferedReader CSVBufferedReader;

    public CSVReader() {
    } // default constructor

    public CSVReader(BufferedReader br) {
        this.CSVBufferedReader = br;
    }

    public CSVReader(BufferedReader br, long startLine, long offset, long tamLine) {
        this.CSVBufferedReader = br;
        this.startLine = startLine;
        this.offset = offset;
        this.tamLine = tamLine;
        this.limit = startLine + offset;
    }

    private synchronized void readCSV() {
//        System.out.println("Current thread " + Thread.currentThread().getName());
        String line;
        long countLine = startLine;
        try {
            CSVBufferedReader.skip(startLine * tamLine);
            String lastLine = "";

            while ((line = CSVBufferedReader.readLine()) != null) {
                lastLine = line;
                countLine++;
                if (countLine >= limit) {
                    System.out.println(Thread.currentThread().getName() + " Ultima linha processada: " + lastLine);
                    break;
                }
            }

            CSVConsumer.COUNT_THREAD--;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, String> readCSV(boolean withoutOffset) throws IOException {

        String line;
        Map<Integer, String> cache = new HashMap<>();

        try {

            while ((line = CSVBufferedReader.readLine()) != null) {
                String[] array = line.split(";");
                cache.put(Integer.parseInt(array[0]), array[1]);
            }

            return cache;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CSVBufferedReader.close();
        }
        return null;
    }

    public void run() {
        readCSV();
    }
}
