@Batch_module
Feature: Test LMS apis batch module with BaseUrl and Endpoints
  
 Background:
  Given User sets Authorization to "No Auth" from batch
  
  
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
 			
 	@POST_NegativeByExistingBatchName 
  Scenario Outline: validating User able to create a batch with valid endpoint and existing BatchName
    Given User is provided with the BaseUrl and endpoint with existing BatchName 
    When User send the HTTPsPOST request to server with the payload from "<sheetname>" and <rownumber> existing BatchName
    Then User validates the response with status code "400" from batchpost
    Examples:
			| sheetname |rownumber|
			| user			| 0   |	
			
  @GET_PositiveAllBatches @batchall @newProgram  
  Scenario: validating User able to retrieve all batches with valid endpoint
    Given User is provided with the BaseUrl and the Endpoints to create a GET request for all batches
    When  User send the HTTPsGET request to get all batches
    Then  User validates the response with Status code 200 from batch 

 @GET_PositiveByValidBatchID 
  Scenario: validating User able to retrieve batch with valid batchID
    Given User is provided with the BaseUrl and the Endpoints to create a GET request from batch
    When  User send the HTTPsGET request with valid batchID
    Then  User validates the response with Status code "200"
    Then  User validates the response with Status code 200 from batch
    
    @GET_PositiveByValidBatchName 
  Scenario: validating User able to retrieve batch with valid batch name
    Given User is provided with the BaseUrl and the Endpoints from batch
    When  User send the HTTPsGET request with valid batch name
   Then  User validates the response with batchName and status 200
    
    @GET_PositiveByValidProgramID 
  Scenario: validating User able to retrieve batch with valid programID
    Given User is provided with the BaseUrl and the Endpoints 
    When  User send the HTTPsGET request for batch with programID
    Then  User validates the response with batchName and status 200 
    
   
 @PUT_PositiveUsingValidBatchID  
  Scenario Outline: validating user able to update a batch with valid batchID and Payload
    Given User is provided with the BaseUrl and the Endpoints to update fields with batchID
    When  User send the HTTPsPUT request with valid batchID "<sheetname>" and <rowno>
    Then  User validates the response with Status code "200"
    Examples:
      |sheetname|rowno|
      |user| 0|
       
     @DELETE_PositiveBatchWithValidBatchID @newProgram
  Scenario: validating user able to delete a batch with valid batchID
    Given User is provided with the BaseUrl and the Endpoints to delete a batch with valid BatchId
    When User send the HTTPsDELETE request with valid batchid
    Then User validates the response with Status code "200"

    
    @DELETE_NegativeBatchWithInvalidProgramName
  Scenario: validating user able to delete a batch with valid endpoint and invalid programName 
    Given User is provided with the BaseUrl and the Endpoints to delete batch with invalid programName 
    When User send the HTTPsDELETE request with invalid programName
    Then User validates the response with Status code "404"
 
@GET_NegativeByinValidBatchID @newProgram
  Scenario: validating User able to retrieve batch with valid batchID
    Given User is provided with the BaseUrl and the Endpoints to create a GET request from batch
    When  User send the HTTPsGET request with valid batchID
    Then  User validates the response with Status code "200"
    
    @PUT_negativeUsingininValidBatchID  
  Scenario Outline: validating user able to update a batch with valid batchID and Payload
    Given User is provided with the BaseUrl and the Endpoints to update fields with batchID
    When  User send the HTTPsPUT request with valid batchID "<sheetname>" and <rowno>
    Then  User validates the response with Status code "200"
    Examples:
      |sheetname|rowno|
      |user| 0|
      
    @GET_NegativeByinValidProgramID 
  Scenario: User send the HTTPsGET request for batch with invalid programID
    Given User is provided with the BaseUrl and the Endpoints 
    When  User send the HTTPsGET request for batch with programID
    Then  User validates the response with batchName and status 404 
    
   