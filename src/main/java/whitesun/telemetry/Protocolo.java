package whitesun.telemetry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Avell-G1711 on 26/03/2016.
 */
public class Protocolo {

    List<Dado> dados = new ArrayList<Dado>();

    public String processaInput(String input){
        String processado = input;
    //    System.out.println("Input: "+input);
        if(input.equals("")){
            return "";
        }

        int posicaoInicio = input.indexOf("!");
        if(posicaoInicio == -1){
            return input;                                                           // Não encontrou nenhuma marcação de inicio
        }else{
            input = input.substring(posicaoInicio);                                 // Se encontrou o caractere de inicio, apaga tudo que tem atras
    //        System.out.println("Cortou tudo que tem antes do inicio: "+ input);
        }
        int posicaoFim = input.indexOf("@");

        if(posicaoFim == -1){
            return input;                                                           // Não encontrou fim, retorna do jeito que ta
        }
        String itemDetectado = input.substring(1, posicaoFim);                      // Captura o item mandado
        processado = input.substring(posicaoFim+1);                                 // Resto do buffer não processado
    //    System.out.println("Item detectado: "+ itemDetectado);
    //    System.out.println("Resto: "+ processado);

        int posicaoIgual = itemDetectado.indexOf("=");

        if(posicaoIgual != -1){                                                     // Se encontrou um "="
            String apelido = itemDetectado.substring(0, posicaoIgual);
        //    System.out.println("Apelido: "+ apelido + "   ---   Tamanho da String: "+apelido.length());
            boolean apelidoOK = false;
            boolean valorOK = false;
            if(apelido.length() == 3){                                                                                  // O apelido deve ter 3 letras
                if(isAlpha(apelido)){                                                                                   // Só deve conter letras
                    apelidoOK = true;                                                                                   // Se tudo isso estiver OK, sinaliza que ta blz
                }
            }

            String valor = itemDetectado.substring(posicaoIgual+1);                                                     // Pega valor do item
            float fvalor = 0;
            try{
                fvalor = Float.parseFloat(valor);                                                                       // Verifica se ele é convertível em float
                valorOK = true;
            }catch(NumberFormatException e){
                valorOK = false;
            }
       //     System.out.println("Valor: "+ valor + "   ---   Valor em String: "+valor+"   ---   Valor em float: "+fvalor);

       //     System.out.println("Apelido Ok? "+apelidoOK + "   ---   Valor Ok? "+valorOK);
            if(apelidoOK && valorOK) {
                atualizaDados(apelido, fvalor);
            }
        }
        if(input.indexOf("!") != -1){                                               // Se sobrou mais algum começo, chama a função de novo
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
     //   System.out.println("Atualizando dados...");
        for (int i = 0; i < dados.size(); i++) {
         //   System.out.println("Entrou no For");
            if(dados.get(i).getApelido().equals(apelido)){                          // Se encontrou um dado com esse nome, adiciona valor
       //         System.out.println("Encontrou um dado com o nome!");
                dados.get(i).addValor(fvalor);
                return;                                                             // Retorna pra não continuar a função
            }
        }
     //   System.out.println("Não encontrou um dado com o nome, criando novo");
        dados.add(new Dado(apelido, fvalor));                                       // Se não encontrou dado com aquele nome, não retornou e caiu aqui, adiciona novo dado
    }

}
