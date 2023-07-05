package com.xiaoruiit.knowledge.point.datatype;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoruiit.knowledge.point.KnowledgePointApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author hanxiaorui
 * @date 2023/7/4
 */
@SpringBootTest(
        classes = KnowledgePointApplication.class)
@RunWith(SpringRunner.class)
@Slf4j
public class IntegerTest {

        public static void main(String[] args) {
            Integer a = 0;
            Integer b = 0;
            Integer c = 200;
            Integer d = 200;

            Integer e = JSON.parseObject(JSON.toJSONString(String.valueOf(0)), Integer.class);

            log.warn("a == b:{}", a == b);
            log.warn("c == d:{}", c == d);
            log.warn("a.equals(b):{}", a.equals(0));
            log.warn("a.equals(e):{}", a.equals(e));
            log.warn("a == e:{}", a == e);
        }
}
