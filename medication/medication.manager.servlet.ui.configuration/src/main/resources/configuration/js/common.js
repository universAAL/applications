var isStatic = true;
isStatic=false;

//Servlet aliases:
var LOGIN_SERVLET_ALIAS = "login",
    CONFIG_ACTION_SERVLET_ALIAS = "config_action_selector",
    USER_MANAGER_SERVLET_ALIAS = "user_management",
    USER_MANAGER_HANDLE_SERVLET_ALIAS = "handle_user",
    PARAMETERS_SERVLET_ALIAS = "parameters_handler",
    PARAMETERS_HANDLE_SERVLET_ALIAS = "handle_parameters",
    NOTIFICATION_RULES_SERVLET_ALIAS = "notification_rules_handler",
    NOTIFICATION_RULES_HANDLE_SERVLET_ALIAS="handle_notification_rules"


CANCEL = "?cancel=true";

$(function () {
    if (isStatic) {
        LOGIN_SERVLET_ALIAS = "login.html";
        CONFIG_ACTION_SERVLET_ALIAS = "select_config_action.html";
        USER_MANAGER_SERVLET_ALIAS = "user_management.html";
        USER_MANAGER_HANDLE_SERVLET_ALIAS=USER_MANAGER_SERVLET_ALIAS;
        PARAMETERS_SERVLET_ALIAS = "parameters.html";
        PARAMETERS_HANDLE_SERVLET_ALIAS=PARAMETERS_SERVLET_ALIAS;
        NOTIFICATION_RULES_SERVLET_ALIAS = "notification.html";
        NOTIFICATION_RULES_HANDLE_SERVLET_ALIAS = "notification.html";
    }
    $('form[name="user"]').attr("action", isStatic ? CONFIG_ACTION_SERVLET_ALIAS : LOGIN_SERVLET_ALIAS);


    $('form[name="action"] button[name]').click(function (e) {
        e.preventDefault();
        var name = $(this).attr("name");
        var loc = "";
        if (name == "back") {
            loc = LOGIN_SERVLET_ALIAS + CANCEL;
        } else {
            loc = window[name.toUpperCase() + "_SERVLET_ALIAS"];
        }
        document.location.href = loc;

    });

    $('form[name!="action"] button[name="back"]').click(function (e) {
        e.preventDefault();
        document.location.href = CONFIG_ACTION_SERVLET_ALIAS + CANCEL;
    });
    $('form[name$="_patient"]').attr("action", USER_MANAGER_HANDLE_SERVLET_ALIAS);
    $('form[name="parameters"]').attr("action", PARAMETERS_HANDLE_SERVLET_ALIAS);
    $('form[name="rules"]').attr("action", NOTIFICATION_RULES_HANDLE_SERVLET_ALIAS);
});

function debug(str) {
    if (typeof console != 'undefined' && typeof console.log == 'function') console.log(str);
    else alert(str);
}
