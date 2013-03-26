var medicineObj={'id':'', 'name':'', 'description':'', 'side_effects':'', "incompliances":'', 'days':0, 'hours':[]};
var isNew=true;
$(function () {
  if(!isNew) {
    $("title").html("universALL - Medication Medical Service: Edit Medicine");
    $("h2").html("Edit Medicine");
  }



  var $tableDays = $('table.days');
  var tr1 = $tableDays.find('tr:first');
  var tr2 = $tableDays.find('tr:last');
  var tr;
  for (var day = 1; day <= 24; day++) {
    tr = (day <= 12) ? tr1 : tr2;
    tr.append('<td>' + day + '</td>');
  }
   if (medicineObj.name) {
     $('[name="name"]').val(medicineObj.name);
     $('[name="description"]').val(medicineObj.description);
     $('[name="side_effects"]').val(medicineObj.side_effects);
     $('[name="incompliances"]').val(medicineObj.incompliances);
     $('[name="days"]').val(medicineObj.days);
     $.each(medicineObj.hours, function(i, h){
      var cell=(h<=12) ? tr1.find("td:nth-child("+(h+1)+")") : tr2.find("td:nth-child("+(h-12)+")");
      cell.addClass("selected");
     });
     /*
        var tr = $trTempl1.clone();
        tr.attr("id", medicine.id);
        tr.find('td:eq(0)').html(medicine.name)
          .next('td').html(medicine.description)
          .next('td').html(medicine.side_effects)
          .next('td').html(medicine.incompliances);
        $(tableSelector).append(tr);
        tr = $trTempl2.clone();
        tr.attr("id", medicine.id);
        if (medicine.hours) $.each(medicine.hours, function (i, hour) {
          tr.find('td:text(' + hour + ')').addClass("selected");
        });
        $(tableSelector).append(tr);
         */


  }
});