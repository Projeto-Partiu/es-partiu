package br.edu.ufcg.partiu.model;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.edu.ufcg.partiu.util.Util;

/**
 * Created by ordan on 29/07/17.
 */

public class Event {

    @SerializedName("_id")
    private String id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String address;
    private double latitude;
    private double longitude;
    private User owner;
    private List<User> interestedUsers;
    private List<User> confirmedUsers;

    public List<User> getInterestedUsers() {
        return interestedUsers;
    }

    public void setInterestedUsers(List<User> interestedUsers) {
        this.interestedUsers = interestedUsers;
    }

    public List<User> getConfirmedUsers() {
        return confirmedUsers;
    }

    public void setConfirmedUsers(List<User> confirmedUsers) {
        this.confirmedUsers = confirmedUsers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public String getFriendlyAddress() {
        int virgulas = 0;
        String address = getAddress();
        int idx = 0;
        while (true) {
            if (address.charAt(idx) == ',') virgulas++;
            if (virgulas == 2) return address.substring(0, idx);
            idx++;
        }
    }

    public String getFriendlyDate() {
        if (endDate != null && !endDate.equals(startDate)) {
            if (Util.toCalendar(startDate).get(Calendar.YEAR) == Util.toCalendar(endDate).get(Calendar.YEAR)) {
                DateFormat format = new SimpleDateFormat("dd 'de' MMM", Locale.getDefault());

                return format.format(startDate) +
                        " - " +
                        format.format(endDate) +
                        " de " +
                        Util.toCalendar(startDate).get(Calendar.YEAR);
            }

            DateFormat format = new SimpleDateFormat("dd 'de' MMM 'de' yyyy", Locale.getDefault());

            return format.format(startDate) +
                    " - " +
                    format.format(endDate);
        } else {
            DateFormat format = new SimpleDateFormat("dd 'de' MMM 'de' yyyy", Locale.getDefault());

            return format.format(startDate);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

