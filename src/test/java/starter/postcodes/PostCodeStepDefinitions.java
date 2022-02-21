package starter.postcodes;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;

import java.util.Locale;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static net.serenitybdd.rest.SerenityRest.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.equalTo;

public class PostCodeStepDefinitions {

    @Steps
    PostCodeAPI postCodeAPI;

    @When("I look up a post code {word} for country code {word}")
    public void lookUpAPostCode(String postCode, String country) {
        postCodeAPI.fetchLocationByPostCodeAndCountry(postCode, country);
    }

    @Then("the resulting location should be {} in {}")
    public void theResultingLocationShouldBe(String placeName, String country) {
        restAssuredThat(response -> response.statusCode(200));
        restAssuredThat(response -> response.body(LocationResponse.COUNTRY, equalTo(country)));
        restAssuredThat(response -> response.body(LocationResponse.FIRST_PLACE_NAME, equalTo(placeName)));
    }

    @Given("I set the base url {word}")
    public void iSetTheBaseUrlBaseUrl(String baseUrl) {
        postCodeAPI.setUrl(baseUrl);
    }

    @When("I perform a {word} to the endpoint {word}")
    public void iPerformAOperationToTheEndpointEndpoint(String operation,String endpoint) {
        if (operation.equals("GET")){
            postCodeAPI.performRequest(endpoint);
        }else{
            postCodeAPI.performDeleteById(endpoint);
        }
    }

    @When("I perform a {word} to the endpoint {word} with the info {word} and {word}")
    public void iPerformAOperationToTheEndpointEndpointWithTheInfoUserNameAndUserJob(String operation,String endpoint,String name,String job) {
        if(operation.equals("POST")){
            postCodeAPI.performPostRquest(endpoint,name,job);
        }else{
            postCodeAPI.performPutRequest(endpoint,name,job,"7");
        }
    }

    @When("I perform a GET to find a user by id {word}")
    public void iPerformAOperationToTheEndpointEndpointAndTheIdId(String id) {
            postCodeAPI.performGetById(id);
    }

    @When("I perform a {word} to the endpoint {word} with the json {word}")
    public void iPerformAOperationToTheEndpointEndpointWithTheJsonJsonPath(String operation,String endpoint,String jsonPath) {
            postCodeAPI.performPostRquestWithJson(endpoint,jsonPath);
    }
}
