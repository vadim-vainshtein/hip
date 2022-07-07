<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="ru.hip_spb.controller.PerformerController"%>

<!DOCTYPE html>

<html>

<head>
    <title>Добавить событие</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="script/add.js"> </script>
</head>

<body>
    
    <form id="add_form" name="add_form" method="post" action="add">
    
        
        Программа: <input type="text" name="program" />
        <br>
        Дата: <input type="date" name="date" />
        Время: <input type="time" name="time" />
        
        <fieldset id="performers">
            <datalist id="performers_list">
                <%
                    PerformerController performerController = new PerformerController();
                %>
        
                <%= performerController.printDatalist() %>

            </datalist>
            <legend>Исполнители</legend>
            <input type="button" onclick="AddPerformer()" value="Добавить исполнителя">
            
        </fieldset>
        Место: <input type="text" name="place" /> Адрес: <input type="text" name="address" />
        <br>
        Ссылка: <input type="text" name="link" />
        <br>
        <input type="submit" id="submit" value="Отправить"/>
        
    </form>
    
</body>
</html>