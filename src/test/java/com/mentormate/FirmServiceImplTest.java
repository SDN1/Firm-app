package com.mentormate;

import com.mentormate.controller.FirmController;
import com.mentormate.entity.Firm;
import com.mentormate.service.FirmService;
import com.mentormate.service.impl.FirmServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import javax.persistence.EntityNotFoundException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(FirmController.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class FirmServiceImplTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FirmServiceImpl firmService;

    @Test
    public void getFirmWhenRequestingJsonShouldReturnAndMakeModel() throws Exception {
        given(this.firmService.getFirm(1))
                .willReturn(new Firm(1, "SDN Company", "346456754", true, (float) 10));

        this.mvc.perform(get("/firms/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(content().json("{'firmId':'1', 'name':'SDN Company', 'bulstat':'346456754', 'balance':'10.0', 'address':null, 'contact':'null', 'active':'true'}"));
    }

    @Test
    public void getFirmShouldReturnNotFound() throws Exception {
        int id = 1000;
        given(this.firmService.getFirm(id))
                .willThrow(new EntityNotFoundException("Operation get failed. Firm with id= " + id + " Not Found!"));

        this.mvc.perform(get("/firms/" + id).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }
}
