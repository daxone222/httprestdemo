package com.httprestdemo.restservicesdemo.testnglisteners;

import java.io.File;

/**
 * Created by dgliga on 05.12.2016.
 */
public class Constants {

    //TestLogListener constants - based on these log parsing is done (ExtentReports, TestCaseGenerator...)
    public final static String TEST_METHOD_LABEL = "Test method";
    public final static String TEST_CLASS_LABEL = "Test class:";
    public final static String TEST_STEPS_LABEL = "TEST STEPS";
    public final static String TEST_RESULT_LABEL = "Test result:";
    public final static String TEST_PRECONDITIONS_LABEL = "TEST PRECONDITIONS";
    public final static String TEST_SKIPPED_LABEL = "marked as skipped and will rerun for ";
    public final static String STEP_COUNTER_LABEL = "**";

    //path to test-reports directory, where extend reports (and maybe others will be stored)
    public final static String REPORTS_PATH = "target" + File.separator + "test-reports";

    //log file path
    public final static String LOG_FILE_PATH = "target" + File.separator
            + "test-reports" + File.separator + "logs" + File.separator + "all.log";


//    //!!Be careful when changing these!!
//    //path to directory where screenshots are saved
//    public final static String SCREENSHOTS_PATH = "target//test-reports//screenshots//";
//    //path to screenshots, relative to ExtentReport.html file, so that it can add screens to report
//    public final static String SCREENSHOTS_RELATIVE_PATH = "screenshots//";
//
//    //path to expected screenshots used for image comparison
//    public final static String EXPECTED_SCREENS_PATH =  "src//main//resources//expectedscreens";
//
//    //path to actual screenshots saved during test execution, used for image comparison
//    public final static String ACTUAL_SCREENS_PATH = SCREENSHOTS_PATH + "image-reports";
//    public final static String TEMP_SCREENS_PATH = SCREENSHOTS_PATH + "image-reports//temp";


    //!!Be careful when changing these!!
    //path to directory where screenshots are saved
    public final static String SCREENSHOTS_PATH = "target" + File.separator + "test-reports" + File.separator
            + "screenshots" + File.separator;
    //path to screenshots, relative to ExtentReport.html file, so that it can add screens to report
    public final static String SCREENSHOTS_RELATIVE_PATH = "screenshots" + File.separator;

    //path to expected screenshots used for image comparison
    public final static String EXPECTED_SCREENS_PATH = "src" + File.separator + "main" + File.separator + "resources"
            + File.separator + "expectedscreens";

    //path to actual screenshots saved during test execution, used for image comparison
    public final static String ACTUAL_SCREENS_PATH = SCREENSHOTS_PATH + "image-reports";
    public final static String TEMP_SCREENS_PATH = SCREENSHOTS_PATH + "image-reports" + File.separator + "temp";

}
