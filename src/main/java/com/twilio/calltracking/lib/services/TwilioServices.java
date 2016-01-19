package com.twilio.calltracking.lib.services;

import com.twilio.calltracking.lib.Config;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.IncomingPhoneNumberFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Application;
import com.twilio.sdk.resource.instance.AvailablePhoneNumber;
import com.twilio.sdk.resource.instance.IncomingPhoneNumber;
import com.twilio.sdk.resource.list.ApplicationList;
import com.twilio.sdk.resource.list.AvailablePhoneNumberList;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class TwilioServices {

    private final TwilioRestClient client;
    private static final String DefaultAppName = "Call tracking app";

    public TwilioServices() {
        client = new TwilioRestClient(Config.getAccountSid(), Config.getAuthToken());
    }

    public TwilioServices(TwilioRestClient client) {
        this.client = client;
    }

    public List<AvailablePhoneNumber> SearchPhoneNumbers(String areaCode)
    {
        Map<String, String> searchParams = new HashMap<>();
        searchParams.put("AreaCode", areaCode);

        AvailablePhoneNumberList phoneNumbers;
        phoneNumbers = getAccount().getAvailablePhoneNumbers(searchParams, "US", "Local");
        return phoneNumbers.getPageData();
    }

    public IncomingPhoneNumber PurchasePhoneNumber(String phoneNumber, String applicationSid)
    {
        try {
            Map<String, String> buyParams = new HashMap<>();
            buyParams.put("PhoneNumber", phoneNumber);
            buyParams.put("VoiceApplicationSid", applicationSid);
            getAccount().getIncomingPhoneNumberFactory().create(buyParams);

            return getIncomingPhoneNumberFactory().create(buyParams);

        } catch (TwilioRestException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String GetApplicationSid() throws TwilioRestException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("FriendlyName", TwilioServices.DefaultAppName);
        Application app = getAccount().getApplications(params).getPageData().get(0);

        if (app != null){
            List<NameValuePair> newAppParams = new ArrayList<NameValuePair>();
            newAppParams.add(new BasicNameValuePair("FriendlyName", TwilioServices.DefaultAppName));
            app = getAccount().getApplicationFactory().create(newAppParams);
        }

        return app.getSid();
    }

    private List<AvailablePhoneNumber> getAvailablePhoneNumbers(Account account, Map<String, String> searchParams) {
        AvailablePhoneNumberList phoneNumbers;
        phoneNumbers = account.getAvailablePhoneNumbers(searchParams, "US", "Local");
        return phoneNumbers.getPageData();
    }

    private Account getAccount(){
        return client.getAccount();
    }

    private IncomingPhoneNumberFactory getIncomingPhoneNumberFactory(){
        return getAccount().getIncomingPhoneNumberFactory();
    }
}
