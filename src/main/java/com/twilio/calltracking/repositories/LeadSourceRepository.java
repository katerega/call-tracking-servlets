package com.twilio.calltracking.repositories;

import com.twilio.calltracking.models.LeadSource;
import javafx.beans.binding.ObjectExpression;

import javax.persistence.NoResultException;
import java.util.*;

public class LeadSourceRepository extends Repository<LeadSource> {

    public LeadSourceRepository() {
        super(LeadSource.class);
    }

    public LeadSource findByIncomingNumberInternational(String number) {

        LeadSource leadSource = null;
        try {
            leadSource = (LeadSource) getEm().createQuery(
                "SELECT e FROM LeadSource e WHERE e.incomingNumberInternational = :number")
                .setMaxResults(1).setParameter("number", number).getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return leadSource;
    }

    public List<Object> findLeadsByLeadSource() {

        List items = null;
        try {
            items = getEm().createQuery(
                "SELECT s.name, COUNT(l) FROM LeadSource s JOIN s.leads l GROUP BY s.name")
                .getResultList();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return QueryHelper.mapResults(items);
    }

    public List<Object> findLeadsByCity() {

        List items = null;
        try {
            items = getEm().createQuery(
                "SELECT l.city, COUNT(l) FROM LeadSource s JOIN s.leads l GROUP BY l.city")
                .getResultList();
        } catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return QueryHelper.mapResults(items);
    }
}
