/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads;

import java.io.*;

class CSVReader implements Runnable
{
    static int lineCount = 1;
    static int limit = 3000;
    
    BufferedReader CSVBufferedReader;
    
    public CSVReader(){} // default constructor
    
    public CSVReader(BufferedReader br){
        this.CSVBufferedReader = br;
    }
    
    private synchronized void readCSV(){
        System.out.println("Current thread "+Thread.currentThread().getName());
        String line;
        try {
            while((line = CSVBufferedReader.readLine()) != null){
                System.out.println(line);
                lineCount ++;
//                if(lineCount >= limit){
//                    break;
//                }            
            }
            
            CSVConsumer.COUNT_THREAD--;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }       
    public void run() { 
        readCSV();
    }
}
