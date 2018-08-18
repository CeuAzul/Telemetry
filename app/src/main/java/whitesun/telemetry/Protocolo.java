package whitesun.telemetry;

import java.util.ArrayList;
import java.util.List;

public class Protocolo {

    List<Dado> dados = new ArrayList<Dado>();

    public String processaInput(String input) {
        String processado = input;
        if (input.equals("")) {
            return "";
        }

        int posicaoInicio = input.indexOf("!");

        if (posicaoInicio == -1) {
            // Não encontrou nenhuma marcação de inicio
            return input;
        } else  {
            // Se encontrou o caractere de inicio, apaga tudo que tem atras
            input = input.substring(posicaoInicio);
        }
        int posicaoFim = input.indexOf("@");

        if (posicaoFim == -1) {
            // Não encontrou fim, retorna do jeito que ta
            return input;
        }

        // Captura o item mandado
        String itemDetectado = input.substring(1, posicaoFim);
        // Resto do buffer não processado
        processado = input.substring(posicaoFim+1);

        int posicaoIgual = itemDetectado.indexOf("=");

        if (posicaoIgual != -1) {
            // Se encontrou um "="
            String apelido = itemDetectado.substring(0, posicaoIgual);
            boolean apelidoOK = false;
            boolean valorOK = false;
            // O apelido deve ter 3 letras
            if (apelido.length() == 3) {
                // Só deve conter letras
                if (isAlpha(apelido)) {
                    // Se tudo isso estiver OK, sinaliza que ta blz
                    apelidoOK = true;
                }
            }

            // Pega valor do item
            String valor = itemDetectado.substring(posicaoIgual+1);
            float fvalor = 0;
            try {
                // Verifica se ele é convertível em float
                fvalor = Float.parseFloat(valor);
                valorOK = true;
            } catch(NumberFormatException e) {
                valorOK = false;
            }
            if (apelidoOK && valorOK) {
                atualizaDados(apelido, fvalor);
            }
        }

        // Se sobrou mais algum começo, chama a função de novo
        if (input.indexOf("!") != -1) {
            return processaInput(processado);
        }
        return processado;
    }

    public List<Dado> getDados(){

        return dados;
    }



    public boolean isAlpha(String name) {

        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    private void atualizaDados(String apelido, float fvalor) {

        for (int i = 0; i < dados.size(); i++) {
            // Se encontrou um dado com esse nome, adiciona valor
            if(dados.get(i).getApelido().equals(apelido)){
                dados.get(i).addValor(fvalor);
                // Retorna pra não continuar a função
                return;
            }
        }

        // Se não encontrou dado com aquele nome, não retornou e caiu aqui, adiciona novo dado
        dados.add(new Dado(apelido, fvalor));
    }

}
