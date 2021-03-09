package com.httprestdemo.restservicesdemo.testnglisteners;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dgliga on 11/10/2016.
 */
public class RetryAnalyzer extends TestListenerAdapter implements IRetryAnalyzer {
    protected final Logger LOGGER = LogManager.getLogger(TestLogListener.class);

    /**
     * In this Class there is the retry analyzer used by the suites of tests
     */
    private int MAX_RETRY_COUNT;

    private AtomicInteger count;

    private List<String> retried = new ArrayList<>();

    public RetryAnalyzer(int retryTimes) {
        MAX_RETRY_COUNT = retryTimes;
        count = new AtomicInteger(MAX_RETRY_COUNT);
    }

    public static void applyRetryOnTestMethodFailure(ITestContext testContext, int retryTimes) {
        for (ITestNGMethod method : testContext.getAllTestMethods()) {
            method.setRetryAnalyzer(new RetryAnalyzer(retryTimes));
        }
    }

    boolean isRetryAvailable() {
        return (count.intValue() > 0);
    }

    public boolean retry(ITestResult result) {
        boolean isRetried = false;
        //if the retried list does not contain the method name + parameters(used when the test will have data provider)
        if (!retried.contains((ArrayUtils.toString(result.getParameters())))) {
            //if the number of given retries > 0
            if (isRetryAvailable()) {
                LOGGER.debug("Test: " + result.getMethod().getMethodName()
                        + " has failed, " + Constants.TEST_SKIPPED_LABEL + count.intValue() + " times");
                count.decrementAndGet();
                isRetried = true;
            } else {
                //the method name + parameters will be added on retried list
                retried.add((ArrayUtils.toString(result.getParameters())));
                //reset count
                count.set(MAX_RETRY_COUNT);
            }
        }
        return isRetried;
    }
}
