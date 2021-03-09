package com.httprestdemo.restservicesdemo.testnglisteners;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by dgliga on 11/8/2016.
 */
public class TestLogListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    private final Logger LOGGER = LogManager.getLogger(TestLogListener.class);
    private long startTime;

    @Override
    public void onTestStart(ITestResult iTestResult) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        startTime = System.currentTimeMillis();
        LOGGER.debug("-------------------------------------------");
        LOGGER.debug(Constants.TEST_METHOD_LABEL + " " + getTestMethodName(iTestResult) + " started running at: " + dateFormat.format(date));
        LOGGER.debug(Constants.TEST_CLASS_LABEL + " " + iTestResult.getMethod().getTestClass().getName());
        LOGGER.debug(Constants.TEST_STEPS_LABEL);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOGGER.debug(Constants.TEST_RESULT_LABEL + " passed! Took around " + (System.currentTimeMillis() - startTime) + " ms");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LOGGER.debug(Constants.TEST_RESULT_LABEL + " failed! Took around " + (System.currentTimeMillis() - startTime) + " ms");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        LOGGER.debug(Constants.TEST_RESULT_LABEL + " skipped! Took around " + (System.currentTimeMillis() - startTime) + " ms");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    /**
     * Returns map containing test classes as key, with their respective test methods concatenated into a string builder
     *
     * @param iTestResultList list of ITestResult objects from which to generate map
     * @return
     */
    private Map<String, StringBuilder> getTestClassesMethodsMap(List<ITestResult> iTestResultList) {
        Map<String, StringBuilder> classMethods = new HashMap<>();

        for (ITestResult result : iTestResultList) {
            String key = result.getTestClass().toString();
            StringBuilder sb = new StringBuilder();

            if (classMethods.containsKey(key)) {
                classMethods.get(key).append(", ").append(result.getMethod().getMethodName())
                        .append(Arrays.toString(result.getParameters()));
            } else {
                sb.append(result.getMethod().getMethodName())
                        .append(Arrays.toString(result.getParameters()));
                classMethods.put(key, sb);
            }

        }

        return classMethods;
    }

    /**
     * Generates test method name, including it's parameters, if provided
     * example testMethod[param1, param2, param3]
     *
     * @param result
     * @return
     */
    public static String getTestMethodName(ITestResult result) {
        StringBuilder sb = new StringBuilder();

        sb.append(result.getInstance().hashCode());
        sb.append(result.getMethod().getMethodName());
        Object[] parameters = result.getParameters();

        if (parameters.length > 0) {
            sb.append("[");
            for (int j = 0; j < parameters.length; j++) {
                sb.append(parameters[j]);
                if (j != parameters.length - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
        }

        return sb.toString();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        LOGGER.debug("TEST [" + iTestContext.getAllTestMethods()[0].getXmlTest().getName() + "] started running.");
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LOGGER.debug("--------------------");
        LOGGER.debug("TEST [" + iTestContext.getAllTestMethods()[0].getXmlTest().getName() + "] finished.");
        LOGGER.debug("--------------------");

        //remove the counting of retried skipped tests
        Iterator<ITestResult> skippedTestCases = iTestContext.getSkippedTests().getAllResults().iterator();
        while (skippedTestCases.hasNext()) {
            ITestResult skippedTestCase = skippedTestCases.next();
            ITestNGMethod method = skippedTestCase.getMethod();
            if (method.getRetryAnalyzer() != null) {
                RetryAnalyzer retryAnalyzer = (RetryAnalyzer) method.getRetryAnalyzer();
                if (retryAnalyzer.isRetryAvailable()) {
                    if (iTestContext.getFailedTests().getResults(method).size() > 0) {
                        skippedTestCases.remove();
                    } else {
                        if (iTestContext.getPassedTests().getResults(method).size() > 0) {
                            skippedTestCases.remove();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onStart(ISuite iSuite) {
        LOGGER.debug("SUITE [" + iSuite.getName() + "] started running.");
        LOGGER.debug("====================");
    }

    @Override
    public void onFinish(ISuite iSuite) {
        LOGGER.debug("====================");
        LOGGER.debug("SUITE [" + iSuite.getName() + "] finished.");

        List<ITestResult> failedTestsList = new ArrayList<>();
        List<ITestResult> skippedTestsList = new ArrayList<>();

        for (ISuiteResult suiteResult : iSuite.getResults().values()) {
            ITestContext iTestContext = suiteResult.getTestContext();
            failedTestsList.addAll(iTestContext.getFailedTests().getAllResults());
            skippedTestsList.addAll(iTestContext.getSkippedTests().getAllResults());
        }
        List<ITestResult> problemsList = new ArrayList<>();
        problemsList.addAll(failedTestsList);
        problemsList.addAll(skippedTestsList);

        //log only if skipped or failed methods exist
        if (problemsList.size() != 0) {
            LOGGER.debug("====================");
            LOGGER.debug("UNSUCCESSFUL METHODS REPORT");
            if (failedTestsList.size() > 0) {
                LOGGER.debug("Failed tests: ");
                logMethodsPerClass(failedTestsList);
            }
            if (skippedTestsList.size() > 0) {
                LOGGER.debug("Skipped tests: ");
                logMethodsPerClass(skippedTestsList);
            }
            LOGGER.debug("====================");
        }

    }

    /**
     * Reads from map containing classes as key and methods that belong to that class as value, and logs the info for
     * unsuccessful methods report
     *
     * @param results list of ITestResults
     */
    private void logMethodsPerClass(List<ITestResult> results) {
        for (Map.Entry<String, StringBuilder> entry : getTestClassesMethodsMap(results).entrySet()) {
            String loggedClassName = entry.getKey();
            String fullClassName = loggedClassName.substring(loggedClassName.indexOf(" "), loggedClassName.indexOf("]"));
            LOGGER.debug("In class" + fullClassName + ": " + entry.getValue());
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        ThreadContext.put("threadId", String.valueOf(Thread.currentThread().getId()));
        if (iTestResult.getMethod().isBeforeMethodConfiguration() || iTestResult.getMethod().isBeforeClassConfiguration()) {
            LOGGER.debug(Constants.TEST_PRECONDITIONS_LABEL);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
    }
}
