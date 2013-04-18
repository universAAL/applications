var userObj = {"name":"", "prescriptions":[]};
$(function () {
  if (userObj) {

    var tableSelector = 'table';
    var templSelector = tableSelector + ' tr.templ';
    var $medTempl = $(templSelector + " td.medicine div").clone();
    $(templSelector+' td.medicine').empty();
    var $trTempl = $(templSelector).clone().show();

    $('h2 span').html(userObj.name);
    if (userObj.prescriptions.length > 0) {
      $(tableSelector + ' tr:has(td)').remove();
      $.each(userObj.prescriptions, function (i, prescription) {
        var tr = $trTempl.clone();
        tr.find('td:eq(0)').html(prescription.date).next('td').find("textarea").html(prescription.notes);
        if ($.isArray(prescription.medicines)) {

          $.each(prescription.medicines, function (i, medicine) {
            var d = $medTempl.clone();
            d.find('span:first').html(medicine.name);
            d.find('span:last').html(medicine.how);
            tr.find('td:eq(2)').append(d);
          });
        }
        $(tableSelector).append(tr);
      });
      $(tableSelector + " tr:odd").addClass("odd");
      $(tableSelector + " tr:even").addClass("even");
      $(tableSelector + " tr:first").removeClass(" odd even");
    }
  } else {
    alert("No user information");
    $('input:submit').attr("disabled", true);
  }
});