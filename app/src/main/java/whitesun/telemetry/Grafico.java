package whitesun.telemetry;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Random;

import de.greenrobot.event.EventBus;


public class Grafico extends Activity {

    private GraphicalView mChart;
    private TextView tvNomeDado;
    private XYSeries visitsSeries ;
    private XYMultipleSeriesDataset dataset;
    ChartTask chart = new ChartTask();

    private XYSeriesRenderer visitsRenderer;
    private XYMultipleSeriesRenderer multiRenderer;
    private EventBus bus = EventBus.getDefault();

    float maxX = -1, maxY = 0, minX = -1, minY = 0;
    float valorAtual, tempoAtual;
    String nome;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafico_conteiner);
        tvNomeDado = (TextView)findViewById( R.id.tv_title);

        // Setting up chart
        setupChart();

        // Start plotting chart
        chart.execute();
        bus.register(this);


    }

    @Override
    protected void onDestroy() {
        // Unregister
        bus.unregister(this);
        if (chart != null) {
            chart.cancel(true);
        }
        super.onDestroy();
    }


    public void onEvent(EventoTrocaDados evento){
        if(evento.isFoco()) {
            nome = evento.getApelido();
            valorAtual = evento.getValor();
            tempoAtual = evento.getTempoRecebimento();
        }
    }
    private void setupChart(){

        // Creating an  XYSeries for Visits
        visitsSeries = new XYSeries("Unique Visitors");
        // Creating a dataset to hold each series
        dataset = new XYMultipleSeriesDataset();
        // Adding Visits Series to the dataset
        dataset.addSeries(visitsSeries);

        // Creating XYSeriesRenderer to customize visitsSeries
        visitsRenderer = new XYSeriesRenderer();
        visitsRenderer.setColor(Color.BLACK);
        visitsRenderer.setPointStyle(PointStyle.CIRCLE);
        visitsRenderer.setFillPoints(true);
        visitsRenderer.setLineWidth(2);
        visitsRenderer.setDisplayChartValues(false);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
        multiRenderer.setChartTitle("");
        multiRenderer.setXTitle("");
        multiRenderer.setYTitle("");
        multiRenderer.setZoomButtonsVisible(false);
        multiRenderer.setLabelsTextSize(25);
        multiRenderer.setMargins(new int[]{20, 100, 5, 20});

        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.TRANSPARENT);
        multiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));

        multiRenderer.setYLabelsColor(0, Color.BLACK);
        multiRenderer.setXLabelsColor(Color.BLACK);
        multiRenderer.setShowGrid(true);
        multiRenderer.setGridColor(Color.GRAY);
        multiRenderer.setXLabels(10);
        multiRenderer.setYLabels(10);

        // Adding visitsRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(visitsRenderer);

        // Getting a reference to LinearLayout of the MainActivity Layout
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);

        mChart = ChartFactory.getLineChartView(getBaseContext(), dataset, multiRenderer);

        // Adding the Line Chart to the LinearLayout
        chartContainer.addView(mChart);
    }

    public float getTempoAtual(){
        return tempoAtual;
    }

    private class ChartTask extends AsyncTask<Void, String, Void> {
        public float tempoAnterior = 0;

        // Generates dummy data in a non-ui thread
        @Override
        protected Void doInBackground(Void... params) {
            try {
                while(true) {
                    String ta[] = new String[2];
                    publishProgress(ta);
                    Thread.sleep(100);
                }
            } catch(Exception e){ }
            return null;
        }

        // Plotting generated data in the graph
        @Override
        protected void onProgressUpdate(String... values) {
            System.out.println("No onProgressUpdate(): Tempo atual: "+tempoAtual+" Anterior: "+tempoAnterior);

            if (tempoAtual != tempoAnterior) {
                if (minX == -1) {
                    minX = tempoAtual;
                }
                if (maxX == -1) {
                    maxX = tempoAtual;
                }
                if (tempoAtual < minX) {
                    minX = tempoAtual;
                    multiRenderer.setXAxisMin(minX);
                }
                if (tempoAtual > maxX) {
                    maxX = tempoAtual;
                    multiRenderer.setXAxisMax(maxX);
                }
                if (valorAtual > maxY) {
                    maxY = valorAtual;
                    multiRenderer.setYAxisMax(maxY );
                }
                if (valorAtual < minY) {
                    minY = valorAtual;
                    multiRenderer.setYAxisMin(minY);
                }
                if (visitsSeries.getItemCount()>1200) {
                    visitsSeries.remove(0);
                }
                if (!tvNomeDado.getText().toString().equals(nome + " = "+ valorAtual)) {
                    tvNomeDado.setText(nome + " = " + valorAtual);
                    visitsSeries.setTitle(nome);

                }
                visitsSeries.add(tempoAtual, valorAtual);
                multiRenderer.getSeriesRendererAt(0).setColor(Color.parseColor("#5159C2"));
                mChart.repaint();
                tempoAnterior = tempoAtual;

            }
        }
    }
}