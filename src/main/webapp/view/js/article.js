window.onload = function() {
    $.when(
        $.getScript( "/js/common.js" ),
    ).done(function () {

    function linker(clazz, link, title, value) {
        let t = 'title=\"'+ title +'\"';
        return '<a class=\"' + clazz +'\" href=\"' + link + '\"' + t + '>' + value + '</a>';
    }

    function createElements(amount, where) {

        for (let i = 0; i < amount; i++) {
            append(where);
        }

        function append(domEl) {
            let img = document.createElement('img');
            img.src = '';
            img.classList.add('commentAvatarBytes');

            let commentAvatar = document.createElement('div');
            commentAvatar.classList.add('commentAvatar');
            commentAvatar.appendChild(img);

            let commentNick = document.createElement('div');
            commentNick.classList.add('commentNick');

            let commentContent = document.createElement('div');
            commentContent.classList.add('commentContent');

            let commentDate = document.createElement('div');
            commentDate.classList.add('commentDate');

            let commentBody = document.createElement('div');
            commentBody.classList.add('commentBody');
            commentBody.appendChild(commentAvatar);
            commentBody.appendChild(commentNick);
            commentBody.appendChild(commentContent);
            commentBody.appendChild(commentDate);

            let newElem = $(commentBody);

            newElem.appendTo(where);
        }
    }

    function fillComments(array) {
        let commentBody = document.querySelectorAll('.commentBody');
        for (let i = 0; i < array.length; i++) {
            let comment = new Comment.initComment(array[i]);
            let e = commentBody[i];
            e.querySelector('.commentAvatarBytes').src = comment.Avatar;
            e.querySelector('.commentNick').innerHTML = linker('commentLincToUser','/person/' + comment.UserUid, 'Перейти', comment.Nick);
            e.querySelector('.commentContent').innerHTML = comment.Content;
            e.querySelector('.commentDate').innerHTML = comment.Date;
        }
    }

    let Comment = {
        UserUid : "",
        Avatar : [],
        Nick : "",
        Content : "",
        Date : "",
        initComment : function (ajaxComment) {
            this.UserUid = ajaxComment.userUid;
            this.Avatar = "data:image/png;base64," + ajaxComment.avatar;
            // this.Content = $("textarea").html(ajaxComment.content).toString();
            this.Nick = ajaxComment.nick;
            this.Content = ajaxComment.content;
            this.Date = ajaxComment.creationDate.dayOfMonth + '.' + ajaxComment.creationDate.monthValue
                + '.' + ajaxComment.creationDate.year;
        },
    };

    function getArticleById() {

        let path = window.location.pathname;
        let url = '/getArticle/' + path.split('/')[2];
        try {
            if (path.split('/')[2].length === 12) {

                $.ajax({
                    method: 'post',
                    url: url,
                    processData: false,
                    cache: false,
                    headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                    success: function (result) {
                        document.querySelector('.header').innerHTML = result.header;
                        document.querySelector('.content').innerHTML = result.content;
                        let serverYear = result.creationDate.year;
                        let serverMonth = result.creationDate.monthValue;
                        let serverDay = result.creationDate.dayOfMonth;
                        document.querySelector('.date').innerHTML = serverDay + '.' + serverMonth + '.' + serverYear;
                        document.querySelector('.authorAvatar').src = "data:image/png;base64," + result.avatar;
                        document.querySelector('.authorNick').innerHTML = result.user.nick;
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.responseText)
                    },
                });
            }
        } catch (e) {}
    }

    function addComment() {
        $('.commentFormButton').click(function () {

            let path = window.location.pathname;
            let url = path.split('/')[2];

            let data = $(".commentForm").serialize();

            $.ajax({
                method : 'post',
                url : '/addComment/' + url,
                cache: false,
                contentType: 'application/x-www-form-urlencoded',
                headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                processData: false,
                data : data,
                success : function (result) {
                    alert(result);
                    location.reload();
                },
                error : function(xhr, ajaxOptions, thrownError) {
                    alert(xhr.responseText)
                },
            });
        });
    }

    function getAllComments() {

        let path = window.location.pathname;
        let url = path.split('/')[2];
        try {
            if (url.length === 12) {
                $.ajax({
                    method: 'post',
                    url: '/getAllComments/' + url,
                    headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                    success: function (result) {

                        createElements(result.length, '.comments');
                        fillComments(result);
                    },
                    error: function (xhr, ajaxOptions, thrownError) {
                        alert(xhr.responseText);
                    },
                });
            }
        }catch (e) {}
    }

    function commentSendValidation() {
        $('.commentFormButton').focusin(function () {
            document.querySelector('.commentFormButton').title = 'Добавить коментарий может только авторизованный пользователь.';
        });
    }

    function getLastAddedArticles() {
        let lastAddedArticles = document.querySelector('.lastAddedArticles');
        let tempContent = '';
        $.ajax({
            method : 'post',
            url : '/getLastAddedArticles',
            headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
            success: function (result) {
              for(let i = 0; i < result.message.length; i++) {
                  tempContent = tempContent + linker('pageableLink','http://localhost:8080/article/'
                      + result.message[i].uid, result.message[i].contentInfo, result.message[i].header) + '</br>';
              }
                lastAddedArticles.innerHTML = lastAddedArticles.innerHTML + tempContent;
            },
        });
    }

    getArticleById();
    addComment();
    getAllComments();
    commentSendValidation();
    getLastAddedArticles();
    });
};