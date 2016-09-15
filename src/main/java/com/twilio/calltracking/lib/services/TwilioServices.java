package com.twilio.calltracking.lib.services;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.calltracking.lib.Config;
import com.twilio.rest.api.v2010.account.Application;
import com.twilio.rest.api.v2010.account.availablephonenumbercountry.Local;
import com.twilio.rest.api.v2010.account.incomingphonenumber.LocalCreator;
import com.twilio.type.PhoneNumber;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("unused")
public class TwilioServices {

    private static final String DEFAULT_APP_NAME = "Call Tracking App";

    public TwilioServices() {
        Twilio.init(Config.getAccountSid(), Config.getAuthToken());
    }

    public List<Local> searchPhoneNumbers(int areaCode) {
        Iterator<Local> phoneNumbers = Local
                .read("US")
                .byAreaCode(areaCode)
                .execute()
                .iterator();

        List<Local> phoneNumbersAsList = new ArrayList<>();
        phoneNumbers.forEachRemaining(phoneNumbersAsList::add);

        return phoneNumbersAsList;
    }

    public com.twilio.rest.api.v2010.account.incomingphonenumber.Local purchasePhoneNumber(
            String phoneNumber, String applicationSid) {

        return new LocalCreator(new PhoneNumber(phoneNumber))
                .setVoiceApplicationSid(applicationSid)
                .execute();
    }

    public String getApplicationSid() {
        ResourceSet<Application> apps = getApplications();

        Application app = apps.iterator().hasNext()
                ? apps.iterator().next()
                : Application.create(DEFAULT_APP_NAME).execute();

        return app.getSid();
    }

    private ResourceSet<Application> getApplications() {
        return Application
                .read()
                .byFriendlyName(DEFAULT_APP_NAME)
                .execute();
    }
}

