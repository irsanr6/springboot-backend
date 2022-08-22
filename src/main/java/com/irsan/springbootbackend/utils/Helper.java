package com.irsan.springbootbackend.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }



    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static String traversWordByWord(String awkward) {
        String delimiter = ","+" "+";"+":"+"+"+"!"+"@"+"?"+"/"+"*"+"&"+"%"+"$"+"#"+"="+"-"+"_";
        List<String> news = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(awkward, delimiter);
        while (st.hasMoreTokens()) {
            news.add(st.nextToken());
        }
        String words = news.stream()
                .map(String::intern)
                .collect(Collectors.joining("-"));

        return StringUtils.lowerCase(words);
    }

}
