package com.twilio.calltracking.repositories;

import com.twilio.calltracking.models.Lead;

public class LeadRepository extends Repository<Lead> {

    public LeadRepository() {
        super(Lead.class);
    }
}
