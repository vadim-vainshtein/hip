<%@page import="ru.hip_spb.controller.ConcertController"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Концерты HIP в Санкт-Петербурге</title>
    </head>
    <body>
        <%
            ConcertController concertController = new ConcertController();
        %>
        
        <%= concertController.format() %>
        
    </body>
</html>
