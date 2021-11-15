package br.com.correntista.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Classe utilitária com diversos formatadores
 *
 * @author AlexLRF
 */
public class Formatador {

    /**
     * Formata para valor monetário em reais (BR)
     *
     * @param string
     * @return valor monetário formatado
     */
    public static String formataValorMonetario(String string) {
        double valor = Double.parseDouble(string);
        Locale locale = new Locale("pt", "BR");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        String valorFormatado = String.valueOf(formatter.format(valor));
        return valorFormatado;
    }

}
