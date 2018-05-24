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

    <title>Регистрация</title>
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
            <li><a href="${log}"><span class="glyphicon glyphicon-log-in"></span>${enter}</a></li>
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
            <form method="post" action="/registration" name="userForm">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <div class="form-group">
                    <label for="InputText">Введите ник</label>
                    <input type="text" class="form-control" id="formGroupExampleInput" placeholder="Enter your nick" name="nick"/>
                    <small id="emailHelp" class="form-text text-muted"></small>
                </div>
                <div class="form-group">
                    <label for="InputEmail">Введите email</label>
                    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email" name="email"/>
                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                </div>
                <div class="form-group">
                    <label for="InputPassword">Введите пароль</label>
                    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="password">
                </div>
                <button type="submit" class="btn btn-primary">Подтвердить</button>
            </form>
        </div>
        <div class="col-lg-9">
            <a class="aa"> </a>
        </div>
    </div>
</div>
</body>
</html>