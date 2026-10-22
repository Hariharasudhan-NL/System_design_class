
package com.rest.models;

public class Table {
    public enum Availability {
        occupy, free
    }

    private int id;
    private int capacity;
    private Availability status;

    public Table(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.status = Availability.free;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public Availability getStatus() {
        return status;
    }

    public void booking() {
        status = Availability.occupy;
    }

    public void freeNow() {
        status = Availability.free;
    }
}
