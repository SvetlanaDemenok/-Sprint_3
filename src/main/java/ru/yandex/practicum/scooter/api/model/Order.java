package ru.yandex.practicum.scooter.api.model;

import java.util.Date;
import java.util.List;

public class Order {
    private Integer id;
    private Integer courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer rentTime;
    private Date deliveryDate;
    private Integer track;
    private List<String> color;
    private String comment;
    private Date createdAt;
    private Date updatedAt;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public Integer getCourierId() {
        return courierId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMetroStation() {
        return metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getRentTime() {
        return rentTime;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public Integer getTrack() {
        return track;
    }

    public List<String> getColor() {
        return color;
    }

    public String getComment() {
        return comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Integer getStatus() {
        return status;
    }
}
