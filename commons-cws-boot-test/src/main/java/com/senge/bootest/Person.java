package com.clear.bootest;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by Administrator on 2015/10/9.
 */
public class Person {

    @NotBlank(message = "name不能为空")
    private String username;

    @NotNull
    private String sex;

    @Pattern(regexp = "")
    private int age;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
