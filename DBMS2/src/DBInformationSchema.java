//Check Informations_schema from database

import java.util.ArrayList;

public class DBInformationSchema
{
	//to store the type-name pair of every column in the table
	private ArrayList<Couple<String, String>> lstPair = new ArrayList<Couple<String, String>>();

	public void addValue(String column, String type)
	{
		if(column != null && type != null)
		{
			Couple<String, String> pairColType = new Couple<String, String>();
			pairColType.put(column, type);
			this.lstPair.add(pairColType);
		}
	}
	
	
	//JAVA type code, ready to go.
	public void setStructTypeJAVA()
	{
		for(int i = 0; i != lstPair.size(); i++)
		{
			String typeOrigName = lstPair.get(i).getSecond();
			if(typeOrigName.equals("character varying"))
			{
				lstPair.get(i).setSecond("String");
			}
			else if(typeOrigName.equals("integer"))
			{
				lstPair.get(i).setSecond("int");
			}
			else if(typeOrigName.equals("character"))
			{
				lstPair.get(i).setSecond("String");
			}
		}
	}
	
	//get the type from the column name
	public String getTypeFromColumn(String column)
	{
		for(int i = 0; i != lstPair.size(); i++)
		{
			if(lstPair.get(i).getFirst().equals(column))
			{
				return lstPair.get(i).getSecond();
			}
		}
		return null;
	}
	
	//return pair list.
	public ArrayList<Couple<String, String>> getList()
	{
		return lstPair;
	}
}
