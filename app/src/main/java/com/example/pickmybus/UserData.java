package com.example.pickmybus;

public class UserData {
    private String OwnerID,Tname,From,To,Vehiclename;

    public UserData(String id, String tname, String from, String to, String vehiclename) {
        this.From=from;
        this.OwnerID=id;
        this.To=to;
        this.Vehiclename=vehiclename;
        this.Tname=tname;
    }

    public String getOwnerID() {
        return OwnerID;
    }

    public String getTname() {
        return Tname;
    }

    public String getFrom() {
        return From;
    }

    public String getto() {
        return To;
    }

    public String getVehiclename() {
        return Vehiclename;
    }
}
