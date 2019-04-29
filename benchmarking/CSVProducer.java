/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.threads.ftpimportar;

import java.io.FileWriter;

/**
 *
 * @author admin
 */
public class CSVProducer {

    static int MAX_LINES_IN_FILE = 2000000;

    public void writeContentToCsv(int file, int system) throws Exception {
        try (FileWriter writer = new FileWriter("c:\\ftp\\legado_" + system + "_arquivo_" + file + ".csv")) {
            for (int i = 0; i < MAX_LINES_IN_FILE; i++) {
                writer.append("" + (i++))
                        .append(";teste 1;teste 2;teste 3;teste 4;teste 5;teste 1;teste 2")
                        .append(";teste 3;teste 4;teste 5;teste 1;teste 2;teste 3;teste 4;teste 5;")
                        .append(System.getProperty("line.separator"));

            }
        }
    }

    public static void main(String[] args) throws Exception {
        CSVProducer producer = new CSVProducer();
        producer.writeContentToCsv(1, 1);
        producer.writeContentToCsv(2, 1);
        producer.writeContentToCsv(3, 1);

        producer.writeContentToCsv(1, 2);
        producer.writeContentToCsv(2, 2);
        producer.writeContentToCsv(3, 2);

        producer.writeContentToCsv(1, 3);
        producer.writeContentToCsv(2, 3);
        producer.writeContentToCsv(3, 3);
    }
}
