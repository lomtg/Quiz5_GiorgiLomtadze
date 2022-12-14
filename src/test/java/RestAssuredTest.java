import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RestAssuredTest {

    @DataProvider(name = "test1")
    public static Object[][] circuitIndexAndValues() {
        return new Object[][] {{1, "USA"}, {5, "Hungary"}};
    }

    @Test(dataProvider = "test1")
    public void test(Integer index, String value) {
        RequestSpecification request = RestAssured.given();

        Response response = request.get("http://ergast.com/api/f1/2017/circuits.json");

        String circuit = response.jsonPath().get("MRData.CircuitTable.Circuits[" + index +"].circuitId");

        Response circuitResponse = request.get("http://ergast.com/api/f1/circuits/" + circuit + ".json");

        String circuitCountry = circuitResponse.jsonPath().get("MRData.CircuitTable.Circuits[0].Location.country");

        assert(circuitCountry).equals(value);
    }
}