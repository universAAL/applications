//var isStatic = true;
isStatic = false;

//Servlet aliases:
var LOGIN_SERVLET_ALIAS = "/review/login",
        SELECT_USER_SERVLET_ALIAS = "/review/select_user",
        HANDLE_SELECT_USER_SERVLET_ALIAS = "/review/handle_user",
        SELECT_MEDICINE_SERVLET_ALIAS = "/review/select_med",
        HANDLE_MEDICINE_SERVLET_ALIAS = "/review/handle_med",
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
    $('form[name="select_med"]').attr("action", HANDLE_MEDICINE_SERVLET_ALIAS);
    $('form[name="select_med"] button[name="back"]').click(function (e) {
        e.preventDefault();
        document.location.href = HANDLE_MEDICINE_SERVLET_ALIAS + BACK;
    });
    $('form[name="success"]').attr("action", HANDLE_SUCCESS_SERVLET_ALIAS);
    $('form[name="success"] button[name="back"]').click(function (e) {
        e.preventDefault();
        document.location.href = HANDLE_SUCCESS_SERVLET_ALIAS + BACK;
    });
    $('form[name="success"] button[name="logout"]').click(function (e) {
        e.preventDefault();
        document.location.href = HANDLE_SUCCESS_SERVLET_ALIAS + CANCEL;
    });


});
