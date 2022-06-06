package apichaining;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.AssertJUnit;

public class EndToEndTest {
	Response response;
	String BaseURI = "http://54.163.203.220:8088/employees";
	
	@Test
	public void test1() {
	
		response = GetMethodAll();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		response = PostMethod("sayali","kole","1000","kolesupriya30@gmail.com");
		AssertJUnit.assertEquals(response.getStatusCode(), 201);
		JsonPath jpath = response.jsonPath();
		int EmpId = jpath.get("id");
		System.out.println("id:- "+EmpId);
		
		response = PutMethod(EmpId, "siya","chaugule","2500","sups30kole@gmail.com");
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		jpath = response.jsonPath();
		String firstName = (jpath.get("firstName"));
		AssertJUnit.assertEquals(firstName,"siya");
		String ResponseBody = response.getBody().asString();
		System.out.println(ResponseBody);
		
		response = DeleteMethod(EmpId); 
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.getBody().asString(), "");
	
		response = GetMethod(EmpId); 
		Assert.assertEquals(response.getStatusCode(), 400);
//	    Assert.assertEquals(response.getBody().asString(), "");
		jpath = response.jsonPath();
	      Assert.assertEquals(jpath.get("message"), "Entity Not Found");
	} 
	public Response GetMethodAll() {
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		 Response response = request.get();
		 return response;
	}
	public Response PostMethod(String FirstName, String LastName,String Salary,String Email) {	
        RestAssured.baseURI = BaseURI;
		JSONObject jobj = new JSONObject();
		jobj.put("firstName", FirstName);
		jobj.put("lastName", LastName);
		jobj.put("salary", Salary);
		jobj.put("email", Email);
		
		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(ContentType.JSON)
		                          .accept(ContentType.JSON)
		                          .body(jobj.toString())
		                          .post();
		return response;
	}
	public Response PutMethod( int EmpID,String FirstName,String LastName,String Salary,String Email ) {	
        RestAssured.baseURI = BaseURI;
		JSONObject jobj = new JSONObject();
		jobj.put("firstName", FirstName);
		jobj.put("lastName", LastName);
		jobj.put("salary", Salary);
		jobj.put("email", Email);
		
		RequestSpecification request = RestAssured.given();
		
		Response response = request.contentType(ContentType.JSON) .accept(ContentType.JSON)
		                          .body(jobj.toString())
		                          .put("/" + EmpID);
		return response;
	}
	public Response DeleteMethod( int EmpID){
	RestAssured.baseURI ="http://54.163.203.220:8088/employees";
	RequestSpecification request = RestAssured.given();
	 Response response = request.delete("/" +EmpID);
	
	return response;
	}
	public Response GetMethod( int EmpID){
		RestAssured.baseURI ="http://54.163.203.220:8088/employees";
		RequestSpecification request = RestAssured.given();
		 Response response = request.get("/" +EmpID);
		
		return response;
	}
	} 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		
		
	

