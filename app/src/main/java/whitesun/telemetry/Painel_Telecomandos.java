package whitesun.telemetry;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import de.greenrobot.event.EventBus;


public class Painel_Telecomandos extends Activity  implements View.OnClickListener{

    private RatingBar rbGps;
    private TextView tfModoOperacao;
    private TextView tfDescricaoModo;
    private SeekBar sbSegur;
    private ToggleButton tbIniciaGravacao;
    private ToggleButton tbTransmBasica;
    private ToggleButton tbApenasTransmissão;
    private ToggleButton tbPausaTelemetria;
    private Button tbPassaPendrive;
    private Button btnNovoArquivo;
    private Button btnReinicia;
    private Button btnDesligaPlataforma;
    private EventBus bus = EventBus.getDefault();
    boolean ultrapassouBarreiras = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telecomandos);
        findViews();
        habilitaBotoes(false);
        bus.register(this);

    }

    @Override
    protected void onDestroy() {
        // Unregister
        bus.unregister(this);
        super.onDestroy();
    }


    public void onEvent(EventoTrocaDados evento) {
        if (evento.getApelido().equals("nfx")) { // GPS
            rbGps.setRating(evento.getValor());
        }

        if (evento.getApelido().equals("sin")) { // Modo de operação
            tfModoOperacao.setText("" + evento.getValor());
            if (evento.getValor() == 0){
                tfDescricaoModo.setText("Aguarde...");
                aguardando();
                tbPausaTelemetria.setChecked(true);
            } else if (evento.getValor() == 1) {
                tfDescricaoModo.setText("Pronto! Aguardando comando.");
                aguardando();
            } else if (evento.getValor() == 2) {
                tfDescricaoModo.setText("Transmitindo alguns dados...");
                transmBasica();
            } else if (evento.getValor() == 3) {
                tfDescricaoModo.setText("Apenas transmitindo dados...");
                transmApenas();
            } else if (evento.getValor() == 4) {
                tfDescricaoModo.setText("Gravando e transmitindo dados...");
                gravando();
            } else {
                // Do nothing
            }
        }
    }

    private void aguardando() {
        tbPausaTelemetria.setChecked(true);
        tbIniciaGravacao.setChecked(false);
        tbApenasTransmissão.setChecked(false);
        tbTransmBasica.setChecked(false);
    }

    private void gravando() {
        tbPausaTelemetria.setChecked(false);
        tbIniciaGravacao.setChecked(true);
        tbApenasTransmissão.setChecked(false);
        tbTransmBasica.setChecked(false);
    }

    private void transmBasica() {
        tbPausaTelemetria.setChecked(false);
        tbIniciaGravacao.setChecked(false);
        tbApenasTransmissão.setChecked(false);
        tbTransmBasica.setChecked(true);
    }

    private void transmApenas() {
        tbPausaTelemetria.setChecked(false);
        tbIniciaGravacao.setChecked(false);
        tbApenasTransmissão.setChecked(true);
        tbTransmBasica.setChecked(false);
    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-08-27 19:01:34 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        rbGps = (RatingBar)findViewById( R.id.rbGps );
        tfModoOperacao = (TextView)findViewById( R.id.tfModoOperacao );
        tfDescricaoModo = (TextView)findViewById( R.id.tfDescricaoModo );
        sbSegur = (SeekBar)findViewById( R.id.sbSegur );
        tbIniciaGravacao = (ToggleButton)findViewById( R.id.tbIniciaGravacao );
        tbTransmBasica = (ToggleButton)findViewById( R.id.tbTransmBasica );
        tbApenasTransmissão = (ToggleButton)findViewById( R.id.tbApenasTransmissão );
        tbPausaTelemetria = (ToggleButton)findViewById( R.id.tbPausaTelemetria );
        tbPassaPendrive = (Button)findViewById( R.id.tbPassaPendrive );
        btnNovoArquivo = (Button)findViewById( R.id.btnNovoArquivo );
        btnReinicia = (Button)findViewById( R.id.btnReinicia );
        btnDesligaPlataforma = (Button)findViewById( R.id.btnDesligaPlataforma );

        tbIniciaGravacao.setOnClickListener( this );
        tbTransmBasica.setOnClickListener( this );
        tbApenasTransmissão.setOnClickListener( this );
        tbPausaTelemetria.setOnClickListener( this );
        tbPassaPendrive.setOnClickListener( this );
        btnNovoArquivo.setOnClickListener( this );
        btnReinicia.setOnClickListener( this );
        btnDesligaPlataforma.setOnClickListener( this );



        sbSegur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch (SeekBar seekBar) {
                if (seekBar.getProgress() != seekBar.getMax()) {
                    seekBar.setProgress(0);
                    ultrapassouBarreiras = false;
                } else {
                    if (ultrapassouBarreiras) {
                        habilitaBotoes(true);
                        ultrapassouBarreiras = false;
                    } else {
                        seekBar.setProgress(0);
                        ultrapassouBarreiras = false;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                if (seekBar.getProgress() != seekBar.getMax()) {
                    habilitaBotoes(false);
                    if (seekBar.getProgress() == 1) {
                        ultrapassouBarreiras = true;
                    }
                }
            }
        });
    }

    private void habilitaBotoes(boolean hab) {
        tbIniciaGravacao.setEnabled(hab);
        tbTransmBasica.setEnabled(hab);
        tbApenasTransmissão.setEnabled(hab);
        tbPausaTelemetria.setEnabled(hab);
        tbPassaPendrive.setEnabled(hab);
        btnNovoArquivo.setEnabled(hab);
        btnDesligaPlataforma.setEnabled(hab);
        btnReinicia.setEnabled(hab);
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-08-27 19:01:34 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */

    @Override
    public void onClick(View v) {
        habilitaBotoes(false);
        ultrapassouBarreiras = false;
        sbSegur.setProgress(0);
        if ( v == tbIniciaGravacao ) {
            EstacaoTerrestre.mandaDado("@t#c%$0#1$");
        } else if ( v == tbTransmBasica ) {
            EstacaoTerrestre.mandaDado("@%&*v##&(@");
        } else if ( v == tbApenasTransmissão ) {
            EstacaoTerrestre.mandaDado("&*$$%#!@&_");
        } else if ( v == tbPausaTelemetria ) {
            EstacaoTerrestre.mandaDado("*)(#$%@!&*");
        } else if ( v == tbPassaPendrive ) {
            EstacaoTerrestre.mandaDado("Tc*B+@F&5v");
        } else if ( v == btnNovoArquivo ) {
            EstacaoTerrestre.mandaDado("AqT%$BNy*(");
        } else if ( v == btnReinicia ) {
            EstacaoTerrestre.mandaDado("!$@f#_a*(%");
        } else if ( v == btnDesligaPlataforma ) {
            EstacaoTerrestre.mandaDado("d&y?%(+#((");
        }
    }
}