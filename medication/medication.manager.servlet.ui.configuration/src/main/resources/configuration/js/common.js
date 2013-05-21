var isStatic = true;
isStatic=false;

//Servlet aliases:
var LOGIN_SERVLET_ALIAS = "login",
    CONFIG_ACTION_SERVLET_ALIAS = "config_action_selector",
    USER_MANAGER_SERVLET_ALIAS = "user_manager servlet",
    PARAMETERS_SERVLET_ALIAS = "parameters_handler",
    NOTIFICATION_RULES_SERVLET_ALIAS = "notification_rules_handler",
    CANCEL = "?cancel=true";

$(function () {
    if (isStatic) {
        LOGIN_SERVLET_ALIAS = "login.html";
        CONFIG_ACTION_SERVLET_ALIAS = "select_config_action.html";
        USER_MANAGER_SERVLET_ALIAS = "user_management.html";
        PARAMETERS_SERVLET_ALIAS = "parameters.html";
        NOTIFICATION_RULES_SERVLET_ALIAS = "notification_rules.html";
    }
    $('form[name="user"]').attr("action", isStatic ? CONFIG_ACTION_SERVLET_ALIAS : LOGIN_SERVLET_ALIAS);


    $('form[name="action"] button[name]').click(function (e) {
        e.preventDefault();
        var name = $(this).attr("name");
        var loc="";
        if (name == "back") {
            loc= LOGIN_SERVLET_ALIAS + CANCEL;
        } else {
            loc = window[name.toUpperCase()+"_SERVLET_ALIAS"];
        }
        document.location.href = loc;

    });
    $('form[name!="action"] button[name="back"]').click(function (e) {
        e.preventDefault();
        document.location.href = CONFIG_ACTION_SERVLET_ALIAS + CANCEL;
    });

    /*
     $('form[name="prescriptions"]').attr("action", LIST_PRESCRIPTIONS_SERVLET_ALIAS);
     $('form[name="prescriptions"] button[name="back"]').click(function (e) {
     e.preventDefault();
     document.location.href = LOGIN_SERVLET_ALIAS + CANCEL;
     });

     $('form[name="new_prescription"]').attr("action", NEW_PRESCRIPTION_SERVLET_ALIAS);
     $('form[name="new_prescription"] button[name="back"]').click(function (e) {
     e.preventDefault();
     document.location.href = SELECT_USER_SERVLET_ALIAS + CANCEL;
     });


     $('form[name="save_prescription"]').attr("action", HANDLE_NEW_PRESCRIPTION_SERVLET_ALIAS);
     $(document).on("click", 'form[name="save_prescription"] tr button[name="edit"]', function (e) {
     e.preventDefault();
     $('form[name="save_prescription"]').attr("action", NEW_MEDICINE_SERVLET_ALIAS + "?id=" + $(this).closest("tr").attr("id"))
     .unbind("submit").submit();
     //var p=$('[name="prescriptionId"]').val();
     //document.location.href = NEW_MEDICINE_SERVLET_ALIAS + "?id=" + $(this).closest("tr").attr("id")+"&prescriptionId="+p;
     });
     $(document).on("click", 'form[name="save_prescription"] tr button[name="delete"]', function (e) {
     e.preventDefault();

     $('form[name="save_prescription"]').attr("action", HANDLE_NEW_MEDICINE_SERVLET_ALIAS + "?deleteID=" + $(this).closest("tr").attr("id"))
     .unbind("submit").submit();
     //var p=$('[name="prescriptionId"]').val();
     //document.location.href = HANDLE_NEW_MEDICINE_SERVLET_ALIAS + "?deleteID=" + $(this).closest("tr").attr("id")+"&prescriptionId="+p;
     });
     $('form[name="save_prescription"] button[name="add_medicine"]').click(function (e) {
     e.preventDefault();
     $('form[name="save_prescription"]').attr("action", NEW_MEDICINE_SERVLET_ALIAS)
     .unbind("submit").submit();
     //var p=$('[name="prescriptionId"]').val();
     //document.location.href = NEW_MEDICINE_SERVLET_ALIAS+"?prescriptionId="+p;
     });
     $('form[name="save_prescription"] button[name="back"]').click(function (e) {
     e.preventDefault();
     document.location.href = LIST_PRESCRIPTIONS_SERVLET_ALIAS + CANCEL;
     });

     $('form[name="save_medicine"]').attr("action", HANDLE_NEW_MEDICINE_SERVLET_ALIAS);
     $('form[name="save_medicine"] button[name="back"]').click(function (e) {
     e.preventDefault();
     var id = $('[name="id"]').val();
     document.location.href = HANDLE_NEW_MEDICINE_SERVLET_ALIAS + "?deleteID=" + id;
     }) });*/

});
function debug(str) {
    if (typeof console != 'undefined' && typeof console.log == 'function') console.log(str);
    else alert(str);
}
