package util_Modules;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ExcelReader;

public class BBatchModule {
	private String noAuth;
	private String batchdes;
	private String batchname;
	private String batchclassno;
	private String batchstatus;
	public static String BatchId;
	Response response;
	RequestSpecification requestSpecification;
	
	public RequestSpecification noAuthendication(String noauth)
	{
		noAuth=noauth;	
	RequestSpecification requestSpecification = RestAssured.given()
			.header("Authorization", noauth).contentType("application/json");
	return requestSpecification;

}
	
	public void getDatafromExcel(String sheetname, int rownumber) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		ExcelReader reader = new ExcelReader();
		String data[]=new String[2];
		List<Map<String, String>> testdata;
		try {
			testdata = reader.getData("src/test/resources/testData/data.xlsx", sheetname);
			 
			
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}

	
	@SuppressWarnings("unchecked")
	public Response postbatch(String uri)
	{String s = RandomStringUtils.randomNumeric(3); 
	  String batch=batchname+s;
	  int programid=10754;
	  System.out.println("**batch***"+batch);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("batchDescription",batchdes);
		jsonObject.put("batchName", batch);
		jsonObject.put("batchNoOfClasses", batchclassno);
		jsonObject.put("batchStatus", batchstatus);
		jsonObject.put("programId",programid );
		String payload = jsonObject.toString();
		Response response = noAuthendication(noAuth).body(payload).post(uri);
		BatchId=response.jsonPath().getString("batchId");
		System.out.println(response.jsonPath().prettyPrint() + BatchId);
		return response;
	}
//	public Response deletebatchid(String uri)
//	{
//	//			return response=noAuthendication(noAuth).param(BatchId).delete(uri);		
//	}

}
