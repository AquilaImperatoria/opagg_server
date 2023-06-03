package com.mirea.opagg;


import org.jsoup.nodes.Document;

public class PlaceStub {
    private String name;

    private Document tripparse;

    private Document zoonparse;

    private Document gisparse;

    public PlaceStub(String name, Document tripparse, Document zoonparse, Document gisparse) {
        this.name = name;
        this.tripparse = tripparse;
        this.zoonparse = zoonparse;
        this.gisparse = gisparse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Document getTripparse() {
        return tripparse;
    }

    public void setTripparse(Document tripparse) {
        this.tripparse = tripparse;
    }

    public Document getZoonparse() {
        return zoonparse;
    }

    public void setZoonparse(Document zoonparse) {
        this.zoonparse = zoonparse;
    }

    public Document getGisparse() {
        return gisparse;
    }

    public void setGisparse(Document gisparse) {
        this.gisparse = gisparse;
    }
}
