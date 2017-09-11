public class Piosenka implements Comparable<Piosenka>{
    String tytul;
    String artysta;
    String ocena;
    String bpm;
    
//    public boolean equals(Object piosenka){
  //      Piosenka p = (Piosenka) piosenka;
    //    return getTytul().equals(p.getTytul());
    //}
    //public int hashCode(){
      //  return tytul.hashCode();
    //}
    public int compareTo(Piosenka pios){
        return tytul.compareTo(pios.getTytul());
    }
    
    Piosenka(String t, String a, String o, String b){
        tytul = t;
        artysta = a;
        ocena = o;
        bpm = b;
    }
    
    public String getTytul(){
        return tytul;
    }
    public String getArtysta(){
        return artysta;
    }
    public String getOcena(){
        return ocena;
    }
    public String getTempo(){
        return bpm;
    }
    public String toString(){
        return tytul;
    }
}