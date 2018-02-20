package whitesun.telemetry;

/**
 * Created by Avell-G1711 on 27/03/2016.
 */
public class EventoTrocaDados {
    boolean foco;
    String apelido;
    float valor;
    float tempoRecebimento;

    public EventoTrocaDados(String apelido, float valor, float tempoRecebimento, boolean foco){
        this.apelido = apelido;
        this.valor = valor;
        this.tempoRecebimento = tempoRecebimento;
        this.foco = foco;
    }

    public String getApelido() {
        return apelido;
    }

    public float getValor() {
        return valor;
    }

    public boolean isFoco() {
        return foco;
    }


    public float getTempoRecebimento() {
        return tempoRecebimento;
    }

}
