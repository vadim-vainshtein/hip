package ru.hip_spb.servlet;

import javax.servlet.ServletException;

import javax.servlet.annotation.MultipartConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
	maxFileSize = 1024 * 1024 * 10,      // 10 MB
	maxRequestSize = 1024 * 1024 * 100   // 100 MB
)


public class HipServlet extends HttpServlet {


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {

            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();
            writer.print("HipServlet is there");
	}
     
        @Override
        protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
            
            PrintWriter writer = response.getWriter();
                        
            String programName = request.getParameter("program");
            String concertDate = request.getParameter("date");
            String concertTime = request.getParameter("time");
                
            writer.println("Program name: "  + programName);
            writer.println("Concert date: " + concertDate);
            writer.println("Concert time: " + concertTime);
            
            String performer = request.getParameter("performer0");
            int i = 1;
            
            while(performer != null) {
                writer.println("performer " + i + ": " + performer);
                performer = request.getParameter("performer" + i);
                i++;
            }
        } 
}
