package com.nterra.springbootadvanced.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class IndexController {


  @GetMapping({"/", "index"})
  public ModelAndView home(ModelAndView modelAndView) {
    modelAndView.setViewName("index");
    return modelAndView;
  }

}
