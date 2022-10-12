package ru.hip_spb.model;

import java.util.ArrayList;

public class Ensemble {
    
    private int id;
    private String name;
    private ArrayList<Performer> performers;
    
    public Ensemble() {
    }

    public Ensemble(int id, String name) {
        this.id = id;
        this.name = name;
        performers.clear();
    }

    public Ensemble(int id, String name, ArrayList<Performer> performers) {
        this.id = id;
        this.name = name;
        this.performers = performers;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((performers == null) ? 0 : performers.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ensemble other = (Ensemble) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (performers == null) {
            if (other.performers != null)
                return false;
        } else if (!performers.equals(other.performers))
            return false;
        return true;
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
    public ArrayList<Performer> getPerformers() {
        return performers;
    }
    public void setPerformers(ArrayList<Performer>) performers) {
        this.performers = performers;
    }
    @Override
    public String toString() {
        return "Ensemble [id=" + id + ", name=" + name + ", performers=" + performers + "]";
    }

}
