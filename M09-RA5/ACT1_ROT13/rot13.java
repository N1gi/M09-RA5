package ACT1_ROT13;

public class rot13 {
    public static final Character[] minuscules = {
        'a', 'á', 'à', 'b', 'c', 'ç', 'd', 'e', 'é', 'è', 'f', 'g', 'h', 'i', 'í', 'ì', 'ï',
        'j', 'k', 'l', 'm', 'n', 'ñ', 'o', 'ó', 'ò', 'p', 'q', 'r', 's', 't', 'u', 'ú', 'ù', 'ü',
        'v', 'w', 'x', 'y', 'z'};

    public static final Character[] majuscules = {
        'A', 'Á', 'À', 'B', 'C', 'Ç', 'D', 'E', 'É', 'È', 'F', 'G', 'H', 'I', 'Í', 'Ì', 'Ï',
        'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'Ó', 'Ò', 'P', 'Q', 'R', 'S', 'T', 'U', 'Ú', 'Ù', 'Ü',
        'V', 'W', 'X', 'Y', 'Z'};
    public static void main(String[] args) {
        String msgs[] = {"ABC", "XYZ", "Hola, Mr.calçot", "Perdó, per tu què és?"};
        String msgX[] = new String[msgs.length];
        System.out.println("\nXifrat\n---------");
        
        for (int i = 0; i<msgs.length; i++) {
            msgX[i] = xifraRot13(msgs[i]);
            System.out.printf("%-23s => %s%n",msgs[i], msgX[i]);
        }

        System.out.println("\nDesxifrat\n---------");

        for (String msg: msgX) {
            System.out.printf("%-23s => %s%n",msg, desxifraRot13(msg[i]));
        }
    }

    public static String xifraRot13 (String msg) {
        
    }

    public static String desxifraRot13 (String msg) {

    }

}
