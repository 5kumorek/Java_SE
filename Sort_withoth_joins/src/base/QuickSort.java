package base;

public class QuickSort implements Runnable{

	private int last;
	private int first;

	QuickSort(int f, int l)
	{
		last=l;
		first=f;
	}
	public void run()
	{
		int i = first;
		int j = last;
		int v = First.array[(first + last) / 2];
		do {
		while (First.array[i]< v)
		i++;
		while (v <First.array[j])
		j--;
		if (i <= j) {
			int temp = First.array[i];
		First.array[i]=First.array[j];
		First.array[j]= temp;
		i++;
		j--;
		}
		}
		while (i <= j);
		if (first< j)
		{
			QuickSort r = new QuickSort(first, j);
			Thread t = new Thread(r);
			t.start();
		}
		if (i <last)
		{
			QuickSort r2 = new QuickSort(i, last);
			Thread t2 = new Thread(r2);
			t2.start();
		}
	}
}
