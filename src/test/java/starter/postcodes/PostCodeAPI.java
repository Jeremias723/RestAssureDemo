package starter.postcodes;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import org.json.simple.JSONObject;
import org.junit.Assert;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
public class PostCodeAPI {

    private static String LOCATION_BY_POST_CODE_AND_COUNTRY = "http://api.zippopotam.us/{country}/{postcode}";
    private static String CLOCKIFY_ID_USER_ENDPOINT = "https://reqres.in/api/users/{userId}";

    @Step("Get location by postcode {0} in country {1}")
    public void fetchLocationByPostCodeAndCountry(String postcode, String country) {
        SerenityRest.given()
                .pathParam("postcode", postcode)
                .pathParam("country", country)
                .get(LOCATION_BY_POST_CODE_AND_COUNTRY);
    }

    @Step("Set the base url {0}")
    public void setUrl(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }

    @Step("I perform the operation {0} to the endpoint {1}")
    public Response performRequest(String endpoint) {

        Response response =
                given()
                        //.header("Content-Type","application/json") -> asignar header y params a la consulta
                        //.param(parameterName,ParameterValue)
                        //.contentType(ContentType.JSON).accept(ContentType.JSON) ->solo aceptaremos respuestas con este formato
                .when()
                    .get(endpoint);
                    //.statusCode(200) -> RestAssured "Assert" statusCode -> impide asignarlo a un objeto Response
                    int status = response.statusCode();
                    Assert.assertEquals(200, status);
        return response;
    }


    @Step("Perform a {0} to the endpoint '{1}' with the name {2} and the job {4}")
    public void performPostRquest(String endpoint, String name, String job) {
        JSONObject request = new JSONObject();
        request.put("name","Matias");
        request.put("job","Medic");
            given()
            .body(request.toJSONString())
            .when()
                .post(endpoint)
            .then()
                .statusCode(201);
    }

    public void performGetById(String id) {
        Response response =given()
                .pathParam("userId", id)
                .when().get(CLOCKIFY_ID_USER_ENDPOINT);
        Assert.assertEquals(200,response.statusCode());
        String name = response.path("data.first_name");
        Assert.assertEquals("Michael",name);
    }

    public void performDeleteById( String endpoint) {
        Response response = performRequest(endpoint);
        JsonPath jsonPathResponse = response.jsonPath();
        String firstUserId = jsonPathResponse.getString("data[1].id");
        given()
                .pathParam("userId",firstUserId)
                .when().delete(CLOCKIFY_ID_USER_ENDPOINT)
                .then().statusCode(204);
    }

    public void performPutRequest(String endpoint,String name,String job, String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("job", job);
        given().body(map.toString())
                .when()
                .put(endpoint+"/"+userId)
                .then().statusCode(200);
    }

    public void performPostRquestWithJson(String endpoint, String jsonPath) {
        File jsonBody = new File(jsonPath);
        Response response = given().contentType("application/json")
                .body(jsonBody)
                .when().post(endpoint);
        String avatar = response.path("avatar");
        Assert.assertEquals(">:v",avatar);
    }
}
