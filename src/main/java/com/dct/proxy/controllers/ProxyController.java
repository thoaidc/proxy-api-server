package com.dct.proxy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ProxyController {
    @GetMapping("/")
    public String welcome() {
        return "OK";
    }

    @RequestMapping("/**")
    public String proxy() {
        return "OK";
    }
}
