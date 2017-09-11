package classes;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by radoslaw on 10.07.17.
 */
public class MainServer implements Runnable{
    //instance of serversocket, which will be use to create server
    public ServerSocket server;
    //instance of objectoutputstream, which will be use ot send arraylsit to client
    private ObjectOutputStream objectOutput;
    //in this arraylist server will display enquire
    private TextArea queryTextArea;
    //button to accept enqurie
    private Button accept;
    //button to refuse enquire
    private Button refuse;
    private boolean isServer = true;
    public boolean isRun = true;
    private int portNumber;

    //public constructor like inicjalization list
    public MainServer(int portNumber, TextArea queryTextArea, Button accept, Button refuse)
    {
        this.portNumber = portNumber;
        this.queryTextArea = queryTextArea;
        this.accept = accept;
        this.refuse = refuse;
    }
    //method overriden, becouse MainServer class implement interface Runnable
    public void run(){
        try {
            server = new ServerSocket(portNumber);
            //until server is available accept all of users
            while(isServer) {

                    Socket incoming = server.accept();
                    //creation of streams
                    try {
                        InputStream inStream = incoming.getInputStream();
                        OutputStream outStream = incoming.getOutputStream();
                        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
                        objectOutput = new ObjectOutputStream(outStream);

                        //loop responsible for getting string from input stream
                        String line;
                        while (isRun && (line = in.readLine()) != null) {
                            Pattern pattern = Pattern.compile("$");
                            Matcher matcher = pattern.matcher(line);
                            if (!matcher.matches()) {
                                //setting equire on textarea
                                queryTextArea.setText(line);
                                accept.setDisable(false);
                                refuse.setDisable(true);
                            }
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

            }
        }catch(IOException ex){
            isRun = false;
        }
    }
    //the method responsible for sends records by outputstream
    public void sendAnswerArrayList(ArrayList<ArrayList<String>> answer){
        try {
            objectOutput.writeObject(answer);
            objectOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //the method is helpful with stop server
    public void closeServer(){
        isServer = false;
        isRun = false;
    }
}
