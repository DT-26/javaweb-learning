package com.itdt.pojo;

import lombok.Data;
import org.springframework.web.bind.annotation.RestController;
@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(){
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Result success(Object object){
        Result result = new Result();
        result.data = object;
        result.code = 1;
        result.msg = "success";
        return result;
    }

    public static Result error(){
        Result result = new Result();
        result.code = 0;
        result.msg = "fail";
        return result;
    }
}
