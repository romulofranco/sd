/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_12;

import br.ifsul.sd.romulo.aula_19.ConexaoCliente;
import br.ifsul.sd.romulo.aula_19.ExecutaFilaChat;
import br.ifsul.sd.romulo.aula_19.RecursoESB;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author romulo
 */
public class MonitoraFilaGeral extends Thread {

    private List<RecursoESB> filaGeral;
    private List<RecursoESB> filaChatCliente;
    private List<RecursoESB> filaChatGerente;
    private List<RecursoESB> filaProdutor;

    private boolean somenteImprime = true;

    public MonitoraFilaGeral(List<RecursoESB> filaGeral, boolean somenteImprime) {
        this.filaGeral = filaGeral;

        filaChatCliente = new ArrayList<>();
        filaChatGerente = new ArrayList<>();
        filaProdutor = new ArrayList<>();

        this.somenteImprime = somenteImprime;
    }

    public void iniciarExecutaFila() {
        ExecutaFila executaFila1 = new ExecutaFila("Fila Chat Cliente", filaChatCliente, RecursoESB.TIPO_CLIENTE);
        ExecutaFila executaFila2 = new ExecutaFila("Fila Chat Gerente", filaChatGerente, RecursoESB.TIPO_GERENTE);
        ExecutaFila executaFila3 = new ExecutaFila("Fila Importar FTP", filaProdutor, RecursoESB.TIPO_PRODUTOR);
        ExecutaFilaChat executaFilaChat = new ExecutaFilaChat("Fila Chat", filaChatCliente, filaChatGerente);

        executaFila1.start();
        executaFila2.start();
        executaFila3.start();
        executaFilaChat.start();
    }

    @Override
    public void run() {
        while (true) {
            if (somenteImprime) {
                imprimirFila();
                Util.aguardar(5000);
            } else {
                definirDestinoFila();
                Util.aguardar(1000);
            }

        }
    }

    private synchronized void inserirFila(RecursoESB recurso) {

        if (recurso.getMessage().startsWith(ConexaoCliente.PROTOCOL_CHATCLIENTE)) {
            filaChatCliente.add(recurso);
        } else if (recurso.getMessage().startsWith(ConexaoCliente.PROTOCOL_CHATGERENTE)) {
            filaChatGerente.add(recurso);
        } else if (recurso.getMessage().startsWith(ConexaoCliente.PROTOCOL_PRODUTOR)) {
            filaProdutor.add(recurso);
        }
    }

    private synchronized void definirDestinoFila() {
        if (!filaGeral.isEmpty()) {
            System.out.println(filaGeral.get(0));

            RecursoESB recurso = (RecursoESB) filaGeral.get(0);
            this.inserirFila(recurso);

            filaGeral.remove(0);

        }
    }

    private synchronized void imprimirFila() {
        if (!filaGeral.isEmpty()) {
            System.out.println("\n*** FILA GERAL ***");

            for (int i = 0; i < filaGeral.size(); i++) {
                System.out.println(filaGeral.get(i));
            }
        }
    }

}
