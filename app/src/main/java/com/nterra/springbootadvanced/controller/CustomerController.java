package com.nterra.springbootadvanced.controller;

import com.nterra.springbootadvanced.model.CustomerDTO;
import com.nterra.springbootadvanced.service.CustomerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @GetMapping(value = "/customers",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CustomerDTO>> getCustomers() {
    return ResponseEntity.ok(customerService.findAll().toList());
  }

  @PostMapping(value = "/customer",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomerDTO> postCustomer(@RequestBody CustomerDTO customerDTO) {
    return ResponseEntity.ok(new CustomerDTO(customerService.save(customerDTO)));
  }

  @GetMapping(value = "/customers",
      produces = MediaType.TEXT_HTML_VALUE)
  public ModelAndView customerList(ModelAndView modelAndView) {
    modelAndView.addObject("customers", customerService.findAll().toList());
    modelAndView.addObject("newCustomer", new CustomerDTO());
    return modelAndView;
  }

  @PostMapping(value = "/customers",
      produces = MediaType.TEXT_HTML_VALUE)
  public ModelAndView customerList(
      @ModelAttribute("newCustomer") CustomerDTO customerFormData,
      BindingResult bindingResult,
      ModelAndView modelAndView) {
    if (!bindingResult.hasErrors()) {
      customerService.save(customerFormData);
      customerFormData = new CustomerDTO();
    }
    modelAndView.addObject("customers", customerService.findAll().toList());
    modelAndView.addObject("newCustomer", customerFormData);
    return modelAndView;
  }
}
