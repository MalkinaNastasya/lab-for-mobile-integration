package ru.mospolytech.lab1;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobDetail {

    @SerializedName("hashid")
    String hashid;

    @SerializedName("title")
    String title;

    @SerializedName("description")
    String description;

    @SerializedName("salary_from")
    int salary_from;

    @SerializedName("salary_to")
    int salary_to;

    @SerializedName("metro")
    List<Metro> metro;

    @SerializedName("address")
    String address;

    @SerializedName("contact_phone")
    String contact_phone;

}
