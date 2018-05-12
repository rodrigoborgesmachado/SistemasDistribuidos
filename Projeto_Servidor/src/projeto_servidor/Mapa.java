/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projeto_servidor;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author Robertta Loise, Rodrigo Machado e Rodrigo Borborema
 */
public class Mapa {
    
    private Map<BigInteger, String> mapa = new HashMap<>();
    
    public boolean Create(BigInteger chave, String valor)
    {
        if((!mapa.containsKey(chave)) && (!valor.equals("")) && (!valor.equals(" ")) && valor != null)
        {
            getMapa().put(chave, valor);
            return true;
        }
        return false;
    }
    
    public boolean Update(BigInteger chave, String valor)
    {
        if(getMapa().containsKey(chave) && (!valor.equals("")) && (!valor.equals(" ")))
        {
            getMapa().replace(chave, valor);
            return true;
        }
        return false;
    }
    
    public boolean Delete(BigInteger chave)
    {
        if(getMapa().containsKey(chave) && (!chave.equals("")) && (!chave.equals(" ")))
        {
            getMapa().remove(chave);
            return true;
        }
        return false;
    }
    
    public String Search(BigInteger chave)
    {
        if(getMapa().containsKey(chave) && (!chave.equals("")) && (!chave.equals(" ")))
        {
            return getMapa().get(chave);
        }
        return "";
    }
    
    public ArrayList<String> Read()
    {
        ArrayList<String> valores = new ArrayList<String>();
        for(BigInteger bi : getMapa().keySet()) valores.add(getMapa().get(bi));
        return valores;
    }
    
    public static void GravarArquivo(BigInteger chave, String valor)
    {
        try
        { 
            FileOutputStream fileout = new FileOutputStream("./properties/base.properties", true);
            Properties prop = ArquivoLog.getProp("log.properties");
                   
            prop.put("key", RetiraLixo(chave.toString()));
                    
            prop.put("value", RetiraLixo(valor.toString()));
                    
                    
            prop.store(fileout, "DataBase");
            fileout.flush();
                    
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    ///Método para retirar o lixo da string
    public static String RetiraLixo(String x)
    {
        return x.replaceAll("\u0000", "").replaceAll("\\u0000", "").replaceAll("\\]","");
    }

    public Map<BigInteger, String> getMapa() {
        return mapa;
    }

    public void setMapa(Map<BigInteger, String> mapa) {
        this.mapa = mapa;
    }
    
}
