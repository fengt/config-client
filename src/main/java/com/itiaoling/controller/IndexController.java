package com.itiaoling.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.itiaoling.dao.OneBaseDao;
import com.itiaoling.dao.TwoBaseDao;
import com.itiaoling.model.TestOne;
import com.itiaoling.model.TestTwo;


@RestController
public class IndexController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
    private static final String NAMESPACE_ONE = "com.itiaoling.model.TestOneMapper.";
    private static final String NAMESPACE_TWO = "com.itiaoling.model.TestTwoMapper.";
    
    
    @Autowired
    private OneBaseDao oneBaseDao;

    @Autowired
    private TwoBaseDao twoBaseDao;

    
    @RequestMapping("/")
    public String index() throws Exception {

        List<TestOne> testOneList = null;

        testOneList = oneBaseDao.getSqlSession().selectList(NAMESPACE_ONE + "selectList", new TestOne());
        for (TestOne item : testOneList) {
            LOG.info("数据源 oneBaseDao ：查询结果:{}", JSONObject.toJSONString(item));
        }

        List<TestTwo> testTwoList = null;

        testTwoList = twoBaseDao.getSqlSession().selectList(NAMESPACE_TWO + "selectList", new TestTwo());

        for (TestTwo item : testTwoList) {
            LOG.info("数据源 twoBaseDao：查询结果:{}", JSONObject.toJSONString(item));
        }

        String onePoList = JSONObject.toJSONString(testOneList);
        String twoPoList = JSONObject.toJSONString(testTwoList);

        return "数据源 oneBaseDao ：查询结果:" + onePoList + "<br/> 数据源 twoBaseDao ：查询结果:" + twoPoList;
    }
}
