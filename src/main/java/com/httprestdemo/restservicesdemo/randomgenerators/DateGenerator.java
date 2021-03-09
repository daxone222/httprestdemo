package com.httprestdemo.restservicesdemo.randomgenerators;

import com.httprestdemo.restservicesdemo.testnglisteners.TestLogListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Weeks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgliga on 15.11.2016.
 */
public class DateGenerator {

    private static final Logger LOGGER = LogManager.getLogger(TestLogListener.class);

    /**
     * Get current date and time
     *
     * @return
     */
    public static DateTime getCurrentDateAndTime() {

        return new DateTime();
    }

    /**
     * Get current date
     *
     * @return
     */
    public static LocalDate getCurrentDate() {

        return LocalDate.now();
    }

    /**
     * Get current time
     *
     * @return
     */
    public static LocalTime getCurrentTime() {

        return LocalTime.now();
    }

    /**
     * Get all dates between the given dates
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<DateTime> getDatesInBetween(DateTime startDate, DateTime endDate) throws InvalidIntervalException {
        List<DateTime> dateTimeList = new ArrayList<>();
        DateTime tmp = startDate;
        while (tmp.isBefore(endDate) || tmp.equals(endDate)) {
            dateTimeList.add(tmp);
            tmp = tmp.plusDays(1);
        }

        if (startDate.isAfter(endDate)) {
            throw new InvalidIntervalException("Start date must be before end date");
        }

        if (dateTimeList.size() == 0) {
            LOGGER.debug("No date was found in the given interval");
        }

        return dateTimeList;

    }

    /**
     * Get the "days" date after given "localDate"
     *
     * @param localDate
     * @param days
     * @return
     */
    public static LocalDate getTheNextDate(LocalDate localDate, int days) {
        int i = 0;
        while (i < days) {
            localDate = localDate.plusDays(1);
            i++;

        }

        return localDate;
    }

    /**
     * Get the "days" working date after given "localDate"
     *
     * @param localDate
     * @param days
     * @return
     */
    public static LocalDate getTheNextWorkingDate(LocalDate localDate, int days) {
        int i = 0;
        while (i < days) {
            localDate = localDate.plusDays(1);//here even sat and sun are added
            //but at the end it goes to the correct week day.
            //because i is only increased if it is week day
            if (localDate.getDayOfWeek() <= 5) {
                i++;
            }
        }

        return localDate;
    }

    /**
     * Get the working dates on a range
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<DateTime> getWorkingDatesInBetween(DateTime startDate, DateTime endDate) throws InvalidIntervalException {
        List<DateTime> dateTimeList = new ArrayList<>();
        DateTime tmp = startDate;
        while (tmp.isBefore(endDate) || tmp.equals(endDate)) {
            if (tmp.getDayOfWeek() >= 1 && tmp.getDayOfWeek() <= 5) {
                dateTimeList.add(tmp);
            }
            tmp = tmp.plusDays(1);
        }

        if (startDate.isAfter(endDate)) {
            throw new InvalidIntervalException("Start date must be before end date");
        }

        if (dateTimeList.size() == 0) {
            LOGGER.debug("No working date was found in the given interval");
        }

        return dateTimeList;
    }

    /**
     * Get the weekend dates on a range
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<DateTime> getWeekendDatesInBetween(DateTime startDate, DateTime endDate) throws InvalidIntervalException {
        List<DateTime> dateTimeList = new ArrayList<>();
        DateTime tmp = startDate;
        while (tmp.isBefore(endDate) || tmp.equals(endDate)) {
            if (tmp.getDayOfWeek() == 6 || tmp.getDayOfWeek() == 7) {
                dateTimeList.add(tmp);
            }
            tmp = tmp.plusDays(1);
        }

        if (startDate.isAfter(endDate)) {
            throw new InvalidIntervalException("Start date must be before end date");
        }

        if (dateTimeList.size() == 0) {
            LOGGER.debug("No weekend date was found in the given interval");
        }

        return dateTimeList;
    }

    /**
     * Get the number of weeks between two given dates
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getNumberOfWeeksBetweenTwoDates(DateTime startDate, DateTime endDate) throws InvalidIntervalException {
        if (startDate.isAfter(endDate)) {
            throw new InvalidIntervalException("Start date must be before end date");
        }

        return Weeks.weeksBetween(startDate.dayOfWeek().withMinimumValue().minusDays(1),
                endDate.dayOfWeek().withMaximumValue().plusDays(1)).getWeeks();
    }
}
