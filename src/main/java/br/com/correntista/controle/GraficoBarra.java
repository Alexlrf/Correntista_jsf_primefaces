package br.com.correntista.controle;

import br.com.correntista.entidade.UnidadeGrafico;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

/**
 *
 * @author AlexLRF
 */
@ManagedBean
@RequestScoped
public class GraficoBarra implements Serializable {

    private LineChartModel lineModel;

    @PostConstruct
    public void init() {
        createLineModel();
    }

    public void createLineModel() {
        List<UnidadeGrafico> lista = buscaGrafico();
        lineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        values.add(lista.get(0).getValor());
        values.add(lista.get(1).getValor());
        values.add(lista.get(2).getValor());
        values.add(lista.get(3).getValor());
        values.add(lista.get(4).getValor());
        values.add(lista.get(5).getValor());
        values.add(lista.get(6).getValor());
        dataSet.setData(values);
        dataSet.setFill(true);
        dataSet.setLabel("Dólar");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);

        List<String> labels = new ArrayList<>();
        labels.add("[*]");
        labels.add("[*]");
        labels.add("[*]");
        labels.add("[*]");
        labels.add("[*]");
        labels.add("[*]");
        labels.add("[*]");
        data.setLabels(labels);

        //Options
        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText(" ");
        options.setTitle(title);

        lineModel.setOptions(options);
        lineModel.setData(data);
    }

    /**
     * Trata o json para retornar a lista de unidades
     *
     * @return Lista de unidades
     */
    public List<UnidadeGrafico> buscaGrafico() {
        List<UnidadeGrafico> lista = new ArrayList<>();
        String jsonPronto = buscaJsonApi();
        JSONArray jsonArray = new JSONArray(jsonPronto);
        UnidadeGrafico unidade;

        for (int i = 0; i < jsonArray.length(); i++) {
            unidade = new UnidadeGrafico();
            JSONObject jsonObject;
            jsonObject = jsonArray.getJSONObject(i);
            unidade.setValor(Double.parseDouble(jsonObject.getString("ask")));
            lista.add(unidade);
        }
        return lista;
    }

    /**
     * Chama a api e disponibiliza o json
     *
     * @return String json com os dados
     */
    public String buscaJsonApi() {
        String json = "";

        try {
            URL url;
            url = new URL("https://economia.awesomeapi.com.br/USD-BRL/7?start_date=202110end_date=20211027");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonSb = new StringBuilder();
            System.out.println(br.lines());
            br.lines().forEach(linha -> jsonSb.append(linha.trim()));
            json = jsonSb.toString();

        } catch (IOException e) {
            System.out.println("Erro ao buscar cotação -- Causa: " + e.getMessage());
        }
        return json;
    }

    // getters and setters //
    public LineChartModel getLineModel() {
        if (lineModel == null) {
            lineModel = new LineChartModel();
        }
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

}
