package id.ac.ui.cs.advprog.koleksikota.subscription.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class SubscriptionController {
    @GetMapping("/")
    public String subscription() {
        return "subscription";
    }
}