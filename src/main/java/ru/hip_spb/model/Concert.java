package ru.hip_spb.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Concert {

    private int concert_id;
    private LocalDateTime dateTime;
    private Place place;
    private String programName;
    private String programText;
    private String link;
    private ArrayList<Ensemble> ensembles;

    public Concert(int concert_id, LocalDateTime dateTime, Place place, String programName, String programText, String link, ArrayList<Ensemble> ensembles) {
        this.concert_id = concert_id;
        this.dateTime = dateTime;
        this.place = place;
        this.programName = programName;
        this.programText = programText;
        this.link = link;
        this.ensembles = ensembles;
    }

    public Concert() {
    
    }
    
    public String getProgramText() {
        return programText;
    }
    
    public void setProgramText(String programText) {
        this.programText = programText;
    }

    public int getConcert_id() {
        return concert_id;
    }

    public void setConcert_id(int concert_id) {
        this.concert_id = concert_id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<Ensemble> getEnsembles() {
        return ensembles;
    }

    public void setEnsembles(ArrayList<Ensemble> ensembles) {
        this.ensembles = ensembles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + concert_id;
        result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
        result = prime * result + ((place == null) ? 0 : place.hashCode());
        result = prime * result + ((programName == null) ? 0 : programName.hashCode());
        result = prime * result + ((programText == null) ? 0 : programText.hashCode());
        result = prime * result + ((link == null) ? 0 : link.hashCode());
        result = prime * result + ((ensembles == null) ? 0 : ensembles.hashCode());
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
        Concert other = (Concert) obj;
        if (concert_id != other.concert_id)
            return false;
        if (dateTime == null) {
            if (other.dateTime != null)
                return false;
        } else if (!dateTime.equals(other.dateTime))
            return false;
        if (place == null) {
            if (other.place != null)
                return false;
        } else if (!place.equals(other.place))
            return false;
        if (programName == null) {
            if (other.programName != null)
                return false;
        } else if (!programName.equals(other.programName))
            return false;
        if (programText == null) {
            if (other.programText != null)
                return false;
        } else if (!programText.equals(other.programText))
            return false;
        if (link == null) {
            if (other.link != null)
                return false;
        } else if (!link.equals(other.link))
            return false;
        if (ensembles == null) {
            if (other.ensembles != null)
                return false;
        } else if (!ensembles.equals(other.ensembles))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Concert [concert_id=" + concert_id + ", dateTime=" + dateTime + ", place=" + place + ", programName="
                + programName + ", programText=" + programText + ", link=" + link + ", ensembles=" + ensembles + "]";
    }

    

}