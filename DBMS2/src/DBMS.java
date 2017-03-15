//this is the original main file before new UI is built.
//it can generate codes of a fixed database.

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBMS
{
	//DB data
	DBInformation dbinfo = new DBInformation();
	static final String JDBC_DRIVER = "org.postgresql.Driver";
	static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
	static final String user = "postgres";
	static final String password = "1247mona";
	//Other class
	static final ParseforMFStructure mfStructOrig = new ParseforMFStructure();
	static DBInformationSchema infoSchema = new DBInformationSchema();
	static final GenerateMFStructCode genMFStructCode = new GenerateMFStructCode();

	static Connection conn;
	
	public static void main(String args[])
	{
			//file name as argument
			String filePath = args[0];
			BufferedReader br = null;
			String parseLine;
			try
			{
				br = new BufferedReader(new FileReader(filePath));
				//read file into ParseforMFStruct()
				while((parseLine = br.readLine()) != null)
				{
					if(parseLine.equals("SELECT ATTRIBUTE(S):"))
					{
						mfStructOrig.setSelectAttributes(br, parseLine);
						continue;
					}
					else if(parseLine.equals("NUMBER OF GROUPING VARIABLES(n):"))
					{
						parseLine = br.readLine();
						mfStructOrig.setGroupingAttrNumber(parseLine);
						continue;
					}
					else if(parseLine.equals("GROUPING ATTRIBUTES(V);"))
					{
						mfStructOrig.setGroupingAttrs(br, parseLine);
						continue;
					}
					else if(parseLine.equals("F-VECT([F]):"))
					{
						mfStructOrig.setFV(br, parseLine);
						continue;
					}
					else if(parseLine.equals("SELECT CONDITION-VECT([σ]):"))
					{
						mfStructOrig.setConditions(br, parseLine);
						continue;
					}
				}
				//Deal with conditions.
			
				Class.forName(JDBC_DRIVER);
				conn = DriverManager.getConnection(DB_URL, user, password);
				checkInfoSchema();
				infoSchema.setStructTypeJAVA();
				//Check output
				
				genMFStructCode.setClassString(mfStructOrig, infoSchema);
			
				GenerateMainLoop gCode = new GenerateMainLoop("./",new DBInformation(), mfStructOrig, genMFStructCode, infoSchema);
				gCode.printGCode();
				conn.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally 
			{
				try
				{
					if (br != null)
					{
						br.close();
					}
				}
				catch (IOException ex) 
				{
					ex.printStackTrace();
				}
			}
		}

	
	//To check information schema from the database
	private static void checkInfoSchema()
	{
		String query = "select column_name, data_type from information_schema.columns\n"
				+ "where table_name = 'sales'";
		try
		{
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next())
			{
				String col = rs.getString("column_name");
				String type = rs.getString("data_type");
				infoSchema.addValue(col, type);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}
