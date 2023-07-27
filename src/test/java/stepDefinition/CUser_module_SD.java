package stepDefinition;

import static io.restassured.RestAssured.when;

import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import util_Modules.CUserModule;
import utilities.Config_Reader;
import utilities.LoggerLoad;
import utilities.PageUtils;

public class CUser_module_SD {
	
	static ResponseBody responsebody;
	private static Config_Reader configreader=new Config_Reader();
    static Properties prop; 	 
    static String batchprogName ,postprogramName;	
    private static RequestSpecification request;
	Response response;
	static int batchId;
	private static String postURI,programdescription,batchName;
    static int postprogramID,batchID;
    static int batchpostprogramID;
	public  static String programID,programName;	   
	private static String currentTime=PageUtils.getcurrentDateTime();
	private static String lastModTime=PageUtils.getcurrentDateTime();  
	public static CUserModule userModule=new CUserModule();
	
	@Given("User sets Authorization to {string} from user")
    public  void user_sets_authoization_to(String string) {    	
 		 
		request =  userModule.noAuth(string);
	}
	
	
	@Given("User executes GET Request for all User")
	public void user_executes_get_request_for_all_user() throws IOException {
		request = RestAssured.given();
	    postURI=configreader.baseUrl()+Config_Reader.userGetAllUsersrequest();
	    LoggerLoad.info("****User executes GET Request for all User***" + postURI);
	}

	@When("User send HTTPS Request")
	public void user_send_https_request() {
		response=request.get(postURI);
		LoggerLoad.info("User send request to get all users :" +response.statusLine() );
	}

   
	@Then("User validates the response with batchName and statuss {int}")
	public void user_validates_the_response_with_status_code_response_time_header(Integer expectedstatuscode) {
		Assert.assertEquals(response.statusCode(), expectedstatuscode);	
		LoggerLoad.info("asserting the batchID " + batchID);	
//		String bodyasString=response.asString()	;
//		Assert.assertTrue(bodyasString.contains(batchName));	
	}
	@Given("User executes GET Request to get User info by ID")
	public void user_executes_get_request_to_get_user_info_by_id() throws IOException {
	    postURI=configreader.baseUrl()+Config_Reader.userUpdateuseruserGetUserinfobyiduserDelete()+CUserModule.UserId;
	    LoggerLoad.info("*********"+postURI);
	    
	}

	@When("User send GET Request to get User info by ID")
	public void user_send_get_request_to_get_user_info_by_id() {
	    response=when().get(postURI);
	    LoggerLoad.info("****user got by userid*****");
	}

	@Then("User gets all Users related info by ID in response {int}")
	public void user_gets_all_users_related_info_by_id_in_response(Integer expectedstatuscode) {
		Assert.assertEquals(response.statusCode(), expectedstatuscode);	
	}

	@Given("User executes the POST request to create new User with Role_Admin")
	public void user_executes_the_post_request_to_create_new_user_with_and() throws IOException {
	    postURI=configreader.baseUrl()+Config_Reader.userPostrequestcreatinguserwithrole();
	    LoggerLoad.info("User send the post request to create the the user role : "+postURI);
	    
	}
	
	

	@When("User tries to create User with the mentioned Roles {string} and {int}")
	public void user_tries_to_create_user_with_the_mentioned_roles(String SheetName,Integer rownumber) throws InvalidFormatException, IOException, ParseException, org.json.simple.parser.ParseException {
		userModule.getDatafromExcel(SheetName, rownumber);
	    response=userModule.postuser(postURI);
	}

	@Then("A new User should be created with the mentioned Roles {string}")
	public void a_new_user_should_be_created_with_the_mentioned_roles(String expectedstatuscode) {
	    { 
	    	Assert.assertEquals(response.statusCode(),Integer.parseInt(expectedstatuscode));	
			
		}
	}
	
	@Given("User is provided with the BaseUrl and the Endpoints to delete user")
	public void user_is_provided_with_the_baseurl_and_the_endpoints_delete() throws IOException
	{
		postURI=configreader.baseUrl()+Config_Reader.userUpdateuseruserGetUserinfobyiduserDelete();
		
		LoggerLoad.info("****baseurl and endpoint to delete user***** : " + postURI);
		
	}

	@When("User send the DELETE request")
	public void user_send_the_delete_request() {
		//String noAuth="No Auth";
		//response=userModule.noAuth(noAuth).delete(postURI+"{userid}",CUserModule.UserId);
		System.out.println(postURI);
		response= userModule.deletebatchid(postURI);
		//response=when().delete(postURI+CUserModule.UserId);
		System.out.println(postURI+CUserModule.UserId);
		System.out.println(response.statusLine());
	}

	@Then("User is able to DELETE User {string}")
	public void user_is_able_to_delete_user(String expectedstatuscode) {
		//Assert.assertEquals(response.statusCode(),(Integer.parseInt(expectedstatuscode)));
		//		response.then().statusCode(Integer.parseInt(expectedstatuscode));
//		  LoggerLoad.info("get Request Successful");			
//		  Assert.assertEquals(expectedstatuscode, expectedstatuscode);			
//			 System.out.println("get all programs " + expectedstatuscode);
//			Assert.assertTrue(true);
	}

	
	@Given("User is provided with the BaseUrl and the Endpoints for update user role status")
	public void user_is_provided_with_the_base_url_and_the_endpoints_for_update_user() throws IOException {
	    postURI=configreader.baseUrl()+Config_Reader.userUpdateuserrolestatus();
	    LoggerLoad.info("****user gets the put request by userid****"+postURI);
	    
	}

	@When("User tries to update First Name, Last Name, Time Zone, and Visa Status {string} and {int}")
	public void user_tries_to_update_first_name_last_name_time_zone_and_visa_status(String sheetname,Integer rownumber) throws InvalidFormatException, IOException {
	    userModule.getDatafromExcel(sheetname,rownumber);
	    response=userModule.putuserRoleStatus(postURI);
	    LoggerLoad.info("****user sends the put request with payload and userid****");
	}

	@Then("User is able to update First Name, Last Name, Time Zone, and Visa Status")
	public void user_is_able_to_update_first_name_last_name_time_zone_and_visa_status(int expectedstatuscode) 
	{
		Assert.assertEquals(response.statusCode(), expectedstatuscode);	
	}
	
	@Given("User is provided with the BaseUrl and the Endpoints with the user id")
	public void user_is_provided_with_the_base_url_and_the_endpoints_with_the_user_id() throws IOException {
	   postURI=configreader.baseUrl()+Config_Reader.userUpdateuseruserGetUserinfobyiduserDelete();
	   LoggerLoad.info("*****User gets the request to update user*****" + postURI);
	}

	@When("User tries to update First Name, Last Name, Time Zone, and Visa Status with {string} and {int}")
	public void user_tries_to_update_first_name_last_name_time_zone_and_visa_status_with_and(String sheetname, Integer rowno) throws InvalidFormatException, IOException {
	   userModule.getDatafromExcel(sheetname, rowno);
	   response=userModule.putuser(postURI);
	}

	@Given("User is provided with the BaseUrl and the Endpoints assignUpdateUserRoleProgramBatchStatus")
	public void user_is_provided_with_the_base_url_and_the_endpoints_and_the_user_id() throws IOException {
		RequestSpecification requestSpecification = RestAssured.given()
				.header("Authorization", "No Auth").contentType("application/json");
	   postURI=configreader.baseUrl()+Config_Reader.assignUpdateuserroleprogrambatchstatus();
	   LoggerLoad.info("*****User gets the request to update user*****" + postURI);
	}
	
	@When("User tries assign user to Programes and Batches {string} and {int}")
	public void user_tries_assign_user_to_programes_and_batches_and(String sheetname, Integer rownumber) throws InvalidFormatException, IOException {
	    userModule.getDatafromExcel(sheetname, rownumber);
	    response=userModule.putUserAssignBatch(postURI);
	}

	@Then("User is able to update and checks the status code as {int}")
	public void user_is_able_to_update_and_checks_the_status_code_as(Integer expectedStatusCode) {
		Assert.assertEquals(response.statusCode(), expectedStatusCode);	
	}
	
	@Given("User executes GET Request to get allstaff info")
	public void user_executes_get_request_to_get_allstaff_info() throws IOException {
	   postURI=configreader.baseUrl()+Config_Reader.userGetallstaff();
	}

	@When("User send GET Request to get info of all staff")
	public void user_send_get_request_to_get_info_of_all_staff() {
		response=when().get(postURI);
	    LoggerLoad.info("****get all staff throeing error*****");
	}

	@Then("expected status code {string}")
	public void expected_status_code_but_gives( String expectedStatusCode) {
		Assert.assertEquals(response.statusCode(), expectedStatusCode);	
	}


}
