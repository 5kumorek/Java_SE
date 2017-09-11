package base;
//elo
//taki test
public class First {
	public static void main(String[] arg)
	{
	    int size=13;
		int array[] = new int[size];
		for(int i=0;i<size;i++)
		{
			array[size-1-i]=i;
		}
		QuickSort r = new QuickSort( array,0, array.length-1);
		Thread t = new Thread(r);
		t.start();
		try {
            t.join();
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
		array = r.getArray();
		for(int i=0;i<size;i++)
		{
			System.out.print(array[i]+", ");
			if((array[i]-i)!=0) System.out.println("błąd");
		}
	}
}
