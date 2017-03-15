//A pair Structure. A useful tool, to store all kinds of data.
public class Couple<T, U>
{
	//override datatypes
	public T t;
	public U u;

	//constructor
	public Couple()
	{
		t = null;
		u = null;
	}
	
	//override constructor
	public Couple(T t, U u)
	{         
		this.t= t;
		this.u= u;
	}

	//set data
	public void put(T t1, U u1)
	{
		this.t = t1;
		this.u = u1;
	}

	//get first data
	public T getFirst()
	{
		return t;
	}

	//get second data
	public U getSecond()
	{
		return u;
	}
	
	public void setSecond(U u1)
	{
		u = u1;
	}
	
	//check equal
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Couple))
		{
			return false;
		}
		if(obj == this)
		{
			return true;
		}
		if(obj.equals(null))
		{
			return false;
		}
		@SuppressWarnings("unchecked")
		Couple<T,	U> p = (Couple<T, U>)obj;
		if(p.getFirst() == null || p.getSecond() == null)
		{
			System.out.println("Pair NULL Error!");
			return false;
		}
		if(p.getFirst().equals(t) && p.getSecond().equals(u))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}