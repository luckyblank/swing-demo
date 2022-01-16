package com.wuzf.swing.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/swing")
public class TestController {

    @GetMapping("/hello")
    public String hello(String helloWord){
        return helloWord;
    }

}
