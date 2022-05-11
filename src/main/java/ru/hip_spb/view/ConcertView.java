/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.hip_spb.view;

import java.io.IOException;
import java.io.Writer;


public class ConcertView {

    private Writer writer;
    
    public ConcertView(Writer writer) {
        this.writer = writer;
    }
    
    public String format(String programName) {
        
        String result = "<br>" + programName + "<br>";
        return result;
    }
    
    public void print(String programName) throws IOException {
        writer.write("<div>");
        writer.write(programName);
        writer.write("</div>");
    }
}
