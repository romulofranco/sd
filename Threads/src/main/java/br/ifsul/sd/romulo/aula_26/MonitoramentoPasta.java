/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_26;

import br.ifsul.sd.romulo.threads.Util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class MonitoramentoPasta extends Thread {

    private String pathMonitora;

    private String servidorFTP;
    private String usuario;
    private String senha;
    private int porta;

    public MonitoramentoPasta(String pathMonitora, String servidorFTP, String usuario, String senha, int porta) {
        this.pathMonitora = pathMonitora;
        this.servidorFTP = servidorFTP;
        this.usuario = usuario;
        this.senha = senha;
        this.porta = porta;
    }

    public void monitorar() throws IOException, InterruptedException {

        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        Path path = Paths.get(this.pathMonitora);

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;
        while ((key = watchService.take()) != null) {

            boolean novosArquivos = false;
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println(
                            "Iniciando envio para FTP do arquivo " + event.context());
                    ArquivoFTP arquivoFTP = new ArquivoFTP(this.pathMonitora + event.context(), event.context().toString());
                    TratadorFTP tratarFTP = new TratadorFTP(arquivoFTP, this.servidorFTP, this.usuario, this.senha, 21);
                    new Thread(tratarFTP, event.context().toString()).start();
                    novosArquivos = true;
                }
            }
            key.reset();

            if (novosArquivos) {
                novosArquivos = false;
                //deletarArquivos();
                Util.waitALittle(5000); //Aguarda os arquivos serem enviados para FTP
                comunicaServidorIniciarConsumidor();
            }
        }
    }

    public void comunicaServidorIniciarConsumidor() {
        
        try {
            Socket cliente = new Socket("localhost", 1234);
            BufferedReader reader = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));
            
            this.recebeMsg(reader);
            Util.waitALittle(1000);
            enviaMsg(writer, "produtor");            
            Util.waitALittle(2000);            
            recebeMsg(reader);
            enviaMsg(writer, "sair");            
            Util.waitALittle(5000);
            recebeMsg(reader);            
            writer.close();
            reader.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void enviaMsg(BufferedWriter writer, String msg) throws IOException {
        System.out.println("Voce: " + msg);
        writer.write(msg  +"\n\r");
        writer.flush();
    }

    private void recebeMsg(BufferedReader reader) throws IOException {
        String msg;
        while ((msg = reader.readLine()) != null) {            
            System.out.println("Servidor: " + msg);
            break;
        }
    }

    @Override
    public void run() {
        try {
            this.monitorar();
        } catch (IOException ex) {
            Logger.getLogger(MonitoramentoPasta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(MonitoramentoPasta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {
        MonitoramentoPasta monitoramentoPasta = new MonitoramentoPasta("c:\\ftp\\", "localhost", "romulo", "123", 21);
        monitoramentoPasta.start();

    }

}
