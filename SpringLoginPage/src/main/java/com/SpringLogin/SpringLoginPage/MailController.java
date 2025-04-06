package com.SpringLogin.SpringLoginPage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailController {

    @Autowired
    private MailService emailservice;

    @GetMapping("/sendmail")
    public String EmailSend(@RequestParam String to){
        System.out.println("Hello");
        emailservice.sendmail(to, "Spring Mail Test","Hello Srishan, this is a mail generated from spring boot!!");
        return "Email Sent Successfully";
    }
}
