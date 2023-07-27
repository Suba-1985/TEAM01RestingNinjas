package utilities;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config_Reader {
	private static Properties prop;
	
	public static Properties init_prop() throws IOException
	{
		prop=new Properties();
		try
		{
			FileInputStream ip=new FileInputStream("src\\test\\resources\\configFile\\config.properties");
			prop.load(ip);
		}
		catch(FileNotFoundException e)
		{
		e.printStackTrace();	
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		return prop;
	}
	
	
	
	public String baseUrl() throws IOException
	{prop=init_prop();
		String url = prop.getProperty("BaseUrl");
		

		System.out.println(url);
		if(url!=null)
		{
			return url;
		}else
		{
			System.out.println("uri is not mentioned in config properties");
		}	
		return url;	
	}
	
	public static String postProgramEndpoint() throws IOException
	{prop=init_prop();

		String postEndPoint = prop.getProperty("PostProgramEndpoint");
		
		if(postEndPoint!=null)
		{
			return postEndPoint;
		}else
		{
			System.out.println("PostProgramEndpoint is not mentioned in config properties");
		}	return postEndPoint;	
	}
	
	public static String getAllEndpoint() throws IOException
	{   
		prop=init_prop();
		String getAllPoint=prop.getProperty("GetAllEndpoint");
		if(getAllPoint!=null)
		{
			return getAllPoint;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getAllPoint;	
	}
	
	public static String getOneProgramIdEndpoint() throws IOException {
		prop=init_prop();

		String getIdEndPoint = prop.getProperty("GetOneProgramIdEndpoint");
		if (getIdEndPoint != null)
			return getIdEndPoint;
		else
			throw new RuntimeException("GetOneProgramIdEndpoint not specified in the Config.properties file");
	}
	
	public static String putProgramByProgramNameEndpoint() throws IOException {
		prop=init_prop();
		String putProgramNamePoint = prop.getProperty("PutProgramByProgramNameEndpoint");
		if (putProgramNamePoint != null)
			return putProgramNamePoint;
		else
			throw new RuntimeException("GetOneProgramIdEndpoint not specified in the Config.properties file");
	}
	public static String putProgramByProgramIdEndpoint() throws IOException {
		prop=init_prop();
		String putbyprogramIdEndPoint = prop.getProperty("PutProgramByProgramIdEndpoint");
		if ( putbyprogramIdEndPoint != null)
			return  putbyprogramIdEndPoint;
		else
			throw new RuntimeException("GetOneProgramIdEndpoint not specified in the Config.properties file");
	}
	public static String deleteprogramByidEndpoint() throws IOException {
		prop=init_prop();
		String deletebyidEndPoint = prop.getProperty("DeleteProgramByProgramIdEndpoint");
		if (deletebyidEndPoint != null)
			return deletebyidEndPoint;
		else
			throw new RuntimeException("GetOneProgramIdEndpoint not specified in the Config.properties file");
	}
	public static String deleteprogramBynameEndpoint() throws IOException {
		prop=init_prop();

		String deletebynameEndPoint = prop.getProperty("DeleteProgramByProgramNameEndpoint");
		if (deletebynameEndPoint != null)
			return deletebynameEndPoint;
		else
			throw new RuntimeException("deletebynameEndPoint not specified in the Config.properties file");
	}
	
	public static String createbatchsaveandGetBatchesAllputBatchUpdatebyBatchIdandDeleteBatch() throws IOException
	{prop=init_prop();
		String getEndPoint = prop.getProperty("createbatchsaveandGetBatchesAllputBatchUpdatebyBatchIdandDeleteBatch");
		
		if(getEndPoint!=null)
		{
			return getEndPoint;
		}else
		{
			System.out.println("Endpoint is not mentioned in config properties");
		}	return getEndPoint;
	}
	public static String testDataResourcePath() throws IOException {
		prop=init_prop();
		String testDataJson = prop.getProperty("testDataResourcePath");
		if (testDataJson != null)
			return testDataJson;
		else
			throw new RuntimeException("testDataJson not specified in the Config.properties file");
	}
	
	public static String getallprg() throws IOException {
		prop=init_prop();
		String testDataJson1 = prop.getProperty("GetAllEndpoint");
		if (testDataJson1 != null)
			return testDataJson1;
		else
			throw new RuntimeException("getallprogram not specified in the Config.properties file");
	}
	
	public static String excelpath() throws IOException
	{   prop=init_prop();
		String excelpath=prop.getProperty("excelpath");
		if(excelpath != null)
		return excelpath;
		else throw new RuntimeException("excelpath not specified in the Config.properties file");			
	}
	public static String getBatchbyID() throws IOException
	{   
		prop=init_prop();
		String getbatchbyid=prop.getProperty("getBatchbyID");
		if(getbatchbyid!=null)
		{
			return getbatchbyid;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getbatchbyid;	
	}
	
	public static String getBatchbyName() throws IOException
	{   
		prop=init_prop();
		String getbatchbyid=prop.getProperty("getBatchbyName");
		if(getbatchbyid!=null)
		{
			return getbatchbyid;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getbatchbyid;	
	}
	
	
	public static String getBatchbyProgramId() throws IOException
	{   
		prop=init_prop();
		String getbatchbyid=prop.getProperty("getBatchbyProgramId");
		if(getbatchbyid!=null)
		{
			return getbatchbyid;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getbatchbyid;	
	}
	
	public static String userPostrequestcreatinguserwithrole() throws IOException
	{   
		prop=init_prop();
		String getbatchbyid=prop.getProperty("userpostrequestcreatinguserwithrole");
		if(getbatchbyid!=null)
		{
			return getbatchbyid;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getbatchbyid;	
	}
	public static String userGetAllUsersrequest() throws IOException
	{   
		prop=init_prop();
		String getbatchbyid=prop.getProperty("usergetAllUsersrequest");
		if(getbatchbyid!=null)
		{
			return getbatchbyid;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getbatchbyid;	
	}
	public static String userGetalluserswithroles() throws IOException
	{   
		prop=init_prop();
		String getbatchbyid=prop.getProperty("usergetalluserswithroles");
		if(getbatchbyid!=null)
		{
			return getbatchbyid;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getbatchbyid;	
	}
	
	public static String userGetallstaff() throws IOException
	{   
		prop=init_prop();
		String getbatchbyid=prop.getProperty("usergetallstaff");
		if(getbatchbyid!=null)
		{
			return getbatchbyid;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getbatchbyid;	
	}
	
	public static String userUpdateuseruserGetUserinfobyiduserDelete() throws IOException
	{   
		prop=init_prop();
		String getbatchbyid=prop.getProperty("userupdateuserusergetUserinfobyiduserdelete");
		if(getbatchbyid!=null)
		{
			return getbatchbyid;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getbatchbyid;	
	}
	
	public static String userUpdateuserrolestatus() throws IOException
	{   
		prop=init_prop();
		String getbatchbyid=prop.getProperty("userupdateuserrolestatus");
		if(getbatchbyid!=null)
		{
			return getbatchbyid;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getbatchbyid;	
	}
	
	public static String assignUpdateuserroleprogrambatchstatus() throws IOException
	{   prop=init_prop();
		
		String getbatchbyid=prop.getProperty("assignupdateuserroleprogrambatchstatus");
		if(getbatchbyid!=null)
		{
			return getbatchbyid;
		}else
		{
			System.out.println("GetAllEndpoint is not mentioned in config properties");
		}	return getbatchbyid;	
	}
	
	
	
	
	}