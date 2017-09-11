import java.io.*;
public class First implements Serializable {
    public static void main(String[] args){
        First client = new First();
        int x =3;
        client.execute();
        client.open();
    }
    private void execute(){
        SerializationHelper ser = new SerializationHelper();
        try{
        FileOutputStream fileStream = new FileOutputStream("MyGame.ser");
        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
        
        objectStream.writeObject(ser);
        objectStream.close();
       }catch(Exception ex){
           ex.printStackTrace();
       }
       try{
        FileWriter printer = new FileWriter("test.txt");
        
        printer.write("Wszyscy swieci/Budka suflera/8/bmp");
        printer.close();
       }catch(IOException ex){
           ex.printStackTrace();
       }
       
    }
    private void open(){
               String line;
        try{
         FileInputStream fileStream = new FileInputStream("MyGame.ser");
         ObjectInputStream objectStream = new ObjectInputStream(fileStream);
         
         Object o = objectStream.readObject();
         SerializationHelper isReaded = (SerializationHelper) o;
         objectStream.close();
         System.out.println(isReaded.x);
         }catch(Exception ex){
             ex.printStackTrace();
         }
         try{
           BufferedReader reader = new BufferedReader(new FileReader("list.txt"));
           while((line=reader.readLine()) != null)
           {
               System.out.println(line);
           }
           reader.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        }
         
}
        