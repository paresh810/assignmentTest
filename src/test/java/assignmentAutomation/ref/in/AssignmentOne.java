package assignmentAutomation.ref.in;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class AssignmentOne {
	
	public String authToken;
	public String fromDate = "2021-05-23";
	public String toDate = "2021-05-24";
	
	
	
	@Test
	public void authentication() {
		RestAssured.baseURI="https://refyne-car-rental.herokuapp.com/api/";
		
		
		String authenticateResponse = given().relaxedHTTPSValidation().header("Content-Type","application/json").
		body(Payloads.authenticatePayload()).
		when().post("/auth/login").
		then().statusCode(200).extract().response().asString();
		
		
		JsonPath path =  new JsonPath(authenticateResponse);
		
		authToken= path.get("authToken");
		
		
		
		String carList = given().log().all().header("Authorization","Bearer "+authToken)
				.pathParam("fromDate", fromDate).pathParam("toDate", toDate)
				.header("Content-Type","application/json").when().get("/car/search-cars/{fromDate}/{toDate}").
				then().log().all().statusCode(200).extract().response().asString();
		
		
		path =  new JsonPath(carList);
		
		String firstCarId = path.get("available_cars[0]._id");
		String firstCarLisc = path.getString("available_cars[0].car_license_number");
		
		
		String carDetails = given().log().all().header("Authorization","Bearer "+authToken)
				.pathParam("carId", firstCarId)
				.pathParam("fromDate", fromDate).pathParam("toDate", toDate)
				.header("Content-Type","application/json").when().get("/car/calculate-price/{carId}/{fromDate}/{toDate}").
				then().log().all().statusCode(200).extract().response().asString();
		
		System.out.println(carDetails);
		
		
		path =  new JsonPath(carDetails);
		
		int totalRent  = path.getInt("totalRentAmount") ;
		
		
		given().relaxedHTTPSValidation().header("Content-Type","application/json").
				header("Authorization","Bearer "+authToken).
				body(Payloads.bookingPayload(firstCarId, firstCarLisc, totalRent,fromDate, toDate)).
				when().post("/car/book").
				then().log().all().statusCode(200).body("message", equalTo("Your car is booked successfully."));
		
		

		given().relaxedHTTPSValidation().header("Content-Type","application/json").
		header("Authorization","Bearer "+authToken).
		when().get("/user/bookings").
		then().log().all().statusCode(200);

		
		
		
	
		
		
		
		
		
		
		
		
		
	}

}
