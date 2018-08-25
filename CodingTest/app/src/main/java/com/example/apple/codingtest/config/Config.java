package com.example.apple.codingtest.config;

/*
        this class hold the base url so that development environment
        and production environment can be switched between easily.
        this also can be controlled from gradle by having tags dev,release,
        Qa and so on.
 */

public class Config {

    public static String BASE_URL="https://restcountries.eu/";
}
