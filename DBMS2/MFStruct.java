//This code is automatically generated.
//Generated Time:Sat 2016.05.07 at 04:20:18 PM EDT.
//
//This code is the Class help implement the methods like avg, sum, max...

public class MFStruct
{
	//all variables...
	String cust;
	String prod;
	int sum_quant_1;
	int sum_quant_2;
	int sum_quant_3;
	int count_1;
	int count_2;
	int count_3;

	//initialize the variables in each group.
	public void init1(String custTmp,String prodTmp,int quant)
	{
		cust = custTmp;
		prod = prodTmp;
		sum_quant_1 = quant;
		count_1 = 1;
	}

	public void init2(String custTmp,String prodTmp,int quant)
	{
		cust = custTmp;
		prod = prodTmp;
		sum_quant_2 = quant;
		count_2 = 1;
	}

	public void init3(String custTmp,String prodTmp,int quant)
	{
		cust = custTmp;
		prod = prodTmp;
		sum_quant_3 = quant;
		count_3 = 1;
	}

	//equals() fucntion.
	public boolean equals(String custTmp,String prodTmp)
	{
		if(cust.equals(custTmp) && prod.equals(prodTmp))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	//sum functions.
	public void set_sum_quant_1(int quantTmp)
	{
		sum_quant_1+=quantTmp;
	}
	public void set_sum_quant_2(int quantTmp)
	{
		sum_quant_2+=quantTmp;
	}
	public void set_sum_quant_3(int quantTmp)
	{
		sum_quant_3+=quantTmp;
	}

	public void set_count_1()
	{
		count_1++;
	}

	public void set_count_2()
	{
		count_2++;
	}

	public void set_count_3()
	{
		count_3++;
	}

}
