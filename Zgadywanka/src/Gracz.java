class Gracz{
    int liczba;
    
    public void zgaduj(){
        Elo w=new Elo();
        w.scanner();
        liczba=w.licz;
        System.out.println("Gracz wytypował liczbę "+liczba);
    }
}