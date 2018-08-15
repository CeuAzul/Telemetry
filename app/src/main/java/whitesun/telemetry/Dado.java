package whitesun.telemetry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Dado {
    String apelido;
    List<Float> valores = new ArrayList<Float>();
    List<Float> tempoRecebimento = new ArrayList<Float>();

    public Dado(String apelido, float valorInicial) {
        this.apelido = apelido;
        addValor(valorInicial);
    }

    public String getApelido() {

        return apelido;
    }

    public void setApelido(String apelido) {

        this.apelido = apelido;
    }

    public List<Float> getValores() {

        return valores;
    }

    public List<Float> getTempoRecebimento(){

        return tempoRecebimento;
    }

    public void addValor(Float valor) {

        this.valores.add(valor);

        Calendar rightNow = Calendar.getInstance();

        // offset to add since we're not UTC
        long offset = rightNow.get(Calendar.ZONE_OFFSET) + rightNow.get(Calendar.DST_OFFSET);
        long sinceMidnight = (rightNow.getTimeInMillis() + offset) % (24 * 60 * 60 * 1000);

        float segundosMeianoite = (float) sinceMidnight / 1000;
        this.tempoRecebimento.add(segundosMeianoite);
    }


}
