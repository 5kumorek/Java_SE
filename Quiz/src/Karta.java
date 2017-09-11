import java.io.*;
public class Karta
{   String pyt;
    String odp;
    public Karta(String pytanie, String odpowiedz){
        pyt = pytanie;
        odp = odpowiedz;
    }
    
    public String getPytanie(){
        return pyt;
    }
    
    public String getOdpowiedz(){
        return odp;
    }
}
