package com.itdt.controller;

import com.itdt.pojo.Emp;
import com.itdt.pojo.EmpQueryParam;
import com.itdt.pojo.PageResult;
import com.itdt.pojo.Result;
import com.itdt.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;

    //@GetMapping("/emps")
    //public Result page(@RequestParam(defaultValue = "1") Integer page,
    //                   @RequestParam(defaultValue = "10") Integer pageSize,
    //                   String name, Integer gender,
    //                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
    //                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
    //    log.info("分页查询: {}, {}, {}, {}, {}, {}", page, pageSize, name, gender, begin, end);
    //    PageResult<Emp> pageResult = empService.page(page, pageSize, name, gender, begin, end);
    //    return Result.success(pageResult);
    //}

    @GetMapping("/emps")
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询: {}", empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }

    @PostMapping("/emps")
    public Result save(@RequestBody Emp emp){
        log.info("新增员工：{}", emp);
        empService.save(emp);
        return Result.success();
    }

    @DeleteMapping("/emps")
    public Result delete(@RequestParam List<Integer> ids){
        log.info("删除员工: {}", ids);
        empService.delete(ids);
        return Result.success();
    }

    @GetMapping("/emps/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询员工信息: {}", id);
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    @PutMapping("/emps")
    public Result update(@RequestBody Emp emp){
        log.info("修改员工: {}", emp);
        empService.update(emp);
        return Result.success();
    }

}
