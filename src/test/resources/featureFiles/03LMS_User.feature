@User_module_POSITIVEansNEGATIVE
Feature: Test LMS api's USER Module with rest assured library and cucumber framework
  Background:
  Given User sets Authorization to "No Auth" from user
  

   @GET_getAllUsers 
   Scenario: User tries to extract all Users info
    Given User executes GET Request for all User
    When  User send HTTPS Request
    Then  User validates the response with batchName and statuss 200 
    
    @POST_Positive @userRun @positiveID  @smoke 
  Scenario Outline: validating User able to create a program 
    Given User is provided with the BaseUrl and endpoint 
    When  User send the POST request to server with the payload from "<sheetname>" and <rownumber>
    Then  User validates the response with status code "201" from batchpost
    Examples:
			| sheetname |rownumber|
			| user		| 0   |

  @POST_Positive @userRun @smoke
  Scenario Outline: validating User able to create a batch with valid endpoint and Payload
    Given User is provided with the BaseUrl and endpoint for batch 
    When User sends HTTPS Request for batch and  request Body with mandatory , additional using "<sheetname>" and <rownumber>
    Then User validates the response with status code "201" from batchpost
    Examples:
			| sheetname |rownumber|
			| user			| 0   |
	
    
  @POST @smoke @userRun 
  Scenario Outline: User tries to create User with Role
    Given User executes the POST request to create new User with Role_Admin 
    When User tries to create User with the mentioned Roles "<sheetname>" and <rownumber>
    Then A new User should be created with the mentioned Roles "201"   
   Examples:
    |sheetname|rownumber|
    |user|0|
    
@GET_getUserInfoById @userRun
   Scenario: User tries to get User info by ID
    Given User executes GET Request to get User info by ID
    When User send GET Request to get User info by ID
    Then User gets all Users related info by ID in response 200
    
    @PUT_updateUserRolestatus @Positiveu @userRun
  Scenario Outline: User tries to update a user with valid User ID and request body
    Given User is provided with the BaseUrl and the Endpoints for update user role status
    When User tries to update First Name, Last Name, Time Zone, and Visa Status "<sheetname>" and <rownumber>
    Then User gets all Users related info by ID in response 200
    Examples:
    |sheetname|rownumber|
    |user|0|
  
   @PUT_updateUser @Positive   @userRun
  Scenario Outline: User tries to update a user with valid User ID and request body
    Given User is provided with the BaseUrl and the Endpoints with the user id
    When User tries to update First Name, Last Name, Time Zone, and Visa Status with "<sheetname>" and <rownumber>
    Then User gets all Users related info by ID in response 200
     Examples:
    |sheetname|rownumber|
    |user|0|
    
     @PUT_assignUpdateUserRoleProgramBatchStatus @Positive @userRun
  Scenario Outline: User tries to assign user to Programes and Batches
    Given User is provided with the BaseUrl and the Endpoints assignUpdateUserRoleProgramBatchStatus
    When User tries assign user to Programes and Batches "<sheetname>" and <rownumber>
    Then User is able to update and checks the status code as 200 
    Examples:
    |sheetname|rownumber|
    |user|0|
  
    @DELETE  @smoke  @userRun
  Scenario: User wants to DELETE User
    Given User is provided with the BaseUrl and the Endpoints to delete user
    When User send the DELETE request
    Then User is able to DELETE User "200"
    
    @DELETE_PositiveBatchWithValidBatchID @newProgram @userRun
  Scenario: validating user able to delete a batch with valid batchID
    When User send the HTTPsDELETE request with valid batchid
    Then User validates the response with status code "200", response time, header
    
    @DELETE_PositIveValidProgramID @positive @userRun
  Scenario: validating user able to delete a program with valid programId 
    Given User is provided with the BaseUrl and the Endpoints to delete a program with valid programId 
    When User send the HTTPsDELETE request with valid programId
    Then User validates the response with Status code "200"
    
    @GET_getUserInfoaboutstaff @Negative  @userstaff
   Scenario: User tries to get User info about all the staff 
    Given User executes GET Request to get allstaff info 
    When User send GET Request to get info of all staff 
    Then expected status code "200" 
  
    
    