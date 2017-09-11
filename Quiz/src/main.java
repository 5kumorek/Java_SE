import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
public class main
{   private JFrame ramka;
    private JPanel panel;
    private JTextArea pytanie;
    private JTextArea odpowiedz;
    private ArrayList<Karta> listaKartek;
    public static void main (String[] args){
        main początek  = new main();
        początek.wywołaj();
    }
    public void wywołaj(){
        ramka = new JFrame("Edytor karteczek quizowych");
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        Font czcionkaDuza = new Font("sanserif",Font.BOLD, 24);
        pytanie = new JTextArea(6,20);
        pytanie.setLineWrap(true);
        pytanie.setWrapStyleWord(true);
        pytanie.setFont(czcionkaDuza);
        JScrollPane przewijaniePyt = new JScrollPane(pytanie);
        przewijaniePyt.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        przewijaniePyt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        odpowiedz = new JTextArea(6,20);
        odpowiedz.setLineWrap(true);
        odpowiedz.setWrapStyleWord(true);
        odpowiedz.setFont(czcionkaDuza);
        JScrollPane przewijanieOdp = new JScrollPane(odpowiedz);
        przewijanieOdp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        przewijanieOdp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        JButton nastepna = new JButton("Następna kartka");
        
        listaKartek = new ArrayList<Karta>();
        
        JLabel labelPyt = new JLabel("Pytanie");
        JLabel labelOdp = new JLabel("Odpowiedz");
        
        panel.add(labelPyt);
        panel.add(przewijaniePyt);
        panel.add(labelOdp);
        panel.add(przewijanieOdp);
        panel.add(nastepna);
        nastepna.addActionListener(new nastepnaListener());
        JMenuBar menu = new JMenuBar();
        JMenu menuPlik = new JMenu("Plik");
        JMenuItem nowa = new JMenuItem("Nowy");
        nowa.addActionListener(new nowaListener());
        
        JMenuItem zapisz = new JMenuItem("Zapisz");
        zapisz.addActionListener(new zapiszListener());
        
        menuPlik.add(nowa);
        menuPlik.add(zapisz);
        menu.add(menuPlik);
        
        ramka.setJMenuBar(menu);
        ramka.getContentPane().add(BorderLayout.CENTER, panel);
        ramka.setSize(500,600);
        ramka.setVisible(true);
    }
    
    public class nastepnaListener implements ActionListener{
        public void actionPerformed(ActionEvent zd){
            Karta karta = new Karta(pytanie.getText(), odpowiedz.getText());
            listaKartek.add(karta);
            czyscKarte();
      }
    }
    public class zapiszListener implements ActionListener{
        public void actionPerformed(ActionEvent zd){
            Karta karta = new Karta(pytanie.getText(), odpowiedz.getText());
            listaKartek.add(karta);
            
            JFileChooser plikDanych = new JFileChooser();
            plikDanych.showSaveDialog(ramka);
            zapiszPlik(plikDanych.getSelectedFile());
        }
    }
    public class nowaListener implements ActionListener{
        public void actionPerformed(ActionEvent zd){
            listaKartek.clear();
            czyscKarte();
        }
    }
    private void czyscKarte(){
        pytanie.setText("");
        odpowiedz.setText("");
        pytanie.requestFocus();
    }
    private void zapiszPlik(File plik){
        try{
            BufferedWriter pisarz = new BufferedWriter(new FileWriter(plik));
            for(Karta karta : listaKartek){
                pisarz.write(karta.getPytanie()+"/");
                pisarz.write(karta.getOdpowiedz()+"/");
            }
            pisarz.close();
        }catch(IOException ex){
            System.out.println("Nie można zapisać pliku");
            ex.printStackTrace();
        }
    }
}
