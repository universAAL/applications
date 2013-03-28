var isNew=true;
var meal={'any':'Any time', 'before':'Before meal', 'with_meal': 'With meal', 'after':'After meal'};
var medicineObj={'id':'','prescriptionId':'', 'name':'', 'description':'', 'side_effects':'', "incompliances":'',
  'days':0, 'dose':0,'unit':'pills', 'meal_relation':'any' , 'hours':[]};

$(function () {

  if(!isNew) {
    $(this).attr("title", "universALL - Medication Medical Service: Edit Medicine");
    $("h2").html("Edit Medicine");
  }

  $('[name="id"]').val(medicineObj.id);
  $('[name="prescriptionId"]').val(medicineObj.prescriptionId);
  var s=$('select[name="meal_relation"]');
  $.each(meal, function(val, name){
    s.append('<option value="'+val+'">'+name+'</option>');
  });

  var spinnerDays = $('[name="days"]').spinner({min: 1,
    change: function( event, ui ) {
      var el=$('[name="days"]');
      if(el.hasClass("error") && spinnerDays.spinner( "value" )>0) {
        el.removeClass("error");
      }
    }
    });
  var spinnerDose = $('[name="dose"]').spinner({min: 1,
    change: function( event, ui ) {
      var el=$('[name="dose"]');
      if(el.hasClass("error") && spinnerDose.spinner( "value" )>0) {
        el.removeClass("error");
      }
    }
  });

  var $tableDays = $('table.days');
  var tr1 = $tableDays.find('tr:first');
  var tr2 = $tableDays.find('tr:last');
  var tr;
  for (var day = 1; day <= 24; day++) {
    tr = (day <= 12) ? tr1 : tr2;
    tr.append('<td><input type="checkbox" name="hours" value="'+day+'"/>' + day + '</td>');
  }


   if (medicineObj.name) {
     $('[name="name"]').val(medicineObj.name);
     $('[name="description"]').val(medicineObj.description);
     $('[name="side_effects"]').val(medicineObj.side_effects);
     $('[name="incompliances"]').val(medicineObj.incompliances);
     spinnerDays.spinner("value", medicineObj.days);//$('[name="days"]').val(medicineObj.days);
     spinnerDose.spinner("value", medicineObj.dose);//$('[name="dose"]').val(medicineObj.dose);
     $('[id="'+medicineObj.unit+'"][name="unit"]').attr("checked", "checked");
     s.val(medicineObj.meal_relation);
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
      $tableDays.find("th").removeClass("error");
    }
  });

  $(":text").change(function(){
    if($(this).hasClass("error") && $.trim($(this).val()).length>0) $(this).removeClass("error");
  });

  $("form").submit(function () {
    var names = ["name", "id", "days", "dose"];
    var hasError = false;
    $.each(names, function (i, name) {
      var el = $('[name="' + name + '"]');
      if ($.trim(el.val()).length == 0) {
        el.val("").addClass("error");
        hasError = true;
      }
    });
    if($tableDays.find(":checkbox:checked").size()==0){
      hasError = true;
      $tableDays.find("th").addClass("error");
    }
    if(!(spinnerDays.spinner( "value" )>0)) {
      hasError = true;
      $('[name="days"]').addClass("error");
    }
    if(!(spinnerDose.spinner( "value" )>0)) {
      hasError = true;
      $('[name="dose"]').addClass("error");
    }
    return !hasError;
  });
});