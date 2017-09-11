import java.io.Serializable;

public class Employee implements Serializable{
	int liczba1, liczba2, liczba3;
	String name;
	public Employee(String imie, int l1, int l2, int l3)
	{
		this.name = imie;
		this.liczba1=l1;
		this.liczba2=l2;
		this.liczba3=l3;
	}
	public String getName()
	{
		return name;
	}
	public int getLiczba1()
	{
		return liczba1;
	}
	public int getLiczba2()
	{
		return liczba2;
	}
	public int getLiczba3()
	{
		return liczba3;
	}
}
