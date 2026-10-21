package com.itdt.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itdt.mapper.EmpExprMapper;
import com.itdt.mapper.EmpMapper;
import com.itdt.pojo.*;
import com.itdt.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private EmpExprMapper empExprMapper;
    //@Override
    //public PageResult<Emp> page(Integer page, Integer pageSize) {
    //    long total = empMapper.count();
    //    Integer start = (page - 1) * pageSize;
    //    List<Emp> rows = empMapper.list(start, pageSize);
    //    return new PageResult<Emp>(total, rows);
    //}

    //@Override
    //public PageResult<Emp> page(Integer page, Integer pageSize, String name, Integer gender, LocalDate begin, LocalDate end) {
    //    PageHelper.startPage(page, pageSize);
    //    List<Emp> emplist = empMapper.list(name, gender, begin, end);
    //    Page<Emp> p = (Page<Emp>) emplist;
    //    return new PageResult<Emp>(p.getTotal(), p.getResult());
    //}

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());
        List<Emp> emplist = empMapper.list(empQueryParam);
        Page<Emp> p = (Page<Emp>) emplist;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(Emp emp){
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);

        List<EmpExpr> exprList = emp.getExprList();
        if(!exprList.isEmpty()){
            for (EmpExpr empExpr : exprList) {
                empExpr.setEmpId(emp.getId());
                empExpr.setCreateTime(LocalDateTime.now()); //补上！
                empExpr.setUpdateTime(LocalDateTime.now()); //补上！
            }
            empExprMapper.insertBatch(exprList);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void delete(List<Integer> ids) {

        empMapper.deleteByIds(ids);

        empExprMapper.deleteByEmpIds(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(Emp emp) {
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));

        List<EmpExpr> exprList = emp.getExprList();
        // 先判断非null，再判断非空
        if(exprList != null && !exprList.isEmpty()){
            LocalDateTime now = LocalDateTime.now();
            exprList.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
                empExpr.setCreateTime(now);
                empExpr.setUpdateTime(now);
            });
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        Emp e = empMapper.selectByUsernameAndPassword(emp);

        if(e != null){
            log.info("登录成功： {}", e);
            return new LoginInfo(e.getId(), e.getUsername(), e.getName(), "");
        }
        return null;
    }
}
