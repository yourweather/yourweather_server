package com.umc.yourweather.domain;

public class Proportion {
    public int sunny;
    public int cloudy;
    public int rainy;
    public int lightning;

    public Proportion() {}

    public Proportion(Builder builder) {
        this.sunny = builder.sunny;
        this.cloudy = builder.cloudy;
        this.rainy = builder.rainy;
        this.lightning = builder.lightning;
    }

    //Builder Class
    public static class Builder{

        // required parameters
        public int sunny;
        public int cloudy;
        public int rainy;
        public int lightning;

        public Builder() {}

        public Builder sunny(int sunny) {
            this.sunny = sunny;
            return this;
        }

        public Builder cloudy(int cloudy) {
            this.cloudy = cloudy;
            return this;
        }

        public Builder rainy(int rainy) {
            this.rainy = rainy;
            return this;
        }

        public Builder lightning(int lightning) {
            this.lightning = lightning;
            return this;
        }

        public Proportion build(){
            return new Proportion(this);
        }

    }
}
