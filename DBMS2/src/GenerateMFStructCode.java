//This file is used to generate "MFStruct.java" file

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GenerateMFStructCode
{
	//declare arraylists
	private ArrayList<Couple<String, String>> lstAllTypeName = null;
	private ArrayList<Couple<String, String>> lstGroupingTypeName = null;
	private ArrayList<Couple<String, String>> lstVFVar = null;
	private ArrayList<String> lstOrigFunctions = new ArrayList<String>();
	private ArrayList<String> lstOrigHavFunctions = new ArrayList<String>();
	private ArrayList<Couple<Integer, String>> lstFunctionNumberNames = new ArrayList<Couple<Integer, String>>();
	private String struct_str;
	private String class_str;
	private String strInitFunction;

	//Constructor.
	public GenerateMFStructCode()
	{
		struct_str = null;
		class_str = null;
		strInitFunction = null;
	}

	//override constructor.
	public GenerateMFStructCode(GenerateMFStructCode orig)
	{
		this.lstAllTypeName = orig.lstAllTypeName;
		this.lstGroupingTypeName = orig.lstGroupingTypeName;
		this.lstVFVar = orig.lstVFVar;
		this.lstOrigFunctions = orig.lstOrigFunctions;
		this.lstFunctionNumberNames = orig.lstFunctionNumberNames;
		this.struct_str = orig.struct_str;
		this.class_str = orig.class_str;
		this.strInitFunction = orig.strInitFunction;
		this.lstOrigHavFunctions = orig.lstOrigHavFunctions;
	}
	
	//get lstFunctionNumberNames
	public ArrayList<Couple<Integer, String>> getFunctionList()
	{
		return this.lstFunctionNumberNames;
	}
	
	//get lstGroupingTypeName Pair<String, String> type name.
	public ArrayList<Couple<String, String>> getGroupingTypeNameList()
	{
		return this.lstGroupingTypeName;
	}
	
	
	//get strInitFunction;
	public String getInitFunctionString()
	{
		return this.strInitFunction;
	}
	
	//return lstAllTypeName, type name.
	public ArrayList<Couple<String, String>> getAllTypeNameList()
	{
		return this.lstAllTypeName;
	}
	
	//Struct String.
	public void setstruct_string(ParseforMFStructure mforig, DBInformationSchema info)
	{
		struct_str = "struct mf_struct\n{\n";
		String columnName = null;
		for(int i = 0; i != mforig.select_attributes.size(); i++)
		{
			struct_str += "\t";
			String columnOrig = mforig.select_attributes.get(i);
			columnName = getColumnName(columnOrig);
			String type = info.getTypeFromColumn(columnName);
			struct_str += type;
			struct_str += " ";
			struct_str += convertVariableName(columnOrig);
			if(columnOrig.equals("cust"))
			{
				struct_str += "[10]";
			}
			struct_str += ";\n";
		}
		struct_str += "};\n";
	}

	//set JAVA Class String...
	public void setClassString(ParseforMFStructure mforig, DBInformationSchema info)
	{
		//get list of functions
		for(int i = 0; i != mforig.grouping_aggregates.size(); i++)
		{
			if(mforig.grouping_aggregates.get(i).contains("avg") && lstOrigFunctions.contains("avg") == false)
			{
				lstOrigFunctions.add("avg");
			}
			if(mforig.grouping_aggregates.get(i).contains("max") && lstOrigFunctions.contains("max") == false)
			{
				lstOrigFunctions.add("max");
			}
			if(mforig.grouping_aggregates.get(i).contains("count") && lstOrigFunctions.contains("count") == false)
			{
				lstOrigFunctions.add("count");
			}
			if(mforig.grouping_aggregates.get(i).contains("min") && lstOrigFunctions.contains("min") == false)
			{
				lstOrigFunctions.add("min");
			}
			if(mforig.grouping_aggregates.get(i).contains("sum") && lstOrigFunctions.contains("sum") == false)
			{
				lstOrigFunctions.add("sum");
			}
		}

		lstAllTypeName = new ArrayList<Couple<String, String>>();
		//Add head comments.
		
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

		class_str = "//This code is automatically generated.\n";
		class_str += "//Generated Time:" + ft.format(dNow) + ".\n";
		class_str += "//\n";
		class_str += "//This code is the Class help implement the methods like avg, sum, max...\n\n";
		
		//
		class_str += "public class ";
		class_str += "MFStruct\n{\n";
		class_str += "\t//all variables...\n";
		String columnOrig = null;

		for(int i = 0; i != mforig.select_attributes.size(); i++)
		{
			columnOrig = mforig.select_attributes.get(i);
			String columnName = getColumnName(columnOrig);
			if(columnOrig.contains("count") == true)
			{
				continue;
			}
			String type = info.getTypeFromColumn(columnName);
			Couple<String, String> pairTypeName = new Couple<String, String>(type, 
					convertVariableName(columnOrig));
			if(this.lstAllTypeName.contains(new Couple<String, String>(type,
					convertVariableName(columnOrig))) == true)
			{
				continue;
			}
			class_str += "\t";
			class_str += type;
			class_str += " ";
			class_str += convertVariableName(columnOrig);
			class_str += ";\n";
			lstAllTypeName.add(pairTypeName);
			//Add sum_variables if not exist
			if(columnOrig.contains("max")||
					columnOrig.contains("avg")||columnOrig.contains("min")||
					columnOrig.contains("count") && columnOrig.contains("sum") == false)
			{
				String subName = this.SumSubstring(columnOrig);
				Couple<String, String> pairTypeName0 = new Couple<String, String>(type, "sum_" + 
						subName + "_" + this.getFunctionNameFirstNumber(columnOrig));
				if(this.lstAllTypeName.contains(pairTypeName0) == true)
				{
					continue;
				}
				else
				{
					class_str += "\t" + type + " sum_" + subName;
					class_str += "_" + this.getFunctionNameFirstNumber(columnOrig) + ";\n";
					lstAllTypeName.add(pairTypeName0);
				}
			}
		}

		//Add count_variables...
		for(int i = 0; i != mforig.grouping_variables; i++)
		{
			class_str = class_str + "\tint count_" + Integer.toString(i+1) + ";\n";
			Couple<String, String> pairTypeName = new Couple<String, String>("int", "count_" + 
					Integer.toString(i+1));
			lstAllTypeName.add(pairTypeName);
		}

		this.setInitVar(mforig, info);
		this.setGroupingTypeName(mforig, info);
		class_str = this.addJavaInitFunction(mforig, info, class_str);
		class_str = this.addJavaEqualsFuction(class_str);
		//Add sum function(s)
		if(lstOrigFunctions.contains("sum"))
		{
			class_str = this.addJavaSumFunction(class_str);
		}
		//Add avg function(s)
		if(lstOrigFunctions.contains("avg"))
		{
			class_str = this.addJavaAvgFunction(class_str);
		}
		//Add max function(s) and min function(s)
		if(lstOrigFunctions.contains("max"))
		{
			class_str = this.addJavaMaxFunction(class_str);
		}
		if(lstOrigFunctions.contains("min"))
		{
			class_str = this.addJavaMinFunction(class_str);
		}
		
		//Add all set_count_* functions...
		for(int i = 0; i != mforig.grouping_variables; i++)
		{
			class_str += "\n\tpublic void ";
			String functionName = "set_count_" + new Integer(i+1);
			class_str += functionName + "()\n";
			class_str += "\t{\n";
			class_str += "\t\tcount_" + new Integer(i+1) + "++;\n";
			class_str += "\t}\n";
		}
		//This is the end.
		class_str += "\n}\n";
	}

	public String getStructStr()
	{
		return struct_str;
	}

	public String getClassStr()
	{
		return class_str;
	}

	private String getColumnName(String str)
	{
		int j = 0;
		for(int i = str.length() - 1; i != 0; i--)
		{
			if(str.charAt(i) == '_')
			{
				j = i;
				break;
			}
			if(i == 1 && str.charAt(0) != '_')
			{
				return str;
			}
		}
		str = str.substring(j + 1, str.length());
		return str;
	}

	//Convert from 1_avg_length to avg_length_1
	public String convertVariableName(String name)
	{
		String numberStr = null;
		if(Character.isDigit(name.charAt(0)) )
		{
			int j = 0;
			for(int i = 0; i != name.length(); i++)
			{
				if(name.charAt(i) == '_')
				{
					j = i;
					break;
				}
			}
			numberStr = name.substring(0, j);
			name = name.substring(j + 1, name.length());
		}
		if(numberStr != null)
		{
			name += "_";
			name += numberStr;
		}
		return name;
	}

	private String SumSubstring(String name)
	{
		String sumColumnNameStr = null;
		if(Character.isDigit(name.charAt(0)))
		{
			int j = 0;
			for(int i = name.length(); i != 0; i--)
			{
				if(name.charAt(i - 1) == '_')
				{
					j = i;
					break;
				}
			}
			sumColumnNameStr = name.substring(j, name.length());
		}
		return sumColumnNameStr;
	}

	//Generate the initialization functions
	private String addJavaInitFunction(ParseforMFStructure mforig, DBInformationSchema info, String class_str)
	{
		class_str += "\n\t//initialize the variables in each group.";
		for(int j = 0; j != mforig.grouping_variables; j++)
		{
			class_str += "\n\tpublic void init" + new Integer(j+1) + "(";
			this.strInitFunction = new String();
			for(int i = 0; i != lstGroupingTypeName.size(); i++)
			{
				String type = lstGroupingTypeName.get(i).getFirst();
				String name = lstGroupingTypeName.get(i).getSecond();
				this.strInitFunction += name + "Tmp" + ",";
				class_str += type;
				class_str += " ";
				class_str += name;
				class_str += "Tmp";
				class_str += ",";
			}
			for(int i = 0; i != lstVFVar.size(); i++)
			{
				String type = lstVFVar.get(i).getFirst();
				String name = lstVFVar.get(i).getSecond();
				this.strInitFunction += name + "Tmp";
				class_str = class_str + type + " " + name;
				if(i != lstVFVar.size() - 1)
				{
					this.strInitFunction += ",";
					class_str += ",";
				}
			}
			this.strInitFunction += ");\n";
			class_str += ")\n\t{\n";
			for(int i = 0; i != lstAllTypeName.size(); i++)
			{
				String varName = lstAllTypeName.get(i).getSecond();
				if(Character.isDigit(varName.charAt(varName.length() - 1)) == false
						|| this.getVariableNumber(varName) == j + 1)
				{
					class_str += "\t\t";
					class_str += varName;
					class_str += " = ";
					String type = info.getTypeFromColumn(varName);
					if(lstGroupingTypeName.contains(new Couple<String, String>(type, varName)) == true)
					{
						class_str += varName;
						class_str += "Tmp";
					}
					type = info.getTypeFromColumn(myInitSubString(varName));
					if(lstVFVar.contains(new Couple<String, String>(type,
							this.myInitSubString(varName))) == true)
					{
						class_str += this.myInitSubString(varName);
					}
					if(varName.contains("count"))
					{
						class_str += "1";
					}
					class_str += ";\n";	
				}
			}
			class_str += "\t}\n";
		}
		return class_str;
	}

	private void setInitVar(ParseforMFStructure mforig, DBInformationSchema info)
	{
		lstVFVar = new ArrayList<Couple<String, String>>();
		for(int i = 0; i != mforig.grouping_aggregates.size(); i ++)
		{
			int i0 = 0;
			if(Character.isDigit(mforig.grouping_aggregates.get(i).charAt(0)))
			{
				String tmp = mforig.grouping_aggregates.get(i);
				for(int j = tmp.length(); j != 0; j--)
				{
					if(tmp.charAt(j - 1) == '_')
					{
						i0 = j;
						break;
					}
				}
				String name = tmp.substring(i0, tmp.length());
				String type = info.getTypeFromColumn(name);
				Couple<String, String> pairTypeName = new Couple<String, String>(type, name);
				if(lstVFVar.contains(pairTypeName) == false)
				{
					lstVFVar.add(pairTypeName);
				}
			}
		}
	}

	private void setGroupingTypeName(ParseforMFStructure mforig, DBInformationSchema info)
	{
		lstGroupingTypeName = new ArrayList<Couple<String, String>>();
		for(int i = 0; i != mforig.grouping_attributes.size(); i++)
		{
			String name = mforig.grouping_attributes.get(i);
			String type = info.getTypeFromColumn(name);
			Couple<String, String> pairNameType = new Couple<String, String>(type, name);
			if(lstGroupingTypeName.contains(pairNameType) == false)
			{
				lstGroupingTypeName.add(pairNameType);
			}
		}
	}

	//cut out the avg_ or sum_ only really variable names are left.
	//avg_length_1 => length
	private String myInitSubString(String varName)
	{
		String tmpName = null;
		int j = 0;
		for(int i = 0; i != varName.length(); i++)
		{
			if(varName.charAt(i) == '_' && j == 0)
			{
				j = i;
				continue;
			}
			if(varName.charAt(i) == '_' && j != 0)
			{
				tmpName = varName.substring(j + 1, i);
				break;
			}
		}
		return tmpName;
	}

	//Generate equals() function.
	private String addJavaEqualsFuction(String class_str)
	{
		String tmpStr = class_str;
		tmpStr += "\n\t//equals() fucntion.\n";
		tmpStr += "\tpublic boolean equals(";
		for(int i = 0; i != this.lstGroupingTypeName.size(); i++)
		{
			tmpStr += this.lstGroupingTypeName.get(i).getFirst();
			tmpStr += " ";
			tmpStr += this.lstGroupingTypeName.get(i).getSecond();
			tmpStr += "Tmp";
			if(i != this.lstGroupingTypeName.size() - 1)
			{
				tmpStr += ",";
			}
		}
		tmpStr += ")\n\t{\n";
		tmpStr += "\t\tif(";
		for(int i = 0; i != this.lstGroupingTypeName.size(); i++)
		{
			tmpStr += this.lstGroupingTypeName.get(i).getSecond();
			tmpStr += ".equals(";
			tmpStr += this.lstGroupingTypeName.get(i).getSecond();
			tmpStr += "Tmp)";
			if(i != this.lstGroupingTypeName.size() - 1)
			{
				tmpStr += " && ";
			}
		}
		tmpStr += ")\n\t\t{\n";
		tmpStr += "\t\t\treturn true;\n";
		tmpStr += "\t\t}\n";
		tmpStr += "\t\telse\n\t\t{\n";
		tmpStr += "\t\t\treturn false;\n";
		tmpStr += "\t\t}\n\t}\n";
		return tmpStr;
	}

	//Get variable number
	//avg_length_1 => 1
	private int getVariableNumber(String name)
	{
		int ireturn = 0;
		for(int i = name.length(); i != 0 ; i--)
		{
			if(name.charAt(i - 1) == '_')
			{
				String sub = name.substring(i, name.length());
				ireturn = Integer.parseInt(sub);
				break;
			}
			else
			{
				continue;
			}
		}
		if(ireturn == 0)
		{
			System.out.println("Variable Number Error!");
		}
		return ireturn;
	}

	//Add avg Functions
	private String addJavaAvgFunction(String class_str)
	{
		class_str += "\n\t//average functions.\n";
		for(int i = 0; i != this.lstAllTypeName.size(); i++)
		{
			if(this.lstAllTypeName.get(i).getSecond().contains("avg"))
			{
				String name = this.lstAllTypeName.get(i).getSecond();
				String type = this.lstAllTypeName.get(i).getFirst();
				class_str += "\tpublic void ";
				String functionName = "set_" + name;
				class_str += functionName;
				this.lstFunctionNumberNames.add(new Couple<Integer, String>(
						new Integer(this.getVariableNumber(functionName)), functionName));
				class_str += "(";
				class_str += type;
				class_str += " ";
				class_str += this.myInitSubString(name);
				class_str += "Tmp)\n";
				String shortName = this.myInitSubString(name);
				class_str += "\t{\n";
//				class_str += "\t\tcount_" + this.getVariableNumber(name) + "++;\n";
//				class_str += "\t\tsum_" + shortName + "_" + this.getVariableNumber(name) + " += " + 
//						shortName + "Tmp;\n";
				class_str += "\t\t" + name + " = " + "sum_" + shortName + "_"
							+ this.getVariableNumber(name) + 
						" / count_" + this.getVariableNumber(name) + ";\n";
				class_str += "\t}\n";
			}
		}
		return class_str;
	}

	//Gnerate sum functions.
	private String addJavaSumFunction(String class_str)
	{
		class_str += "\n\t//sum functions.\n";
		for(int i = 0; i != this.lstAllTypeName.size(); i++)
		{
			if(this.lstAllTypeName.get(i).getSecond().contains("sum"))
			{
				String name = this.lstAllTypeName.get(i).getSecond();
				String type = this.lstAllTypeName.get(i).getFirst();
				class_str += "\tpublic void ";
				String functionName = "set_" + name;
				this.lstFunctionNumberNames.add(new Couple<Integer, String>(
						new Integer(this.getVariableNumber(functionName)), functionName));
				class_str += functionName;
				class_str += "(" + type + " " + this.myInitSubString(name) + "Tmp)\n";
				String shortName = this.myInitSubString(name);
				class_str += "\t{\n";
//				class_str += "\t\tcount_" + this.getVariableNumber(name) + "++;\n";
				class_str += "\t\tsum_" + shortName + "_" + this.getVariableNumber(name) + "+="+
						shortName + "Tmp;\n";
				class_str += "\t}\n";
			}
		}
		return class_str;
	}
	
	//Generate maxFunctions
	private String addJavaMaxFunction(String class_str)
	{
		class_str += "\n\t//max functions\n";
		for(int i = 0; i != this.lstAllTypeName.size(); i++)
		{
			if(this.lstAllTypeName.get(i).getSecond().contains("max"))
			{
				String name = this.lstAllTypeName.get(i).getSecond();
				String type = this.lstAllTypeName.get(i).getFirst();
				class_str += "\tpublic void ";
				String functionName = "set_" + name;
				this.lstFunctionNumberNames.add(new Couple<Integer, String>(
						new Integer(this.getVariableNumber(functionName)), functionName));
				class_str += functionName;
				class_str += "(" + type + " " + this.myInitSubString(name) + "Tmp)\n";
				String shortName = this.myInitSubString(name);
				class_str += "\t{\n";
//				class_str += "\t\tcount_" + this.getVariableNumber(name) + "++;\n";
				class_str += "\t\tif(" + shortName + "Tmp > " + name + ")\n";
				class_str += "\t\t{\n";
				class_str += "\t\t\t" + name + " = " + shortName + "Tmp;\n";
				class_str += "\t\t}\n\t}\n";
			}
		}
		return class_str;
	}

	//Generate MinFunctions
	private String addJavaMinFunction(String class_str)
	{
		class_str += "\n\t//min functions.\n";
		for(int i = 0; i != this.lstAllTypeName.size(); i++)
		{
			if(this.lstAllTypeName.get(i).getSecond().contains("min"))
			{
				String name = this.lstAllTypeName.get(i).getSecond();
				String type = this.lstAllTypeName.get(i).getFirst();
				class_str += "\tpublic void ";
				String functionName = "set_" + name;
				this.lstFunctionNumberNames.add(new Couple<Integer, String>(
						new Integer(this.getVariableNumber(functionName)), functionName));
				class_str += functionName;
				class_str += "(" + type + " " + this.myInitSubString(name) + "Tmp)\n";
				String shortName = this.myInitSubString(name);
				class_str += "\t{\n";
//				class_str += "\t\tcount_" + this.getVariableNumber(name) + "++;\n";
				class_str += "\t\tif(" + shortName + "Tmp < " + name + ")\n";
				class_str += "\t\t{\n";
				class_str += "\t\t\t" + name + " = " + shortName + "Tmp;\n";
				class_str += "\t\t}\n\t}\n";
			}
		}
		return class_str;
	}
	
	//Get the number of a method.
	//1_avg_length => 1
	private int getFunctionNameFirstNumber(String fname)
	{
		int ireturn = 0;
		for(int i = 0; i != fname.length(); i++)
		{
			if(fname.charAt(i) == '_')
			{
				String tmp = fname.substring(0, i);
				ireturn = Integer.parseInt(tmp);
				break;
			}
		}
		if(ireturn == 0)
		{
			System.out.println("Error in getFunctionNameFirstNumber method!");
		}
		return ireturn;
	}
}
