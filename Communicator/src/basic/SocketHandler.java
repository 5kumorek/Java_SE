package basic;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketHandler implements Runnable{
    //incoming is socket which want establish connection with server
	private Socket incoming;
    //numberOfUsers tell as how many users would connect to as
    private static int numberOfUsers=0;
    //userIndex means userIndex
    private int userIndex;
    //indexInArrayList tell as which number have socket in myServers arraylist
    private int indexInArrayList;
    //in read messages from user
    private BufferedReader in;
    //out write messages for user
    private PrintWriter out;
	SocketHandler(Socket i)
	{
		incoming = i;
	}
	public void run()
	{
		try
		{
		    //I set proper values and run thread which will be listening
            InputStream input = incoming.getInputStream();
            OutputStream output = incoming.getOutputStream();

            userIndex = ++SocketHandler.numberOfUsers;
            indexInArrayList = MyServer.i;
            in = new BufferedReader(new InputStreamReader(input));
            out = new PrintWriter(output, true);
            out.println("Server Info: Hello! Enter BYE to exit." );
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    listening();
                }
            });
            t.start();
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}
	private void listening()
    {
        //listening read messages from in and sends to server.
        //if messages is bye, listening is interrupt and connection is refused
        try {
            String line;
            boolean done = true;
            while (MainFrame.isServer && done && (line = in.readLine()) != null) {
                Pattern pattern = Pattern.compile("$");//, Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(line);
                if (!matcher.matches()) {
                    MyServer.sendMessage("User "+userIndex+": "+line);
                    //System.out.println("Serv:" + line);
                }
                if (line.trim().toLowerCase().equals("bye")) done = false;
            }
            //removing current connection
            if(MainFrame.isServer)
                removeConnect();
        }catch(IOException ex){
            //ex.printStackTrace();
        }
    }
    //this method closes in,out stream and remove current SocketHandler
    void removeConnect(){
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
        MyServer.remove(indexInArrayList);
    }
    //sending String to user
	void print(String line){
	    out.println(line);
    }
}
