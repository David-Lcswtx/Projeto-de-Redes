package cripto;

public class Criptografia {
    private static final int CHAVE = 3;

    public static String criptografar(String texto) {
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                char novo = (char) ((c - base + CHAVE) % 26 + base);
                resultado.append(novo);
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }

    public static String descriptografar(String texto) {
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                char novo = (char) ((c - base - CHAVE + 26) % 26 + base);
                resultado.append(novo);
            } else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }
}
