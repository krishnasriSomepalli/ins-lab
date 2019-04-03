import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
 
class SHA1Example {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String message = "160115733128";
        System.out.println("Message: "+message);

        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] hash = md.digest(message.getBytes());

        StringBuffer sb = new StringBuffer();
        for (byte b:hash) {
            sb.append(String.format("%02x",b));
        }
        System.out.println("Hashcode, in hexadecimal: "+sb.toString());
    }
}