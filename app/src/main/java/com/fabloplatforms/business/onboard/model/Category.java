package com.fabloplatforms.business.onboard.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    /**
     * No args constructor for use in serialization
     */
    public Category() {
    }

    /**
     * @param message
     * @param error
     * @param items
     * @param status
     */
    public Category(Boolean status, String message, String error, List<Item> items) {
        super();
        this.status = status;
        this.message = message;
        this.error = error;
        this.items = items;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public class Item {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("index")
        @Expose
        private Integer index;
        @SerializedName("name_english")
        @Expose
        private String nameEnglish;
        @SerializedName("name_hindi")
        @Expose
        private String nameHindi;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("base_rate")
        @Expose
        private Float baseRate;
        @SerializedName("status")
        @Expose
        private Integer status;

        /**
         * No args constructor for use in serialization
         *
         */
        public Item() {
        }

        /**
         *
         * @param image
         * @param baseRate
         * @param nameHindi
         * @param nameEnglish
         * @param index
         * @param id
         * @param status
         */
        public Item(String id, Integer index, String nameEnglish, String nameHindi, String image, Float baseRate, Integer status) {
            super();
            this.id = id;
            this.index = index;
            this.nameEnglish = nameEnglish;
            this.nameHindi = nameHindi;
            this.image = image;
            this.baseRate = baseRate;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getNameEnglish() {
            return nameEnglish;
        }

        public void setNameEnglish(String nameEnglish) {
            this.nameEnglish = nameEnglish;
        }

        public String getNameHindi() {
            return nameHindi;
        }

        public void setNameHindi(String nameHindi) {
            this.nameHindi = nameHindi;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Float getBaseRate() {
            return baseRate;
        }

        public void setBaseRate(Float baseRate) {
            this.baseRate = baseRate;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

    }

}