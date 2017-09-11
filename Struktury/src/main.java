import java.io.*;
import java.util.*;
public class main{
    private ArrayList<Piosenka> listaPiosenek = new ArrayList<Piosenka>();
    public static void main(String[] args){
        new main().doRoboty();
    }
    
    class ArtystaCompare implements Comparator<Piosenka>{
        public int compare(Piosenka p1, Piosenka p2){
            return p1.getArtysta().compareTo(p2.getArtysta());
        }
    }
    class TempoCompare implements Comparator<Piosenka>{
        public int compare(Piosenka p1, Piosenka p2){
            return p1.getTempo().compareTo(p2.getTempo());
        }
    }

    private void doRoboty(){
        pobierzPiosenke();
        System.out.println(listaPiosenek);
        Collections.sort(listaPiosenek);
        System.out.println(listaPiosenek);
        
        ArtystaCompare komparator = new ArtystaCompare();
        Collections.sort(listaPiosenek, komparator);
        TreeSet<Piosenka> zbiorPiosenek = new TreeSet<Piosenka>();
        zbiorPiosenek.addAll(listaPiosenek);
        System.out.println(zbiorPiosenek);
    }

    private void pobierzPiosenke(){
        try{
            File plik = new File("test.txt");
            BufferedReader czytelnik = new BufferedReader(new FileReader(plik));
            String wiersz;
            while((wiersz=czytelnik.readLine())!= null){
                dodajPiosenke(wiersz);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void dodajPiosenke(String zdanie){
        String[] elementy = zdanie.split("/");
        Piosenka nastepnaPiosenka = new Piosenka(elementy[0], elementy[1], elementy[2], elementy[3]);
        listaPiosenek.add(nastepnaPiosenka);
    }
}