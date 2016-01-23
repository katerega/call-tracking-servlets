package com.twilio.calltracking.repositories;

import java.util.ArrayList;
import java.util.List;

public class QueryHelper {

    public static List<Object> mapResults(List<Object[]> items) {
        List<Object> results = new ArrayList<>();
        // Place results in map
        for (Object[] item: items) {

            Object tuple = new Object() {
                public final String Label = (String) item[0];
                public final Long Value = (Long) item[1];
            };
            results.add(tuple);
        }
        return results;
    }
}
