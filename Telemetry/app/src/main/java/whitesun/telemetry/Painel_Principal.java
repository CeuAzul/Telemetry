package whitesun.telemetry;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Painel_Principal extends Activity  implements View.OnClickListener{

    private Button btnGPS;
    private Button btnGeral;
    private Button btnPitot;
    private Button btnAGM;
    private Button btnBarometro;
    private Button btnTacometro;
    private Button btnUltrassom;
    private Button btnTelecomandos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_controle);
        findViews();
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-08-27 18:49:56 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        btnGPS = (Button) findViewById(R.id.btnGPS);
        btnPitot = (Button) findViewById(R.id.btnPitot);
        btnAGM = (Button) findViewById(R.id.btnAGM);
        btnBarometro = (Button) findViewById(R.id.btnBarometro);
        btnTacometro = (Button) findViewById(R.id.btnTacometro);
        btnUltrassom = (Button) findViewById(R.id.btnUltrassom);
        btnTelecomandos = (Button) findViewById(R.id.btnTelecomandos);
        btnGeral = (Button) findViewById(R.id.btnGeral);


        btnGeral.setOnClickListener(this);
        btnGPS.setOnClickListener(this);
        btnPitot.setOnClickListener(this);
        btnAGM.setOnClickListener(this);
        btnBarometro.setOnClickListener(this);
        btnTacometro.setOnClickListener(this);
        btnUltrassom.setOnClickListener(this);
        btnTelecomandos.setOnClickListener(this);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-08-27 18:49:56 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btnGPS) {
            // Handle clicks for btnGPS
        } else if (v == btnGeral) {
            Intent myIntent = new Intent(Painel_Principal.this, Painel_Graficos_geral.class);
            Painel_Principal.this.startActivity(myIntent);
        } else if (v == btnPitot) {
            // Handle clicks for btnPitot
        } else if (v == btnAGM) {
            // Handle clicks for btnAGM
        } else if (v == btnBarometro) {
            // Handle clicks for btnBarometro
        } else if (v == btnTacometro) {
            // Handle clicks for btnTacometro
        } else if (v == btnUltrassom) {
            // Handle clicks for btnUltrassom
        } else if (v == btnTelecomandos) {
            Intent myIntent = new Intent(Painel_Principal.this, Painel_Telecomandos.class);
            Painel_Principal.this.startActivity(myIntent);
        }
    }
}