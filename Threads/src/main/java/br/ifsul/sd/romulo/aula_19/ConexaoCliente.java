/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_19;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author romulo
 */
public class ConexaoCliente extends RecursoESB {

    private static final String PROTOCOL_STATUS = "status";
    public static final String PROTOCOL_PRODUTOR = "produtor";
    public static final String PROTOCOL_CHATGERENTE = "chat-gerente";
    public static final String PROTOCOL_CHATCLIENTE = "chat-cliente";
    public static final String PROTOCOL_AJUDA = "ajuda";
    private static final String PROTOCOL_TESTE = "teste";

    public static final String PROMPT = "\n\n\r-> ";

    private List<RecursoESB> filaGeral;

    private static final int CONTINUA = 1;
    private static final int ENCERRA = 2;
    private static final int BLOQUEIA = 3;

    public ConexaoCliente(List<RecursoESB> filaGeral, Socket socket) {
        super(socket);
        this.filaGeral = filaGeral;
    }

    public void insereFila(RecursoESB recurso) {
        filaGeral.add(recurso);
    }

    @Override
    public synchronized void run() {
        try {
            System.out.println("Cliente conectado: " + socket);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.defaultCharset()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write("*** Seja bem vindo, envie o comando ou digite ajuda para iniciar ***\n\n\r-> ");
            out.flush();

            while (this.isAlive()) {                
                String msgCliente = in.readLine();
                System.out.println("Comando recebido: " + msgCliente);

                if (parserProtocolo(msgCliente, in, out) == ENCERRA) {
                    this.interrupt();
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ex) {
            Logger.getLogger(ConexaoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int parserProtocolo(String msgCliente, BufferedReader in, BufferedWriter out) throws InterruptedException, IOException {
        if (msgCliente.startsWith(PROTOCOL_TESTE)) {
            out.write("Servidor esta OK! \t" + new Date() + PROMPT);
        } else if (msgCliente.startsWith(PROTOCOL_AJUDA)) {
            out.write("*** Comando\tDescricao  ***\n\r");
            out.write("teste\t\tMostra se o servidor esta operante\n\r");
            out.write("ajuda\t\tMostra este console\n\r");
            out.write("chat-cliente\tSolicita um gerente para atendimento\n\r");
            out.write("chat-gerente\tCria um gerente de atendimento\n\r");
            out.write("produtor\tSolicita um monitor de FTP para a importacao\n\r");
            out.write(PROMPT);
        } else if (msgCliente.startsWith(PROTOCOL_CHATCLIENTE)) {
            out.write("Aguarde - buscando gerente de atendimento\n\r");
            //insere cliente na fila            
            this.insereFila(new ChatCliente(msgCliente, socket));
            return ENCERRA;
        } else if (msgCliente.startsWith(PROTOCOL_CHATGERENTE)) {
            //insere gerente na fila
            out.write("Aguarde - servidor ira atender sua requisicao\n\r");
            this.insereFila(new ChatGerente(msgCliente, this.socket));
            return ENCERRA;
        } else if (msgCliente.startsWith(PROTOCOL_PRODUTOR)) {
            //insere produtor na fila
            this.insereFila(new RecursoESB(msgCliente, this.socket));
            return ENCERRA;
        } else if (msgCliente.contains(PROTOCOL_STATUS)) {
            out.write("Servidor Admin Console\r\n Endereco: localhost:1234\r\n Num. Conexoes: 5\r\n Num. Arquivos FTP: 12\n\r Servicos ativos: Chat | FTP | Console Admin\n\n\r-> ");
        } else if (msgCliente.contains("sair")) {
            out.write("OK Servidor encerrando sua conexao! Tchau...");
            out.flush();
            Thread.sleep(2000);
            socket.close();
        } else {
            out.write("\n\r-> " + this.getClass().getSimpleName() + " - " + msgCliente + " - Comando nao encontrado\n");
        }
        out.flush();

        return CONTINUA;
    }

}
