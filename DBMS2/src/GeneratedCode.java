//This code is automatically generated.
//Generated time: Sat 2016.05.07 at 01:12:01 PM EDT
//
//How to run this code:
//compile:	javac GeneratedCode.java
//run:		java GeneratedCode

import java.sql.*;
import java.util.ArrayList;

public class GeneratedCode
{
	//Declare and initialize all types and data.
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
	static final String user = "postgres";
	static final String password = "1247mona";
	static Connection conn;
	//There might be some unused datatypes, eliminate warning.
	@SuppressWarnings("unused")

	//main method...
	static public void main(String arg[])
	{
		//ArrayList stores all the MFStruct type of data.
		ArrayList<MFStruct> lstMFStruct = new ArrayList<MFStruct>();
		try
		{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, user, password);
			System.out.println("Below is the final query result:");
			String queryStr = "SELECT * FROM sales";
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(queryStr);
			//Scan the whole database.
			while(rs.next())
			{
				String custTmp = rs.getString("cust");
				String prodTmp = rs.getString("prod");
				int dayTmp = rs.getInt("day");
				int monthTmp = rs.getInt("month");
				int yearTmp = rs.getInt("year");
				String stateTmp = rs.getString("state");
				int quantTmp = rs.getInt("quant");
				//#1 Selection Conditions.
				if(stateTmp.equalsIgnoreCase("ny"))
				{
					if(lstMFStruct.size() == 0)
					{
						MFStruct mfStructTmp = new MFStruct();
						mfStructTmp.init1(custTmp,prodTmp,quantTmp);
						lstMFStruct.add(mfStructTmp);
					}
					else
					{
						for(int i = 0; i != lstMFStruct.size(); i++)
						{
							if(lstMFStruct.get(i).equals(custTmp,prodTmp) == true)
							{
								lstMFStruct.get(i).set_count_1();
								lstMFStruct.get(i).set_sum_quant_1(quantTmp);
								break;
							}
							if(i == lstMFStruct.size() - 1)
							{
								MFStruct mfStructTmp = new MFStruct();
								mfStructTmp.init1(custTmp,prodTmp,quantTmp);
								lstMFStruct.add(mfStructTmp);
								break;
							}
						}
					}
				}
				//#2 Selection Conditions.
				if(stateTmp.equalsIgnoreCase("nj"))
				{
					if(lstMFStruct.size() == 0)
					{
						MFStruct mfStructTmp = new MFStruct();
						mfStructTmp.init2(custTmp,prodTmp,quantTmp);
						lstMFStruct.add(mfStructTmp);
					}
					else
					{
						for(int i = 0; i != lstMFStruct.size(); i++)
						{
							if(lstMFStruct.get(i).equals(custTmp,prodTmp) == true)
							{
								lstMFStruct.get(i).set_count_2();
								lstMFStruct.get(i).set_sum_quant_2(quantTmp);
								break;
							}
							if(i == lstMFStruct.size() - 1)
							{
								MFStruct mfStructTmp = new MFStruct();
								mfStructTmp.init2(custTmp,prodTmp,quantTmp);
								lstMFStruct.add(mfStructTmp);
								break;
							}
						}
					}
				}
				//#3 Selection Conditions.
				if(stateTmp.equalsIgnoreCase("ct"))
				{
					if(lstMFStruct.size() == 0)
					{
						MFStruct mfStructTmp = new MFStruct();
						mfStructTmp.init3(custTmp,prodTmp,quantTmp);
						lstMFStruct.add(mfStructTmp);
					}
					else
					{
						for(int i = 0; i != lstMFStruct.size(); i++)
						{
							if(lstMFStruct.get(i).equals(custTmp,prodTmp) == true)
							{
								lstMFStruct.get(i).set_count_3();
								lstMFStruct.get(i).set_sum_quant_3(quantTmp);
								break;
							}
							if(i == lstMFStruct.size() - 1)
							{
								MFStruct mfStructTmp = new MFStruct();
								mfStructTmp.init3(custTmp,prodTmp,quantTmp);
								lstMFStruct.add(mfStructTmp);
								break;
							}
						}
					}
				}
			}
			//to print out the results.
			System.out.printf("%-14s%-14s%14s%14s%14s\n", "CUST" , "PROD" , "1_SUM_QUANT" , "2_SUM_QUANT" , "3_SUM_QUANT");
			for(int i = 0; i != lstMFStruct.size(); i++)
			{
				System.out.printf("%-14s%-14s%14s%14s%14s\n", 
					 lstMFStruct.get(i).cust , 
					 lstMFStruct.get(i).prod , 
					 lstMFStruct.get(i).sum_quant_1 , 
					 lstMFStruct.get(i).sum_quant_2 , 
					 lstMFStruct.get(i).sum_quant_3);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
