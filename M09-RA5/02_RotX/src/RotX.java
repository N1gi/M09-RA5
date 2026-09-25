public class RotX {
    public static final String alfabet = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static final char[] minuscules = alfabet.toCharArray();
    public static final char[] majuscules = alfabet.toUpperCase().toCharArray();
    public static void main(String[] args) {
        String msgs[] = {"ABC", "XYZ", "Hola, Mr. calçot", "Perdó, per tu què és?"};
        String msgX[] = new String[msgs.length];
        String msgX2 = "Úiüht, úiü wx ùxì ív?";
        System.out.println("\nXifrat\n---------");
        int index = 0;
        for (int i = 0; i<msgs.length; i++) {
            msgX[i] = xifraRotX(msgs[i],index);
            System.out.printf("(%d) %-23s => %s%n",index, msgs[i], msgX[i]);
            index = index + 2;
        }

        System.out.println("\nDesxifrat\n---------");
        index = 0;
        for (String msg: msgX) {
            System.out.printf("(%d) %-23s => %s%n",index, msg, desxifraRotX(msg,index));
            index = index + 2;
        }
        
        System.out.println("\nMissatge xifrat: " + msgX2 + "\n----------------");
        forcaBrutaRotX(msgX2);
    }

    public static String xifraRotX (String msg, int index) {
        String txtF = "";
        for (int i = 0; i<msg.length(); i++) {
            char c = msg.charAt(i);
            Boolean trobat = false;
            for (int x = 0; x<minuscules.length; x++) {
                if (c == minuscules[x]) {
                    txtF = txtF + minuscules[(x + index) % minuscules.length];
                    trobat = true;
                    break;
                }
                if (c == majuscules[x]) {
                    txtF = txtF + majuscules[(x + index) % majuscules.length];
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

    public static String desxifraRotX (String msg, int index) {
        String txtF = "";
        for (int i = 0; i<msg.length(); i++) {
            char c = msg.charAt(i);
            Boolean trobat = false;
            for (int x = 0; x<minuscules.length; x++) {
                if (c == minuscules[x]) {
                    txtF = txtF + minuscules[(x - index + minuscules.length) % minuscules.length];
                    trobat = true;
                    break;
                }
                if (c == majuscules[x]) {
                    txtF = txtF + majuscules[(x - index + majuscules.length) % majuscules.length];
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

    public static void forcaBrutaRotX (String msg) {
        for (int index = 0; index<alfabet.length(); index++) {
            String txtF = "";
            for (int i = 0; i<msg.length(); i++) {
            char c = msg.charAt(i);
            Boolean trobat = false;
                for (int x = 0; x<minuscules.length; x++) {
                    if (c == minuscules[x]) {
                       txtF = txtF + minuscules[(x - index + minuscules.length) % minuscules.length];
                        trobat = true;
                        break;
                    }
                    if (c == majuscules[x]) {
                        txtF = txtF + majuscules[(x - index + majuscules.length) %  majuscules.length];
                        trobat = true;
                        break;
                    }
                }
                if (!trobat) {
                    txtF = txtF + c;
                }
            }
            System.out.printf("(%d)->%s%n", index, txtF);
        }
    }
}