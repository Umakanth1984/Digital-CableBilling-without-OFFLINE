package com.digitalrupay.interfaces;

/**
 * Created by Santosh on 10/1/2016.
 */

public interface CommunicationListener {

    void searchCustomer(String customerID);

    void getComplaints();

    void registerComplaint(String customerId, String complaintMsg, String category);
}
