package br.com.correntista.util;

import br.com.correntista.entidade.Moeda;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

/**
 * Classe busca e trata o json de cotação de moedas
 *
 * @author AlexLRF
 */
public class Cotacao {

    public static List<Moeda> buscaCotacao() {
        Moeda moedaVO;
        List<Moeda> moedas = new ArrayList<>();
        String json = buscaJsonApi();
        JSONObject responseJson = new JSONObject(json);

        JSONObject dolar = responseJson.getJSONObject("USDBRL");
        JSONObject euro = responseJson.getJSONObject("EURBRL");

        JSONArray ar = new JSONArray();
        ar.put(dolar);
        ar.put(euro);

        JSONObject jsonTemporario;
        for (int i = 0; i < ar.length(); i++) {
            moedaVO = new Moeda();
            jsonTemporario = ar.getJSONObject(i);
            moedaVO.setNomeConversao(jsonTemporario.getString("name"));
            moedaVO.setNome(jsonTemporario.getString("code"));
            moedaVO.setValorMinimo(Formatador.formataValorMonetario(jsonTemporario.getString("low")));
            moedaVO.setValorMaximo(Formatador.formataValorMonetario(jsonTemporario.getString("high")));
            moedaVO.setValorFinal(Formatador.formataValorMonetario(jsonTemporario.getString("ask")));

            moedas.add(moedaVO);
        }
        return moedas;
    }

    public static String buscaJsonApi() {
        String json = "";

        try {
            URL url;
            url = new URL("https://economia.awesomeapi.com.br/last/USD-BRL,EUR-BRL");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonSb = new StringBuilder();
            System.out.println(br.lines());
            br.lines().forEach(l -> jsonSb.append(l.trim()));
            json = jsonSb.toString();

        } catch (IOException e) {
            System.out.println("Erro ao buscar cotação -- Causa: " + e.getMessage());
        }
        return json;
    }
}
