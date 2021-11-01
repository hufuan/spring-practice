package com.fuhu.querydsl.controller;

import com.fuhu.querydsl.service.AuthBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class AuthBookController {
    @Autowired
    private AuthBookService authBookService;
}
