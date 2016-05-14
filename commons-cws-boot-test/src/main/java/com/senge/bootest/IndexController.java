package com.clear.bootest;

import com.alibaba.fastjson.JSON;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Administrator on 2015/10/9.
 */
@RestController
public class IndexController {
    @RequestMapping("/index")
    public String index(@Valid Person person, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError allError : allErrors) {
            System.out.println(allError.getDefaultMessage());
        }
        return JSON.toJSONString(person);
    }
}
