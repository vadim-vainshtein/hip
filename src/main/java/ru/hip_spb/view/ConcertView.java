package ru.hip_spb.view;

import java.io.Writer;
import java.time.LocalDateTime;
import java.util.List;

import ru.hip_spb.model.Ensemble;


public class ConcertView {

    private final Writer writer;
    
    public ConcertView(Writer writer) {
        this.writer = writer;
    }
    
    public String format(String programName, LocalDateTime dateTime, List<Ensemble> ensembles) {
        
        String result = "<br><h1>" + programName + "</h1><br>" + dateTime.toString();
        
        for(Ensemble ensemble : ensembles) {
            result += "<p>" + ensemble.getName() + "</p>";
        }
        
        result += "<hr>";
        
        return result;
    }
}
