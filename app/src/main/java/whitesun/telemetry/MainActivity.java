package whitesun.telemetry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
    Button btnComeca;
    Button btnEstacaoTerrestre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instanciaBotoes();
    }

    public void instanciaBotoes() {
        btnComeca = (Button) findViewById(R.id.btnStart);
        btnComeca.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this, Sensors.class);
                MainActivity.this.startActivity(myIntent);
            }

        });

        btnEstacaoTerrestre = (Button) findViewById(R.id.btnGndStation);
        btnEstacaoTerrestre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this, EstacaoTerrestre.class);
                MainActivity.this.startActivity(myIntent);
            }

        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*        if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.action_privacypolitics) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

            // Setting Dialog Title
            alertDialog.setTitle(getString(R.string.action_privacypolitics));
            // Setting Dialog Message
            alertDialog.setMessage(Html.fromHtml("\n" +
                    "<html>\n" +
                    "<body>\n" +
                    "<h2>Privacy Policy</h2>\n" +
                    "Telemetria foi desenvolvido para equipe de competição Céu Azul Aeronaves e visa a aquisição de dados de sensores para validação do projeto de engenharia de uma aeronave do tipo VANT.</p>\n" +
                    "<p><strong>Informações coletadas</strong></p>\n" +
                    "<p>O aplicativo Telemetria utiliza as informações dos sensores unicamente para ser armazenadas no arquivo log interno do celular.</p>\n" +
                    "<p><strong>NENHUM dado</strong> de sensor será enviado pela internet para fins pessoais, uso de terceiros ou fins comerciais. </p>\n" +
                    "<p>Caso você baixe o aplicativo pelo Google Play, a plataforma envia informações do dispositivo, como modelo do smartphone, IP etc.. para ser utilizada para depuração de erros, sendo esta utilização não possui qualquer ligação direta com o aplicativo e é feita automaticamente pelo Google. </p>\n" +
                    "\n" +
                    "<p><strong>Como usamos as informações?</strong></p>\n" +
                    "<p>Todas as informações coletadas pelos sensores permanecem na memória interna do smarthone, e por isso, não temos acesso nem fazemos o uso de qualquer informação coletada pelo aplicativo.</p>\n" +
                    "\n" +
                    "<p><strong>Informações compartilhadas</strong></p> "+
                    "<p>Nenhum dado é compartilhado.\n" +
                    "<p><strong>Contato e mais informações:</strong></p> "+
                    "<p>Leonardo Mariga (leomariga@gmai.com):</p>" +
                    "<p>http://www.aerodesign.ufsc.br/</p>" +
                    "</body>\n" +
                    "</html>"));

            // On pressing Settings button
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                }
            });


            // Showing Alert Message
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
