package base;

public class First {
	static int size=15;
	static int[] array=new int[size];
	public static void main(String[] arg)
	{
		for(int i=0;i<size;i++)
		{
			array[size-1-i]=i;
		}
		QuickSort r = new QuickSort(0, array.length-1);
		Thread t = new Thread(r);
		t.start();
		while(t.isAlive())
		{	
			try {
				Thread.sleep(0,1);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i=0;i<size;i++)
		{
			System.out.print(array[i]+", ");
			if((array[i]-i)!=0) System.out.println("błąd");
		}
	}
}
