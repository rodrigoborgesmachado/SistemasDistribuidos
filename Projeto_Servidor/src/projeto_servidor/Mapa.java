/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projeto_servidor;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author root
 */
public class Mapa {
    private Map<BigInteger, String> Mapa_Requisicoes = null;
    
    /// Construtor principal da classe
    public Mapa(){
        this.Mapa_Requisicoes = new HashMap<BigInteger, String>();
    }

    /**
     * @return the Mapa_Requisicoes
     */
    public Map<BigInteger, String> getMapa_Requisicoes() {
        return Mapa_Requisicoes;
    }

    /**
     * @param Mapa_Requisicoes the Mapa_Requisicoes to set
     */
    public void setMapa_Requisicoes(Map<BigInteger, String> Mapa_Requisicoes) {
        this.Mapa_Requisicoes = Mapa_Requisicoes;
    }
    
    public boolean InsereMap(BigInteger num, String str){
        if(this.Mapa_Requisicoes.putIfAbsent(num, str) == null) 
            return true;
        return false;
    }
    
    public boolean Existe(BigInteger num, String str){
        return this.Mapa_Requisicoes.containsValue(num);
    }
    
}
