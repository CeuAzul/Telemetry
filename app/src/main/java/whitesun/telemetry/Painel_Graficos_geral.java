package whitesun.telemetry;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import de.greenrobot.event.EventBus;


public class Painel_Graficos_geral extends Activity{
    private TextView tvGpsStatus;
    private TextView tvModoOperacao;
    private TextView tvWow;
    private TextView tvEscritaCartao;
    private TextView tvVcas;
    private LinearLayout chartVcas;
    private TextView tvHps;
    private LinearLayout chartHps;
    private TextView tvRpm1;
    private LinearLayout chartRpm1;
    private TextView tvRpm2;
    private LinearLayout chartRpm2;
    private TextView tvGps;
    private LinearLayout chartGps;
    private EventBus bus = EventBus.getDefault();

    private Graf grafVcas = new Graf();
    private Graf grafHps = new Graf();
    private Graf grafRpm1 = new Graf();
    private Graf grafRpm2 = new Graf();
    private Graf grafGPSxy = new Graf();

    private boolean temX = false;
    private boolean temY = false;
    float ultimoX = 0;
    float ultimoY = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafico_geral);
        findViews();

        grafVcas.setupChart("Velocidade calibrada (m/s)", chartVcas, tvVcas, false);
        grafHps.setupChart("Altitude pressão (ft)", chartHps, tvHps, false);
        grafRpm1.setupChart("RPM 1", chartRpm1, tvRpm1, false);
        grafRpm2.setupChart("RPM 2", chartRpm2, tvRpm2, false);
        grafGPSxy.setupChart("Gps X Y (m)", chartGps, tvGps, true);



        bus.register(this);
        grafVcas.startMyTask();
        grafHps.startMyTask();
        grafRpm1.startMyTask();
        grafRpm2.startMyTask();
        grafGPSxy.startMyTask();

    }

    @Override
    protected void onDestroy() {
        // Unregister
        bus.unregister(this);
        grafVcas.getChart().cancel(true);
        grafHps.getChart().cancel(true);
        grafRpm1.getChart().cancel(true);
        grafRpm2.getChart().cancel(true);
        grafGPSxy.getChart().cancel(true);
        super.onDestroy();
    }


    public void onEvent(EventoTrocaDados evento) {
        if (evento.getApelido().equals("nfx")){ //GPS
            tvGpsStatus.setText("GPS St. :" + evento.getValor() + "d");
        }
        if (evento.getApelido().equals("sin")){
            tvModoOperacao.setText("Modo: " + evento.getValor());
        }
        if (evento.getApelido().equals("wow")){
            tvWow.setText("Wow: " + evento.getValor());
        }
        if (evento.getApelido().equals("agr")){
            tvEscritaCartao.setText("M:" + evento.getValor());
        }
        if (evento.getApelido().equals("vcs")){
            grafVcas.atualiza(evento.getTempoRecebimento(), evento.getValor());
        }
        if (evento.getApelido().equals("hps")){
            grafHps.atualiza(evento.getTempoRecebimento(), evento.getValor());
        }
        if (evento.getApelido().equals("rpm")){
            grafRpm1.atualiza(evento.getTempoRecebimento(), evento.getValor());
        }
        if (evento.getApelido().equals("rpd")){
            grafRpm2.atualiza(evento.getTempoRecebimento(), evento.getValor());
        }
        if (evento.getApelido().equals("gpx")){
            temX = true;
            ultimoX = evento.getValor();
            if (temX && temY) {
                System.out.println("Atualizou gps");
                temX = false;
                temY = false;
                grafGPSxy.atualiza(ultimoX, ultimoY);
            }
        }
        if (evento.getApelido().equals("gpy")) {
            temY = true;
            ultimoY = evento.getValor();
            if (temX && temY) {
                System.out.println("Atualizou gps");
                temX = false;
                temY = false;
                grafGPSxy.atualiza(ultimoX, ultimoY);
            }
        }
    }



    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-09-05 20:46:49 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        tvGpsStatus = (TextView)findViewById( R.id.tvGpsStatus );
        tvModoOperacao = (TextView)findViewById( R.id.tvModoOperacao );
        tvWow = (TextView)findViewById( R.id.tv_wow );
        tvEscritaCartao = (TextView)findViewById( R.id.tv_escrita_cartao );
        tvVcas = (TextView)findViewById( R.id.tv_vcas );
        chartVcas = (LinearLayout)findViewById( R.id.chart_vcas );
        tvHps = (TextView)findViewById( R.id.tv_hps );
        chartHps = (LinearLayout)findViewById( R.id.chart_hps );
        tvRpm1 = (TextView)findViewById( R.id.tv_rpm1 );
        chartRpm1 = (LinearLayout)findViewById( R.id.chart_rpm1 );
        tvRpm2 = (TextView)findViewById( R.id.tv_rpm2 );
        chartRpm2 = (LinearLayout)findViewById( R.id.chart_rpm2 );
        tvGps = (TextView)findViewById( R.id.tvGps );
        chartGps = (LinearLayout)findViewById( R.id.chart_gps );
    }

    public class Graf {
        private GraphicalView mChart;
        private TextView tvNomeDado;
        private XYSeries visitsSeries;
        private XYMultipleSeriesDataset dataset;
        private ChartTask chart = new ChartTask();
        private int indexGPS = 0;
        private XYSeriesRenderer visitsRenderer;
        private XYMultipleSeriesRenderer multiRenderer;
        private boolean isGps = false;
        private boolean temDado = false;

        boolean atualizou;

        float maxX = -1, maxY = 0, minX = -1, minY = 0;
        float valorAtual, tempoAtual;
        private String nome;

        public ChartTask getChart(){
            return chart;
        }


        private void atualiza(float temp, float val){
            atualizou = true;
            tempoAtual =  temp;
            valorAtual = val;
        }

        private void setupChart(String var, LinearLayout chartContainer, TextView tvnomDad, boolean isGp){
            tvNomeDado = tvnomDad;
            nome = var;
            isGps = isGp;

            if(!isGps) {

                // Creating an  XYSeries for Visits
                visitsSeries = new XYSeries("Unique Visitors");
                // Creating a dataset to hold each series
                dataset = new XYMultipleSeriesDataset();
                // Adding Visits Series to the dataset
                dataset.addSeries(visitsSeries);

                // Creating XYSeriesRenderer to customize visitsSeries
                visitsRenderer = new XYSeriesRenderer();
                //  visitsRenderer.setColor(Color.BLACK);
                // visitsRenderer.setPointStyle(PointStyle.CIRCLE);
                //        visitsRenderer.setFillPoints(true);
                visitsRenderer.setLineWidth(2);
                visitsRenderer.setDisplayChartValues(false);


                // Creating a XYMultipleSeriesRenderer to customize the whole chart
                multiRenderer = new XYMultipleSeriesRenderer();
                multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
                multiRenderer.setChartTitle("");
                multiRenderer.setXTitle("");
                multiRenderer.setYTitle("");
                multiRenderer.setZoomButtonsVisible(false);
                multiRenderer.setShowLegend(false);
                multiRenderer.setLabelsTextSize(25);
                multiRenderer.setMargins(new int[]{20, 100, 5, 20});

                multiRenderer.setApplyBackgroundColor(true);
                multiRenderer.setBackgroundColor(Color.TRANSPARENT);
                multiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));

                multiRenderer.setYLabelsColor(0, Color.BLACK);
                multiRenderer.setXLabelsColor(Color.BLACK);
                multiRenderer.setShowGrid(true);
                multiRenderer.setGridColor(Color.GRAY);

                // Adding visitsRenderer to multipleRenderer
                // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
                // should be same
                multiRenderer.addSeriesRenderer(visitsRenderer);

                mChart = ChartFactory.getLineChartView(getBaseContext(), dataset, multiRenderer);

                // Adding the Line Chart to the LinearLayout
                chartContainer.addView(mChart);
            } else {
                // Creating an  XYSeries for Visits
                visitsSeries = new XYSeries("Unique Visitors");
                // Creating a dataset to hold each series
                dataset = new XYMultipleSeriesDataset();
                // Adding Visits Series to the dataset
                dataset.addSeries(visitsSeries);
                // Creating XYSeriesRenderer to customize visitsSeries
                visitsRenderer = new XYSeriesRenderer();
                visitsRenderer.setPointStyle(PointStyle.CIRCLE);
                visitsRenderer.setFillPoints(true);
                visitsRenderer.setDisplayChartValues(false);


                // Creating a XYMultipleSeriesRenderer to customize the whole chart
                multiRenderer = new XYMultipleSeriesRenderer();
                multiRenderer.setYLabelsAlign(Paint.Align.RIGHT);
                multiRenderer.setChartTitle("");
                multiRenderer.setXTitle("");
                multiRenderer.setYTitle("");
                multiRenderer.setZoomButtonsVisible(false);
                multiRenderer.setShowLegend(false);
                multiRenderer.setLabelsTextSize(25);
                multiRenderer.setMargins(new int[]{20, 100, 5, 20});
                multiRenderer.setApplyBackgroundColor(true);
                multiRenderer.setBackgroundColor(Color.TRANSPARENT);
                multiRenderer.setMarginsColor(Color.argb(0x00, 0x01, 0x01, 0x01));
                multiRenderer.setPointSize(7f);

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

                mChart = ChartFactory.getScatterChartView(getBaseContext(), dataset, multiRenderer);

                // Adding the Line Chart to the LinearLayout
                chartContainer.addView(mChart);
            }
        }


        @TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
        void startMyTask() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                chart.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else {
                chart.execute();
            }
        }



        private class ChartTask extends AsyncTask<Void, String, Void> {
            public float tempoAnterior = 0;

            // Generates dummy data in a non-ui thread
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    while (true) {
                        String ta[] = new String[2];
                        publishProgress(ta);
                        atualizou = false;
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    // Do nothing
                }
                return null;
            }

            // Plotting generated data in the graph
            @Override
            protected void onProgressUpdate(String... values) {
                if(!isGps) {
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
                            maxY = valorAtual*1.05f;
                            multiRenderer.setYAxisMax(maxY);
                        }
                        if (valorAtual < minY) {
                            minY = valorAtual*1.05f;
                            multiRenderer.setYAxisMin(minY);
                        }
                        if (!tvNomeDado.getText().toString().equals(nome + " = " + valorAtual)) {
                            tvNomeDado.setText(nome + " = " + valorAtual);
                            //      multiRenderer.setChartTitle(nome);
                            visitsSeries.setTitle(nome);

                        }
                        if(visitsSeries.getItemCount()>300){
                            visitsSeries.remove(0);
                        }
                        visitsSeries.add(tempoAtual, valorAtual);
                        multiRenderer.getSeriesRendererAt(0).setColor(Color.parseColor("#5159C2"));
                        mChart.repaint();
                        tempoAnterior = tempoAtual;
                    }
                } else {
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
                        multiRenderer.setYAxisMax(maxY);
                    }
                    if (valorAtual < minY) {
                        minY = valorAtual;
                        multiRenderer.setYAxisMin(minY);
                    }
                    if(visitsSeries.getItemCount()>1200){
                        visitsSeries.remove(0);
                    }
                    double absoluto = Math.pow((Math.abs(tempoAtual*tempoAtual)+Math.abs(valorAtual*valorAtual)), 0.5);

                    if (!tvNomeDado.getText().toString().equals(nome + " = " + absoluto)) {
                        tvNomeDado.setText(nome + " = " + valorAtual);
                        visitsSeries.setTitle(nome);
                    }
                    visitsSeries.add(tempoAtual, valorAtual);
                    multiRenderer.getSeriesRendererAt(0).setColor(Color.parseColor("#E8660C"));
                    mChart.repaint();
                }
            }
        }
    }
}