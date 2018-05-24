
    function createDataList(quantity, where) {
        for (let i = 0; i < quantity; i++) {
            create();
        }

        function create() {
            let opt = document.createElement('li');
            opt.classList.add('searchOpt');
            let newEl = $(opt);
            newEl.appendTo(where);
        }
    }

    function searchInputEvent() {
        let searchInput = document.querySelector('#searchInput');

        searchInput.onkeyup = function () {
            let text = searchInput.value;
            let searchUl = document.querySelector('.searchUl');
            try {
                $.ajax({
                    type: 'post',
                    url: '/search/' + text,
                    headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
                    success: function (result) {
                        searchUl.innerHTML = '';
                        createDataList(result.length, searchUl);
                        let li = document.querySelectorAll('.searchOpt');
                        for (let i = 0; i < li.length; i++) {
                            li[i].innerHTML = result[i].header;
                        }
                    },
                    error: function (xhr) {
                        searchUl.innerHTML = '';
                    },
                });
            } catch (e) {
            }
        }
    }

    searchInputEvent();