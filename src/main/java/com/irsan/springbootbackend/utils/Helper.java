package com.irsan.springbootbackend.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Irsan Ramadhan
 * @email: irsan.ramadhan@iconpln.co.id
 */
@Slf4j
public class Helper {

    public static PageRequest getPageRequest(int pageNumber, int pageSize, String sortField) {
        return PageRequest.of(--pageNumber, pageSize, Sort.by(sortField).ascending());
    }

    public static void logError200(String message) {
        log.info("<<<<<<<<<<");
        log.info("status: " + 200);
        log.info("success: " + true);
        log.info("message: " + message);
        log.info("data: " + null);
        log.info(">>>>>>>>>>");
    }

    public static <T> void ok(String message) {
        log.info("<<<<<<<<<<");
        log.info("status: " + 200);
        log.info("success: " + true);
        log.info("message: " + message);
        log.info("currentDateTime: {}", dateTimeNow());
        log.info("data: " + null);
        log.info(">>>>>>>>>>");
    }

    public static <T> void ok(String message, T data) {
        log.info("<<<<<<<<<<");
        log.info("status: " + 200);
        log.info("success: " + true);
        log.info("message: " + message);
        log.info("currentDateTime: {}", dateTimeNow());
        log.info("data: " + data);
        log.info(">>>>>>>>>>");
    }

    public static String dateTimeNow() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        return df.format(date);
    }

    public static String fullName(String fisrtName, String lastName) {
        Stream<String> words = Stream.of(fisrtName, lastName);
        return words.collect(Collectors.joining(" "));
    }

    public static Date currentDate() {
        Date date = new Date();
//        Reformat date to string
//        Date date = new Date().toString();
//        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(dateString);
//        DateFormat target = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//        target.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Jakarta")));
//        log.info("currentDate {}", target.format(date));
        return date;
    }

}
