import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
public class KwizGra
{
    private JTextArea pytanie;
    private JTextArea odpowiedz;
    private ArrayList<Karta> listaKart;
    private Karta biezacaKarta;
    private int indeksBiezacejKarty;
    private JFrame ramka;
    private JButton przyciskNas;
    private boolean czyOdpowiedz;
   
    public static void main(String[] args){
        KwizGra nowy = new KwizGra();
        nowy.wykonaj();
    }
    public void wykonaj(){
        ramka = new JFrame("Kwiz");
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        Font czcionkaDuza = new Font("sanserif", Font.BOLD, 24);
        
        pytanie = new JTextArea(10, 20);
        pytanie.setFont(czcionkaDuza);
        pytanie.setLineWrap(true);
        pytanie.setEditable(false);
        
        JScrollPane przewijanieP = new JScrollPane(pytanie);
        przewijanieP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        przewijanieP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        przyciskNas = new JButton("Pokaż pytanie");
        panel.add(przewijanieP);
        panel.add(przyciskNas);
        przyciskNas.addActionListener(new przyciskNasListener());
        
        JMenuBar pasekMenu = new JMenuBar();
        JMenu menuPlik = new JMenu("Plik");
        JMenuItem opcjaO = new JMenuItem("Otwórz zbiór kart");
        opcjaO.addActionListener(new opcjaOListener());
        menuPlik.add(opcjaO);
        pasekMenu.add(menuPlik);
        ramka.setJMenuBar(pasekMenu);
        ramka.getContentPane().add(BorderLayout.CENTER, panel);
        ramka.setSize(640, 500);
        ramka.setVisible(true);
    }
    public class przyciskNasListener implements ActionListener{
        public void actionPerformed(ActionEvent zd){
            if(czyOdpowiedz){
                pytanie.setText(biezacaKarta.getOdpowiedz());
                przyciskNas.setText("Następna karta");
                czyOdpowiedz = false;
            }
            else{
                if(indeksBiezacejKarty<listaKart.size()){
                    pokazNastepnaKarta();
                }
                else{
                    pytanie.setText("To Była ostatnia karta");
                    przyciskNas.setEnabled(false);
                }
            }
        }
    }
    public class opcjaOListener implements ActionListener{
        public void actionPerformed(ActionEvent ev){
            JFileChooser dialog = new JFileChooser();
            dialog.showOpenDialog(ramka);
            wczytajPlik(dialog.getSelectedFile());
        }
    }
    private void wczytajPlik(File file){
        listaKart = new ArrayList<Karta>();
        try{
            BufferedReader czytelnik = new BufferedReader(new FileReader(file));
            String wiersz = null;
            while((wiersz = czytelnik.readLine())!=null){
                tworzKarte(wiersz);
            }
            czytelnik.close();
        }catch(Exception ex){
            System.out.println("nie można odczytać pliku");
            ex.printStackTrace();
        }
    }
    private void tworzKarte(String wierszDanych){
        String[] wyniki = wierszDanych.split("/");
        Karta card = new Karta(wyniki[0], wyniki[1]);
        System.out.println("utworzono karte");
    }
    private void pokazNastepnaKarta(){
        biezacaKarta = listaKart.get(indeksBiezacejKarty);
        indeksBiezacejKarty++;
        pytanie.setText(biezacaKarta.getPytanie());
        przyciskNas.setText("pokaż odpowiedz");
        czyOdpowiedz = true;
    }    
}
