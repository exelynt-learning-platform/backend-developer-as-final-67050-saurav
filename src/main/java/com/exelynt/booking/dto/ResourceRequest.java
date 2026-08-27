package com.exelynt.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class ResourceRequest {

    @NotBlank(message = "Resource name is required")
    private String name;

    private String description;

    @NotNull(message = "Price per hour is required")
    @Positive(message = "Price per hour must be positive")
    private BigDecimal pricePerHour;

    public ResourceRequest() {}

    public ResourceRequest(String name, String description, BigDecimal pricePerHour) {
        this.name = name;
        this.description = description;
        this.pricePerHour = pricePerHour;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(BigDecimal pricePerHour) { this.pricePerHour = pricePerHour; }
}