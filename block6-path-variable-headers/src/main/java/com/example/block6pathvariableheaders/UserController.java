package com.example.block6pathvariableheaders;

import org.springframework.web.bind.annotation.*;


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
    String receiveParams(@RequestParam("var1") String var1, @RequestParam("var2") String var2) {
        return "var1 = " + var1 + ", var2 = " + var2;
    }
}