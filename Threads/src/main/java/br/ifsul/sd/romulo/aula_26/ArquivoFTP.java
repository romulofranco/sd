/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsul.sd.romulo.aula_26;

/**
 *
 * @author admin
 */
public class ArquivoFTP {    
    
    private String pathCompleto;
    private String nomeRemoto;

    public ArquivoFTP(String pathCompleto, String nomeRemoto) {
        this.pathCompleto = pathCompleto;
        this.nomeRemoto = nomeRemoto;
    }

    public String getPathCompleto() {
        return pathCompleto;
    }

    public void setPathCompleto(String pathCompleto) {
        this.pathCompleto = pathCompleto;
    }

    public String getNomeRemoto() {
        return nomeRemoto;
    }

    public void setNomeRemoto(String nomeRemoto) {
        this.nomeRemoto = nomeRemoto;
    }

    @Override
    public String toString() {
        return "ArquivoFTP{" + "pathCompleto=" + pathCompleto + ", nomeRemoto=" + nomeRemoto + '}';
    }
    
    
    
}
