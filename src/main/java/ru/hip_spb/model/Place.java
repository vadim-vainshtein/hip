package ru.hip_spb.model;

import java.util.Objects;

public class Place {
    
    private int id;
    private String name;
    private String address;    

    public Place(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Place() {
        id = 0;
        name = "";
        address = "";
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Place other = (Place) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.address, other.address);
    }

    @Override
    public String toString() {
        return "Place{" + "id=" + id + ", name=" + name + ", address=" + address + '}';
    }
    
    
}
