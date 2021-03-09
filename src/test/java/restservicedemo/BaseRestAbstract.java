package restservicedemo;

import com.httprestdemo.restservicesdemo.service.ServiceImpl;
import com.httprestdemo.restservicesdemo.testnglisteners.TestLogListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Listeners;

/**
 * Created by dgliga on 26.02.2018.
 */
@Component("base-rest-abstract")
@Listeners(TestLogListener.class)
@ContextConfiguration(locations = {"classpath:conf/democonfig.xml"})
public abstract class BaseRestAbstract extends AbstractTestNGSpringContextTests {

    @Autowired
    ServiceImpl serviceImpl;

}
