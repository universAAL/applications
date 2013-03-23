var prescriptionObj = {"date":'', 'notes':'', 'medicines':[]};
$(function () {
  if (prescriptionObj) {
    var tableSelector = 'table#prescription';
    var $tableDays = $(tableSelector + ' table.days');
    var tr1 = $tableDays.find('tr:first');
    var tr2 = $tableDays.find('tr:last');
    var tr;
    for (var day = 1; day <= 24; day++) {
      tr = (day <= 12) ? tr1 : tr2;
      tr.append('<td>' + day + '</td>');
    }
    var $trTempl1 = $(tableSelector + ' tr.templ1').clone();
    var $trTempl2 = $(tableSelector + ' tr.templ2').clone();
    if (prescriptionObj.date) $(':text[name="date"]').val(prescriptionObj.date);
    if (prescriptionObj.notes) $(':text[name="notes"]').val(prescriptionObj.notes);

    if (prescriptionObj.medicines.length > 0) {
      $(tableSelector + ' tr:has(td)').remove();
      $.each(prescriptionObj.medicines, function (i, medicine) {
        var tr = $trTempl1.clone();
        tr.find('td:eq(0)').html(medicine.name)
          .next('td').html(medicine.description)
          .next('td').html(medicine.side_effects)
          .next('td').html(medicine.incompliances);
        $(tableSelector).append(tr);
        tr = $trTempl2.clone();
        if(medicine.hours) $.each(medicine.hours, function(i, hour){
          tr.find('td:text('+hour+')').addClass("selected");
        });
        $(tableSelector).append(tr);
      });
    }

  } else alert("No prescription information");
});