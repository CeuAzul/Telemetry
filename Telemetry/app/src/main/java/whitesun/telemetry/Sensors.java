package whitesun.telemetry;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.List;


public class Sensors extends MainActivity  implements SensorEventListener {

    private SensorManager sensorManager;
    boolean isGravando;
    boolean gpsLocado = false;
    long startMills=0;
    File gpxfile;

    private Sensor accelerometer;
    private Sensor gyroscope;
    private Sensor gravity;
    private Sensor gyroscopeUncalibrated;
    private Sensor linearAcceleration;
    private Sensor rotationVector;
    private Sensor stepCounter;
    private Sensor gameRotationVector;
    private Sensor geomagneticRotationVector;
    private Sensor magneticField;
    private Sensor magneticFieldUncalibrated;
    private Sensor orientationSensor;
    private Sensor proximitySensor;
    private Sensor temperatureAmbient;
    private Sensor lightSensor;
    private Sensor pressureSensor;
    private Sensor relativeHumidity;
    private Sensor temperatureDevice;

    private boolean hasAcc = false;
    private boolean hasGyro = false;
    private boolean hasGravity = false;
    private boolean hasGyroUncalibrated = false;
    private boolean hasLinearAcceleration =  false;
    private boolean hasRotationVector = false;
    private boolean hasStepCounter = false;
    private boolean hasGameRotationVector = false;
    private boolean hasGeomagneticRotationVector = false;
    private boolean hasMagneticField = false;
    private boolean hasMagneticFieldUncalibrated = false;
    private boolean hasOrientationSensor =  false;
    private boolean hasProximitySensor = false;
    private boolean hasTemperatureAmbient = false;
    private boolean hasLightSensor= false;
    private boolean hasPressureSensor = false;
    private boolean hasRelativeHumidity = false;
    private boolean hasTemperatureDevice = false;
    private boolean hasGPS = false;

    private boolean needsAcc = false;
    private boolean needsGyro = false;
    private boolean needsGravity = false;
    private boolean needsGyroUncalibrated = false;
    private boolean needsLinearAcceleration =  false;
    private boolean needsRotationVector = false;
    private boolean needsStepCounter = false;
    private boolean needsGameRotationVector = false;
    private boolean needsGeomagneticRotationVector = false;
    private boolean needsMagneticField = false;
    private boolean needsMagneticFieldUncalibrated = false;
    private boolean needsOrientationSensor =  false;
    private boolean needsProximitySensor = false;
    private boolean needsTemperatureAmbient = false;
    private boolean needsLightSensor= false;
    private boolean needsPressureSensor = false;
    private boolean needsRelativeHumidity = false;
    private boolean needsTemperatureDevice = false;
    //private boolean needsSoundSensor = false;
    private boolean needsGPS = false;



    private LinearLayout accView;
    private LinearLayout gyroView;
    private LinearLayout gravityView;
    private LinearLayout gyroUncalibratedView;
    private LinearLayout linearAccelerationView;
    private LinearLayout rotationVectorView;
    private LinearLayout stepCounterView;
    private LinearLayout gameRotationVectorView;
    private LinearLayout geomagneticRotationVectorView;
    private LinearLayout magneticFieldView;
    private LinearLayout magneticFieldUncalibratedView;
    private LinearLayout orientationSensorView;
    private LinearLayout proximitySensorView;
    private LinearLayout ambientTemperatureView;
    private LinearLayout lightSensorView;
    private LinearLayout pressureSensorView;
    private LinearLayout relativeHumidityView;
    private LinearLayout temperatureDeviceView;
    private LinearLayout soundSensorView;
    private LinearLayout gpsView;


    private TextView tvAcelerometer;
    private TextView tvGravity;
    private TextView tvGyroscope;
    private TextView tvGyroscopeUncalibrated;
    private TextView tvLinearAcceleration;
    private TextView tvRotationVerctor;
    private TextView tvStepCounter;
    private TextView tvGameRotationVector;
    private TextView tvGeomagneticRotationVector;
    private TextView tvMagneticField;
    private TextView tvMagneticFieldUncalibrated;
    private TextView tvOrientationSensor;
    private TextView tvProximitySensor;
    private TextView tvAmbientTemperature;
    private TextView tvLightSensor;
    private TextView tvPressureSensor;
    private TextView tvRelativeHumidity;
    private TextView tvTemperatureDevice;
    private TextView tvSoundSensor;
    private TextView tvGps;

    private CheckBox cbAcx;
    private CheckBox cbAcy;
    private CheckBox cbAcz;
    private CheckBox cbGyx;
    private CheckBox cbGyy;
    private CheckBox cbGyz;
    private CheckBox cbGyncx;
    private CheckBox cbGyncy;
    private CheckBox cbGyncz;
    private CheckBox cbDriftX;
    private CheckBox cbDriftY;
    private CheckBox cbDriftZ;
    private CheckBox cbGravX;
    private CheckBox cbGravY;
    private CheckBox cbGravZ;
    private CheckBox cbLinaccX;
    private CheckBox cbLinaccY;
    private CheckBox cbLinaccZ;
    private CheckBox cbRotVecX;
    private CheckBox cbRotVecY;
    private CheckBox cbRotVecZ;
    private CheckBox cbSteps;
    private CheckBox cbRotVecGameX;
    private CheckBox cbRotVecGameY;
    private CheckBox cbRotVecGameZ;
    private CheckBox cbGeoRotVecX;
    private CheckBox cbGeoRotVecY;
    private CheckBox cbGeoRotVecZ;
    private CheckBox cbMagFieldX;
    private CheckBox cbMagFieldY;
    private CheckBox cbMagFieldZ;
    private CheckBox cbMagFieldUncaX;
    private CheckBox cbMagFieldUncaY;
    private CheckBox cbMagFieldUncaZ;
    private CheckBox cbIronBiasX;
    private CheckBox cbIronBiasY;
    private CheckBox cbIronBiasZ;
    private CheckBox cbOrientationX;
    private CheckBox cbOrientationY;
    private CheckBox cbOrientationZ;
    private CheckBox cbProximityDist;
    private CheckBox cbAmbiTemp;
    private CheckBox cbLight;
    private CheckBox cbPressure;
    private CheckBox cbRelHum;
    private CheckBox cbDeviceTemp;
    //private CheckBox cbSoundIntensity;
    private CheckBox cbSpeed;
    private CheckBox cbAltitude;
    private CheckBox cbCog;
    private CheckBox cbDistX;
    private CheckBox cbDistY;
    private CheckBox cbDistTotal;
    private CheckBox cbLatitude;
    private CheckBox cbLongitude;
    private CheckBox cbMillissecondsFromBegin;
    private CheckBox cbDay;
    private CheckBox cbMonth;
    private CheckBox cbYear;
    private CheckBox cbHour;
    private CheckBox cbMinute;
    private CheckBox cbSeconds;
    private CheckBox cbMillisseconds;
    private CheckBox cbSelectAll;

    private EditText et_delay;


    String nomeArquivo= "";
    FileWriter writer;

    //NoiseRecorder som = new NoiseRecorder();

    SensorData dado = new SensorData();
    private Button btnStartLog;

    //private volatile boolean soundRecording = true;

    GPSTracker gps = new GPSTracker(Sensors.this);

    @Override
    public void onBackPressed() {
        if(isGravando){
            //soundRecording = false;
            btnStartLog.setTextColor(Color.BLACK);
            btnStartLog.setText(getString(R.string.start_logging));
            isGravando = false;
            setCheckBoxEnabled(true);
            fechaArquivo();
            Toast toast = Toast.makeText(Sensors.this,getString(R.string.loggedsucess), Toast.LENGTH_LONG);
            toast.show();
            //som.finalizaSom();
            Sensors.this.finish();
        }else{
            //som.finalizaSom();
            Sensors.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        inicializaViews();
        verificaListaDeSensores();
        iniciaSensores();
        
    }

    private void inicializaViews() {
        accView = (LinearLayout) findViewById(R.id.viewaccel);
        gyroView = (LinearLayout) findViewById(R.id.viewgyro);
        gyroUncalibratedView = (LinearLayout) findViewById(R.id.viewgyrouncalibrated);
        gravityView = (LinearLayout) findViewById(R.id.viewgravity);
        linearAccelerationView = (LinearLayout) findViewById(R.id.viewlinearaccele);
        stepCounterView = (LinearLayout) findViewById(R.id.viewstepcounter);
        rotationVectorView = (LinearLayout) findViewById(R.id.viewvetorrotacao);
        gameRotationVectorView = (LinearLayout) findViewById(R.id.viewGameRotationVector);
        geomagneticRotationVectorView = (LinearLayout) findViewById(R.id.viewGeomagneticRotationVector);
        magneticFieldView = (LinearLayout) findViewById(R.id.viewMagneticField);
        magneticFieldUncalibratedView = (LinearLayout) findViewById(R.id.viewMagneticFieldUncalibrated);
        orientationSensorView = (LinearLayout) findViewById(R.id.viewOrientation);
        proximitySensorView = (LinearLayout) findViewById(R.id.viewProximity);
        ambientTemperatureView = (LinearLayout) findViewById(R.id.viewAmbientTemperature);
        lightSensorView = (LinearLayout) findViewById(R.id.viewLightSensor);
        pressureSensorView = (LinearLayout) findViewById(R.id.viewPressure);
        relativeHumidityView = (LinearLayout) findViewById(R.id.viewRelativeHumidity);
        temperatureDeviceView = (LinearLayout) findViewById(R.id.viewDeviceTemperature);
        //soundSensorView = (LinearLayout) findViewById(R.id.viewSoundSensor);
        gpsView = (LinearLayout) findViewById(R.id.viewGps);

        cbAcx = (CheckBox) findViewById(R.id.cb_accx );
        cbAcy = (CheckBox) findViewById(R.id.cb_accy );
        cbAcz = (CheckBox) findViewById(R.id.cb_accz );
        cbGyx = (CheckBox) findViewById(R.id.cb_gyrox );
        cbGyy = (CheckBox) findViewById(R.id.cb_gyroy );
        cbGyz = (CheckBox) findViewById(R.id.cb_gyroz );
        cbGyncx = (CheckBox) findViewById(R.id.cb_gyrouncax );
        cbGyncy = (CheckBox) findViewById(R.id.cb_gyrouncay );
        cbGyncz = (CheckBox) findViewById(R.id.cb_gyrouncaz );
        cbDriftX = (CheckBox) findViewById(R.id.cb_driftx );
        cbDriftY = (CheckBox) findViewById(R.id.cb_drifty );
        cbDriftZ = (CheckBox) findViewById(R.id.cb_driftz );
        cbGravX = (CheckBox) findViewById(R.id.cb_gravx );
        cbGravY = (CheckBox) findViewById(R.id.cb_gravy );
        cbGravZ = (CheckBox) findViewById(R.id.cb_gravz );
        cbLinaccX = (CheckBox) findViewById(R.id.cb_linaccx );
        cbLinaccY = (CheckBox) findViewById(R.id.cb_linaccy );
        cbLinaccZ = (CheckBox) findViewById(R.id.cb_linaccz );
        cbRotVecX = (CheckBox) findViewById(R.id.cb_verotx );
        cbRotVecY = (CheckBox) findViewById(R.id.cb_veroty );
        cbRotVecZ = (CheckBox) findViewById(R.id.cb_verotz );
        cbSteps = (CheckBox) findViewById(R.id.cb_numbersteps );
        cbRotVecGameX = (CheckBox) findViewById(R.id.cb_gameRotVecX );
        cbRotVecGameY = (CheckBox) findViewById(R.id.cb_gameRotVecY );
        cbRotVecGameZ = (CheckBox) findViewById(R.id.cb_gameRotVecZ );
        cbGeoRotVecX = (CheckBox) findViewById(R.id.cb_geoRotVecX );
        cbGeoRotVecY = (CheckBox) findViewById(R.id.cb_geoRotVecY );
        cbGeoRotVecZ = (CheckBox) findViewById(R.id.cb_geoRotVecZ );
        cbMagFieldX = (CheckBox) findViewById(R.id.cb_magneticFieldX );
        cbMagFieldY = (CheckBox) findViewById(R.id.cb_magneticFieldY );
        cbMagFieldZ = (CheckBox) findViewById(R.id.cb_magneticFieldZ );
        cbMagFieldUncaX = (CheckBox) findViewById(R.id.cb_magneticFieldUncalibratedX );
        cbMagFieldUncaY = (CheckBox) findViewById(R.id.cb_magneticFieldUncalibratedY );
        cbMagFieldUncaZ = (CheckBox) findViewById(R.id.cb_magneticFieldUncalibratedZ );
        cbIronBiasX = (CheckBox) findViewById(R.id.cb_ironBiasX );
        cbIronBiasY = (CheckBox) findViewById(R.id.cb_ironBiasY );
        cbIronBiasZ = (CheckBox) findViewById(R.id.cb_ironBiasZ );
        cbOrientationX = (CheckBox) findViewById(R.id.cb_orientationX );
        cbOrientationY = (CheckBox) findViewById(R.id.cb_orientationY );
        cbOrientationZ = (CheckBox) findViewById(R.id.cb_orientationZ );
        cbProximityDist = (CheckBox) findViewById(R.id.cb_proximity );
        cbAmbiTemp = (CheckBox) findViewById(R.id.cb_ambientTemperature );
        cbLight = (CheckBox) findViewById(R.id.cb_illuminance );
        cbPressure = (CheckBox) findViewById(R.id.cb_pressure );
        cbRelHum = (CheckBox) findViewById(R.id.cb_relativeHumidity );
        cbDeviceTemp = (CheckBox) findViewById(R.id.cb_deviceTemperature );
        //cbSoundIntensity = (CheckBox) findViewById(R.id.cb_sound );

        cbLatitude = (CheckBox) findViewById(R.id.cb_latitude );
        cbLongitude = (CheckBox) findViewById(R.id.cb_longitude );
        cbSpeed = (CheckBox) findViewById(R.id.cb_speed );
        cbAltitude = (CheckBox) findViewById(R.id.cb_altitude );
        cbCog = (CheckBox) findViewById(R.id.cb_cog );
        cbDistX = (CheckBox) findViewById(R.id.cb_distX );
        cbDistY = (CheckBox) findViewById(R.id.cb_distY );
        cbDistTotal = (CheckBox) findViewById(R.id.cb_distTotal );

        cbMillissecondsFromBegin = (CheckBox) findViewById(R.id.cb_milliFromBegin );
        cbDay = (CheckBox) findViewById(R.id.cb_day );
        cbMonth = (CheckBox) findViewById(R.id.cb_month );
        cbYear = (CheckBox) findViewById(R.id.cb_year );
        cbHour = (CheckBox) findViewById(R.id.cb_hour );
        cbMinute = (CheckBox) findViewById(R.id.cb_minute );
        cbSeconds = (CheckBox) findViewById(R.id.cb_second );
        cbMillisseconds = (CheckBox) findViewById(R.id.cb_millisseconds );

        cbSelectAll = (CheckBox) findViewById(R.id.cb_selectall );

        tvGps = (TextView) findViewById(R.id.tv_gps);
        et_delay = (EditText) findViewById(R.id.et_dalay);
        btnStartLog = (Button) findViewById(R.id.btnStartLog);

        cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

               @Override
               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                   if (hasAcc){
                       cbAcx.setChecked(isChecked);
                       cbAcy.setChecked(isChecked);
                       cbAcz.setChecked(isChecked);
                   }
                   if (hasGyro){
                       cbGyx.setChecked(isChecked);
                       cbGyy.setChecked(isChecked);
                       cbGyz.setChecked(isChecked);
                   }
                   if (hasGravity){
                       cbGravX.setChecked(isChecked);
                       cbGravY.setChecked(isChecked);
                       cbGravZ.setChecked(isChecked);
                   }
                   if (hasGyroUncalibrated){
                       cbGyncx.setChecked(isChecked);
                       cbGyncy.setChecked(isChecked);
                       cbGyncz.setChecked(isChecked);
                       cbDriftX.setChecked(isChecked);
                       cbDriftY.setChecked(isChecked);
                       cbDriftZ.setChecked(isChecked);
                   }
                   if (hasLinearAcceleration ){
                       cbLinaccX.setChecked(isChecked);
                       cbLinaccY.setChecked(isChecked);
                       cbLinaccZ.setChecked(isChecked);
                   }
                   if (hasRotationVector){
                       cbRotVecX.setChecked(isChecked);
                       cbRotVecY.setChecked(isChecked);
                       cbRotVecZ.setChecked(isChecked);
                   }
                   if (hasStepCounter){
                       cbSteps.setChecked(isChecked);
                   }
                   if (hasGameRotationVector){
                       cbRotVecGameX.setChecked(isChecked);
                       cbRotVecGameY.setChecked(isChecked);
                       cbRotVecGameZ.setChecked(isChecked);
                   }
                   if (hasGeomagneticRotationVector){
                       cbGeoRotVecX.setChecked(isChecked);
                       cbGeoRotVecY.setChecked(isChecked);
                       cbGeoRotVecZ.setChecked(isChecked);
                   }
                   if (hasMagneticField){
                       cbMagFieldX.setChecked(isChecked);
                       cbMagFieldY.setChecked(isChecked);
                       cbMagFieldZ.setChecked(isChecked);
                   }
                   if (hasMagneticFieldUncalibrated){
                       cbMagFieldUncaX.setChecked(isChecked);
                       cbMagFieldUncaY.setChecked(isChecked);
                       cbMagFieldUncaZ.setChecked(isChecked);
                       cbIronBiasX.setChecked(isChecked);
                       cbIronBiasY.setChecked(isChecked);
                       cbIronBiasZ.setChecked(isChecked);
                   }
                   if (hasOrientationSensor ){
                       cbOrientationX.setChecked(isChecked);
                       cbOrientationY.setChecked(isChecked);
                       cbOrientationZ.setChecked(isChecked);
                   }
                   if (hasProximitySensor){
                       cbProximityDist.setChecked(isChecked);
                   }
                   if (hasTemperatureAmbient){
                       cbAmbiTemp.setChecked(isChecked);
                   }
                   if (hasLightSensor){
                       cbLight.setChecked(isChecked);
                   }
                   if (hasPressureSensor){
                       cbPressure.setChecked(isChecked);
                   }
                   if (hasRelativeHumidity){
                       cbRelHum.setChecked(isChecked);
                   }
                   if (hasTemperatureDevice){
                       cbDeviceTemp.setChecked(isChecked);
                   }
                   if (hasGPS){
                       cbSpeed.setChecked(isChecked);
                       cbAltitude.setChecked(isChecked);
                       cbCog.setChecked(isChecked);

                       cbDistX.setChecked(isChecked);
                       cbDistY.setChecked(isChecked);
                       cbDistTotal.setChecked(isChecked);

                       cbLatitude.setChecked(isChecked);
                       cbLongitude.setChecked(isChecked);
                   }
                   cbMillissecondsFromBegin.setChecked(isChecked);
                   cbDay.setChecked(isChecked);
                   cbMonth.setChecked(isChecked);
                   cbYear.setChecked(isChecked);
                   cbHour.setChecked(isChecked);
                   cbMinute.setChecked(isChecked);
                   cbSeconds.setChecked(isChecked);
                   cbMillisseconds.setChecked(isChecked);
                   //cbSoundIntensity.setChecked(isChecked);



               }
           }
        );

        btnStartLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(!isGravando){
                    //soundRecording = true;
                    btnStartLog.setText(getString(R.string.stopLog));
                    btnStartLog.setTextColor(Color.RED);
                    setCheckBoxEnabled(false);
                    isGravando = true;
                    nomeArquivo = getString(R.string.app_name)+" -" + System.currentTimeMillis() + ".txt";
                    iniciaArquivo(nomeArquivo);
                    startMills = System.currentTimeMillis();
                    comecaGravacao();
                }else{
                    mostraTelinhaDeSucesso();



                 //   som.finalizaSom();
                }



            }

        });
    }

    private void setCheckBoxEnabled(boolean b) {
        cbAcx.setEnabled(b);
        cbAcy.setEnabled(b);
        cbAcz.setEnabled(b);
        cbGyx.setEnabled(b);
        cbGyy.setEnabled(b);
        cbGyz.setEnabled(b);
        cbGyncx.setEnabled(b);
        cbGyncy.setEnabled(b);
        cbGyncz.setEnabled(b);
        cbDriftX.setEnabled(b);
        cbDriftY.setEnabled(b);
        cbDriftZ.setEnabled(b);
        cbGravX.setEnabled(b);
        cbGravY.setEnabled(b);
        cbGravZ.setEnabled(b);
        cbLinaccX.setEnabled(b);
        cbLinaccY.setEnabled(b);
        cbLinaccZ.setEnabled(b);
        cbRotVecX.setEnabled(b);
        cbRotVecY.setEnabled(b);
        cbRotVecZ.setEnabled(b);
        cbSteps.setEnabled(b);
        cbRotVecGameX.setEnabled(b);
        cbRotVecGameY.setEnabled(b);
        cbRotVecGameZ.setEnabled(b);
        cbGeoRotVecX.setEnabled(b);
        cbGeoRotVecY.setEnabled(b);
        cbGeoRotVecZ.setEnabled(b);
        cbMagFieldX.setEnabled(b);
        cbMagFieldY.setEnabled(b);
        cbMagFieldZ.setEnabled(b);
        cbMagFieldUncaX.setEnabled(b);
        cbMagFieldUncaY.setEnabled(b);
        cbMagFieldUncaZ.setEnabled(b);
        cbIronBiasX.setEnabled(b);
        cbIronBiasY.setEnabled(b);
        cbIronBiasZ.setEnabled(b);
        cbOrientationX.setEnabled(b);
        cbOrientationY.setEnabled(b);
        cbOrientationZ.setEnabled(b);
        cbProximityDist.setEnabled(b);
        cbAmbiTemp.setEnabled(b);
        cbLight.setEnabled(b);
        cbPressure.setEnabled(b);
        cbRelHum.setEnabled(b);
        cbDeviceTemp.setEnabled(b);
        //cbSoundIntensity.setEnabled(b);
        cbSpeed.setEnabled(b);
        cbAltitude.setEnabled(b);
        cbCog.setEnabled(b);
        cbDistX.setEnabled(b);
        cbDistY.setEnabled(b);
        cbDistTotal.setEnabled(b);
        cbLatitude.setEnabled(b);
        cbLongitude.setEnabled(b);
        cbMillissecondsFromBegin.setEnabled(b);
        cbDay.setEnabled(b);
        cbMonth.setEnabled(b);
        cbYear.setEnabled(b);
        cbHour.setEnabled(b);
        cbMinute.setEnabled(b);
        cbSeconds.setEnabled(b);
        cbMillisseconds.setEnabled(b);
        cbSelectAll.setEnabled(b);
        et_delay.setEnabled(b);
    }

    private void comecaGravacao() {

        verificaNecessidades();
        if(needsGPS){
            disparaGPS();
        }
        String linha;
        linha = escreveCabecalho();
        linha += "\r\n" ;
        escreveArquivo(linha);
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (isGravando) {
                        Thread.sleep(Integer.parseInt(et_delay.getText().toString()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long tempo = System.currentTimeMillis();
                                    //Log.d("oi " ,"ta gravando");
                                    escreveArquivo(getLinhaDeDados() +"\r\n" );
                                tempo = tempo - System.currentTimeMillis();
                                //Log.d("TEMPO ESCRITA " ,tempo+"");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    private void disparaGPS() {

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (isGravando) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                gps.getLocation();
                                dado.setLatitude((float)gps.getLatitude());
                                //Log.d("DIABO DO CARALHO " ,(float)gps.getLatitude()+"");
                                dado.setLongitude((float)gps.getLongitude());
                                dado.setSpeed((float)gps.getSpeed());
                                dado.setCog((float)gps.getCoG());
                                dado.setAltitude((float)gps.getAltitude());
                                dado.setDistX((float)gps.getDistX());
                                dado.setDistY((float)gps.getDistY());
                                dado.setDistTotal((float)gps.getDistTotal());

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }

    private void verificaNecessidades() {
        if(hasAcc){
            if(cbAcx.isChecked()){
                needsAcc = true;
            }
            if(cbAcy.isChecked()){
                needsAcc = true;
            }
            if(cbAcz.isChecked()){
                needsAcc = true;
            }
        }
        if(hasGyro){
            if(cbGyx.isChecked()){
                needsGyro = true;
            }
            if(cbGyy.isChecked()){
                needsGyro = true;
            }
            if(cbGyz.isChecked()){
                needsGyro = true;
            }
        }
        if(hasGyroUncalibrated){
            if(cbGyncx.isChecked()){
                needsGyroUncalibrated = true;
            }
            if(cbGyncy.isChecked()){
                needsGyroUncalibrated = true;
            }
            if(cbGyncz.isChecked()){
                needsGyroUncalibrated = true;
            }
            if(cbDriftX.isChecked()){
                needsGyroUncalibrated = true;
            }
            if(cbDriftY.isChecked()){
                needsGyroUncalibrated = true;
            }
            if(cbDriftZ.isChecked()){
                needsGyroUncalibrated = true;
            }
        }
        if(hasGravity){
            if(cbGravX.isChecked()){
                needsGravity = true;
            }
            if(cbGravY.isChecked()){
                needsGravity = true;
            }
            if(cbGravZ.isChecked()){
                needsGravity = true;
            }
        }
        if(hasLinearAcceleration){
            if(cbLinaccX.isChecked()){
                needsLinearAcceleration = true;
            }
            if(cbLinaccY.isChecked()){
                needsLinearAcceleration = true;
            }
            if(cbLinaccZ.isChecked()){
                needsLinearAcceleration = true;
            }
        }
        if(hasRotationVector){
            if(cbRotVecX.isChecked()){
                needsRotationVector = true;
            }
            if(cbRotVecY.isChecked()){
                needsRotationVector = true;
            }
            if(cbRotVecY.isChecked()){
                needsRotationVector = true;
            }
        }
        if(hasStepCounter){
            if(cbSteps.isChecked()){
                needsStepCounter = true;
            }
        }
        if(hasGameRotationVector){
            if(cbRotVecGameX.isChecked()){
                needsGameRotationVector = true;
            }
            if(cbRotVecGameY.isChecked()){
                needsGameRotationVector = true;
            }
            if(cbRotVecGameZ.isChecked()){
                needsGameRotationVector = true;
            }
        }
        if(hasGeomagneticRotationVector){
            if(cbGeoRotVecX.isChecked()){
                needsGeomagneticRotationVector = true;
            }
            if(cbGeoRotVecY.isChecked()){
                needsGeomagneticRotationVector = true;
            }
            if(cbGeoRotVecZ.isChecked()){
                needsGeomagneticRotationVector = true;
            }
        }
        if(hasMagneticField){
            if(cbMagFieldX.isChecked()){
                needsMagneticField = true;
            }
            if(cbMagFieldY.isChecked()){
                needsMagneticField = true;
            }
            if(cbMagFieldZ.isChecked()){
                needsMagneticField = true;
            }
        }
        if(hasMagneticFieldUncalibrated){
            if(cbMagFieldUncaX.isChecked()){
                needsMagneticFieldUncalibrated = true;
            }
            if(cbMagFieldUncaY.isChecked()){
                needsMagneticFieldUncalibrated = true;
            }
            if(cbMagFieldUncaZ.isChecked()){
                needsMagneticFieldUncalibrated = true;
            }
            if(cbIronBiasX.isChecked()){
                needsMagneticFieldUncalibrated = true;
            }
            if(cbIronBiasY.isChecked()){
                needsMagneticFieldUncalibrated = true;
            }
            if(cbIronBiasZ.isChecked()){
                needsMagneticFieldUncalibrated = true;
            }
        }
        if(hasOrientationSensor){
            if(cbOrientationX.isChecked()){
                needsOrientationSensor = true;
            }
            if(cbOrientationY.isChecked()){
                needsOrientationSensor = true;
            }
            if(cbOrientationZ.isChecked()){
                needsOrientationSensor = true;
            }
        }
        if(hasProximitySensor){
            if(cbProximityDist.isChecked()){
                needsProximitySensor = true;
            }
        }
        if(hasTemperatureAmbient){
            if(cbAmbiTemp.isChecked()){
                needsTemperatureAmbient = true;
            }
        }
        if(hasLightSensor){
            if(cbLight.isChecked()){
                needsLightSensor = true;
            }
        }
        if(hasPressureSensor){
            if(cbPressure.isChecked()){
                needsPressureSensor = true;
            }
        }
        if(hasRelativeHumidity){
            if(cbRelHum.isChecked()){
                needsRelativeHumidity = true;
            }
        }
        if(hasTemperatureDevice){
            if(cbDeviceTemp.isChecked()){
                needsTemperatureDevice = true;
            }
        }

        /*if(cbSoundIntensity.isChecked()){
            needsSoundSensor = true;
        }*/

        if(hasGPS){
            if(cbLatitude.isChecked()){
                needsGPS = true;
            }
            if(cbLongitude.isChecked()){
                needsGPS = true;
            }
            if(cbSpeed.isChecked()){
                needsGPS = true;
            }
            if(cbAltitude.isChecked()){
                needsGPS = true;
            }
            if(cbCog.isChecked()){
                needsGPS = true;
            }
            if(cbDistX.isChecked()){
                needsGPS = true;
            }
            if(cbDistY.isChecked()){
                needsGPS = true;
            }
            if(cbDistTotal.isChecked()){
                needsGPS = true;
            }

        }
    }


    private String getLinhaDeDados() {
        String linha = "";
        Calendar c = Calendar.getInstance();
        if(cbMillissecondsFromBegin.isChecked()){
            linha += System.currentTimeMillis() - startMills + ",";
        }
        if(cbDay.isChecked()){
            linha += c.get(Calendar.DAY_OF_MONTH) + ",";
        }
        if(cbMonth.isChecked()){
            linha += (c.get(Calendar.MONTH)+1) + ",";
        }
        if(cbYear.isChecked()){
            linha += c.get(Calendar.YEAR) + ",";
        }
        if(cbHour.isChecked()){
            linha += c.get(Calendar.HOUR_OF_DAY) + ",";
        }
        if(cbMinute.isChecked()){
            linha += c.get(Calendar.MINUTE) + ",";
        }
        if(cbSeconds.isChecked()){
            linha += c.get(Calendar.SECOND) + ",";
        }
        if(cbMillisseconds.isChecked()){
            linha += c.get(Calendar.MILLISECOND) + ",";
        }
        if (needsAcc) {
            if (cbAcx.isChecked()) {
                linha += dado.getAcx() + ",";
            }
            if (cbAcy.isChecked()) {
                linha += dado.getAcy() + ",";
            }
            if (cbAcz.isChecked()) {
                linha += dado.getAcz() + ",";
            }
        }
        if (needsGyro) {
            if (cbGyx.isChecked()) {
                linha += dado.getGyx() + ",";
            }
            if (cbGyy.isChecked()) {
                linha += dado.getGyy() + ",";
            }
            if (cbGyz.isChecked()) {
                linha += dado.getGyz() + ",";
            }
        }
        if (needsGyroUncalibrated) {
            if (cbGyncx.isChecked()) {
                linha += dado.getGyncx() + ",";
            }
            if (cbGyncy.isChecked()) {
                linha += dado.getGyncy() + ",";
            }
            if (cbGyncz.isChecked()) {
                linha += dado.getGyncz() + ",";
            }
            if (cbDriftX.isChecked()) {
                linha += dado.getDriftX() + ",";
            }
            if (cbDriftY.isChecked()) {
                linha += dado.getDriftY() + ",";
            }
            if (cbDriftZ.isChecked()) {
                linha += dado.getDriftZ() + ",";
            }
        }
        if (needsGravity) {
            if (cbGravX.isChecked()) {
                linha += dado.getGravX() + ",";
            }
            if (cbGravY.isChecked()) {
                linha += dado.getGravY() + ",";
            }
            if (cbGravZ.isChecked()) {
                linha += dado.getGravZ() + ",";
            }
        }
        if (needsLinearAcceleration) {
            if (cbLinaccX.isChecked()) {
                linha += dado.getLinaccX() + ",";
            }
            if (cbLinaccY.isChecked()) {
                linha += dado.getLinaccY() + ",";
            }
            if (cbLinaccZ.isChecked()) {
                linha += dado.getLinaccZ() + ",";
            }
        }
        if (needsRotationVector) {
            if (cbRotVecX.isChecked()) {
                linha += dado.getRotVecX() + ",";
            }
            if (cbRotVecY.isChecked()) {
                linha += dado.getRotVecY() + ",";
            }
            if (cbRotVecY.isChecked()) {
                linha += dado.getRotVecZ() + ",";
            }
        }
        if (needsStepCounter) {
            if (cbSteps.isChecked()) {
                linha += dado.getSteps() + ",";
            }
        }
        if (needsGameRotationVector) {
            if (cbRotVecGameX.isChecked()) {
                linha += dado.getRotVecGameX() + ",";
            }
            if (cbRotVecGameY.isChecked()) {
                linha += dado.getRotVecGameY() + ",";
            }
            if (cbRotVecGameZ.isChecked()) {
                linha += dado.getRotVecGameZ() + ",";
            }
        }
        if (needsGeomagneticRotationVector) {
            if (cbGeoRotVecX.isChecked()) {
                linha += dado.getGeoRotVecX() + ",";
            }
            if (cbGeoRotVecY.isChecked()) {
                linha += dado.getGeoRotVecY() + ",";
            }
            if (cbGeoRotVecZ.isChecked()) {
                linha += dado.getGeoRotVecZ() + ",";
            }
        }
        if (needsMagneticField) {
            if (cbMagFieldX.isChecked()) {
                linha += dado.getMagFieldX() + ",";
            }
            if (cbMagFieldY.isChecked()) {
                linha += dado.getMagFieldY() + ",";
            }
            if (cbMagFieldZ.isChecked()) {
                linha += dado.getMagFieldZ() + ",";
            }
        }
        if (needsMagneticFieldUncalibrated) {
            if (cbMagFieldUncaX.isChecked()) {
                linha += dado.getMagFieldUncaX() + ",";
            }
            if (cbMagFieldUncaY.isChecked()) {
                linha += dado.getMagFieldUncaY() + ",";
            }
            if (cbMagFieldUncaZ.isChecked()) {
                linha += dado.getMagFieldUncaZ() + ",";
            }
            if (cbIronBiasX.isChecked()) {
                linha += dado.getIronBiasX() + ",";
            }
            if (cbIronBiasY.isChecked()) {
                linha += dado.getIronBiasY() + ",";
            }
            if (cbIronBiasZ.isChecked()) {
                linha += dado.getIronBiasZ() + ",";
            }
        }
        if (needsOrientationSensor) {
            if (cbOrientationX.isChecked()) {
                linha += dado.getOrientationX() + ",";
            }
            if (cbOrientationY.isChecked()) {
                linha += dado.getOrientationY() + ",";
            }
            if (cbOrientationZ.isChecked()) {
                linha += dado.getOrientationZ() + ",";
            }
        }
        if (needsProximitySensor) {
            if (cbProximityDist.isChecked()) {
                linha += dado.getProximityDist() + ",";
            }
        }
        if(needsTemperatureAmbient){
            if(cbAmbiTemp.isChecked()){
                linha += dado.getAmbiTemp() + ",";
            }
        }
        if(needsLightSensor){
            if(cbLight.isChecked()){
                linha += dado.getLight() + ",";
            }
        }
        if(needsPressureSensor){
            if(cbPressure.isChecked()){
                linha += dado.getPressure() + ",";
            }
        }
        if(needsRelativeHumidity){
            if(cbRelHum.isChecked()){
                linha += dado.getRelHum() + ",";
            }
        }
        if(needsTemperatureDevice){
            if(cbDeviceTemp.isChecked()){
                linha += dado.getDeviceTemp() + ",";
            }
        }
        /*if(needsSoundSensor){
            if(cbSoundIntensity.isChecked()){

                linha += dado.getSoundIntensity() + ",";
            }
        }*/
        if(needsGPS){
            if(cbLatitude.isChecked()){
                linha += dado.getLatitude() + ",";
            }
            if(cbLongitude.isChecked()){
                linha += dado.getLongitude() + ",";
            }
            if(cbSpeed.isChecked()){
                linha += dado.getSpeed() + ",";
            }
            if(cbAltitude.isChecked()){
                linha += dado.getAltitude() + ",";
            }
            if(cbCog.isChecked()){
                linha += dado.getCog() + ",";
            }
            if(cbDistX.isChecked()){
                linha += dado.getDistX() + ",";
            }
            if(cbDistY.isChecked()){
                linha += dado.getDistY() + ",";
            }
            if(cbDistTotal.isChecked()){
                linha += dado.getDistTotal() + ",";
            }

        }
        return linha;
    }

    private String escreveCabecalho() {
        String cabecalho = "";

        if(cbMillissecondsFromBegin.isChecked()){
            cabecalho = cabecalho + getString(R.string.milliFromBegin)+",";
        }
        if(cbDay.isChecked()){
            cabecalho = cabecalho + getString(R.string.currentDay)+",";
        }
        if(cbMonth.isChecked()){
            cabecalho = cabecalho + getString(R.string.currentMonth)+",";
        }
        if(cbYear.isChecked()){
            cabecalho = cabecalho + getString(R.string.currentYear)+",";
        }
        if(cbHour.isChecked()){
            cabecalho = cabecalho + getString(R.string.currentHour)+",";
        }
        if(cbMinute.isChecked()){
            cabecalho = cabecalho + getString(R.string.currentMinute)+",";
        }
        if(cbSeconds.isChecked()){
            cabecalho = cabecalho + getString(R.string.currentSecond)+",";
        }
        if(cbMillisseconds.isChecked()){
            cabecalho = cabecalho + getString(R.string.currentMillissecond)+",";
        }

        if(needsAcc){
            if(cbAcx.isChecked()){
                cabecalho = cabecalho + getString(R.string.acx)+",";
            }
            if(cbAcy.isChecked()){
                cabecalho = cabecalho + getString(R.string.acy)+",";
            }
            if(cbAcz.isChecked()){
                cabecalho = cabecalho + getString(R.string.acz)+",";
            }
        }
        if(needsGyro){
            if(cbGyx.isChecked()){
                cabecalho = cabecalho + getString(R.string.gyx)+",";
            }
            if(cbGyy.isChecked()){
                cabecalho = cabecalho + getString(R.string.gyy)+",";
            }
            if(cbGyz.isChecked()){
                cabecalho = cabecalho + getString(R.string.gyz)+",";
            }
        }
        if(needsGyroUncalibrated){
            if(cbGyncx.isChecked()){
                cabecalho = cabecalho + getString(R.string.gyncx)+",";
            }
            if(cbGyncy.isChecked()){
                cabecalho = cabecalho + getString(R.string.gyncy)+",";
            }
            if(cbGyncz.isChecked()){
                cabecalho = cabecalho + getString(R.string.gyncz) + ",";
            }
            if(cbDriftX.isChecked()){
                cabecalho = cabecalho + getString(R.string.driftx)+",";
            }
            if(cbDriftY.isChecked()){
                cabecalho = cabecalho + getString(R.string.drifty)+",";
            }
            if(cbDriftZ.isChecked()){
                cabecalho = cabecalho + getString(R.string.driftz)+",";
            }
        }
        if(needsGravity){
            if(cbGravX.isChecked()){
                cabecalho = cabecalho + getString(R.string.gravx)+",";
            }
            if(cbGravY.isChecked()){
                cabecalho = cabecalho + getString(R.string.gravy)+",";
            }
            if(cbGravZ.isChecked()){
                cabecalho = cabecalho + getString(R.string.gravz)+",";
            }
        }
        if(needsLinearAcceleration){
            if(cbLinaccX.isChecked()){
                cabecalho = cabecalho + getString(R.string.linaccx)+",";
            }
            if(cbLinaccY.isChecked()){
                cabecalho = cabecalho + getString(R.string.linaccy)+",";
            }
            if(cbLinaccZ.isChecked()){
                cabecalho = cabecalho + getString(R.string.linaccz)+",";
            }
        }
        if(needsRotationVector){
            if(cbRotVecX.isChecked()){
                cabecalho = cabecalho + getString(R.string.rotvecx)+",";
            }
            if(cbRotVecY.isChecked()){
                cabecalho = cabecalho + getString(R.string.rotvecy)+",";
            }
            if(cbRotVecY.isChecked()){
                cabecalho = cabecalho + getString(R.string.rotvecz)+",";
            }
        }
        if(needsStepCounter){
            if(cbSteps.isChecked()){
                cabecalho = cabecalho + getString(R.string.stepscounter)+",";
            }
        }
        if(needsGameRotationVector){
            if(cbRotVecGameX.isChecked()){
                cabecalho = cabecalho + getString(R.string.rotvecgamex)+",";
            }
            if(cbRotVecGameY.isChecked()){
                cabecalho = cabecalho + getString(R.string.rotvecgamey)+",";
            }
            if(cbRotVecGameZ.isChecked()){
                cabecalho = cabecalho + getString(R.string.rotvecgamez)+",";
            }
        }
        if(needsGeomagneticRotationVector){
            if(cbGeoRotVecX.isChecked()){
                cabecalho = cabecalho + getString(R.string.geomagrotvecx)+",";
            }
            if(cbGeoRotVecY.isChecked()){
                cabecalho = cabecalho + getString(R.string.geomagrotvecy)+",";
            }
            if(cbGeoRotVecZ.isChecked()){
                cabecalho = cabecalho + getString(R.string.geomagrotvecz)+",";
            }
        }
        if(needsMagneticField){
            if(cbMagFieldX.isChecked()){
                cabecalho = cabecalho + getString(R.string.magneticfieldx)+",";
            }
            if(cbMagFieldY.isChecked()){
                cabecalho = cabecalho + getString(R.string.magneticfieldy)+",";
            }
            if(cbMagFieldZ.isChecked()){
                cabecalho = cabecalho + getString(R.string.magneticfieldz)+",";
            }
        }
        if(needsMagneticFieldUncalibrated){
            if(cbMagFieldUncaX.isChecked()){
                cabecalho = cabecalho + getString(R.string.magneticfielduncalibratedx)+",";
            }
            if(cbMagFieldUncaY.isChecked()){
                cabecalho = cabecalho + getString(R.string.magneticfielduncalibratedy)+",";
            }
            if(cbMagFieldUncaZ.isChecked()){
                cabecalho = cabecalho + getString(R.string.magneticfielduncalibratedz)+",";
            }
            if(cbIronBiasX.isChecked()){
                cabecalho = cabecalho + getString(R.string.ironbiasestimationx)+",";
            }
            if(cbIronBiasY.isChecked()){
                cabecalho = cabecalho + getString(R.string.ironbiasestimationy)+",";
            }
            if(cbIronBiasZ.isChecked()){
                cabecalho = cabecalho + getString(R.string.ironbiasestimationz)+",";
            }
        }
        if(needsOrientationSensor){
            if(cbOrientationX.isChecked()){
                cabecalho = cabecalho + getString(R.string.orientationx)+",";
            }
            if(cbOrientationY.isChecked()){
                cabecalho = cabecalho + getString(R.string.orientationy)+",";
            }
            if(cbOrientationZ.isChecked()){
                cabecalho = cabecalho + getString(R.string.orientationz)+",";
            }
        }
        if(needsProximitySensor){
            if(cbProximityDist.isChecked()){
                cabecalho = cabecalho + getString(R.string.proximitydistance)+",";
            }
        }
        if(needsTemperatureAmbient){
            if(cbAmbiTemp.isChecked()){
                cabecalho = cabecalho + getString(R.string.ambienttemperature)+",";
            }
        }
        if(needsLightSensor){
            if(cbLight.isChecked()){
                cabecalho = cabecalho + getString(R.string.illuminance)+",";
            }
        }
        if(needsPressureSensor){
            if(cbPressure.isChecked()){
                cabecalho = cabecalho + getString(R.string.pressure)+",";
            }
        }
        if(needsRelativeHumidity){
            if(cbRelHum.isChecked()){
                cabecalho = cabecalho + getString(R.string.relativehumidity)+",";
            }
        }
        if(needsTemperatureDevice){
            if(cbDeviceTemp.isChecked()){
                cabecalho = cabecalho + getString(R.string.devicetemperature)+",";
            }
        }
        /*if(needsSoundSensor){
            if(cbSoundIntensity.isChecked()){
                cabecalho = cabecalho + getString(R.string.intensidadesonora)+",";
            }
        }*/
        if(needsGPS){
            if(cbLatitude.isChecked()){
                cabecalho = cabecalho + getString(R.string.latitude)+",";
            }
            if(cbLongitude.isChecked()){
                cabecalho = cabecalho + getString(R.string.longitude)+",";
            }
            if(cbSpeed.isChecked()){
                cabecalho = cabecalho + getString(R.string.speed)+",";
            }
            if(cbAltitude.isChecked()){
                cabecalho = cabecalho + getString(R.string.altitude)+",";
            }
            if(cbCog.isChecked()){
                cabecalho = cabecalho + getString(R.string.cog)+",";
            }
            if(cbDistX.isChecked()){
                cabecalho = cabecalho + getString(R.string.distX)+",";
            }
            if(cbDistY.isChecked()){
                cabecalho = cabecalho + getString(R.string.distY)+",";
            }
            if(cbDistTotal.isChecked()){
                cabecalho = cabecalho + getString(R.string.distTotal)+",";
            }

        }
        return cabecalho;
    }

    private void iniciaSensores() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        iniciaAcelerometro();
        iniciaGravidade();
        iniciaGiroscopio();
        iniciaGiroscopioNaoCalibrado();
        iniciaAceleracaoLinear();
        iniciaVetorDeRotacao();
        iniciaContadorDePassos();
        iniciaVetorDeRotacaoJogos();
        iniciaVetorDeRotacaoGeomagnetico();
        iniciaCampoMagnetico();
        iniciaCampoMagneticoNaoCalibrado();
        iniciaOrientacao();
        iniciaProximidade();
        iniciaTemperaturaAmbiente();
        iniciaSensorDeLuz();
        iniciaPressao();
        iniciaHumidadeRelativa();
        iniciaTemperaturaDispositivo();
        //iniciaSensorDeSom();
        iniciaGPS();
/*
        */
    }

    private void iniciaGPS() {
        gps.getLocation();
        if(gps.canGetLocation()){
            hasGPS = true;
        }else{
            hasGPS = false;
            gps.showSettingsAlert();

            Thread t = new Thread() {

                @Override
                public void run() {
                    try {
                        while (!isGravando&&!gpsLocado) {
                            Thread.sleep(500);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    gps.getLocation();
                                    if(gps.canGetLocation()){
                                        Log.d("oi " ,"DEU BOA GPS");
                                        hasGPS = true;
                                        gpsView.setVisibility(View.VISIBLE);
                                        if(gps.hasReference()){
                                            tvGps.setText(getString(R.string.gps)+getString(R.string.ready));
                                            tvGps.setTextColor(Color.BLACK);
                                            gpsLocado = true;
                                        }else{
                                            tvGps.setText(getString(R.string.gps)+getString(R.string.notLocked));
                                            tvGps.setTextColor(Color.RED);
                                            gpsLocado = false;
                                        }
                                    }else{
                                        Log.d("oi " ,"DEU RUIM GPS");
                                        hasGPS = false;
                                        gpsView.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            t.start();

        }
    }

   /* private void iniciaSensorDeSom() {
        //som.inicializeSom();
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (soundRecording) {
                        Thread.sleep(30);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               // float barulho = (float)som.getNoiseLevel();
                                //Log.d("SOM " ,barulho+"");

                                //dado.setSoundIntensity(barulho);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }*/


    private void iniciaTemperaturaDispositivo() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE) != null) {
            hasTemperatureDevice = true;
            temperatureDevice = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
            sensorManager.registerListener(this, temperatureDevice, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            temperatureDeviceView.setVisibility(View.GONE);
            hasTemperatureDevice = false;
        }
    }

    private void iniciaHumidadeRelativa() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
            hasRelativeHumidity = true;
            relativeHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            sensorManager.registerListener(this, relativeHumidity, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            relativeHumidityView.setVisibility(View.GONE);
            hasRelativeHumidity = false;
        }
    }

    private void iniciaPressao() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null) {
            hasPressureSensor = true;
            pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            pressureSensorView.setVisibility(View.GONE);
            hasPressureSensor = false;
        }
    }

    private void iniciaSensorDeLuz() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            hasLightSensor = true;
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            lightSensorView.setVisibility(View.GONE);
            hasLightSensor = false;
        }
    }

    private void iniciaTemperaturaAmbiente() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            hasTemperatureAmbient = true;
            temperatureAmbient = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            sensorManager.registerListener(this, temperatureAmbient, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            ambientTemperatureView.setVisibility(View.GONE);
            hasTemperatureAmbient = false;
        }
    }

    private void iniciaProximidade() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            hasProximitySensor = true;
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            proximitySensorView.setVisibility(View.GONE);
            hasProximitySensor = false;
        }
    }

    private void iniciaOrientacao() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {
            hasOrientationSensor = true;
            orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            orientationSensorView.setVisibility(View.GONE);
            hasOrientationSensor = false;
        }
    }

    private void iniciaCampoMagneticoNaoCalibrado() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) != null) {
            hasMagneticFieldUncalibrated = true;
            magneticFieldUncalibrated = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED);
            sensorManager.registerListener(this, magneticFieldUncalibrated, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            magneticFieldUncalibratedView.setVisibility(View.GONE);
            hasMagneticFieldUncalibrated = false;
        }
    }

    private void iniciaCampoMagnetico() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null) {
            hasMagneticField = true;
            magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            magneticFieldView.setVisibility(View.GONE);
            hasMagneticField = false;
        }
    }

    private void iniciaVetorDeRotacaoGeomagnetico() {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null) {
            hasGeomagneticRotationVector = true;
            geomagneticRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
            sensorManager.registerListener(this, geomagneticRotationVector, SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            geomagneticRotationVectorView.setVisibility(View.GONE);
            hasGeomagneticRotationVector = false;
        }
    }


    private void iniciaVetorDeRotacaoJogos() {
        // VETOR DE ROTAÇÃO JOGOS --------------------------------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR) != null) {
            hasGameRotationVector = true;
            gameRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
            sensorManager.registerListener(this, gameRotationVector, SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            gameRotationVectorView.setVisibility(View.GONE);
            hasGameRotationVector = false;
        }
    }

    private void iniciaContadorDePassos() {
        // CONTADOR DE PASSOS --------------------------------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            hasStepCounter = true;
            stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            sensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            stepCounterView.setVisibility(View.GONE);
            hasStepCounter = false;
        }
    }

    private void iniciaVetorDeRotacao() {
        // VETOR DE ROTAÇÃO --------------------------------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
            hasRotationVector = true;
            rotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            sensorManager.registerListener(this, rotationVector, SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            rotationVectorView.setVisibility(View.GONE);
            hasRotationVector = false;
        }
    }

    private void iniciaAceleracaoLinear() {
        // ACELERAÇÃO LINEAR --------------------------------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            hasLinearAcceleration = true;
            linearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            sensorManager.registerListener(this, linearAcceleration, SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            linearAccelerationView.setVisibility(View.GONE);
            hasLinearAcceleration = false;
        }
    }

    private void iniciaGiroscopioNaoCalibrado() {
        // GIROSCÓPIO NÃO CALIBRADO --------------------------------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null) {
            hasGyroUncalibrated = true;
            gyroscopeUncalibrated = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
            sensorManager.registerListener(this, gyroscopeUncalibrated, SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            gyroUncalibratedView.setVisibility(View.GONE);
            hasGyroUncalibrated = false;
        }
    }

    private void iniciaGiroscopio() {
        // GIROSCÓPIO ---------------------------------------------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null) {
            hasGyro = true;
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            gyroView.setVisibility(View.GONE);
            hasGyro = false;
        }
    }

    private void iniciaGravidade() {
        // SENSOR DE GRAVIDADE ---------------------------------------------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
            hasGravity = true;
            gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            gravityView.setVisibility(View.GONE);
            hasGravity = false;
        }
    }

    private void iniciaAcelerometro() {
        // ACELEROMETRO -------------------------------------------------------------------------
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            hasAcc = true;
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            accView.setVisibility(View.GONE);
            hasAcc = false;
        }
    }


    private void verificaListaDeSensores() {

        SensorManager mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = mgr.getSensorList(Sensor.TYPE_ALL);
        /*for (Sensor sensor : sensors) {
            Log.d("Sensors", "" + sensor.getName());
            Log.d("Vendor", "" + sensor.getVendor());
            Log.d("Version", "" + sensor.getVersion());
            Log.d("Max range", "" + sensor.getMaximumRange());
            Log.d("Max resolution", "" + sensor.getResolution());
            Log.d("Min delay", "" + sensor.getMinDelay());
            Log.d("--------------------", "----------------------");
        }*/

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
       if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            dado.setAcx(event.values[0]);
            dado.setAcy(event.values[1]);
            dado.setAcz(event.values[2]);
       }else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
           dado.setGyx(event.values[0]);
           dado.setGyy(event.values[1]);
           dado.setGyz(event.values[2]);
       }else if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED){
           dado.setGyncx(event.values[0]);
           dado.setGyncy(event.values[1]);
           dado.setGyncz(event.values[2]);
           dado.setDriftX(event.values[3]);
           dado.setDriftY(event.values[4]);
           dado.setDriftZ(event.values[5]);
       }else if(event.sensor.getType() == Sensor.TYPE_GRAVITY){
           dado.setGravX(event.values[0]);
           dado.setGravY(event.values[1]);
           dado.setGravZ(event.values[2]);
       }else if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
           dado.setLinaccX(event.values[0]);
           dado.setLinaccY(event.values[1]);
           dado.setLinaccZ(event.values[2]);
       }else if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
           dado.setRotVecX(event.values[0]);
           dado.setRotVecY(event.values[1]);
           dado.setRotVecZ(event.values[2]);
       }else if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
           dado.setSteps(event.values[0]);
       }else if(event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR){
           dado.setRotVecGameX(event.values[0]);
           dado.setRotVecGameY(event.values[1]);
           dado.setRotVecGameZ(event.values[2]);
       }else if(event.sensor.getType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR){
           dado.setGeoRotVecX(event.values[0]);
           dado.setGeoRotVecY(event.values[1]);
           dado.setGeoRotVecZ(event.values[2]);
       }else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
           dado.setMagFieldX(event.values[0]);
           dado.setMagFieldY(event.values[1]);
           dado.setMagFieldZ(event.values[2]);
       }else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED){
           dado.setMagFieldUncaX(event.values[0]);
           dado.setMagFieldUncaY(event.values[1]);
           dado.setMagFieldUncaZ(event.values[2]);
           dado.setIronBiasX(event.values[3]);
           dado.setIronBiasY(event.values[4]);
           dado.setIronBiasZ(event.values[5]);
       }else if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
           dado.setOrientationX(event.values[0]);
           dado.setOrientationY(event.values[1]);
           dado.setOrientationZ(event.values[2]);
       }else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
           dado.setProximityDist(event.values[0]);
       }else if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
           dado.setAmbiTemp(event.values[0]);
       }else if(event.sensor.getType() == Sensor.TYPE_LIGHT){
           dado.setLight(event.values[0]);
       }else if(event.sensor.getType() == Sensor.TYPE_PRESSURE){
           dado.setPressure(event.values[0]);
       }else if(event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
           dado.setRelHum(event.values[0]);
       }else if(event.sensor.getType() == Sensor.TYPE_TEMPERATURE){
           dado.setDeviceTemp(event.values[0]);
       }else{
           //wtf
       }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void iniciaArquivo(String sFileName){
        File root = new File(Environment.getExternalStorageDirectory(), getString(R.string.app_name));

        if (!root.exists()) {
            root.mkdirs();
            //MediaScannerConnection.scanFile(this, new String[]{root.toString()}, null, null);
        }
        gpxfile = new File(root, sFileName);

        //MediaScannerConnection.scanFile(this, new String[]{gpxfile.getAbsolutePath()}, null, null);
        try {
            writer = new FileWriter(gpxfile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escreveArquivo(String sBody){
        try
        {

            writer.append(sBody);


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    void fechaArquivo(){
        try {
            writer.flush();
            writer.close();
            //Log.d("local", "" + gpxfile.getAbsolutePath());
            MediaScannerConnection.scanFile(this, new String[]{gpxfile.getAbsolutePath()}, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostraTelinhaDeSucesso(){
        fechaArquivo();
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Sensors.this);

        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.sucesso));
        // Setting Dialog Message
        alertDialog.setMessage(getString(R.string.loggedsucess));

        // On pressing Settings button
        alertDialog.setPositiveButton(getString(R.string.show), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                //soundRecording = false;
                btnStartLog.setTextColor(Color.BLACK);
                btnStartLog.setText(getString(R.string.start_logging));
                isGravando = false;
                setCheckBoxEnabled(true);

                //som.finalizaSom();
                Sensors.this.finish();
            }
        });


        // Showing Alert Message
        alertDialog.show();
    }

}
