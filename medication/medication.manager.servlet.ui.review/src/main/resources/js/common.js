//var isStatic = true;
isStatic = false;

//Servlet aliases:
var LOGIN_SERVLET_ALIAS = "/review/login",
    SELECT_USER_SERVLET_ALIAS = "/review/select_user",
    HANDLE_SELECT_USER_SERVLET_ALIAS = "/review/handle_user",
    HANDLE_INTAKES_SERVLET_ALIAS = "/review/handle_intakes",
    CANCEL = "?cancel=true";
    BACK = "?back=true";
    NEXT = "?next=true";
    PREV = "?prev=true";

$(function () {
    $('form[name="user"]').attr("action", LOGIN_SERVLET_ALIAS);
    $('form[name="select_user"]').attr("action", HANDLE_SELECT_USER_SERVLET_ALIAS);
    $('form[name="select_user"] button[name="back"]').click(function (e) {
        e.preventDefault();
        document.location.href = LOGIN_SERVLET_ALIAS + CANCEL;
    });
    $('.intakes button[name="back"]').click(function (e) {
        e.preventDefault();
        document.location.href = HANDLE_INTAKES_SERVLET_ALIAS + BACK;
    });
    $('.intakes button[name="next"]').click(function (e) {
        e.preventDefault();
        document.location.href = HANDLE_INTAKES_SERVLET_ALIAS + NEXT;
    });
    $('.intakes button[name="prev"]').click(function (e) {
        e.preventDefault();
        document.location.href = HANDLE_INTAKES_SERVLET_ALIAS + PREV;
    });
    $('.intakes button[name="logout"]').click(function (e) {
        e.preventDefault();
        document.location.href = HANDLE_INTAKES_SERVLET_ALIAS + CANCEL;
    });


});
