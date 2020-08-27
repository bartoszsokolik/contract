package pl.solutions.software.sokolik.bartosz.contract.person

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import pl.solutions.software.sokolik.bartosz.contract.person.domain.PersonService
import pl.solutions.software.sokolik.bartosz.contract.sampledata.PersonSampleData
import spock.lang.Specification

@DirtiesContext
@AutoConfigureMessageVerifier
@WebMvcTest(controllers = PersonResource)
class PersonResourceBase extends Specification implements PersonSampleData {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    PersonService personService = Mock()

    def setup() {
        RestAssuredMockMvc.mockMvc(mockMvc)
        personService.get(UUID.fromString('294c0951-b392-4fa9-9653-ad8f2b99b966')) >> savedPersonDto
    }
}
