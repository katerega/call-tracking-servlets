package com.twilio.calltracking.repositories;

import com.twilio.calltracking.models.LeadSource;

import javax.persistence.NoResultException;

public class LeadSourceRepository extends Repository<LeadSource> {

    public LeadSourceRepository() {
        super(LeadSource.class);
    }

    public LeadSource findByIncomingNumberInternational(String number) {

        LeadSource leadSource = null;
        try {
            leadSource = (LeadSource)
                    getEm().createQuery("SELECT e FROM LeadSource e WHERE e.incomingNumberInternational = :number")
                            .setMaxResults(1)
                            .setParameter("number", number)
                            .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return leadSource;
    }
}
