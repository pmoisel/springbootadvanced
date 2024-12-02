package com.nterra.springbootadvanced.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Test
  @WithMockUser(roles = {})
  public void testGetCustomersForbidden() throws Exception {
    mockMvc.perform(
            get("/customers")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = {"USER"})
  public void testGetCustomers() throws Exception {
    mockMvc.perform(
            get("/customers")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  public void testPostCustomerForbidden() throws Exception {
    mockMvc.perform(
            post("/customer")
                .contentType(MediaType.APPLICATION_JSON).content("""
                    {"email": "jd@sacredheart.com", "fistName": "John", "lastName": "Dorian"}
                    """)
                .with(csrf()))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = {"MANAGER"})
  public void testPostCustomer() throws Exception {
    mockMvc.perform(
            post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"email": "jd@sacredheart.com", "fistName": "John", "lastName": "Dorian"}
                    """)
                .with(csrf()))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = {"MANAGER"})
  public void testGetCustomersAsManager() throws Exception {
    mockMvc.perform(
            get("/customers")
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
