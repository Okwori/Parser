package com.ef;

import com.ef.config.BatchConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BatchConfigTest.class})
public class ParserTests {

    @Test
    public void contextLoads() {
    }
}
