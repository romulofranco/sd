/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads.ftpimportar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


class CSVResourceHandler {

    String CSVPath;

    public CSVResourceHandler() {
    }// default constructor 

    public CSVResourceHandler(String path) {
        File f = new File(path);
        if (f.exists()) {
            CSVPath = path;
        } else {
            System.out.println("Wrong file path! You gave: " + path);
        }
    }

    public BufferedReader getCSVFileHandler() {
        BufferedReader br = null;
        try {
            FileReader is = new FileReader(CSVPath);
            br = new BufferedReader(is);
        } catch (Exception e) {

        }
        return br;
    }
}