package com.twilio.calltracking.lib;

public final class Config {

    private Config() {
        // Prevent instantiation.
    }

    public static String getAccountSid() {
        return System.getenv("TWILIO_ACCOUNT_SID");
    }

    public static String getAuthToken() {
        return System.getenv("TWILIO_AUTH_TOKEN");
    }

    public static String getTwimlApplicationSid() {
        return System.getenv("TWILIO_APP_SID");
    }
}
