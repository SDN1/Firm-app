package com.mentormate;

import com.mentormate.dto.FirmDTO;
import com.mentormate.entity.Firm;
import com.mentormate.repository.FirmRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FirmControllerTest {

    @LocalServerPort
    private int port;

    @MockBean
    private FirmRepository firmRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void canRetrieveByIdWhenExists() {
        int id = 1;
        BDDMockito.given(firmRepository.findOne(id)).willReturn(new Firm(id, "SDN Company", "346456754", true, (float) 10));
        ResponseEntity<Firm> firmResponseEntity = testRestTemplate.getForEntity("/firms/" + id, Firm.class);

        Assertions.assertThat(firmResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(firmResponseEntity.getBody().equals(new Firm(id, "SDN Company", "346456754", true, (float) 10)));
    }

    @Test
    public void canSearchByNameWhenExists() {
        int id = 2;
        String name = "superer";
        List<Firm> result = new ArrayList<>();
        result.add(new Firm(id, name, "759642133", true, (float) 40000));

        BDDMockito.given(firmRepository.findByNameContainsAllIgnoreCase(name)).willReturn(result);
        ResponseEntity<Object[]> firmResponseEntity = testRestTemplate.getForEntity("/firms/search-by-name?content=" + name, Object[].class);

        Assertions.assertThat(firmResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(firmResponseEntity.getBody().equals(result));
    }

    @Test
    public void canRetrieveByIdWhenDoesNotExists() {
        int id = 111;
        BDDMockito.given(firmRepository.findOne(id)).willThrow(new EntityNotFoundException());
        ResponseEntity<Firm> firmResponseEntity = testRestTemplate.getForEntity("/firms/" + id, Firm.class);

        Assertions.assertThat(firmResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Assertions.assertThat(firmResponseEntity.getBody().equals(null));
    }

    @Test
    public void canSearchByNameWhenDoesNotExists() {
        String name = "xxxxxx";
        BDDMockito.given(firmRepository.findByNameContainsAllIgnoreCase(name)).willThrow(new EntityNotFoundException());
        ResponseEntity<Firm> firmResponseEntity = testRestTemplate.getForEntity("/firms/search-by-name", Firm.class);

        Assertions.assertThat(firmResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(firmResponseEntity.getBody().equals(null));
    }

    @Test
    public void canCreateANewFirm() {
        int addressId = 1;
        int contactId = 1;
        ResponseEntity<Firm> firmResponseEntity = testRestTemplate.postForEntity("/firms", new FirmDTO("SDN Company", "348156754", true, 10F, addressId, contactId), Firm.class);
        Assertions.assertThat(firmResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void contextLoads() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/firms")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        System.out.println(mvcResult.getResponse());
    }
}
