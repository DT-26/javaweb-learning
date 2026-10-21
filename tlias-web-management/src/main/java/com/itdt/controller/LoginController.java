package com.itdt.controller;

import com.itdt.pojo.Emp;
import com.itdt.pojo.LoginInfo;
import com.itdt.pojo.Result;
import com.itdt.service.EmpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public Result Login(@RequestBody Emp emp){
        log.info("登录： {}", emp);
        LoginInfo info = empService.login(emp);

        return info != null ? Result.success(info) : Result.error();
    }

}
