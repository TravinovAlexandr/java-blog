
window.onload = function () {
    // $.when(
    //     $.getScript( "/js/common.js" ),
    // ).done(function () {
        // let xmlHttp;
        //
        // if (window.XMLHttpRequest) { // code for IE7+, Firefox, Chrome, Opera, Safari
        //     xmlHttp = new XMLHttpRequest();
        // } else { // code for IE6, IE5
        //     xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        // }

        function articleConfirmationBox(message, fun) {
            document.querySelector('.confirmBoxMessage').innerHTML = message;
            document.querySelector('.confirmBox').style.display = 'block';
            document.querySelector('.confirmBackLayout').style.display = 'block';

            document.querySelector(".closeBtn").onclick = function () {
                off();
            };
            document.querySelector(".butCancel").onclick = function () {
                off();
            };
            document.querySelector(".butOK").onclick = function () {
                fun.call();
                off();
            };

            function off() {
                $(".confirmBox").hide(100);
                $(".confirmBackLayout").hide(300);
            }
        }

        function alertBox(message, fun) {

            document.querySelector('.alertBoxMessage').innerHTML = message;
            document.querySelector('.alertBox').style.display = 'block';
            document.querySelector('.alertBackLayout').style.display = 'block';

            document.querySelector('.alertBackLayout').onclick = function () {
                fun.call();
            };
        }

        function counter() {
            let countNum = 0;
            return function () {
                return countNum++;
            };
        }

        function linker(clazz, link, value, title) {
            let t = 'title=\"' + title + '\"';
            return '<a class=\"' + clazz + '\" href=\"' + link + '\"' + t + '>' + value + '</a>';
        }

        function addArticle() {
            document.querySelector('#addArticle').onclick = function () {

                articleConfirmationBox("Подтвердите  отправку  вашей  статьи.", add);

                function add() {
                    let data = $(".articleForm").serialize();
                    jQuery.ajax({
                        type: 'post',
                        url: '/addArticle',
                        processData: false,
                        contentType: 'application/x-www-form-urlencoded',
                        headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                        cache: false,
                        data: data,
                        success: function (result) {

                            document.querySelector('.header').value = '';
                            document.querySelector('.content').value = '';
                            alertBox(result.message, function () {
                                $(".alertBox").hide(100);
                                $(".alertBackLayout").hide(300);

                            });
                        },
                    });
                }
            }
        }

        function getUserArticles(fun) {
            document.querySelector('.artPaginationBut').onclick = function () {

                let count = fun.call();
                let url = '/getPageableArticleLinks?num=' + count;
                let content = document.querySelector('.articlePaginationDiv');
                let tempContent = '';

                jQuery.ajax({
                    type: 'get',
                    url: url,
                    success: function (result) {
                        for (let i = 0; i < result.message.length; i++) {
                            tempContent = tempContent + linker('pageableLink', 'article/' + result.message[i].uid,
                                result.message[i].header, result.message[i].contentInfo) + '</br>';
                        }
                        content.innerHTML = content.innerHTML + tempContent;
                    },
                });
            }
        }

        function getUserInformation() {

            let path = window.location.pathname;
            let url = path == '/person' ? '/commonPerson' : '/commonPerson/' + path.split('/')[2];
            $.ajax({
                method: 'post',
                url: url,
                headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                success: function (result) {
                    let information = document.querySelector(".information");
                    information.innerHTML = information.innerHTML + result.nick + '</br>';
                    information.innerHTML = information.innerHTML + result.email + '</br>';
                    information.innerHTML = information.innerHTML + result.name + '</br>';
                    information.innerHTML = information.innerHTML + result.lastName + '</br>';

                    document.querySelector('.avatar').src = "data:image/png;base64," + result.avatar;
                },
            });
        }

        function changeUserInformation() {
            $("#changeInformationButton").click(function () {

                let data = $(".changeInformationForm").serialize();

                $.ajax({
                    type: 'post',
                    url: '/changeInformation',
                    processData: false,
                    contentType: 'application/x-www-form-urlencoded',
                    headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                    cache: false,
                    data: data,
                    success: function (result) {
                        alertBox(result.message, function () {
                            $(".alertBox").hide(100);
                            $(".alertBackLayout").hide(300);
                            if (result.message.endsWith('на.')) {
                                document.querySelector('.information').innerHTML = '';
                                getUserInformation();
                                document.querySelector('.changeInformationName').value = '';
                                document.querySelector('.changeInformationLastName').value = '';
                                document.querySelector('.changeInformationPassword').value = '';
                            }
                        });
                    },
                });
            });
        }

        function changeAvatar() {
            $('.changeAvatarForm').submit(function (e) {
                $.ajax({
                    url: '/changeAvatar',
                    method: 'post',
                    data: new FormData(this),
                    headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                    processData: false,
                    contentType: false,
                    success: function (result) {
                        alertBox(result.message, function () {

                            $(".alertBox").hide(100);
                            $(".alertBackLayout").hide(300);

                            let information = document.querySelector('.userInformation').childNodes;
                            information.forEach(function (elem) {
                                elem.innerHTML = '';
                            });

                            let oldElement = $('.changeAvatarFileInput');
                            let element = document.createElement('input');
                            element.type = 'file';
                            element.classList.toggle('changeAvatarFileInput');
                            element.name = 'image';
                            oldElement.replaceWith(element);

                            getUserInformation();

                        });
                    },
                    error: function (xhr) {
                        alertBox(xhr.responseText.substring(1, xhr.responseText.length - 1), function () {

                            $(".alertBox").hide(100);
                            $(".alertBackLayout").hide(300);

                            let oldElement = $('.changeAvatarFileInput');
                            let element = document.createElement('input');
                            element.type = 'file';
                            element.classList.toggle('changeAvatarFileInput');
                            element.name = 'image';
                            oldElement.replaceWith(element);
                        });
                    }
                });
                e.preventDefault();
            });
        }

        function AuthorizeContentControl() {
            try {
                let path = window.location.pathname.split('/');
                if (path.length === 2 && path[1] === 'person') {
                    let el = document.querySelector('.authorizeContent');
                    el.style.display = 'block';
                }
            } catch (e) {
            }
        }

        AuthorizeContentControl();
        getUserInformation();
        changeUserInformation();
        changeAvatar();
        addArticle();
        getUserArticles(counter());
    // });
};

