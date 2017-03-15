//small class to store all DB data
//includes username, password, DBURL, DBtablename

public class DBInformation
{
	String strDBUserName = new String();
	String strDBPSW = new String();
	String strDBURL = new String();
	String strTableName = new String();
	
	public DBInformation()
	{
		strDBURL = "jdbc:postgresql://localhost:5432/postgres";
		strDBUserName = "postgres";
		strDBPSW = "1247mona";
		strTableName = "sales";
	}
	
	public DBInformation(DBInformation orig)
	{
		this.strDBPSW = orig.strDBPSW;
		this.strDBURL = orig.strDBURL;
		this.strDBUserName = orig.strDBUserName;
		this.strTableName = orig.strTableName;
	}
}
