package com.minsoo.co.tireerpserver.api.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

    @GetMapping(value = "/home")
    public String home() {
        return "<h1>home</h1>";
    }
}
