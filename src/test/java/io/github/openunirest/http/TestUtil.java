package io.github.openunirest.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestUtil {
    private static final com.fasterxml.jackson.databind.ObjectMapper om = new com.fasterxml.jackson.databind.ObjectMapper();
    static {
        om.registerModule(new GuavaModule());
    }
    public static String toString(InputStream is) {
        return new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining("\n"));
    }

    public static <T> T readValue(InputStream i, Class<T> clss) {
        try {
            return om.readValue(i, clss);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object o) {
        try {
            return om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void assertException(Runnable runnable, Class<? extends Throwable> exClass, String message) {
        try{
            runnable.run();
            fail("Expected exception but got none. \nExpected " + exClass);
        } catch (Exception e){
            if (!e.getClass().isAssignableFrom(exClass)) {
                fail("Expected wrong exception type \n Expected: " + exClass + "\n but got " + e.getClass());
            }
            assertEquals("Wrong Error Message", message, e.getMessage());
        }
    }

    public static <T> T readValue(String body, Class<T> as) {
        try {
            return om.readValue(body, as);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream emptyInput() {
        return new ByteArrayInputStream(new byte[]{});
    }

    public static InputStream toInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    public static <K, V> Map<K, V> mapOf(Object... keyValues) {
        Map<K, V> map = new HashMap<>();

        K key = null;
        for (int index = 0; index < keyValues.length; index++) {
            if (index % 2 == 0) {
                key = (K)keyValues[index];
            }
            else {
                map.put(key, (V)keyValues[index]);
            }
        }

        return map;
    }
}
