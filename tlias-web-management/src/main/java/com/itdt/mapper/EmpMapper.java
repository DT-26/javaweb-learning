package com.itdt.mapper;

import com.itdt.pojo.Emp;
import com.itdt.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {

    //@Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
    //public long count();

    //@Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id " +
    //        "order by e.update_time desc limit #{start}, #{pageSize}")
    //public List<Emp> list(Integer start, Integer pageSize);

    //@Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id " +
    //        "order by e.update_time desc")

    //public List<Emp> list(String name, Integer gender, LocalDate begin, LocalDate end);

    List<Emp> list(EmpQueryParam empQueryParam);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time) " +
    "values(#{username}, #{name},  #{gender}, #{image}, #{job}, #{entrydate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);


    void deleteByIds(List<Integer> ids);

    Emp getById(Integer id);

    void updateById(Emp emp);


    @Select("select id, username, name from  emp where username = #{username} and password = #{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
