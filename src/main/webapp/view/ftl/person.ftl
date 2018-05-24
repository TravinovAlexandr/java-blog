<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">


    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <link rel="stylesheet" type="text/css" href="/css/main.css">
    <script src="/js/jquery-git.js"></script>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script src="/js/person.js" defer></script>
    <script src="/js/common.js" defer></script>

    <title>Личная страничка</title>
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
                        <input id="searchInput"  type="text" class="form-control searchInput" placeholder="Search" name="q">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="button"><i class="glyphicon glyphicon-search"></i></button>
                        </div>
                    </div>
                </form>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${log}"><span class="glyphicon glyphicon-log-in"></span>${enter}</a></li>
                <li><a href="/registration"><span class="glyphicon glyphicon-user"></span> Регистрация</a></li>
            </ul>
        </div>
    </div>
</nav>

<#--Serch-->
<ul class="searchUl">

</ul>

<#--AVATAR-->
<div class="userInformation">
    <img class="avatar" src="" />
    <div class="information"></div> <hr>
</div>



<#--Authorize content for person page-->
<div class="authorizeContent">
<#---->
    <form class="articleForm" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <label for="InputText">Ваша статья
            <textarea class="header"  name="header"></textarea>
            <textarea class="content"  name="content" ></textarea>
        </label>
        <button id="addArticle" type="button" class="btn btn-primary">Подтвердить</button>
    </form>

    <hr />

<#---->
    <div class="articlePaginationDiv">

    </div>
    <button class="artPaginationBut">Показать статьи</button>

    <#--${change}-->
<#---->




    <form method="post" class="changeInformationForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="text" name="name" class="changeInformationName"/>
        <input type="text" name="lastName" class="changeInformationLastName"/>
        <input type="password" name="password" class="changeInformationPassword"/>
        <button type="button" id="changeInformationButton">OK</button>
    </form>

    <hr/>





    <#---->
    <form method="post" enctype="multipart/form-data" class="changeAvatarForm">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <input type="file"  class="changeAvatarFileInput" name="image"/>
        <button type="submit" class="changeAvatarButton">Ok</button>
    </form>
    <#--${file}-->



</div>




<#--Confirm Box-->
<div class="confirmBackLayout">
    <div class="confirmBox">
        <span class="closeBtn">&times;</span>
        <p class="confirmBoxMessage"></p>
        <div class="buttons">
            <button class="butOK">Ok</button>
            <button class="butCancel">Cancel</button>
        </div>
    </div>
</div>

<#--AlertBox-->
<div class="alertBackLayout">
    <div class="alertBox">
        <p class="alertBoxMessage"></p>
        <div class="buttons">
        </div>
    </div>
</div>
</body>
</html>