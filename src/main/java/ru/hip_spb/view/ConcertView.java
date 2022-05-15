package ru.hip_spb.view;

import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;


public class ConcertView {

    private Writer writer;
    
    public ConcertView(Writer writer) {
        this.writer = writer;
    }
    
    public String format(String programName, LocalDateTime dateTime) {
        
        String result = "<br>" + programName + "<br>" + dateTime.toString();
        return result;
    }
    
    public void print(String programName) throws IOException {
        writer.write("<div>");
        writer.write(programName);
        writer.write("</div>");
    }
}
