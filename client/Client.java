import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    // Load the AES key
    private static SecretKey loadAESKey() throws Exception {
        byte[] keyBytes = new byte[16];
        try (FileInputStream fis = new FileInputStream("../keys/testKey.aes")) {
            fis.read(keyBytes);
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: itemID is required!");
            return;
        }
        int itemID = Integer.parseInt(args[0]);
        try {
            String name = "Auction";
            Registry registry = LocateRegistry.getRegistry("localhost");
            Auction server = (Auction) registry.lookup(name);
            
            // Retrieve the encrypted AuctionItem object
            SealedObject sealedObject = server.getSpec(itemID);

            // Decrypt the AuctionItem
            SecretKey key = loadAESKey();
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            AuctionItem item = (AuctionItem) sealedObject.getObject(cipher);

            System.out.println("The specification of the item is:");
            System.out.println("itemID = " + item.itemID);
            System.out.println("name = " + item.name);
            System.out.println("description = " + item.description);
            System.out.println("highestBid = " + item.highestBid);
        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
    }
}
