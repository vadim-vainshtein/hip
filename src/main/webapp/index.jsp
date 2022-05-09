<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>

<html>

<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script src="/hip-spb/script/add.js"> </script>
</head>

<body>
    
    <form id="add_form" name="add_form" method="post" action="HipServlet">
        
        Программа: <input type="text" name="program" />
        Дата: <input type="date" name="date" />
        Время: <input type="time" name="time" />
        
        <fieldset id="performers" legend="Исполнители">
            <input type="button" value="Добавить" onclick="AddPerformer();"/>
            <input type="text" name="performer0" />
        </fieldset>

        Пароль: <input type="password" name="password"/>
        <input type="submit" id="submit" value="Отправить"/>

    </form>
</body>
</html>