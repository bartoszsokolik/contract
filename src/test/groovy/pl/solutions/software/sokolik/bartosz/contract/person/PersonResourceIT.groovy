package pl.solutions.software.sokolik.bartosz.contract.person

import com.fasterxml.jackson.databind.ObjectMapper
import io.restassured.RestAssured
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.http.ContentType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import pl.solutions.software.sokolik.bartosz.contract.sampledata.PersonSampleData
import spock.lang.Specification

import static io.restassured.config.JsonConfig.jsonConfig
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL
import static org.apache.http.HttpStatus.SC_OK
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.notNullValue
import static pl.solutions.software.sokolik.bartosz.contract.SpringProfile.TEST
import static pl.solutions.software.sokolik.bartosz.contract.person.PersonResource.PERSON_URI

@ActiveProfiles(TEST)
@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
class PersonResourceIT extends Specification implements PersonSampleData {

    @LocalServerPort
    Integer port

    @Autowired
    ObjectMapper objectMapper

    def setup() {
        RestAssured.port = port
        RestAssured.config = RestAssuredConfig.newConfig()
                .logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())
                .jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL))
    }

    def given() {
        return RestAssured.given()
                .contentType(ContentType.JSON)
    }

    def "should create person"() {
        given:
            def createPersonRequest = given()

        when:
            def createPersonResponse = createPersonRequest.when()
                    .body(objectMapper.writeValueAsString(createPersonDto))
                    .post("${PERSON_URI}")

        then:
            createPersonResponse.then()
                    .statusCode(SC_OK)
                    .body('firstName', equalTo('Jan'),
                    'lastName', equalTo('Kowalski'),
                            'id', notNullValue())
    }
}
