package com.clear.bootest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * Created by Administrator on 2015/9/24.
 */
public class JsonTest {

    @Test
    public void testJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User value = new User();
            value.setSex("男");
            value.setUserName("aaa");
            String reader = objectMapper.writeValueAsString(value);
            System.out.println("reader = " + reader);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    class User {
        private String userName;
        private String sex;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

    }

}
