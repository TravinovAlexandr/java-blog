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
    <script src="/js/article.js" defer></script>
    <script src="/js/common.js" defer></script>
    <script src="/js/comment_parser.js" defer></script>

    <title>Контактная информация</title>
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
                        <input id="searchInput" type="text" class="form-control searchInput" placeholder="Search" name="q">
                        <div class="input-group-btn">
                            <button class="btn btn-default" type="button"><i class="glyphicon glyphicon-search"></i></button>
                        </div>
                    </div>
                </form>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <form class="Log" method="post" action="${log}">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <li><a href="" onclick="parentNode.submit();"><span class="glyphicon glyphicon-log-in"></span>${enter}</a></li>
                </form>
                <li><a href="/registration"><span class="glyphicon glyphicon-user"></span> Регистрация</a></li>
            </ul>
        </div>
    </div>
</nav>

<#--Search-->
<ul class="searchUl">

</ul>
<#--Article-->
<div class="article">
    <div class="header"></div>
    <div class="content"></div>
    <div class="date"></div>

</div>
<#--UserInfo-->
<div class="authorInformation">
    <div class="avatar">
        <image class="authorAvatar" src=""/>
    </div>
    <div class="authorNick"></div>

</div>
<#--WRAP="PHYSICAL"-->
<#--\x0A-->
<#--commentForm-->
<div class="form">
    <form method="post" class="commentForm" name="commentForm">
        <fieldset>
        <textarea class="commentContent" name="content" wrap="physical"></textarea>
        </fieldset>
        <button type="button" class="commentFormButton">Ok</button>
    </form>
</div>


<#--CommentBody-->
<#--Comments-->
<div class="comments">

</div>


<div class="lastAddedArticles">

</div>


<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</body>
</html>