/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_19;

import br.ifsul.sd.romulo.aula_12.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author romulo
 */
public class ExecutaFilaChat extends ExecutaFila {

    private List<RecursoESB> filaCliente;
    private List<RecursoESB> filaGerente;
    private List<ChatCanal> filaCanal;

    public ExecutaFilaChat(String name, List<RecursoESB> filaCliente, List<RecursoESB> filaGerente) {
        super(name);
        this.filaCliente = filaCliente;
        this.filaGerente = filaGerente;
        this.filaCanal = new ArrayList<>();
    }

    @Override
    public synchronized void run() {
        System.out.println("Execução Fila para criação de canais - Thread " + this.getName() + " iniciada");

        while (true) {

            if (!filaCliente.isEmpty() || !filaGerente.isEmpty()) {
                System.out.print("|FC: " + filaCliente.size() + " - FG: " + filaGerente.size() + "|\n");
            }

            if (!filaCliente.isEmpty() && !filaGerente.isEmpty()) {
                RecursoESB recursoCliente = filaCliente.get(0);
                RecursoESB recursoGerente = filaGerente.get(0);
                
                ChatCanal canal = new ChatCanal(recursoCliente, recursoGerente);
                filaCanal.add(canal);
                canal.start();
                filaCliente.remove(0);
                filaGerente.remove(0);
            }
            Util.aguardar(3000);
        }
    }
}
