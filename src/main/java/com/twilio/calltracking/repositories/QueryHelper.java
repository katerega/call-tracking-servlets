package com.twilio.calltracking.repositories;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("checkstyle:visibilitymodifier")
public final class QueryHelper {

    private QueryHelper() {
        // Prevent instantiation.
    }

    public static List<Object> mapResults(List<Object[]> items) {

        List<Object> results = new ArrayList<>();
        // Place results in map
        for (Object[] item : items) {

            Object tuple = new Object() {
                public final String label = (String) item[0];
                public final Long value = (Long) item[1];
            };

            results.add(tuple);
        }

        return results;
    }
}
