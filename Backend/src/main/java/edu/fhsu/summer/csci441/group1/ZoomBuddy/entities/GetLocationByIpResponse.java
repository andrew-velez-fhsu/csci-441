package edu.fhsu.summer.csci441.group1.ZoomBuddy.entities;

import java.util.List;

public class GetLocationByIpResponse {
    private String city;
    private Connection connection;
    private String continent_code;
    private String continent_name;
    private String country_code;
    private String country_name;
    private List<Currency> currencies;
    private String ip;
    private boolean is_eu;
    private double latitude;
    private Location location;
    private double longitude;
    private String region_name;
    private List<String> timezones;
    private String type;

    // Getters and Setters
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getContinent_code() {
        return continent_code;
    }

    public void setContinent_code(String continent_code) {
        this.continent_code = continent_code;
    }

    public String getContinent_name() {
        return continent_name;
    }

    public void setContinent_name(String continent_name) {
        this.continent_name = continent_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isIs_eu() {
        return is_eu;
    }

    public void setIs_eu(boolean is_eu) {
        this.is_eu = is_eu;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public List<String> getTimezones() {
        return timezones;
    }

    public void setTimezones(List<String> timezones) {
        this.timezones = timezones;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Nested Classes
    public static class Connection {
        // Define fields if needed
    }

    public static class Currency {
        private String code;
        private String name;
        private String symbol;

        // Getters and Setters
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
    }

    public static class Location {
        private List<String> calling_codes;
        private String capital;
        private String flag;
        private String native_name;
        private List<String> top_level_domains;

        // Getters and Setters
        public List<String> getCalling_codes() {
            return calling_codes;
        }

        public void setCalling_codes(List<String> calling_codes) {
            this.calling_codes = calling_codes;
        }

        public String getCapital() {
            return capital;
        }

        public void setCapital(String capital) {
            this.capital = capital;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getNative_name() {
            return native_name;
        }

        public void setNative_name(String native_name) {
            this.native_name = native_name;
        }

        public List<String> getTop_level_domains() {
            return top_level_domains;
        }

        public void setTop_level_domains(List<String> top_level_domains) {
            this.top_level_domains = top_level_domains;
        }
    }
}
