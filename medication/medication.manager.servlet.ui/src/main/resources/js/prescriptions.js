var userObj = {"name":"", "prescriptions":[]};
$(function () {

  if (userObj) {
    alert("but pres=" + $('button[name="new_prescription"]').size())
    $('button[name="new_prescription"]').click(function () {
      document.location.href = "/display_new_prescription";
    });
    $('button[name="back"]').click(function () {
      history.back();
    });

    var tableSelector = 'table';
    var $trTempl = $(tableSelector + ' tr.templ').clone();

    $('h2 span').html(userObj.name);
    if (userObj.prescriptions.length > 0) {
      $(tableSelector + ' tr:has(td)').remove();
      $.each(userObj.prescriptions, function (i, prescription) {
        var tr = $trTempl.clone();
        tr.find('td:eq(0)').html(prescription.date).next('td').html(prescription.notes);
        if ($.isPlainObject(prescription.medicine)) {
          tr.find('td:eq(2) span:first').html(prescription.medicine.name);
          tr.find('td:eq(2) span:last').html(prescription.medicine.how);
        }
        $(tableSelector).append(tr);
      });
    }
  } else alert("No user information");
});