//var isStatic = true;
isStatic = false;

//Servlet aliases:
var LOGIN_SERVLET_ALIAS = "/prescription/login",
        SELECT_USER_SERVLET_ALIAS = "/prescription/select_user",
        LIST_PRESCRIPTIONS_SERVLET_ALIAS = "/prescription/display_prescriptions",
        NEW_PRESCRIPTION_SERVLET_ALIAS = "/prescription/display_new_prescription",
        HANDLE_NEW_PRESCRIPTION_SERVLET_ALIAS = "/prescription/handle_new_prescription",
        NEW_MEDICINE_SERVLET_ALIAS = "/prescription/display_new_medicine",
        HANDLE_NEW_MEDICINE_SERVLET_ALIAS = "/prescription/handle_new_medicine",
        CANCEL = "?cancel=true";

$(function () {
    if (isStatic) {
        LOGIN_SERVLET_ALIAS = "login.html";
        SELECT_USER_SERVLET_ALIAS = "user.html",
                LIST_PRESCRIPTIONS_SERVLET_ALIAS = "prescriptions.html",
                NEW_PRESCRIPTION_SERVLET_ALIAS = "new_prescription.html",
                HANDLE_NEW_PRESCRIPTION_SERVLET_ALIAS = "prescriptions.html",
                NEW_MEDICINE_SERVLET_ALIAS = "medicine.html",
                HANDLE_NEW_MEDICINE_SERVLET_ALIAS = "new_prescription.html";
    }
    $('form[name="user"]').attr("action", isStatic ? SELECT_USER_SERVLET_ALIAS : LOGIN_SERVLET_ALIAS);

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
    $('form[name="save_medicine"] button[name="delete"]').click(function (e) {
        e.preventDefault();
        var id = $('[name="id"]').val();
        document.location.href = HANDLE_NEW_MEDICINE_SERVLET_ALIAS + "?deleteID=" + id;
    });

    $('form[name="save_medicine"]').attr("action", HANDLE_NEW_MEDICINE_SERVLET_ALIAS);
    $('form[name="save_medicine"] button[name="back"]').click(function (e) {
        e.preventDefault();
        var id = $('[name="id"]').val();
        document.location.href = HANDLE_NEW_MEDICINE_SERVLET_ALIAS + CANCEL + "&id=" + id;
    });
});
