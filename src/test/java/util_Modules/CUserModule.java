package util_Modules;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import stepDefinition.BBatch_module_SD;
import utilities.Config_Reader;
import utilities.ExcelReader;
import utilities.PageUtils;

public class CUserModule {
	private String noAuth;
	private String usercomments;
	private String usereduPg;
	private String userduUg;
	private String userfirstname;
	private String userlastname;
	private String userlinkedinUrl;
	private String userlocation;
	private String usermiddlename;
	private String roleid;
	private String userRoleStatus;
	private String userTimeZone;
	private String userVisaStatus,userrolestatus;
	private String userid;
	private String userRoleStatusModified,userVisaStatusModified,userTimeZoneModified,userRoleProgramStatus;
	public static String UserId;
	public static int roleID;
	public static String StaffId;
	public static String AdminId;
	public static String StudId;
	RequestSpecification requestSpecification;
	BBatch_module_SD batchmodule=new BBatch_module_SD();
	Response response;
	
	
	
	public RequestSpecification noAuth(String noauth)
	{
		noAuth=noauth;	
	RequestSpecification requestSpecification = RestAssured.given()
			.header("Authorization", noauth).contentType("application/json");
	return requestSpecification;
	}
	public void getDatafromExcel(String sheetname, int rownumber) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
		ExcelReader reader = new ExcelReader();	
		List<Map<String, String>> testdata;
		try {
			testdata = reader.getData(Config_Reader.excelpath(), sheetname);
			usercomments = testdata.get(rownumber).get("userComments");	
			 userduUg=testdata.get(rownumber).get("userEduPg");
			 usereduPg=testdata.get(rownumber).get("userEduUg");
			 userfirstname = testdata.get(rownumber).get("userFirstName");	
			 userlastname=testdata.get(rownumber).get("userLastName");
			 userlinkedinUrl=testdata.get(rownumber).get("userLinkedinUrl");
			 userlocation = testdata.get(rownumber).get("userLocation");	
			 usermiddlename=testdata.get(rownumber).get("userMiddleName");
			 roleid=testdata.get(rownumber).get("roleIdR01");
			 userRoleStatus=testdata.get(rownumber).get("userRoleStatusActive");
			 userTimeZone = testdata.get(rownumber).get("TimeEST");	
			 userVisaStatus=testdata.get(rownumber).get("userVisaStatus");
			 userRoleStatusModified=testdata.get(rownumber).get("userRoleStatusModified");
			 userVisaStatusModified=testdata.get(rownumber).get("uservisastatusmodified");
			 userTimeZoneModified=testdata.get(rownumber).get("usertimezonemodified");
			 userRoleProgramStatus=testdata.get(rownumber).get("UserRoleProgramBatchStatus");
			
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
	public Response putUserAssignBatch(String posturi) throws JsonProcessingException {			
		ObjectMapper obj=new ObjectMapper();
		ObjectNode objnode1 = obj.createObjectNode();
		objnode1.put("programId",BBatch_module_SD.batchpostprogramID);
		objnode1.put("roleId", "R01");
		objnode1.put("userId", UserId);
		ObjectNode objnode2 = obj.createObjectNode();
		objnode2.put("batchId",BBatch_module_SD.batchID);
		objnode2.put("userRoleProgramBatchStatus", userRoleProgramStatus);
		ArrayNode  objnode3 = obj.createArrayNode();
		objnode3.add(objnode2);
		objnode1.set("userRoleProgramBatches",objnode3);
		objnode1.arrayNode();	
		String payload = objnode1.toString();
		String createdNestedJsonObject = obj.writerWithDefaultPrettyPrinter().writeValueAsString(objnode1);
		System.out.println("Created nested JSON Object is : \n"+ createdNestedJsonObject);		
		Response response = noAuth(noAuth).body(payload).put(posturi+"{userid}",CUserModule.UserId);
	//	Response response = noAuth(noAuth).body(payload).put(posturi+CUserModule.UserId);
		//UserId=response.jsonPath().getString("userId");		
		System.out.println(response.statusLine());	
		return response;
		}
	
	
	public Response postuser(String postURI) throws IOException, ParseException
	{
		ObjectMapper obj=new ObjectMapper();
		ObjectNode objnode = obj.createObjectNode();
		objnode.put("userComments",usercomments);
		objnode.put("userEduPg", usereduPg);
		objnode.put("userEduUg", userduUg);
		objnode.put("userFirstName",userfirstname);
		objnode.put("userId","");
		objnode.put("userLastName", userlastname);
		objnode.put("userLinkedinUrl", userlinkedinUrl);
		objnode.put("userLocation",userlocation);
		objnode.put("userMiddleName", usermiddlename);
		objnode.put("userPhoneNumber", PageUtils.phoneNum());
	
		ObjectNode objnode2 = obj.createObjectNode();
		objnode2.put("roleId",roleid);
		objnode2.put("userRoleStatus", userRoleStatus);
		ArrayNode  objnode3 = obj.createArrayNode();
		objnode3.add(objnode2);
		objnode.set("userRoleMaps",objnode3);
		objnode.arrayNode();
		objnode.put("userTimeZone",userTimeZone);
		objnode.put("userVisaStatus", userVisaStatus);
		String payload = objnode.toString();
		String createdNestedJsonObject = obj.writerWithDefaultPrettyPrinter().writeValueAsString(objnode);
		
		System.out.println("Created nested JSON Object is : \n"+ createdNestedJsonObject);		
		Response response = noAuth(noAuth).body(payload).post(postURI);
		UserId=response.jsonPath().getString("userId");
		//roleID=response.jsonPath().getString("roleId");
		//String roleID=response.jsonPath().getString("userRoleMaps.roleId[0]");
	//	System.out.println(roleID);
		System.out.println(response.jsonPath().prettyPrint());		
		return response;
	}
	@SuppressWarnings("unchecked")
	public Response putuserRoleStatus(String postURI) throws JsonProcessingException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("roleId",roleid);
		jsonObject.put("userRoleStatus",userRoleStatus);			
		String payload = jsonObject.toString();
		Response response = noAuth(noAuth).body(payload).put(postURI+"{userid}",CUserModule.UserId);
		String body=response.statusLine();	
		System.out.println("MESSAGE:"+body);
		return response;
		
	}
	
	@SuppressWarnings("unchecked")
	public Response putuser(String postURI) throws JsonProcessingException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("userComments",usercomments);
		jsonObject.put("userEduPg", usereduPg);		
		jsonObject.put("userEduUg", userduUg);
		jsonObject.put("userFirstName",userfirstname);	
		jsonObject.put("userId",UserId);
		jsonObject.put("userLastName", userlastname);	
		jsonObject.put("userLinkedinUrl", userlinkedinUrl);
		jsonObject.put("userLocation",userlocation);	
		jsonObject.put("userMiddleName", usermiddlename);
		jsonObject.put("userPhoneNumber", PageUtils.phoneNum());	
		jsonObject.put("userTimeZone",userTimeZoneModified);
		jsonObject.put("userVisaStatus", userVisaStatusModified);	
		System.out.println(userTimeZoneModified + userVisaStatusModified + UserId);		
		String payload = jsonObject.toString();
		Response response = noAuth(noAuth).body(payload).put(postURI+"{userid}",CUserModule.UserId);		
		String body=response.statusLine();	
		System.out.println("MESSAGE:"+body);
		return response;
		
	}
	public Response deletebatchid(String postURI) {
		response=noAuth(noAuth).delete(postURI+"{userid}",CUserModule.UserId);	
		return response;
	}
	


	
	
}
