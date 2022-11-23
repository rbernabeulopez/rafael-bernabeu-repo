package com.example.block6pathvariableheaders;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;


@RestController
public class UserController {
    @PostMapping
    User getUserByBody(@RequestBody User user) {
        return user;
    }

    @GetMapping("user/{userId}")
    long getUserByPathId(@PathVariable Long userId) {
        return userId;
    }

    @PutMapping("post")
    HashMap<String, String> receiveParams(@RequestParam("var1") String var1, @RequestParam("var2") String var2) {
        HashMap<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("var1", var1);
        hashMap.put("var2", var2);
        return hashMap;
    }
}