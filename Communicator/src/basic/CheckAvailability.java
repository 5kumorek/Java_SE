package basic;

import javax.swing.JButton;
import javax.swing.JTextField;

public class CheckAvailability implements Runnable {
	private JButton tempButton;
	private JTextField tempField;
	private boolean isStart;
	CheckAvailability(JButton button, JTextField field, boolean start)
	{
		isStart = start;
		tempButton = button;
		tempField = field;
	}
	public void run() 
	{
		boolean compare= false;
		String expression ="[0-9][0-9][0-9][0-9]";
		while(true)
		{
			try{
			    compare = tempField.getText().matches(expression);
			}catch(Exception ex){
			    ex.printStackTrace();
            }
			if(compare && !isStart)
			{
				tempButton.setEnabled(true);
			}else
			{
				tempButton.setEnabled(false);
			}
		}
	}
}
