package br.com.correntista.util;

/**
 *
 * @author AlexLRF
 */
public class GeradorUtil {

    /**
     *
     * @param qtd - número de dígitos do número retornado
     *
     * @return número(s) aleatório(s)
     */
    public static String gerarNumero(int qtd) {
        String numeroAleatorio = "";
        for (int i = 0; i < qtd; i++) {
            numeroAleatorio += (int) (Math.random() * 10);
        }
        return numeroAleatorio;
    }

    /**
     *
     * @return CNPJ formatado com a máscara 99.999.999/9999-99
     */
    public static String gerarCNPJ() {
        return gerarNumero(2) + "." + gerarNumero(3) + "." + gerarNumero(3)
                + "/" + gerarNumero(4) + "-" + gerarNumero(2);
    }

    /**
     *
     * @return CPF formatado com a máscara 999.999.999-99
     */
    public static String gerarCPF() {
        return gerarNumero(3) + "." + gerarNumero(3) + "." + gerarNumero(3)
                + "-" + gerarNumero(2);
    }

    public static String gerarSenha() {
        String senhaGerada = "";
        int indice;
        String[] caracteres = {"0", "1", "b", "2", "4", "5", "6", "7", "8",
            "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z"};

        for (int i = 0; i < 5; i++) {
            indice = (int) (Math.random() * caracteres.length);
            senhaGerada += caracteres[indice];
        }

        return senhaGerada;
    }

}
