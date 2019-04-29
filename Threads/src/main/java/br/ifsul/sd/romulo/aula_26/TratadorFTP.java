/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_26;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author admin
 */
public class TratadorFTP implements Runnable {

    private ArquivoFTP arquivoFTP;
    private String servidor;
    private String usuario;
    private String senha;
    private int porta;

    public TratadorFTP(ArquivoFTP arquivoFTP, String servidor, String usuario, String senha, int porta) {
        this.arquivoFTP = arquivoFTP;
        this.servidor = servidor;
        this.usuario = usuario;
        this.senha = senha;
        this.porta = porta;
    }

    @Override
    public void run() {
        iniciaUpload();
    }

    public void iniciaUpload() {

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(servidor, porta);
            ftpClient.login(usuario, senha);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File arquivoLocal = new File(arquivoFTP.getPathCompleto());
            String arquivoRemoto = arquivoFTP.getNomeRemoto();
            InputStream inputStream = new FileInputStream(arquivoLocal);
            System.out.println("Iniciando upload do arquivo: " + arquivoLocal);
            boolean done = ftpClient.storeFile(arquivoRemoto, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("Arquivo concluído - " + arquivoLocal);
            }

            boolean completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("Thread finalizada - " + arquivoLocal);
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();

        } finally {
            try {
                if (ftpClient.isConnected()) {
//                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

  

}
