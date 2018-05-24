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
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="/js/common.js" defer></script>

    <title>ALEX_TR.com</title>
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

            <div class="col-sm-3 col-md-3">
                <form class="navbar-form" role="search">
                    <div class="input-group">
                        <input id="searchInput" type="text" class="form-control" placeholder="Search" name="q">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="button"><i class="glyphicon glyphicon-search"></i></button>
                        </div>
                    </div>
                </form>
            </div>

            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form class="Log" method="${method}" action="${log}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <a href="${logLink}" ${onClick}><span class="glyphicon glyphicon-log-in"></span>${enter}</a>
                    </form>
                </li>

                <li><a href="/registration"><span class="glyphicon glyphicon-user"></span> Регистрация</a></li>
            </ul>
        </div>
    </div>
</nav>

<#--Serch-->
<ul class="searchUl">

</ul>



<h1>Hello</h1>
<iframe width="560" height="315"
        src="https://www.youtube.com/embed/9WnEOTUMPBQ?rel=0"
        frameborder="0" allow="autoplay; encrypted-media" allowfullscreen>
</iframe>
<hr/>
<iframe width="560" height="315"
        src="https://www.youtube.com/embed/xi_ZMhQ3cFE?rel=0"
        frameborder="0" allow="autoplay; encrypted-media" allowfullscreen>
</iframe>
<hr/>
<iframe width="560" height="315" src="https://www.youtube.com/embed/THc6Iv745PY" frameborder="0"
        allow="autoplay; encrypted-media" allowfullscreen></iframe>

<iframe width="560" height="315" src="https://www.youtube.com/embed/ow0oPZNxPdg" frameborder="0"
        allow="autoplay; encrypted-media" allowfullscreen></iframe>


<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</body>

</html>