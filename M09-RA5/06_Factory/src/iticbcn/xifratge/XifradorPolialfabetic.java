package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class XifradorPolialfabetic implements Xifrador {
    public static final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static final char[] majuscules = alfabet.toUpperCase().toCharArray();
    private char[] alfabetP = new char[majuscules.length];
    private Random random;

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        long clauLong;
        try {
            clauLong = Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau per xifrat Polialfabètic ha de ser un String convertible a long");
        }
        initRandom(clauLong);
        return new TextXifrat(xifraPoliAlfa(msg).getBytes());
    }

    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        long clauLong;
        try {
            clauLong = Long.parseLong(clau);
        } catch (NumberFormatException e) {
            throw new ClauNoSuportada("La clau de Polialfabètic ha de ser un String convertible a long");
        }
        initRandom(clauLong);
        return desxifraPoliAlfa(xifrat.toString());
    }

    public void initRandom(long clauSecreta) {
        random = new Random(clauSecreta);
    }

    public void permutaAlfabet() {
        ArrayList<Character> list = new ArrayList<>();
        for (char c : majuscules) {
            list.add(c);
        }
        Collections.shuffle(list, random);
        for (int i = 0; i < alfabetP.length; i++) {
            alfabetP[i] = list.get(i);
        }
    }

    public String xifraPoliAlfa(String msg) {
        String txtF = "";
        for (int i = 0; i < msg.length(); i++) {
            permutaAlfabet();
            char c = msg.charAt(i);
            boolean trobat = false;
            for (int x = 0; x < majuscules.length; x++) {
                if (c == majuscules[x]) {
                    txtF = txtF + alfabetP[x];
                    trobat = true;
                    break;
                }
                if (c == Character.toLowerCase(majuscules[x])) {
                    txtF = txtF + Character.toLowerCase(alfabetP[x]);
                    trobat = true;
                    break;
                }
            }
            if (!trobat) {
                txtF = txtF + c;
            }
        }
        return txtF;
    }

    public String desxifraPoliAlfa(String msgXifrat) {
        String txtF = "";
        for (int i = 0; i < msgXifrat.length(); i++) {
            permutaAlfabet();
            char c = msgXifrat.charAt(i);
            boolean trobat = false;
            for (int x = 0; x < alfabetP.length; x++) {
                if (c == alfabetP[x]) {
                    txtF = txtF + majuscules[x];
                    trobat = true;
                    break;
                }
                if (c == Character.toLowerCase(alfabetP[x])) {
                    txtF = txtF + Character.toLowerCase(majuscules[x]);
                    trobat = true;
                    break;
                }
            }
            if (!trobat) {
                txtF = txtF + c;
            }
        }
        return txtF;
    }
}