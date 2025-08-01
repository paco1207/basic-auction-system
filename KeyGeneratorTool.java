import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.IOException;

public class KeyGeneratorTool{
    public static void main(String[] args) throws Exception {
        //Generate key
        KeyGenerator gen = KeyGenerator.getInstance("AES");
        gen.init(128);
        SecretKey key = gen.generateKey();

        //Store key to testKey.aes
        try{
            FileOutputStream f = new FileOutputStream("keys/testKey.aes");
            f.write(key.getEncoded());
        } catch(Exception e){
            System.out.println("Failed to create testKey.aes!");
        }
    }
}