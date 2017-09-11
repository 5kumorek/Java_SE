class Zgasywanka{
    Gracz p1;
    Gracz p2;
    Gracz p3;
    
    public void RozpocznijGre(){
        p1=new Gracz();
        p2=new Gracz();
        p3=new Gracz();
        
        int typp1=0;
        int typp2=0;
        int typp3=0;
        
        boolean p1odgadl=false;
        boolean p2odgadl=false;
        boolean p3odgadl=false;
        
       
        System.out.println("Myślę o zakresie od 0-9.NIech gracze wytypują liczby");
        
        while(true){
            int liczbaOdgadywana=(int) (Math.random()*10);
            p1.zgaduj();
            p2.zgaduj();
            p3.zgaduj();
            
            typp1=p1.liczba;
            System.out.println("Gracz nr1 wytypowal liczbę "+typp1);
            typp2=p2.liczba;
            System.out.println("Gracz nr2 wytypowal liczbę "+typp2);
            typp3=p3.liczba;
            System.out.println("Gracz nr3 wytypowal liczbę "+typp3);
            System.out.println("Należało wytypować liczbę "+liczbaOdgadywana);
            
            if(typp1==liczbaOdgadywana){
                p1odgadl=true;}
            if(typp2==liczbaOdgadywana){
                p2odgadl=true;}
            if(typp3==liczbaOdgadywana){
                p3odgadl=true;}    
                
            if(p1odgadl||p2odgadl||p3odgadl){
            System.out.println("Gracz pierwszy "+p1odgadl);
            System.out.println("Gracz drugi "+p2odgadl);
            System.out.println("Gracz trzeci "+p3odgadl);
            System.out.print("Koniec Gry");
            break;}
            else{
            System.out.println("Spróbuj jeszcze raz");
        }
    }
}
}
        
        