package whitesun.telemetry;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

public class EstacaoTerrestre extends MainActivity {
    private TextView svalorvelocidade;
    private TextView tfAltitudePressao;
    private static TextView tfLog;

    private Button btnEnviaDados, btnPainel;
    private Button btnVerGraficos;
    private EditText etRawdata;
    private EditText etDadoPraEnviar;

    private RadioButton btnGPSpronto;
    private ToggleButton btnIniciarRecepcao;
    private ToggleButton btnGravaDado;
    UsbManager usbManager;
    UsbDevice device;
    static UsbSerialDevice serialPort;
    UsbDeviceConnection connection;
    public final String ACTION_USB_PERMISSION = "com.hariharan.arduinousb.USB_PERMISSION";
    String ultimoDadoRecebido = "";
    Protocolo protocolo = new Protocolo();

    private EventBus bus = EventBus.getDefault();

    int idApelidoSelecionado = -1;

    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() { //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            try {
                ultimoDadoRecebido = ultimoDadoRecebido + new String(arg0, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };


    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //Broadcast Receiver to automatically start and stop the Serial connection.
        @Override
        public void onReceive(Context context, Intent intent) {

            Context toastContext = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) {
                    connection = usbManager.openDevice(device);
                    serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
                    if (serialPort != null) {
                        if (serialPort.open()) { //Set Serial Connection Parameters.
                            setConectado(true);
                            serialPort.setBaudRate(57600);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialPort.read(mCallback);
                            Toast toast = Toast.makeText(toastContext, "Port open!", duration);
                            toast.show();

                        } else {
                            Toast toast = Toast.makeText(toastContext, "Port closed!", duration);
                            toast.show();
                        }
                    } else {
                        Toast toast = Toast.makeText(toastContext, "Port is null!", duration);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(toastContext, "Permission not granted!", duration);
                    toast.show();
                }
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                inicializaUSB();
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                paraConexao();
            }
        }

        ;
    };

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-03-19 03:19:51 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */

    private void findViews() {
        svalorvelocidade = (TextView)findViewById( R.id.svalorvelocidade );
        tfAltitudePressao = (TextView)findViewById( R.id.tfAltitudePressao );
        tfLog = (TextView)findViewById( R.id.tvLog );

        btnVerGraficos = (Button)findViewById( R.id.btnVerGraficos );
        btnPainel = (Button)findViewById( R.id.btnPainelDeControle );

        btnEnviaDados = (Button)findViewById( R.id.btnEnviar );
        etRawdata = (EditText)findViewById( R.id.et_rawdata );
        etDadoPraEnviar = (EditText)findViewById( R.id.etTransmitir);
        btnGPSpronto = (RadioButton)findViewById( R.id.btnGPSpronto );
        btnIniciarRecepcao = (ToggleButton)findViewById( R.id.btnIniciarRecepcao );
        btnGravaDado = (ToggleButton)findViewById( R.id.btnGravaDado );
        btnIniciarRecepcao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnIniciarRecepcao.setTextOn("Iniciando conexão...");
                    inicializaUSB();
                    btnIniciarRecepcao.setTextOn("Parar conexão");
                } else {
                    btnIniciarRecepcao.setTextOn("Parando...");
                    paraConexao();
                    btnIniciarRecepcao.setTextOn("Iniciar conexão");
                }
            }
        });

        btnEnviaDados.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mandaDado(etDadoPraEnviar.getText().toString());
                etDadoPraEnviar.setText("");
            }
        });


        btnVerGraficos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String apelidoDados[] = new String[protocolo.getDados().size()];
                for (int i = 0; i < protocolo.getDados().size(); i++) {
                    apelidoDados[i] = protocolo.getDados().get(i).getApelido();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(EstacaoTerrestre.this);
                builder.setTitle("Selecione o identificador");
                builder.setItems(apelidoDados, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Coisou "+ which);
                        idApelidoSelecionado = which;
                        Intent myIntent = new Intent(EstacaoTerrestre.this, Grafico.class);
                        EstacaoTerrestre.this.startActivity(myIntent);
                    }
                });
                builder.show();
            }
        });

        btnPainel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(EstacaoTerrestre.this, Painel_Principal.class);
                EstacaoTerrestre.this.startActivity(myIntent);
            }
        });

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groundstation);
        findViews();

        usbManager = (UsbManager) getSystemService(this.USB_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(broadcastReceiver, filter);


        final Handler handler = new Handler();
        Timer timerProcessaDado = new Timer();
        TimerTask taskProcessaDado = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if(!ultimoDadoRecebido.equals("")) {
                            ultimoDadoRecebido = protocolo.processaInput(ultimoDadoRecebido);
                        }
                    }
                });
            }
        };

        timerProcessaDado.schedule(taskProcessaDado, 0, 100);

        final Handler handler2 = new Handler();
        Timer timerMostraDado = new Timer();
        TimerTask taskMostraDado = new TimerTask() {
            @Override
            public void run() {
                handler2.post(new Runnable() {
                    public void run() {
                        etRawdata.setText("");
                        if (protocolo.getDados().size() != 0) {
                            btnVerGraficos.setEnabled(true);
                        } else {
                            btnVerGraficos.setEnabled(false);
                        }

                        for (int i = 0; i < protocolo.getDados().size(); i++) {
                            boolean taFocando = false;
                            if (i == idApelidoSelecionado) {
                                taFocando = true;
                            }

                            float ultimoTempoRecebido =  protocolo.getDados().get(i).getTempoRecebimento().get(protocolo.getDados().get(i).getTempoRecebimento().size()-1);
                            System.out.println("Tempo enviado: "+ultimoTempoRecebido);
                            EventoTrocaDados evento = new EventoTrocaDados(protocolo.getDados().get(i).apelido, protocolo.getDados().get(i).getValores().get(protocolo.getDados().get(i).getValores().size()-1), ultimoTempoRecebido, taFocando);
                            bus.post(evento);

                            etRawdata.append("-"+protocolo.getDados().get(i).getValores().size()+"-");
                            if (protocolo.getDados().get(i).apelido.equals("nda")) {
                                svalorvelocidade.setText(": "+protocolo.getDados().get(i).getValores().get(protocolo.getDados().get(i).getValores().size()-1));
                            }
                            if (protocolo.getDados().get(i).apelido.equals("agr")) {
                                tfAltitudePressao.setText(": "+protocolo.getDados().get(i).getValores().get(protocolo.getDados().get(i).getValores().size()-1));
                            }
                            if (protocolo.getDados().get(i).apelido.equals("igc")) {
                                if (protocolo.getDados().get(i).getValores().get(protocolo.getDados().get(i).getValores().size()-1) == 1) {
                                    btnGPSpronto.setChecked(true);
                                } else {
                                    btnGPSpronto.setChecked(false);
                                }
                            }
                        }
                    }
                });
            }
        };

        timerMostraDado.schedule(taskMostraDado, 0, 100);
        setConectado(false);

    }


    public void inicializaUSB() {
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                if (deviceVID == 0x0403)//3DR vendor ID
                {
                    PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    usbManager.requestPermission(device, pi);
                    keep = false;
                } else {
                    connection = null;
                    device = null;
                }

                if (!keep) {
                    break;
                }
            }
        }
    }

    public static void mandaDado(String dado) {
        serialPort.write(dado.getBytes());
    }

    public void paraConexao() {
        setConectado(false);
        serialPort.close();
    }

    public void setConectado(boolean bool) {
        btnGravaDado.setEnabled(bool);
    }

    public void onClickClear(View view) {
        etRawdata.setText(" ");
    }

    private void dadoAppend(EditText tv, CharSequence text) {
        final EditText ftv = tv;
        final CharSequence ftext = text;
        if (ftv.getText().length() == 9999) {
            ftv.setText(ftext);
        } else {
            ftv.append(ftext);
        }
    }

    private static void logShow(TextView tv, CharSequence text) {
        final TextView ftv = tv;
        final CharSequence ftext = text;
        ftv.setText(ftext);
    }

}


