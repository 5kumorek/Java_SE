package base;
import org.apache.commons.lang.ArrayUtils;
import java.util.Arrays;

public class QuickSort implements Runnable{
	private int last;
    private int first;
    private int array[];
	QuickSort(int [] tempArray, int f, int l)
	{
		array= tempArray;
		last=array.length-1;
		first=0;

	}
	public void run()
	{
        int i = first;
        int j = last;
        int v = array[(first + last) / 2];
		do {
			while (array[i]< v)
			i++;
			while (v <array[j])
			j--;
			if (i <= j) {
                int temp = array[i];
				array[i]=array[j];
				array[j]= temp;
				i++;
				j--;
			}
		} while (i <= j);
		if (first< j)
		{
            QuickSort r = new QuickSort(Arrays.copyOfRange(array, first, j + 1), 0, j);
			Thread t = new Thread(r);
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            array=ArrayUtils.addAll(r.getArray(), Arrays.copyOfRange(array, j +1,last+1));
		}
		if (i <last)
		{
            QuickSort r2 = new QuickSort(Arrays.copyOfRange(array, i, last + 1), 0, last - i);
			Thread t2 = new Thread(r2);
			t2.start();
			try {
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            array=ArrayUtils.addAll(Arrays.copyOfRange(array,first, i), r2.getArray());
		}
	}
	int[] getArray()
	{
		return array;
	}
}