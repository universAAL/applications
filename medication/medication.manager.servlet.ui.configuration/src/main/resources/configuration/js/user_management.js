var patients = {},
    physicians = {},
    caregivers = {},
    dispensers = {},
    users = [];
function editPatient(info) {
    $('form fieldset #patient').val(info["patient"]);
    $('form fieldset #physician').val(info["physician"]);
    $('form fieldset #caregiver').val(info["caregiver"]);
    $('form fieldset #dispenser').val(info["dispenser"]);
    var checks=["due", "missed","successful", "upside"];
    $.each(checks, function(i, name){
        $('form fieldset :checkbox[name="'+name+'"]').attr("checked", info["alerts"][name]);
    });
    $('form>[name!="back"]').show();
}
function fillSelect(selector, obj) {
    var s = $(selector);
    $.each(obj, function (id, name) {
        s.append('<option value="' + id + '">' + name + '</option>');
    });
}

$(function () {
    $('table#patients button[name="edit"]').click(function (e) {
        e.preventDefault();
        editPatient($(this).closest("tr").data("info"));

    });

    $('table#patients button[name="delete"]').click(function (e) {
        e.preventDefault();
        var f=$('form[name="delete_patient"]');
        f.find('input').val($(this).closest("tr").attr("id"));
        f.submit();
    });

    $('form button[name="cancel"]').click(function (e) {
        e.preventDefault();
        $('form>[name!="back"]').hide();
    });

    if (users.length > 0) {
        fillSelect('select#patient', patients);
        fillSelect('select#physician', physicians);
        fillSelect('select#caregiver', caregivers);
        fillSelect('select#dispenser', dispensers);

        var tableSelector = 'table';
        var templSelector = tableSelector + ' tr.templ';
        var $trTempl = $(templSelector).clone(true).show();

        $(tableSelector + ' tr:has(td)').remove();
        $.each(users, function (i, user) {
            var tr = $trTempl.clone(true);
            tr.data("info", user);
            tr.attr("id", user["patient"]);
            tr.find('td:eq(0)').html(patients[user["patient"]])
                .next('td').html(physicians[user["physician"]])
                .next('td').html(caregivers[user["caregiver"]])
                .next('td').html(dispensers[user["dispenser"]]);

            $(tableSelector).append(tr);
        });
        $(tableSelector + " tr:odd").addClass("odd");
        $(tableSelector + " tr:even").addClass("even");
        $(tableSelector + " tr:first").removeClass(" odd even");

    }
});