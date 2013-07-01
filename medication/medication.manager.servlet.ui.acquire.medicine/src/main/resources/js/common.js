//var isStatic = true;
isStatic = false;

//Servlet aliases:
var LOGIN_SERVLET_ALIAS = "/acquire/login",
        SELECT_USER_SERVLET_ALIAS = "/acquire/select_user",
        CANCEL = "?cancel=true";

$(function () {
    if (isStatic) {
        LOGIN_SERVLET_ALIAS = "login.html";
        SELECT_USER_SERVLET_ALIAS = "user.html";
    }
    $('form[name="user"]').attr("action", isStatic ? SELECT_USER_SERVLET_ALIAS : LOGIN_SERVLET_ALIAS);


});
