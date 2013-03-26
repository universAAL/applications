var medicineObj={'id':'','prescriptionId':'', 'name':'', 'description':'', 'side_effects':'', "incompliances":'', 'days':0, 'hours':[]};
var isNew=true;

$("form").submit(function () {
  var names = ["name", "id", "days"];
  var hasError = false;
  $.each(names, function (i, name) {
    var el = $('[name="' + name + '"]');
    if ($.trim(el.val()).length == 0) {
      el.val("").addClass("error");
      hasError = true;
    }
  });
  return !hasError;
});

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
    tr.append('<td><input type="checkbox" name="hours" value="'+day+'"/>' + day + '</td>');
  }
   if (medicineObj.name) {
     $('[name="id"]').val(medicineObj.id);
     $('[name="prescriptionId"]').val(medicineObj.prescriptionId);
     $('[name="name"]').val(medicineObj.name);
     $('[name="description"]').val(medicineObj.description);
     $('[name="side_effects"]').val(medicineObj.side_effects);
     $('[name="incompliances"]').val(medicineObj.incompliances);
     $('[name="days"]').val(medicineObj.days);
     $.each(medicineObj.hours, function(i, h){
      var cell=(h<=12) ? tr1.find("td:nth-child("+(h+1)+")") : tr2.find("td:nth-child("+(h-12)+")");
      cell.addClass("selected");
      cell.find(":checkbox").attr("checked", true);
     });
   }
  $tableDays.find("td").click(function(e){
    e.stopPropagation();
    e.preventDefault();
    if($(this).hasClass("selected")) {
      $(this).removeClass("selected");
      $(this).find(":checkbox").removeAttr("checked");
    } else {
      $(this).addClass("selected");
      $(this).find(":checkbox").attr("checked", true);
    }

  });
});