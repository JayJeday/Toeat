package com.globeandi.toeat.util;


import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class GlobeDateUtils {

    private GlobeDateUtils(){
        //class cant be instantiated
    }

    public static String setDateTimeToServer(String date, String time){
        String dateTime = date +" "+ time;
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime,dateTimeFormat);
        return dateTimeFormat.print(localDateTime);
    }

    /*
    format date from the server
     */
    public static String getDateFromServer(String dateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm");
        LocalDateTime dateTimeFormated = LocalDateTime.parse(dateTime,dateTimeFormatter);
        String month = dateTimeFormated.getMonthOfYear() + "";
        String day = dateTimeFormated.getDayOfMonth() + "";
        String year = dateTimeFormated.getYear() + "";
        String date = month+"/"+day+"/"+year;
        return formatDateToDisplay(date);
    }

    public static String getTimeFromServer(String dateTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm");
        LocalTime formatTime = LocalTime.parse(dateTime,dateTimeFormatter);
        String hour = formatTime.getHourOfDay() + "";
        String minutes = formatTime.getMinuteOfHour() + "";
        String time = hour+":"+minutes;
        return formatTimeToDisplay(time);
    }


    /*
    For trip creation
     */
    public static String formatTimeToDisplay(String time){
        DateTimeFormatter timeFormat = DateTimeFormat.forPattern("HH:mm");
        LocalTime formatDate = LocalTime.parse(time,timeFormat);
        return timeFormat.print(formatDate);
    }

    public static String formatDateToDisplay(String date){
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("MM/dd/yyyy");
        LocalDate formatDate = LocalDate.parse(date,dateFormat);
        return dateFormat.print(formatDate);
    }
    /*
    Validate if trip date is valid
     */
    public static boolean isTripDateValid(String date, String time){
        String dateTime = date +" "+ time;
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm");
        LocalDateTime tripDateTime = LocalDateTime.parse(dateTime,dateTimeFormat);
        LocalDateTime now = LocalDateTime.now().toDateTime(DateTimeZone.UTC).toLocalDateTime();
        if(tripDateTime.isBefore(now)){
            return false;
        }
        return true;
    }

    /*
    voting date results before 3 hours
     */

    public static boolean isVoteAvailable(String datetime){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm");
        LocalDateTime tripDateTime = LocalDateTime.parse(datetime,dateTimeFormat);
        //dateline for the results to be available
        LocalDateTime voteResultsDateTime = tripDateTime.minusHours(3);
        //get current date
        LocalDateTime now = LocalDateTime.now().toDateTime(DateTimeZone.UTC).toLocalDateTime();
        return voteResultsDateTime.isBefore(now);
    }


    /*
    return true if trip date time is before than now
     */
    public static boolean isTripCompleted(String datetime){
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm");
        LocalDateTime tripDateTime = LocalDateTime.parse(datetime,dateTimeFormat);
        //get current date
        LocalDateTime now = LocalDateTime.now().toDateTime(DateTimeZone.UTC).toLocalDateTime();
        return tripDateTime.isBefore(now);
    }
}
