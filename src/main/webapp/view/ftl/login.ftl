<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


    <title>Войти</title>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">TR</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                ${person}
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="/registration"><span class="glyphicon glyphicon-user"></span> Регистрация</a></li>
            </ul>
        </div>
    </div>
</nav>

<#--Search-->
<ul class="searchUl">

</ul>

<div class="container-fluid">
    <div class="raw centered">
        <div class="col-lg-3">
            <#--<div class="form-group ${validLoginMessage != null ? 'has-error' : ''}">-->
            <div class="form-group ${validLoginMessage}">
                <span>${validLoginMessage}</span>
            <fieldset>
            <form method="post" action="/login" name="form">


                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <div class="form-group">
                    <label for="InputText">Введите email</label>
                    <input type="text" class="form-control" id="formGroupExampleInput" placeholder="Enter your nick" name="username"/>

                    <small id="emailHelp" class="form-text text-muted"></small>
                </div>
                <div class="form-group">
                    <label for="InputPassword">Введите пароль</label>
                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="password">

                </div>
                <div class="form-check">
                    <label class="form-check-label">
                        <input type="checkbox" class="form-check-input" name="remember-me">
                        Запомнить меня
                    </label>
                </div>
                <button type="submit" class="btn btn-primary">Подтвердить</button>
            </form>
            </fieldset>
        </div>
        <div class="col-lg-9">
            <a class="aa"> </a>
        </div>
    </div>
</div>
</body>
</html>