import java.util.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.zip.*;
public class First implements Serializable{
		   @SuppressWarnings("resource")
		public static void main(String[] args) throws IOException, ClassNotFoundException {
		         
		      InputStream is = null;
		      DataInputStream dis = null;
		      FileOutputStream fos = null;
		      DataOutputStream dos = null;
		      String[] s = {"Hello", "World!!"};
		      int[] d = {1, 3, 4};
		      PrintWriter writer;		      
		      Employee[] staff = new Employee[3];
		      staff[0]=new Employee("John ceacker", 7500, 74, 3);
		      staff[1]=new Employee("Thomas ceacker", 7500, 74, 3);
		      staff[2]=new Employee("Carl ceacker", 7500, 74, 3);
		      try{
		         // create file output stream
		         fos = new FileOutputStream("test.txt");
		         dos = new DataOutputStream(fos);
		         for(String j:s)
		         {
		           
		            dos.writeUTF(j);
		         }
		         for(int liczba:d)
		         {
		        	 dos.writeInt(liczba);
		         }
		           
		         // force data to the underlying file output stream
		         dos.flush();
		         
		         // wypisywanie na ekran
		         is = new FileInputStream("test.txt");
		         dis = new DataInputStream(is);	         
		         // available stream to be read
		         int length = dis.available();
		         byte[] buf = new byte[length];
		         dis.readFully(buf);
		        for(byte b:buf)
		         {
		        	 char k=(char) b;
		        	 //System.out.print(k);
		         }System.out.println();
		      }catch(Exception e){
		         // if any error occurs
		         e.printStackTrace();
		      }finally{
		         
		         // releases all system resources from the streams
		         if(is!=null)
		            is.close();
		         if(dis!=null)
		            dis.close();
		         if(fos!=null)
		            fos.close();
		         if(dos!=null)
		            dos.close();
		      }
		      writer = new PrintWriter(new FileWriter("test2.txt"));
		      for(String word:s)
		    	  writer.print(word+" ");
		      for(int number:d)
		    	  writer.print(number+" ");
		      writer.close();
		      BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream("test2.txt"), "UTF-8"));
		      String  line;
		      while((line = read.readLine())!= null)
		      {
		    	  System.out.print(line);
		      }
		      
		      
		      //DRUGA CZĘŚĆ ZADANIA	
		      
		      try(ObjectOutputStream serout = new ObjectOutputStream(new FileOutputStream("serializacja.ser")))
			   {
		    	  serout.writeObject(staff[0]);
		    	  serout.writeObject(staff);
			   }
		      try(ObjectInputStream serin = new ObjectInputStream(new FileInputStream("serializacja.ser")))
			   {
		    	  Employee p1 =(Employee) serin.readObject();
		    	  Employee[] tab =(Employee[]) serin.readObject();
			   
		      try(PrintWriter w = new PrintWriter("test3.txt", "UTF-8"))
		      {
		    	  //save(tab, w);
		    	  //save(staff, w);
		      }
			   }
		      //w.close();
		      try(Scanner in = new Scanner(new FileInputStream("test3.txt"), "UTF-8"))
		      {
		    	  String linia;
		    	  while(in.hasNextLine())
		    	  {
		    		  linia = in.nextLine();
		    		  System.out.println(linia);
		    	  }
		    	  in.close();
		      }
		      try (RandomAccessFile rin = new RandomAccessFile("test3.txt", "rw"))
		      {
		    	  System.out.println();
		    	  long n=12;
		    	  rin.seek(n-1);
		    	  rin.writeUTF("masur");
		    	  String linijka;
		    	  rin.seek(0);
		    	  while((linijka=rin.readLine())!=null);
		    	  	System.out.println(linijka);
		      }
		      try(ZipOutputStream zout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream("test4.zip"))))
		      {
		    	  ZipEntry z= new ZipEntry("pierwszy.txt");
		    	  zout.putNextEntry(z);
		    	  zout.write("To mój pierwszy plik tekstowy w zipie".getBytes());
		    	  zout.closeEntry();
		    	  
		    	  zout.close();
		      }
		      try(ZipInputStream zin = new ZipInputStream(new FileInputStream("test4.zip")))
		      {
		    	  ZipEntry z = zin.getNextEntry();
		    	  Scanner wejscie = new Scanner(zin);
		    	/*  while(wejscie.hasNextLine())
		    		  System.out.println(wejscie.nextLine());
		    	  if(!z.isDirectory())
		    		  System.out.println("Nie jest to katalog");
		    	  System.out.println("Plik ten nazywa się "+ z.getName());
		    	  System.out.println("Rozmiar pliku to "+z.getSize()+" bitów");
		    	  System.out.println("Suam kontrolna pliku to "+z.getCrc());*/
		    	  zin.close();
		      }
		     //Scanner odczyt = new Scanner(System.in);
		     String name="text5.txt";
		     Charset charset = Charset.forName("ISO-8859-1");
		     File file = new File(name);
		     Path sciezka2=file.toPath();
		     if(file.exists() && !file.isDirectory()) { 
		    	 String nazwa_sciezki = file.getAbsolutePath();
		    	 Path sciezka1= Paths.get(nazwa_sciezki);
		    	 sciezka2 = file.toPath();
		    	 byte[] bytes = Files.readAllBytes(sciezka2);
		    	
		    	 
		    	 try
				 {
		    	 	List<String> lines = Files.readAllLines(sciezka2, charset);
		    	 	for(String lin :lines)
		    	 	{
		    			 System.out.println(lin);
		    	 	}
					 Files.write(sciezka2, "\r\nDopisuje nowy wiersz".getBytes(charset), StandardOpenOption.APPEND);
					 Files.copy(Paths.get("text5.txt"), Paths.get("nowy.txt"),StandardCopyOption.REPLACE_EXISTING);
		    	 }
		    	 catch(IOException ex){System.out.println(ex);}
		     }else
		     {
		    	 System.out.println("Nie odnaleziono takeigo pliku");
		     }


		     
		     Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr--r--");
		     FileAttribute<Set<PosixFilePermission>> attribute = PosixFilePermissions.asFileAttribute(perms);
		     if(!Files.exists(Paths.get("temp")))Files.createDirectory(Paths.get("temp"));
		     
		     try{
		     	Files.move(Paths.get("nowy.txt"), Paths.get("temp/nowy_przeniesiony.txt"),StandardCopyOption.ATOMIC_MOVE);
				 Files.delete(Paths.get("nowy_przeniesiony.txt"));
		     }
		     catch(IOException ex){System.out.print(ex);}
		   
		     try(DirectoryStream<Path> entries = Files.newDirectoryStream(Paths.get(""),"*.txt"))
		     {
		    	 for(Path entry : entries)
		    	 {
		    		 System.out.println(entry.toString());
		    	 }
		     }
		   }
		   
		   private static void save(Employee[] employees, PrintWriter out)
		   {
			   out.println(employees.length);
			   for (Employee e : employees)        
				   writeEmployee(out, e);
		   }
		   private static void writeEmployee(PrintWriter out, Employee e)
		   {
			   out.println(e.getName()+" | "+e.getLiczba1()+" | "+e.getLiczba2()+" | "+e.getLiczba3());
		   }
		}