//Deal with original file input, save them into ArrayLists.

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ParseforMFStructure
{
	//To store all the data from the file.
	public ArrayList<String> select_attributes = new ArrayList<String>();
	public int grouping_variables = 0;
	public ArrayList<String> grouping_attributes = new ArrayList<String>();
	public ArrayList<String> grouping_aggregates = new ArrayList<String>();
	public ArrayList<String> suchthat_condition = new ArrayList<String>();
	
	//Constructor...
	public ParseforMFStructure(ParseforMFStructure orig)
	{
		this.select_attributes = orig.select_attributes;
		this.grouping_variables = orig.grouping_variables;
		this.grouping_attributes = orig.grouping_attributes;
		this.grouping_aggregates = orig.grouping_aggregates;
		this.suchthat_condition = orig.suchthat_condition;
	}
	
	//Override constructor...
	public ParseforMFStructure()
	{
		this.select_attributes = new ArrayList<String>();
		this.grouping_variables = 0;
		this.grouping_attributes = new ArrayList<String>();
		this.grouping_aggregates = new ArrayList<String>();
		this.suchthat_condition = new ArrayList<String>();
	}
	
	//convert everything to lowercase as sql is case insensitive.
		private ArrayList<String> toLowerCase(ArrayList<String> lst)
		{
			for (int i = 0; i != lst.size(); i++)
			{
				lst.set(i, lst.get(i).toLowerCase());
			}
			return lst;
		}

	
	//set select attributes from file
	public void setSelectAttributes(BufferedReader br, String parseLine)
	{
		
		try
		{
			parseLine = br.readLine();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		int j = 0;
		for(int i = 0; i != parseLine.length(); i++)
		{
			if(parseLine.charAt(i) == ',')
			{
				if(j == 0)
				{
					String strAttr = parseLine.substring(j, i);
					
					this.select_attributes.add(strAttr);
					j = i;
				}
				else
				{
					while(parseLine.charAt(j) == ',' || parseLine.charAt(j) == ' ')
					{
						j++;
					}
					String strAttr = parseLine.substring(j, i);
					
					this.select_attributes.add(strAttr);
					j = i;
				}
			}
			else if(i == parseLine.length() - 1)
			{
				while(parseLine.charAt(j) == ',' || parseLine.charAt(j) == ' ')
				{
					j++;
				}
				String strAttr = parseLine.substring(j, i + 1);
				this.select_attributes.add(strAttr);
			}
		}
		select_attributes = this.toLowerCase(select_attributes);
	}
	
	//set grouping attributes number read from  file
	public void setGroupingAttrNumber(String parseLine)
	{
		this.grouping_variables = Integer.parseInt(parseLine);
	}
	
	
	
	//set grouping attributes from file input version.
	public void setGroupingAttrs(BufferedReader br, String parseLine)
	{
		try
		{
			parseLine = br.readLine();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		int j = 0;
		for(int i = 0; i != parseLine.length(); i++)
		{
			if(parseLine.charAt(i) == ',')
			{
				if(j == 0)
				{
					String strAttr = parseLine.substring(j, i);
					this.grouping_attributes.add(strAttr);
					j = i;
				}
				else
				{
					while(parseLine.charAt(j) == ',' || parseLine.charAt(j) == ' ')
					{
						j++;
					}
					String strAttr = parseLine.substring(j, i);
					this.grouping_attributes.add(strAttr);
					j = i;
				}
			}
			else if(i == parseLine.length() - 1)
			{
				while(parseLine.charAt(j) == ',' || parseLine.charAt(j) == ' ')
				{
					j++;
				}
				String strAttr = parseLine.substring(j, i + 1);
				this.grouping_attributes.add(strAttr);
			}
		}
		grouping_attributes = this.toLowerCase(grouping_attributes);
	}
	
	
	//file version input for f vector.
	public void setFV(BufferedReader br, String parseLine)
	{
		try
		{
			parseLine = br.readLine();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		int j = 0;
		for(int i = 0; i != parseLine.length(); i++)
		{
			if(parseLine.charAt(i) == ',')
			{
				if(j == 0)
				{
					String strAttr = parseLine.substring(j, i);
					this.grouping_aggregates.add(strAttr);
					j = i;
				}
				else
				{
					while(parseLine.charAt(j) == ',' || parseLine.charAt(j) == ' ')
					{
						j++;
					}
					String strAttr = parseLine.substring(j, i);
					this.grouping_aggregates.add(strAttr);
					j = i;
				}
			}
			else if(i == parseLine.length() - 1)
			{
				while(parseLine.charAt(j) == ',' || parseLine.charAt(j) == ' ')
				{
					j++;
				}
				String strAttr = parseLine.substring(j, i + 1);
				this.grouping_aggregates.add(strAttr);
			}
		}
		grouping_aggregates = this.toLowerCase(grouping_aggregates);
	}
	
	
	//conditions file input version
	public void setConditions(BufferedReader br, String parseLine)
	{
		try
		{
			parseLine = br.readLine();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		try {
			do
			{
				int j = 0;
				for(int i = 0; i != parseLine.length(); i++)
				{
					if(parseLine.charAt(i) == ',')
					{
						if(j == 0)
						{
							String strAttr = parseLine.substring(j, i);
							this.suchthat_condition.add(strAttr);
							j = i;
						}
						else
						{
							while(parseLine.charAt(j) == ',' || parseLine.charAt(j) == ' ')
							{
								j++;
							}
							String strAttr = parseLine.substring(j, i);
							this.suchthat_condition.add(strAttr);
							j = i;
						}
					}
					else if(i == parseLine.length() - 1)
					{
						while(parseLine.charAt(j) == ',' || parseLine.charAt(j) == ' ')
						{
							j++;
						}
						String strAttr = parseLine.substring(j, i + 1);
						this.suchthat_condition.add(strAttr);
					}
				}
			}
			while((parseLine = br.readLine()) != null);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		suchthat_condition = this.toLowerCase(suchthat_condition);
	}
	
	
}
