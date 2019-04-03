package com.cmcc.ks.controller;

import com.cmcc.ks.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName PhoenixController
 * @Description TODO
 * @Author Alex
 * @Date 2019/3/18 10:57
 * @Version 1.0
 **/
@RestController("/phoenix/")
public class PhoenixController {

    @Autowired
    private JdbcTemplate phoenixJdbcTemplate;

    @GetMapping("list")
    public Result list() {
        List<Map<String, Object>> list = phoenixJdbcTemplate.queryForList("select * from ");
        return new Result("200", list);
    }

}
