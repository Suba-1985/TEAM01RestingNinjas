
@Program_module
Feature: Test LMS api program module with BaseUrl and Endpoints
  Background:
  Given User sets Authorization to "No Auth" from program
 	
 
  @GET_PositiveAllPrograms @schema1
  Scenario: validating User able to retrieve all programs with valid endpoint
    Given User is provided with the BaseUrl and the Endpoints to create a GET request
    When  User send the HTTPsGET request
    Then  User validates the response with Status code "200" and validate schema for all the program 
    Then User validates the response with Schema validation for all programs

  @POST_Positive  @schema
  Scenario Outline: validating User able to create a program with valid endpoint and Payload
    Given User is provided with the BaseUrl and endpoint and nonexisting fields in payload
    When  User send the HTTPsPOST request to server with the payload from "<sheetname>" and <rownumber>
    Then  User validates the response with status code "201" from post
    
    Examples:
			| sheetname |rownumber|
			| user		| 0   |
			
	@GET_PositiveByValidProgramID @schema
  Scenario: validating User able to retrieve program with valid programID
    Given User is provided with the BaseUrl and the Endpoints to create a GET request with valid program id
    When  User send the HTTPsGET request with valid programID
    Then  User validates the response with Status code "200" and schema
    Then User validates the response with Schema validation
			
  @POST_NegativeByExistingProgramName @negative
  Scenario Outline: validating User able to create a program with  existing ProgramName
    Given User is provided with the BaseUrl and endpoint with existing ProgramName to create a POST Request
    When User send the HTTPsPOST request to server with the payload from "<sheetname>" and <rownumber> with existing Programname
    Then User validates the response with status code "400" from post
    Examples:
			| sheetname |rownumber|
			| user			| 0  |		    
  

 @PUT_PositiveUsingValidProgramID @positive
  Scenario Outline: validating user able to update a program with valid programID and Payload
    Given User is provided with the BaseUrl and the Endpoints to update status with programID
    When  User send the HTTPsPUT request with valid programID and the payload from "<sheetname>" and <rownumber>
    Then  User validates the response with Status code "200"
    Examples:
			| sheetname |rownumber|
			| user			| 0  |		     
  
    
    @PUT_PositiveUsingValidProgramName @positive
  Scenario Outline: validating user able to update a program with valid programName and Payload
    Given User is provided with the BaseUrl and the Endpoints to update status with programName
    When  User send the HTTPsPUT request with valid programName from "<sheetname>" and <rownumber>
    Then  User validates the response with Status code "200" 
    Examples:
			| sheetname |rownumber|
			| user			| 0  |		 
   
    
  @DELETE_PositiveWithValidProgramName @positive @schema
  Scenario: validating user able to delete a program with valid programName 
    Given User is provided with the BaseUrl and the Endpoints to delete a program with valid programName 
    When User send the HTTPsDELETE request with valid programName
    Then User validates the response with Status code "200"     
 
    
   @DELETE_PositIveValidProgramID @positive
  Scenario: validating user able to delete a program with valid programId 
    Given User is provided with the BaseUrl and the Endpoints to delete a program with valid programId 
    When User send the HTTPsDELETE request with valid programId
    Then User validates the response with Status code for delete 
    
    @DELETE_NEgativeINValidProgramID @negative
  Scenario: validating user able to delete a program with valid programId 
    Given User is provided with the BaseUrl and the Endpoints to delete a program with valid programId 
    When User send the HTTPsDELETE request with valid programId
    Then User validates the response with Status code "404"
    
    @GET_NegativeByINValidProgramID @negative
  Scenario: validating User able to retrieve program with valid programID
    Given User is provided with the BaseUrl and the Endpoints to create a GET request with valid program id
    When  User send the HTTPsGET request with valid programID
    Then  User validates the response with Status code "404"
  
  