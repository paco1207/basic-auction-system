import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import javax.crypto.SealedObject;
import java.io.FileInputStream;
import java.io.IOException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Server implements Auction{
    private static ArrayList<AuctionItem> auctionItems;
    public Server() {
        super();
        Server.auctionItems = new ArrayList<AuctionItem>();
    }
    private static SecretKey readAESKey() throws IOException {
        byte[] key = new byte[16];
        try{
            FileInputStream f = new FileInputStream("../keys/testKey.aes");
            f.read(key);
        }catch(Exception e){
            System.out.println("Failed to read testKey.aes!");
        }
        return new SecretKeySpec(key, "AES");
    }
    private static void storeItems(){
        AuctionItem item1 = new AuctionItem();
        item1.itemID = 1;
        item1.name = "TShirt";
        item1.description = "White, Cool";
        item1.highestBid = 30;
        AuctionItem item2 = new AuctionItem();
        item2.itemID = 2;
        item2.name = "Cup";
        item2.description = "Colorful, Art";
        item2.highestBid = 10;
        AuctionItem item3 = new AuctionItem();
        item3.itemID = 3;
        item3.name = "Sam Sung Cell phone";
        item3.description = "Black, G100";
        item3.highestBid = 400;
        Server.auctionItems.add(item1);
        Server.auctionItems.add(item2);
        Server.auctionItems.add(item3);
    }
    public SealedObject getSpec(int itemID) throws RemoteException{
        int itemNum = Server.auctionItems.size();
        //default is item1
        int requiredIndex = 0;
        SecretKey key = null;
        Cipher cipher = null;
        SealedObject encrypted =null;
        for(int i=0;i<itemNum;i++){
            if (Server.auctionItems.get(i).itemID==itemID){
                requiredIndex = i;
                break;
            }
        }
        AuctionItem item = Server.auctionItems.get(requiredIndex);
        // Encrypt item
        try{
            key = Server.readAESKey();
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = new SealedObject(item, cipher);
        }
        catch(Exception e){

        }
        return encrypted;
    }
  
    public static void main(String[] args) {
        try{
            Server s = new Server();
            String name = "Auction";
            Auction stub = (Auction) UnicastRemoteObject.exportObject(s, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Server ready");
            Server.storeItems();
        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();;
        }
    }
}
