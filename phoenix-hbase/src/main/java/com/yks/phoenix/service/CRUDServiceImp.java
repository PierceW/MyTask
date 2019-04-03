package com.yks.phoenix.service;

import com.yks.phoenix.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by cgt on 17-12-7.
 */
@Service
public class CRUDServiceImp implements CRUDService {

    @Autowired
    @Qualifier("phoenixJdbcTemplate")
    JdbcTemplate phoenixJdbcTemplate;
    public Result add(){
        int i=1;
//        for (int i=0; i<100; i++) {
            phoenixJdbcTemplate.update("upsert into STOCK_SYMBOL(SYMBOL,COMPANY) values('xuxiao_"+i+"','德国柏林_"+i+"')");
//        }

        return new Result(true,"数据添加成功");

    }

    public Result update(){
        int res = phoenixJdbcTemplate.update("upsert into STOCK_SYMBOL(id,name) values('20','yyggg')");
        return new Result(true,"数据更新成功");

    }

    public Result delete(){
        phoenixJdbcTemplate.update("delete from STOCK_SYMBOL where id ='20'");
        return new Result(true,"数据删除成功");

    }


    public List<Map<String, Object>> query(){
        return phoenixJdbcTemplate.queryForList("select * from STOCK_SYMBOL");
    }

    public Result createSchema(String schema) {
        phoenixJdbcTemplate.update("create schema mytry");
        return new Result(true, "创建schema 成功");
    }
}
