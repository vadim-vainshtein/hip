package ru.hip_spb.model;

import java.util.ArrayList;
import java.util.Objects;

public class Performer {
    
    private int id;
    private String name;
    private ArrayList<Instrument> instruments;

    public Performer(int id, String name, ArrayList<Instrument> instruments) {
        this.id = id;
        this.name = name;
        this.instruments = instruments;
    }

    public Performer(int id, String name) {
        this.id = id;
        this.name = name;
        instruments = new ArrayList<>();
    }

    public ArrayList<Instrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(ArrayList<Instrument> instruments) {
        this.instruments = instruments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }       


    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Performer other = (Performer) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Performer{" + "id=" + id + ", name=" + name + '}';
    }
    
}
