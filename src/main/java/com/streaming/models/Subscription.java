package com.streaming.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Subscription {
    private Long id;
    private String plan;
    private Double price;
    private Integer maxDevices;
    private String maxQuality;
    private Boolean adSupported;
    private LocalDateTime createdAt;

    public Subscription() {}

    public Subscription(String plan, Double price, Integer maxDevices, String maxQuality, Boolean adSupported) {
        this.plan = plan;
        this.price = price;
        this.maxDevices = maxDevices;
        this.maxQuality = maxQuality;
        this.adSupported = adSupported;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlan() { return plan; }
    public void setPlan(String plan) { this.plan = plan; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getMaxDevices() { return maxDevices; }
    public void setMaxDevices(Integer maxDevices) { this.maxDevices = maxDevices; }

    public String getMaxQuality() { return maxQuality; }
    public void setMaxQuality(String maxQuality) { this.maxQuality = maxQuality; }

    public Boolean getAdSupported() { return adSupported; }
    public void setAdSupported(Boolean adSupported) { this.adSupported = adSupported; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", plan='" + plan + '\'' +
                ", price=" + price +
                ", maxQuality='" + maxQuality + '\'' +
                '}';
    }
}
