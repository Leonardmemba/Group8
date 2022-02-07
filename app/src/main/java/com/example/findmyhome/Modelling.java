package com.example.findmyhome;

public class Modelling {

        String ImageUrl;
        String location;
        String price;
        String description;

        public Modelling(String imageUrl, String location, String price,String description) {
            this.ImageUrl = imageUrl;
            this.location = location;
            this.price = price;
            this.description = description;
        }

        public Modelling() {
        }

        public String getImageUrl() {
            return ImageUrl;
        }



        public void setImageUrl(String imageUrl) {
            ImageUrl = imageUrl;
        }



        public void setLocation(String location) {
            this.location = location;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getLocation() {
            return location;
        }

        public String getPrice() {
            return price;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }


