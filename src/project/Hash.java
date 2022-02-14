package project;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Password encryption 
 *
 * @author Nihal
 */
public class Hash {
      
    public static String encryptX(String eingabe)
    {
        try {
            
           MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(eingabe.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashed = no.toString(16);
            while (hashed.length() < 32) {
                hashed = "0" + hashed;
            }
 
            return hashed;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } 
}
}
