//This class parses the select condition vector

import java.util.ArrayList;
import java.util.Arrays;

public class SelectConditionsVector
{
	ParseforMFStructure mfstruct;
	ArrayList<String> lstConVects = new ArrayList<String>();
	ArrayList<Couple<Integer, String>> listconditions = new ArrayList<Couple<Integer, String>>();
	DBInformationSchema info;
	
	//Constructor!
	public SelectConditionsVector(ParseforMFStructure orig, DBInformationSchema info_Orig)
	{
		mfstruct = orig;
		this.info = info_Orig;
		this.lstConVects = orig.suchthat_condition;
		this.setCodeList();
	}
	
	//set code list
	private void setCodeList()
	{
		for(int i = 0; i != this.lstConVects.size(); i++)
		{
			String tmpStr = this.lstConVects.get(i);
			this.setPairIntString(tmpStr);
		}
	}
	
	//set int-string pair
	private void setPairIntString(String tmpStr)
	{		
		int target = -1;
		String finish_parsedStr = new String();
		
		String parsedStr = tmpStr;
		
		parsedStr = parsedStr.replace('\'', '\"');

		for(int i1 = 0; i1 != info.getList().size(); i1++)
		{
			String subName = info.getList().get(i1).getFirst();
			parsedStr = parsedStr.replaceAll(subName, subName + "Tmp");
		}
		
		
		for(int i = 0; i != tmpStr.length(); i++)
		{
			if (tmpStr.charAt(i) == '.')
			{
				String tmp = tmpStr.substring(0,i);
				target = Integer.parseInt(tmp);
				tmp = tmp+"\\.";
				parsedStr = parsedStr.replaceAll(tmp,"");	
				break;
			}
		}	
		
		String[] tempStrings = parsedStr.split(" "); 
		ArrayList<String> strings = new ArrayList<String>(Arrays.asList(tempStrings));
		
		// Deal with and, or, '='...
		for(int j = 0; j != strings.size(); j++)
		{
			String tempStr = strings.get(j);
			if (tempStr.equals("and"))
			{
				finish_parsedStr += "&&";
			}
			else if (tempStr.equals("or"))
			{
				finish_parsedStr += "||";
			}
			else if (tempStr.contains("="))
			{
				String type = new String();
				for(int i2 = 0; i2 != info.getList().size(); i2++)
				{
					String subName = info.getList().get(i2).getFirst();
					if (tempStr.contains(subName))
					{
						type = info.getTypeFromColumn(subName);
					}
				}
				if(!tempStr.contains(">") && 
						!tempStr.contains("<") &&
						!tempStr.contains("!"))
				{
					if (type.equals("String"))
					{
						tempStr = tempStr.replaceFirst("=", ".equalsIgnoreCase(");
						tempStr += ")";
					}
					else
					{
						tempStr = tempStr.replaceFirst("=", "==");
					}
				}
				else if (tempStr.contains("!"))
				{
					if (type.equals("String"))
					{
						tempStr = tempStr.replaceFirst("!=", ".equalsIgnoreCase(");
						tempStr += ")";
						tempStr = "!" + tempStr;
					}
				}
				finish_parsedStr += tempStr;
			}
			else 
			{		
				finish_parsedStr += tempStr;
			}
		}
		this.listconditions.add(new Couple<Integer, String>(target, finish_parsedStr));
	}
}
