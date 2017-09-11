package basic;

import javax.sql.rowset.CachedRowSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

class DataPanel extends JPanel implements KeyListener{

    //names is array of textFields, which displays records of row
    private JTextField[] names;
    //edit button set editable of textFields on true, or false, depending for earlier state
    private JButton editButton;
    //countOfColumns contain number of columns in table
    private int countOfColumns;
    //namesOfColumns contain names of columns in table
    private String[] namesOfColumns;

    //tblName contain  name of current table
    private String tblName;
    //rs is my buffer
    private CachedRowSet rs;
    //variable editable is necessary to determination editable state of my JTextFields
    private boolean editable=false;
    //
    private boolean tableIsEmpty = false;

    DataPanel(CachedRowSet results, String[] columnsNames, int columnsCount, String tableName){

        //initialization of variable in dataPanel
        countOfColumns = columnsCount;
        namesOfColumns = columnsNames;
        names = new JTextField[countOfColumns];
        rs = results;
        tblName = tableName;
        //move to first record and check if table is empty
        toFirstRow();
        //showing records on oneRowTable and on listOfRecords
        addEmptyLabels();
        fillLabels();
        addKeyListener(this);
        setRequestFocusEnabled(true);
        showAllRecords();
    }
    private void toFirstRow() {
        try {
            rs.next();
            if(rs.isAfterLast()) {
                JOptionPane.showMessageDialog(this, "Current table is empty");
                tableIsEmpty = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //method which create editButton and empty textFileds
    private void addEmptyLabels()
    {
        //creating and addition button responsible for editable
        editButton = new JButton("Edit ");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionForButton();
            }
        });
        add(editButton, new FlowLayout());

        //creating nad addition textfields with records
        names = new JTextField[countOfColumns];
        for(int i = 0; i< countOfColumns; i++)
        {
            names[i]=new JTextField();
            names[i].setEditable(false);
            names[i].setFont(new Font("SANS_SERIF", Font.ITALIC, 20));
            add(names[i], new FlowLayout());
        }
    }

    //method which fill labels
    private void fillLabels()
    {
        for(int i = 0; i< countOfColumns; i++)
        {
            try {
                if(!tableIsEmpty)
                    names[i].setText(" "+rs.getString(i+1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //method which remove editbutton and textfileds
    void clean(){
        remove(editButton);
        for(int i = 0; i< countOfColumns; i++)
            remove(names[i]);
    }
    // method responsible for changing editable of textfields
    private void actionForButton(){
        editable = !editable;
        for(JTextField name:names)
            name.setEditable(editable);
        String edit ="Edit ";
        if(editable)
            edit = "Save";
        editButton.setText(edit);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    //method which copy text from textfields to records in buffer
    private void saveRecords() throws SQLException{
        for (int i = 1; i <= countOfColumns; i++) {
            rs.updateString(i, names[i-1].getText());
        }
        rs.updateRow();
    }
    //method wchich display table on listOfRecords
    private void showAllRecords(){
        MyFrame.listOfRecords.setText("");
        StringBuilder line= new StringBuilder();
        try{

            for(int i=0;i<countOfColumns-1;i++)
                line.append(namesOfColumns[i]).append("\t");
            line.append(namesOfColumns[countOfColumns-1]).append("\t\n");
            rs.beforeFirst();
            while(rs.next())
            {
                for(int i=0;i<countOfColumns-1;i++)
                    line.append(rs.getString(i+1)).append("\t");
                line.append(rs.getString(countOfColumns)).append("\t\n");
            }
            MyFrame.listOfRecords.append(String.valueOf(line));
            rs.first();
        }catch(SQLException s)
        {
            s.printStackTrace();
        }
    }

    //method provides by keyListener, which allow creating react on pressed
    @Override
    public void keyPressed(KeyEvent e) {
        //I read which kyeboard button was pressed
        int key = e.getKeyCode();

        try{
            //and now chcek keycode, and invoke proper method

            //moving in tables backward
            if (key == KeyEvent.VK_LEFT) {
                if(!rs.isFirst())
                    rs.previous();
            }//moving in tables forward
            else if (key == KeyEvent.VK_RIGHT){
                if(!rs.isLast())
                    rs.next();
            }//saving data from buffer to DB
            else if (key == KeyEvent.VK_C){
                try
                {
                    try (Connection conn = First.getConnection())
                    {
                        saveRecords();
                        rs.acceptChanges(conn);
                    }
                }
                catch (SQLException i)
                {
                    JOptionPane.showMessageDialog(this, "Operation is not available for tables without primary key");
                }
                catch (IOException i)
                {
                    JOptionPane.showMessageDialog(this, i);
                }
                rs = (CachedRowSet) First.getResultSet(tblName)[0];
                toFirstRow();
                showAllRecords();
            }//delete one row of records
            else if (key == KeyEvent.VK_D) {
                try
                {
                    try (Connection conn = First.getConnection())
                    {
                        rs.deleteRow();
                        rs.acceptChanges(conn);
                        if (rs.isAfterLast())
                            rs.first();
                    }
                }
                catch (SQLException i)
                {
                    JOptionPane.showMessageDialog(this, "Operation is not available for tables without primary key");
                }
                catch (IOException i)
                {
                    JOptionPane.showMessageDialog(this, i);
                }
                rs = (CachedRowSet) First.getResultSet(tblName)[0];
                toFirstRow();
                showAllRecords();
            }//addition epmty row
            else if (key == KeyEvent.VK_A) {
                try
                {
                    try (Connection conn = First.getConnection())
                    {
                        rs.moveToInsertRow();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        for(int i = 1; i<= countOfColumns; i++){
                            if(rsmd.getColumnClassName(i).equals("java.lang.String"))
                                rs.updateString(i,"null");
                            else
                                rs.updateInt(i,0);
                        }
                        rs.insertRow();
                        rs.moveToCurrentRow();
                        rs.acceptChanges(conn);
                    }
                }
                catch (IOException i)
                {
                    JOptionPane.showMessageDialog(this, i);
                }
                rs = (CachedRowSet) First.getResultSet(tblName)[0];
                toFirstRow();
                showAllRecords();
            }
            //and at the end filling my lables
            fillLabels();
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, ex);
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
}

