package ru.hip_spb.view;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import ru.hip_spb.model.Performer;


public class ConcertView {

    private final Writer writer;
    
    public ConcertView(Writer writer) {
        this.writer = writer;
    }
    
    public String format(String programName, LocalDateTime dateTime, Performer[] performers) {
        
        String result = "<br><h1>" + programName + "</h1><br>" + dateTime.toString();
        
        for(Performer performer : performers) {
            result += "<p>" + performer.getName() + "</p>";
        }
        
        result += "<hr>";
        
        return result;
    }
    
    public void print(String programName) throws IOException {
        writer.write("<div>");
        writer.write(programName);
        writer.write("</div>");
    }
}
