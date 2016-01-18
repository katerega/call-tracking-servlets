package com.twilio.calltracking.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Entity
@Table(name = "lead_sources")
public class LeadSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "incoming_number_national")
    private String incomingNumberNational;

    @Column(name = "incoming_number_international")
    private String incomingNumberInternational;

    @Column(name = "forwarding_number")
    private LeadSource forwardingNumber;

    @OneToMany(mappedBy = "agent")
    private List<Lead> leads;

    public LeadSource() {
        this.leads = new ArrayList<>();
    }

    public LeadSource(String name, String incomingNumberNational, String incomingNumberInternational, LeadSource forwardingNumber) {
        this();
        this.name = name;
        this.incomingNumberNational = incomingNumberNational;
        this.incomingNumberInternational = incomingNumberInternational;
        this.forwardingNumber = forwardingNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIncomingNumberNational() {
        return incomingNumberNational;
    }

    public void setIncomingNumberNational(String incomingNumberNational) {
        this.incomingNumberNational = incomingNumberNational;
    }

    public String getIncomingNumberInternational() {
        return incomingNumberInternational;
    }

    public void setIncomingNumberInternational(String incomingNumberInternational) {
        this.incomingNumberInternational = incomingNumberInternational;
    }

    public LeadSource getForwardingNumber() {
        return forwardingNumber;
    }

    public void setForwardingNumber(LeadSource forwardingNumber) {
        this.forwardingNumber = forwardingNumber;
    }
}