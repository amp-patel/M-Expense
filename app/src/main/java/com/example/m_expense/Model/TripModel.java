package com.example.m_expense.Model;


public class TripModel {
    public int tripId;
    public String tripName;
    public String tripDest;
    public String tripDate;
    public String tripRisk;
    public String tripDesc;

    public TripModel(int tripId, String tripName, String tripDest, String tripDate, String tripRisk, String tripDesc) {
        this.tripId = tripId;
        this.tripName = tripName;
        this.tripDest = tripDest;
        this.tripDate = tripDate;
        this.tripRisk = tripRisk;
        this.tripDesc = tripDesc;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripDest() {
        return tripDest;
    }

    public void setTripDest(String tripDest) {
        this.tripDest = tripDest;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripRisk() {
        return tripRisk;
    }

    public void setTripRisk(String tripRisk) {
        this.tripRisk = tripRisk;
    }

    public String getTripDesc() {
        return tripDesc;
    }

    public void setTripDesc(String tripDesc) {
        this.tripDesc = tripDesc;
    }
}
