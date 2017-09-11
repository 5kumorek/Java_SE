package basic;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
class MainFrame extends JFrame
{
    //button responsible for invoke method to create server
	private JButton createButton;
	//button responsible for invoke method to close server
	private JButton closeButton;
	//button responsible for invoke method to interrupt connect with server
	private JButton interruptButton;

	//textArea which display conversation
    static JTextArea chatTextArea;
    //textArea which display what user want send to server
	private static JTextArea sendTextArea;
	//textfield where host type server port
    private static JTextField portServer;
    //textfield where user type server port
	private static JTextField portUser;
	//printWriter allow send text from client to server
    private PrintWriter out;
    //two boolean variable, which tell as if server and socked work
	private static boolean isSocket = false;
	static boolean isServer = false;
	//my server
	private MyServer server;
	//thread responsible for connecting with server
	private Thread connecting;
	//my socket which is connecting to server
	private Socket socket;

	private static final long serialVersionUID = -9205796134737778342L;

	MainFrame(String name)
	{
	    //sets for MyFrame
		super(name);
		setBounds(400, 100, 700, 300);

		//I compose fields responsible for createing serverSocked
        JLabel logo = new JLabel("RadoslawIT        ");
		logo.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        JLabel hostServerLabel = new JLabel("Create server ");
		portServer = new JTextField(10);
		createButton = new JButton("CREATE");
        createButton.setEnabled(false);
		closeButton = new JButton("CLOSE");
        closeButton.setEnabled(false);

        JPanel connectPanelServer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        connectPanelServer.add(logo);
        connectPanelServer.add(hostServerLabel);
        connectPanelServer.add(portServer);
        connectPanelServer.add(createButton);
        connectPanelServer.add(closeButton);

        //I compose fields responsible for establish connecting with server
        JLabel connectToPortLabel = new JLabel("Connect to port:");
        portUser = new JTextField(10);
        JLabel connectToHostLabel = new JLabel("Connect to host:");
        JTextField ipUser = new JTextField(10);
		JButton connectButton = new JButton("CONNECT");
        connectButton.setEnabled(false);
		interruptButton = new JButton("BREAK");
        interruptButton.setEnabled(false);

        JPanel connectPanelUser = new JPanel(new FlowLayout(FlowLayout.LEFT));
        connectPanelUser.add(connectToPortLabel);
        connectPanelUser.add(portUser);
        connectPanelUser.add(connectToHostLabel);
        connectPanelUser.add(ipUser);
        connectPanelUser.add(connectButton);
        connectPanelUser.add(interruptButton);

        //and compose panel responsible for server and connecting with server
        JPanel connectPanel = new JPanel(new BorderLayout());
        connectPanel.add(connectPanelServer, BorderLayout.NORTH);
        connectPanel.add(connectPanelUser, BorderLayout.SOUTH);

        //I compose part of my frame, which is responsible for conversation
		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
        JScrollPane chatScrollPanel = new JScrollPane(chatTextArea);
        chatScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		sendTextArea = new JTextArea();
		sendTextArea.setRows(2);
        JScrollPane sendScrollPanel = new JScrollPane(sendTextArea);
        sendScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //and at the end compose MyFrame
        add(connectPanel, BorderLayout.NORTH);
        add(chatScrollPanel, BorderLayout.CENTER);
        add(sendScrollPanel, BorderLayout.SOUTH);


		//two further threads are created becouse I want check if number of port is correct
		Runnable availabilityCreateButton = new CheckAvailability(createButton, portServer, isServer);
		Thread threadCreateButton = new Thread(availabilityCreateButton);
        threadCreateButton.start();

		Runnable availabilityConnectButton = new CheckAvailability(connectButton, portUser, isSocket);
		Thread threadConnectButton = new Thread(availabilityConnectButton);
        threadConnectButton.start();
		
		createButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
			    //I get number of port from portServer and create new MyServer
				int number = Integer.parseInt(portServer.getText());
				server = new MyServer(number);
				Thread t = new Thread(server);
				t.start();
				createButton.setEnabled(false);
				closeButton.setEnabled(true);
				isServer = true;
			}
		});
		closeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
			    //I closed all sockets associated with MyServer
				server.t.interrupt();
				try {server.s.close();}
				catch (IOException e1) {e1.printStackTrace();}
				isServer = false;
				closeButton.setEnabled(false);
			}
		});
		connectButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				//I establish connection with server
				interruptButton.setEnabled(true);
				connecting = new Thread(new Runnable()
				{
					public void run()
					{
						try
						{
						    //connecting with server and creation of input and output
							socket = new Socket(ipUser.getText(), Integer.parseInt((portUser.getText())));
							BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							out = new PrintWriter(socket.getOutputStream(), true);
							chatTextArea.append(br.readLine()+"\n");

							//I started new thread which will be receiving messages form server
							Thread t = new Thread(new Runnable() {
								@Override
								public void run() {
									String line;
									try{
										while ((line = br.readLine())!= null){
											chatTextArea.append(line+"\n");
										}
									} catch (IOException e1) {
										chatTextArea.append("Connect refused\n");
									}
								}
							});
							t.start();

							//program checking sendTextArea and if user type enter program send string to serwer
							String line;
							String patternString = ".*[\n\r]";
							while(sendTextArea.getText()!=null)
							{
								line = sendTextArea.getText();
								Pattern pattern = Pattern.compile(patternString);//, Pattern.MULTILINE);
								Matcher matcher = pattern.matcher(line);
								if (matcher.matches())
								{
									out.println(line);
									sendTextArea.setText("");
								}
							}
						}catch(IOException ex)
						{
							chatTextArea.append("Connection is not possible\n"+ex+"\n");
						}
					}
				});
				connecting.start();
			}});
		
		interruptButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
			    //click on interrupt button break connection with server
				isSocket = false;
				interruptButton.setEnabled(false);
				try {
					out.println("bye");
					socket.close();
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				connecting.interrupt();
			}});
	}
	
}
