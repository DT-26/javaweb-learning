package com.itdt.service;

import com.itdt.pojo.Emp;
import com.itdt.pojo.EmpQueryParam;
import com.itdt.pojo.LoginInfo;
import com.itdt.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    void save(Emp emp);

    void delete(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(Emp emp);

    LoginInfo login(Emp emp);
}
