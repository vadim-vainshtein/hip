<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="ru.hip_spb.controller.PerformerController"%>
<%@page import="ru.hip_spb.controller.PlaceController"%>

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
    
        
        <p>Название: <input type="text" name="program_name" /></p>
        <p>Программа:</p>
        <p>
            <textarea name="program_text" maxlength="10000" cols="100" rows="10">

            </textarea>
        </p>
        <p>
            Дата: <input type="date" name="date" />
            Время: <input type="time" name="time" />
        </p>

        <datalist id="performers_list">
            <%
                PerformerController performerController = new PerformerController();
            %>
    
            <%= performerController.printDatalist() %>

        </datalist>
        
        <div id="ensembles-wrapper">
            
            <input type="button" value="Добавить ансамбль" onclick="AddEnsemble(this)"/>
            
            <fieldset id="ensemble0">
                <legend>Ансамбль №1</legend>
            
                <p>Название ансамбля: <input name="ensemble0"></p>
                <div><input type="button" onclick="AddPerformer(this)" value="Добавить исполнителя"></div>
            
            </fieldset>
            
        </div>

        <datalist id="places">
            <%
                PlaceController placeController = new PlaceController();
            %>
        
            <%= placeController.printDatalist() %>
        </datalist>
        <p>
            Место: <input type="text" name="place" list="places" onchange="AutoFillAddress(this.value)"/>
            Адрес: <input id="address" type="text" name="address" />
            Ссылка: <input type="text" name="link" />
        </p>
        <p><input type="submit" id="submit" value="Отправить"/></p>
        
    </form>
    
</body>
</html>