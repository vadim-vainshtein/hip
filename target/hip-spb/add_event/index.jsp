<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="ru.hip_spb.controller.PerformerController"%>

<!DOCTYPE html>

<html>

<head>
    <title>Добавить событие</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="script/add.js"> </script>
    <link rel="stylesheet" href="style/style.css">
</head>

<body>
    
    <form id="add_form" name="add_form" method="post" action="add">
    
        
        <p>Программа: <input type="text" name="program" /></p>
        <p>Дата: <input type="date" name="date" /></p>
        <p>Время: <input type="time" name="time" /></p>
        
        <fieldset id="performers">
            <datalist id="performers_list">
                <%
                    PerformerController performerController = new PerformerController();
                %>
        
                <%= performerController.printDatalist() %>

            </datalist>
            <legend>Исполнители</legend>
            
            <p><input type="button" onclick="AddPerformer()" value="Добавить исполнителя"></p>
            
        </fieldset>
        <p>Место: <input type="text" name="place" /> Адрес: <input type="text" name="address" /></p>
        <p>Ссылка: <input type="text" name="link" /></p>
        <p><input type="submit" id="submit" value="Отправить"/></p>
        
    </form>
    
</body>
</html>