package assignmentAutomation.ref.in;

public class Payloads {
	
	
	public static String authenticatePayload() {
		
	return	"{\n"
		+ "\"mobile\": \"9988776655\",\n"
		+ "\"password\" : \"testuser@123\"\n"
		+ "}";
	}
	
	
	public static String bookingPayload(String carId, String carLisc, int price, String fromDate, String toDate) {
		
	return	"{\n"
			+ "			\"car_id\": \""+carId+"\",\n"
			+ "			\"car_license_number\": \""+carLisc+"\",\n"
			+ "			\"total_rent_bill\": "+price+",\n"
			+ "			\"from_date_time\": \""+fromDate+"\",\n"
			+ "			\"to_date_time\": \""+toDate+"\"\n"
			+ "			}";
	}

}
