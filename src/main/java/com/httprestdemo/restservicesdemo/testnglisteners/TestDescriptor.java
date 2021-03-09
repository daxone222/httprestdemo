package com.httprestdemo.restservicesdemo.testnglisteners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by dgliga on 25.11.2016.
 */
public class TestDescriptor {
    private static final Logger LOGGER = LogManager.getLogger(TestLogListener.class);

    public static void describeStep(String stepDescription) {
        LOGGER.debug(Constants.STEP_COUNTER_LABEL + ". " + stepDescription);
    }

}
