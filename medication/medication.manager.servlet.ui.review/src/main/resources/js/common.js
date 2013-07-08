//var isStatic = true;
isStatic = false;

//Servlet aliases:
var LOGIN_SERVLET_ALIAS = "/review/login",
        SELECT_USER_SERVLET_ALIAS = "/review/select_user",
        HANDLE_SELECT_USER_SERVLET_ALIAS = "/review/handle_user",
        HANDLE_SUCCESS_SERVLET_ALIAS = "/review/handle_success",
        CANCEL = "?cancel=true";
        BACK = "?back=true";

$(function () {
    $('form[name="user"]').attr("action", LOGIN_SERVLET_ALIAS);
    $('form[name="select_user"]').attr("action", HANDLE_SELECT_USER_SERVLET_ALIAS);
    $('form[name="select_user"] button[name="back"]').click(function (e) {
        e.preventDefault();
        document.location.href = LOGIN_SERVLET_ALIAS + CANCEL;
    });



});
