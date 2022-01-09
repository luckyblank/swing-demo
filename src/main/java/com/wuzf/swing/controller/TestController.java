package com.wuzf.swing.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/swing")
public class TestController {

    @GetMapping("/hello")
    public String hello(String helloWord){
        return helloWord;
    }
}
