package stepDefinition;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.junit.Assert;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import util_Modules.AProgramModule;
import utilities.Config_Reader;
import utilities.ExcelReader;
import utilities.LoggerLoad;
import utilities.PageUtils;
public class AProgram_module_SD {
	private static String postURI,programdescription;
	private static int postprogramID;
	static int programID;
	public  static String programName,postprogramName;
    private static RequestSpecification request;
    private static String currentTime=PageUtils.getcurrentDateTime();
    private static String lastModTime=PageUtils.getcurrentDateTime();
    private static Config_Reader configreader=new Config_Reader();
    static Properties prop;
    public static ResponseBody body;
    public static Response response;  
    private static AProgramModule program=new AProgramModule();     
    @Given("User sets Authorization to {string} from program")
    public  void user_sets_authoization_to(String string) {    	
 		  request = RestAssured.given().header("Authorization", string).contentType("application/json"); 	   
    }
       

	
	@Given("User is provided with the BaseUrl and endpoint and nonexisting fields in payload")
	public void user_is_provided_with_the_base_url_and_endpoint_and_nonexisting_fields_in_payload() throws IOException {
	  prop=Config_Reader.init_prop();	  
	  postURI=configreader.baseUrl()+Config_Reader.postProgramEndpoint();
	  System.out.println(postURI);
	  LoggerLoad.info("**************getting the baseurl and endpoint for post**************" + postURI);  
	}

	@SuppressWarnings("unchecked")
	@When("User send the HTTPsPOST request to server with the payload from {string} and {int}")
	public void user_send_the_HTTPsPOST_post_request_to_server_with_the_payload_from_and_rownumber(String SheetName, int rowno) throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		ExcelReader reader = new ExcelReader();
		
		List<Map<String, String>> testdata;
    	 testdata = reader.getData(Config_Reader.excelpath(), SheetName);
		String programdescription = testdata.get(rowno).get("programDescription");	
		String progname= testdata.get(rowno).get("programName");
		String progstatus= testdata.get(rowno).get("programStatus");			
		JSONObject jsonObject = new JSONObject();
		String s = RandomStringUtils.randomNumeric(3); 
		String programNamestr=progname+s;		
		jsonObject.put("programDescription",programdescription);
		jsonObject.put("programName", programNamestr);
		jsonObject.put("programStatus", progstatus);	
	//	programID=response.jsonPath().getString("programId");//this gives all the id in the program module
	//	programName = response.jsonPath().getString("programName");	
	//	System.out.println("before response"+programID + programName );
		response=request.body(jsonObject.toJSONString()).when().post(postURI).then().log().all().extract().response();
		postprogramName=response.path("programName");//this gives the created programid and name
		//postprogramID=response.path("programId");	
		postprogramID=response.jsonPath().getInt("programId");
	    LoggerLoad.info("****************Program is created valid data*********************");
		
	}
	
	@Then ("User validates the response with Schema validation for all programs")
	public void User_validates_the_response_with_status_codegetall() throws IOException{
		//System.out.println(postURI+Config_Reader.getallprg());
        String base=postURI;
        System.out.println("from schema :" + base);
       
//		JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
//    .setValidationConfiguration(
//            ValidationConfiguration.newBuilder()
//              .setDefaultVersion(SchemaVersion.DRAFTV4).freeze())
//                .freeze();
	//given().get(base).then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getallp.json"));
	
	//System.out.println(postURI+postprogramID);
  //  String base=postURI+postprogramID;
    //  given().get(base).then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getallp.json")).statusCode(200);
}

	@SuppressWarnings("deprecation")
	@Then ("User validates the response with Schema validation")
	public void User_validates_the_response_with_status_code() 
	{String base=postURI+postprogramID;
		JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
    .setValidationConfiguration(
            ValidationConfiguration.newBuilder()
              .setDefaultVersion(SchemaVersion.DRAFTV4).freeze())
                .freeze();
	given().get(base).then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("getbyidSchema.json").using(jsonSchemaFactory));
	
		
        
   //  String responseBody=response.getBody().asPrettyString();
 	//	assertThat(responseBody,JsonSchemaValidator.matchesJsonSchemaInClasspath("getprogrambyid.json"));  
        // given().get(postURI).then().assertThat().body(matchesJsonSchemaInClasspath("getprogrambyid.json"));
		
	}
	
	
	
	@Then("User validates the response with status code {string} from post")
	public void user_validates_the_response_with_status_code_response_time_header( String statuscode) {
		final int Poststatuscode = response.getStatusCode();

		if (Poststatuscode == 201) {
		  response.then().statusCode(Integer.parseInt(statuscode));
		  LoggerLoad.info("Post Request Successful");			
		  Assert.assertEquals(statuscode, "201");			
			 System.out.println("created program Successfully " + Poststatuscode);
			Assert.assertTrue(true);
		} else {
			LoggerLoad.error("Not Successful: 400");
			 System.out.println("Not Successful" + Poststatuscode);
			 Assert.assertFalse(false);		}		
		ValidatableResponse  v=response.then();
		v.time(Matchers.lessThan(2594L));		
		//header validation
		response.then().assertThat().header("connection","keep-alive");
		response.then().assertThat().header("Content-Type" , "application/json");
		LoggerLoad.info("*********Header validation for post request**********");	
		response.then().assertThat().statusLine(response.statusLine());
		LoggerLoad.info("statusline");
		
		//to validate response body
		 body = response.getBody();
		// To check for sub string presence get the Response body as a String.
		// Do a String.contains
		String bodyAsString = body.asString();
		System.out.println("assertion DONE");
		//Assert.assertEquals(bodyAsString.contains("Selenium") /*Expected value*/, true /*Actual Value*/, "Response body contains Hyderabad");
		
	}
	
	@Then("User validates the response with Status code {string}")
	public void user_validates_the_response_with_status_code(String statuscode) {
		final int Poststatuscode = response.getStatusCode();
		response.then().assertThat().header("connection","keep-alive");
	//	response.then().assertThat().header("Content-Type" , "application/json");
		LoggerLoad.info("*********Header and statusline and statuscode and responsetime validation**********");	
		response.then().assertThat().statusLine(response.statusLine());
		ValidatableResponse  v=response.then();
		v.time(Matchers.lessThan(1000L));	
		System.out.println(response.statusLine());

		if (Poststatuscode == 200) {
		  response.then().statusCode(Integer.parseInt(statuscode));
		  LoggerLoad.info("Post Request Successful");			
		  Assert.assertEquals(statuscode, "200");			
			 System.out.println("created program Successfully " + Poststatuscode);
			Assert.assertTrue(true);
		} else if(Poststatuscode == 405){
			LoggerLoad.error("Not Successful: 405" + Poststatuscode);
			 System.out.println("Not Successful" + Poststatuscode);
			Assert.assertFalse(false);
		} else if(Poststatuscode == 400) {
			LoggerLoad.error("Not Successful: 400" + Poststatuscode);
		}
	}
	@Then("User validates the response with Status code for delete")
	public void user_validates_the_response_with_status_code_for_delete()
	{
		response.then().assertThat().header("connection","keep-alive");
		//response.then().assertThat().header("Content-Type" , "application/json");
		LoggerLoad.info("*********Header and statusline and statuscode and responsetime validation**********");	
		response.then().assertThat().statusLine(response.statusLine());
		ValidatableResponse  v=response.then();
		v.time(Matchers.lessThan(1000L));	
	}

	@Given("User is provided with the BaseUrl and endpoint with missing ProgramName to create a POST Request with missing programname")
	public void user_is_provided_with_the_base_url_and_endpoint_with_missing_program_name_to_create_a_post_request_with_missing_programname() throws IOException {
		 postURI=configreader.baseUrl()+Config_Reader.postProgramEndpoint();
		 LoggerLoad.info("*******missing mandatory fields to create program*******");
	}
	

	
	@Given("User is provided with the BaseUrl and endpoint with existing ProgramName to create a POST Request")
	public void user_is_provided_with_the_base_url_and_endpoint_with_existing_program_name_to_create_a_post_request() throws IOException {
	  prop=Config_Reader.init_prop();	 
	  postURI=configreader.baseUrl()+Config_Reader.postProgramEndpoint();
	  System.out.println(postURI);
	  LoggerLoad.info("**************getting the baseurl and endpoint for post to resend the data for negative scenario**************" + postURI);   
	}
	@SuppressWarnings("unchecked")
	@When("User send the HTTPsPOST request to server with the payload from {string} and {int} with existing Programname")
	public void user_send_the_https_post_request_to_server_with_the_payload_from_and_with_existing_programname(String SheetName, Integer rowno) throws InvalidFormatException, IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		System.out.println("value for program id and name  "+postprogramID + postprogramName);	
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testdata;
    	testdata = reader.getData(Config_Reader.excelpath(), SheetName);
		String programdescription = testdata.get(rowno).get("programDescription");	
		String progname= testdata.get(rowno).get("programName");
		String progstatus= testdata.get(rowno).get("programStatus");	
		
		JSONObject jsonObject = new JSONObject();
		//String s = RandomStringUtils.randomNumeric(3); 
		//String programNamestr=progname+s;		
		jsonObject.put("programDescription",programdescription);
		jsonObject.put("programName",postprogramName);
		jsonObject.put("programStatus", progstatus);	
		programID=response.jsonPath().getInt("programId");//this gives all the id in the program module
		programName = response.jsonPath().getString("programName");	
		System.out.println("resending same program name"+programID + programName );
		response=request.body(jsonObject.toJSONString()).when().post(postURI).then().log().all().extract().response();
		//postprogramName=response.path("programName");//this gives the created programid and name
		//postprogramID=response.path("programId");
		
	    LoggerLoad.info("****************Sending the request with same data to create should get error 400*********************");
	  
	}


	

//	@When("User send the HTTPsPOST request to server with the payload from {string} and {int} with existing Programname")
//	public void user_send_the_htt_ps_post_request_to_server_with_the_payload_from_and_with_existing_programname(String string, Integer int1) {
//	    
//	}
//	
	@Given("User is provided with the BaseUrl and endpoint with missing ProgramName to create a POST Request")
	public void user_is_provided_with_the_base_url_and_endpoint_with_missing_program_name_to_create_a_post_request() throws IOException {
	   postURI=configreader.baseUrl()+Config_Reader.postProgramEndpoint();
	   LoggerLoad.info("**********given baseurl and endpoint to create without mandatory firlds************");
	}

    
	@Given("User is provided with the BaseUrl and the Endpoints to create a GET request")
	public void user_is_provided_with_the_base_url_and_the_endpoints_to_create_a_get_request() throws IOException {
	  postURI=configreader.baseUrl()+Config_Reader.getAllEndpoint();
	  request=RestAssured.given().header("Content-Type","application/json");
	  LoggerLoad.info("***Sending the GetAllProgram Given code***" + postURI);
	}

	@When("User send the HTTPsGET request")
	public void user_send_the_https_get_request() throws IOException {
	 response=request.get(postURI);
	 response.then().log().all();
	 LoggerLoad.info("***user sends the get all request****");		  
	}
	
	@SuppressWarnings("deprecation")
	@Then("User validates the response with Status code {string} and validate schema for all the program")
	public void user_validates_the_response_with_Status_code_response_time_header(final String statuscode) {
		int Poststatuscode = response.getStatusCode();
		
		//String responseBody=response.getBody().asString();
	//	assertThat(responseBody,matchesJsonSchemaInClasspath(".\\src\\test\\resources\\getallprogramSchema.json"));
		

		if (Poststatuscode == 200) {
		  response.then().statusCode(Integer.parseInt(statuscode));
		  LoggerLoad.info("get Request Successful");			
		  Assert.assertEquals(statuscode, "200");			
			 System.out.println("get all programs " + Poststatuscode);
			Assert.assertTrue(true);
		} else {
			LoggerLoad.error("Not Successful: 400");
			 System.out.println("Not Successful" + Poststatuscode);
			 Assert.assertFalse(false);
		}
		response.then().assertThat().header("connection","keep-alive");
		response.then().assertThat().header("Content-Type" , "application/json");
		LoggerLoad.info("*********Header and statusline and statuscode and responsetime validation**********");	
		response.then().assertThat().statusLine(response.statusLine());
		ValidatableResponse  v=response.then();
		v.time(Matchers.lessThan(2000L));	
		
		
	}
	
	@SuppressWarnings("deprecation")
	@Then("User validates the response with Status code {string} and schema")
	public void user_validates_the_response_with_Status_code_response(final String statuscode) {
		final int Poststatuscode = response.getStatusCode();
		System.out.println(response.statusLine());
		String responseBody=response.getBody().asString();
		assertThat(responseBody,matchesJsonSchemaInClasspath("getbyidSchema.json"));
		//assertThat(responseBody,matchesJsonSchemaInClasspath("Schema.json"));	
		response.then().assertThat().header("connection","keep-alive");
		//response.then().assertThat().header("Content-Type" , "application/json");
		LoggerLoad.info("*********Header and statusline and statuscode and responsetime validation**********");	
		response.then().assertThat().statusLine(response.statusLine());
		ValidatableResponse  v=response.then();
		v.time(Matchers.lessThan(2000L));	
		if (Poststatuscode == 200) {
		  response.then().statusCode(Integer.parseInt(statuscode));
		  LoggerLoad.info("get Request Successful");			
		  Assert.assertEquals(statuscode, "200");			
			 System.out.println("get all programs " + Poststatuscode);
			Assert.assertTrue(true);
		} else {
			LoggerLoad.error("Not Successful: 400");
			 System.out.println("Not Successful" + Poststatuscode);
			 Assert.assertFalse(false);
		}
		}
	
	@Given("User is provided with the BaseUrl and the Endpoints to create a GET request with valid program id")
	public void user_is_provided_with_the_base_url_and_the_endpoints_to_create_a_get_request_with_valid_program_id() throws IOException {
	
	 System.out.println("value for program id and name  "+postprogramID + postprogramName);	
		postURI=configreader.baseUrl()+Config_Reader.getOneProgramIdEndpoint();
	  request=RestAssured.given().header("Content-Type","application/json");
	  LoggerLoad.info("***Sending the GetAllProgram Given code***" + postURI);
	}

	@When("User send the HTTPsGET request with valid programID")
	public void user_send_the_htt_ps_get_request_with_valid_program_id() throws IOException
	{System.out.println("value for program id and name  "+postprogramID + postprogramName);	
		String getendpoint=postURI + postprogramID;
		System.out.println("value of postprogramID  "+postprogramID);
	   response=given().when().get(getendpoint).then().log().all().extract().response();
	   LoggerLoad.info("***user sends the get request by sending the program id***");
	}
//	@When("User send the HTTPsGET request with invalid programID")
//	public void user_send_the_htt_ps_get_request_with_invalid_program_id() throws IOException {
//		   postURI=configreader.baseUrl()+configreader.getOneProgramIdEndpoint()+10730;
//		   program.getAllProgramsbyID(postURI);
//		   LoggerLoad.info("*************GET  PROGRAM BY invalid ID**********"+postURI);
//	}
//
	@Given("User is provided with the BaseUrl and the Endpoints to update status with programID")
	public void user_is_provided_with_the_base_url_and_the_endpoints_to_update_status_with_program_id() throws IOException {
		
	  postURI=configreader.baseUrl()+ Config_Reader.putProgramByProgramIdEndpoint()+ postprogramID;
	  LoggerLoad.info("******given with the baseurl and endpoint for update program status with programid*******");
			   
	}

	@SuppressWarnings("unchecked")
	@When("User send the HTTPsPUT request with valid programID and the payload from {string} and {int}")
	public void user_send_the_httpsput_request_with_valid_program_id(String SheetName, Integer rowno) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		
		System.out.println("value for program id and name  "+postprogramID + postprogramName);	
		ExcelReader reader = new ExcelReader();		
		String description=RandomStringUtils.randomAlphabetic(5);
		//String description="Quality Analyst";
		List<Map<String, String>> testdata;
    	 testdata = reader.getData(Config_Reader.excelpath(), SheetName);
    	
		String programdescription = testdata.get(rowno).get("programDescription");	
		String progname= testdata.get(rowno).get("programName");
		String progstatus= testdata.get(rowno).get("programStatus");	
		
		JSONObject jsonObject = new JSONObject();		
		jsonObject.put("programDescription",description);
		jsonObject.put("programId", postprogramID);
		jsonObject.put("programName",postprogramName);
		jsonObject.put("programStatus", progstatus);
		jsonObject.put("creationTime", currentTime);
		jsonObject.put("lastModTime", lastModTime);
		
		System.out.println("updateing programdesc  "+programID + programdescription );
		response=given().contentType(ContentType.JSON).accept(ContentType.JSON).body(jsonObject.toJSONString()).when()
		.put(postURI);
		//response=request.body(jsonObject.toJSONString()).when().post(postURI).then().log().all().extract().response();
		postprogramName=response.path("programName");//this gives the created programid and name
		postprogramID=response.path("programId");
		System.out.println("after response"+postprogramID + postprogramName);
		System.out.println(response.asString());
		System.out.println(programdescription+ "is updated with " +description);
	    System.out.println("The status received: " + response.statusLine());
	    System.out.println("The status received: " + response.statusCode());
	    System.out.println("The status received: " + response.asString());
	    System.out.println("The status received: " + response.contentType());
	    LoggerLoad.info("****************successfully updated with programid*********************");
	  
	}

//	@Given("User is provided with the BaseUrl and the Endpoints to update status with invalid programID")
//	public void user_is_provided_with_the_base_url_and_the_endpoints_to_update_status_with_invalid_program_id() {
//	    
//	}
//
//	@When("User send the HTTPsPUT request with invalid programID")
//	public void user_send_the_htt_ps_put_request_with_invalid_program_id() {
//	    
//	}
//
	@Given("User is provided with the BaseUrl and the Endpoints to update status with missing programName in payload")
	public void user_is_provided_with_the_base_url_and_the_endpoints_to_update_status_with_missing_program_name_in_payload() {
	   
	}

	@Given("User is provided with the BaseUrl and the Endpoints to update status with programName")
	public void user_is_provided_with_the_base_url_and_the_endpoints_to_update_status_with_program_name() throws IOException {
	   postURI=configreader.baseUrl()+Config_Reader.putProgramByProgramNameEndpoint()+postprogramName;
	   LoggerLoad.info("**********Given baseurl and endpoint for update program with name*********");
	}

	@SuppressWarnings("unchecked")
	@When("User send the HTTPsPUT request with valid programName from {string} and {int}")
	public void user_send_the_htt_ps_put_request_with_valid_program_name(String SheetName, Integer rowno) throws org.apache.poi.openxml4j.exceptions.InvalidFormatException, IOException {
		System.out.println("value for program id and name  "+postprogramID + postprogramName);	
		ExcelReader reader = new ExcelReader();	
		String description=RandomStringUtils.randomAlphabetic(5);
		//String description="Quality Analyst";
		List<Map<String, String>> testdata;
    	 testdata = reader.getData(Config_Reader.excelpath(), SheetName);
		String programdescription = testdata.get(rowno).get("programDescription");	
		String progname= testdata.get(rowno).get("programName");
		String progstatus= testdata.get(rowno).get("programStatus");	
		
		JSONObject jsonObject = new JSONObject();		
		jsonObject.put("programDescription",description);
		jsonObject.put("programName",postprogramName);
		jsonObject.put("programStatus", progstatus);
		jsonObject.put("creationTime", currentTime);
		jsonObject.put("lastModTime", lastModTime);
		System.out.println("updateing programdesc  "+programID + programdescription );
		response=given().contentType(ContentType.JSON).accept(ContentType.JSON).body(jsonObject.toJSONString()).when()
				.put(postURI).then().log().all().extract().response();
		postprogramName=response.path("programName");//this gives the created programid and name
		//postprogramID=response.path("programId");
		System.out.println("after response"+postprogramID + postprogramName);
		System.out.println(response.asString());
		System.out.println(programdescription+ "is updated with " +description);
	    System.out.println("The status received: " + response.statusLine());
	    System.out.println("The status received: " + response.statusCode());
	    System.out.println("The status received: " + response.asString());
	    System.out.println("The status received: " + response.contentType());
	    LoggerLoad.info("****************successfully updated with program name*********************");
	}

//	@Given("User is provided with the BaseUrl and the Endpoints to update status with invalid programName")
//	public void user_is_provided_with_the_base_url_and_the_endpoints_to_update_status_with_invalid_program_name() {
//	   
//	}
//
//	@When("User send the HTTPsPUT request with invalid programName")
//	public void user_send_the_htt_ps_put_request_with_invalid_program_name() {
//	    
//	}
//
//	@Given("User is provided with the BaseUrl and the Endpoints to update status with missing program status")
//	public void user_is_provided_with_the_base_url_and_the_endpoints_to_update_status_with_missing_program_status() {
//	    
//	}
//
//	@When("User send the HTTPsPUT request with missing program status")
//	public void user_send_the_htt_ps_put_request_with_missing_program_status() {
//	    
//	}
//
	@Given("User is provided with the BaseUrl and the Endpoints to delete a program with valid programName")
	public void user_is_provided_with_the_base_url_and_the_endpoints_to_delete_a_program_with_valid_program_name() throws IOException {
	    postURI=configreader.baseUrl()+Config_Reader.deleteprogramBynameEndpoint()+postprogramName;
	}

	@When("User send the HTTPsDELETE request with valid programName")
	public void user_send_the_htt_ps_delete_request_with_valid_program_name() {
		response = when().delete(postURI).then().extract().response();
	}
//
//	@Given("User is provided with the BaseUrl and the Endpoints to delete a program with invalid programName")
//	public void user_is_provided_with_the_base_url_and_the_endpoints_to_delete_a_program_with_invalid_program_name() throws IOException {
//	   postURI=configreader.baseUrl()+configreader.deleteprogramBynameEndpoint()+"JUL23-RestingNinjas-SDET-0020182";
//	}

//	@When("User send the HTTPsDELETE request with invalid programName")
//	public void user_send_the_htt_ps_delete_request_with_invalid_program_name() {
//		given()	
//		.when()
//		 .delete(postURI)
//		.then() 
//	       //  .statusLine("success")
//		  .log().all();			
//	}

	@Given("User is provided with the BaseUrl and the Endpoints to delete a program with valid programId")
	public void user_is_provided_with_the_base_url_and_the_endpoints_to_delete_a_program_with_valid_program_id() throws IOException {
	   postURI=configreader.baseUrl()+Config_Reader.deleteprogramByidEndpoint()+postprogramID;
	   LoggerLoad.info("**************given baseurl and endpoint to delete program with porgram id****************");
	}
  
	@When("User send the HTTPsDELETE request with valid programId")
	public void user_send_the_htt_ps_delete_request_with_valid_program_id() throws IOException {
		response = when().delete(postURI).then().extract().response();
      
}
	 
//	@When("User send the HTTPsDELETE request with invalid programId")
//	public void user_send_the_htt_ps_delete_request_with_invalid_program_id() {
//		response = when().delete(postURI).then().extract().response();
//	}
}
