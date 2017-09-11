package basic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer implements Runnable{
    //from s we create server
	ServerSocket s;
	//number of port which we use to create server
	private int portNumber;
	//arraylist of my sockets
	private static ArrayList<SocketHandler> sockets = new ArrayList<>();
	//index which is helpfull with arraylist
	static int i=0;
	//thread which provide accepting of new sockets
	Thread t;
	MyServer(int n)
	{
		portNumber = n;
	}
	public void run()
	{
		try
		{
		    //I create server adn run new thread which is responsible for accepting new sockets
			s = new ServerSocket(portNumber);
			MainFrame.chatTextArea.append("Server Info: Server started. Port: "+portNumber+"\n");
            t = new Thread(new Runnable() {
                @Override
                public void run() {
					acceptingNewSockets();
				}
            });
            t.start();
		}catch(Exception ex)
		{
			MainFrame.chatTextArea.append(ex+"\n");
		}
	}
	private void acceptingNewSockets(){
	    //method which accepting new sockets and adds their to arraylist of sockets
		try
		{
			while(MainFrame.isServer)
			{
				Socket incoming = s.accept();
				MainFrame.chatTextArea.append("Server Info: New user is connected\n");
                SocketHandler r = new SocketHandler(incoming);

				Thread t = new Thread(r);

				t.start();
				sockets.add(r);
                i++;
			}
		}catch(IOException io){
			sendMessage("Server is closed. Goodbye");
			for(int j=0;j<i;j++)
				sockets.get(0).removeConnect();
		}
	}
	//sending message to all of sockets from arraylist
	static void sendMessage(String msg){
		for(int j=0;j<i;j++)
			sockets.get(j).print(msg);
	}
	//remove socket from arraylist with index "index"
	static void remove(int index)
	{
		sockets.remove(index-1);
		i--;
	}
}
