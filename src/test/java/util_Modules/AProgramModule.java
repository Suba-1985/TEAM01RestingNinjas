package util_Modules;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.specification.RequestSpecification;
import utilities.Config_Reader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.PageUtils;
public class AProgramModule {
	private String noAuth;
	private static String programdescription;
	private String putprogramId;
	private static String progname;
	private static String progName;
	private static String progidinvalid,programNamestr;
	private static String progstatus;
	private static Response response;
	public static String programId;
	public static String progid;
	static RequestSpecification request;
	private static String progNameinvalid;
	private static Config_Reader configreader=new Config_Reader();
	public  static String programName,postprogramName;	
	private static int postprogramID;
	public static int programID;//delete after
	

	public void getDatafromExcel(String sheetname, int rownumber) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {	
		
ExcelReader reader = new ExcelReader();
		
		List<Map<String, String>> testdata;
    	 testdata = reader.getData(Config_Reader.excelpath(), sheetname);
		String programdescription = testdata.get(rownumber).get("programDescription");	
		String progname= testdata.get(rownumber).get("programName");
		String progstatus= testdata.get(rownumber).get("programStatus");			
	}
	
	
	@SuppressWarnings("unchecked")
	public static Response postprogram(String postUri) throws org.apache.poi.openxml4j.exceptions.InvalidFormatException, IOException
	{
	request=RestAssured.given();
	JSONObject jsonObject = new JSONObject();
	String s = RandomStringUtils.randomNumeric(3); 
	String programNamestr=progname+s;		
	jsonObject.put("programDescription",programdescription);
	jsonObject.put("programName", PageUtils.programName());
	jsonObject.put("programStatus", progstatus);	
//	programID=response.jsonPath().getString("programId");//this gives all the id in the program module
//	programName = response.jsonPath().getString("programName");	
//	System.out.println("before response"+programID + programName );
	response=request.body(jsonObject.toJSONString()).when().post(postUri).then().log().all().extract().response();
	postprogramName=response.path("programName");//this gives the created programid and name
	//postprogramID=response.path("programId");	
	postprogramID=response.jsonPath().getInt("programId");
    LoggerLoad.info("****************Program is created valid data*********************");
    return response;
	}
	
	
	@SuppressWarnings("unchecked")
	public Response postbatch(String postUri)
	{
		JSONObject jsonObject = new JSONObject();
		String s = RandomStringUtils.randomNumeric(3); 			
		jsonObject.put("batchDescription", progstatus);
		jsonObject.put("batchName", progstatus);
		jsonObject.put("batchNoOfClasses", progstatus);
		jsonObject.put("batchStatus", progstatus);
		jsonObject.put("programId", 10754);	
		System.out.println(programId);
		
		String payload = jsonObject.toString();
		response = request.body(jsonObject.toJSONString()).when().post(postUri).then().log().all().extract().response();		
		programId=response.jsonPath().getString("programId");
		progName=response.jsonPath().getString("programName");
		LoggerLoad.info("post request sent with valid data");
	//	System.out.println("input data************"+programdescription+programNamestr+progstatus);
		
		System.out.println(response.jsonPath().prettyPrint());
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	public Response putprogram(String postUri)
	{
		JSONObject jsonObjectput = new JSONObject();
		String s = RandomStringUtils.randomNumeric(3); 
		  programNamestr=progname+s+"modified";
		  System.out.println(programNamestr);
		  jsonObjectput.put("programDescription",programdescription);
		  jsonObjectput.put("programName", programNamestr);
		  jsonObjectput.put("programStatus", progstatus);
		String payload = jsonObjectput.toString();
		//Response response = noAuthentication(noAuth).body(jsonObjectput).post(postUri).then().log().all().extract().response();			
		putprogramId=response.jsonPath().getString("programId from putprogram module" + response.jsonPath().getString("programName"));
	//	System.out.println("input data************"+programdescription+programNamestr+progstatus);
		
		System.out.println(response.jsonPath().prettyPrint() + putprogramId);
		
		return response;
	}
	@SuppressWarnings("unchecked")
	public String invalidpostprogram(String postUri) throws IOException
	{
		JSONObject jsonObjectpost = new JSONObject();
		jsonObjectpost.put("programDescription",programdescription);
		jsonObjectpost.put("programName", progNameinvalid);
		jsonObjectpost.put("programStatus", progstatus);
		//String payload = jsonObject.toString();
		String endpoint=postUri+Config_Reader.deleteprogramByidEndpoint()+progNameinvalid;
		System.out.println(endpoint+"******from invalid id");
		String responseinvalid = progidinvalid;		
		///programId=response.jsonPath().getString("programId");
		//Assert.assertEquals(false, null);
		//System.out.println(responseinvalid.jsonPath().prettyPrint()+ "from invalid");
		return responseinvalid;
	}

	

	public void deletebyprogramid(String progID, String deleteEndpoint) {
		given()	
		.when()
		 .delete(deleteEndpoint+progidinvalid)
		.then() 
		  .statusCode(200)
		//  .statusLine(success)
		  .log().all();
		
		System.out.println(deleteEndpoint+programId +"****************** deletepoint");
		System.out.println("response class****"+Response.class);
	}
	
	public void deletebyprogramName( String deleteEndpoint) {
		given()	
		.when()
		 .delete(deleteEndpoint+progidinvalid)
		.then() 
		  .statusCode(200)
		//  .statusLine(success)
		  .log().all();
		
		System.out.println(deleteEndpoint+programId +"****************** deletepoint");
		System.out.println("response class****"+Response.class);
	}

	public void getAllPrograms(String postURI) throws IOException {
		//File expectedJson = new File(configreader.testDataResourcePath());
		//String expectedJson = FileUtils.readFileToString(new File(configreader.testDataResourcePath()));
	    
	//System.out.println("path************ " + expectedJson + "     "+ postURI);
		response=given()
		   .header("Content-Type","application/json")
			.when()
			   .get(postURI);
//			.then()
//			 .assertThat()
//			// .body(JsonSchemaValidator.matchesJsonSchema (new File("C://Users//subas//Rest-Assure-Hackathon//Rest_Assure_Hackathon//getallprogramSchema.json")));
//			 .statusCode(200)
//			 .log().all();
		System.out.println("path************DONE ");
	 
	}

	public void getAllProgramsbyID(String postURI) {
		String programId="10739";
		RestAssured.given()
		   .header("Content-Type","application/json")
		  // .params("progID","10739")
			.when()
			   .get(postURI)
			 .then()
		 	 .assertThat()
			// .body(matchesJsonSchemaInClasspath("Schema.json"))
			 .body(matchesJsonSchemaInClasspath("Schema.json"))
			 .log().all();
		
		
		System.out.println("path************DONE ");
	}

	
}
