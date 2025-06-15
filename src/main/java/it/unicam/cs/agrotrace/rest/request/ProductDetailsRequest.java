package it.unicam.cs.agrotrace.rest.request;

import lombok.*;

@Data
public class ProductDetailsRequest {
    private String title;
    private String description;
    private double price;
    private int quantity;
}
