var prescriptionObj = {"id":'', "date":'', 'notes':'', 'medicines':[]};

$(function () {
  var now=new Date();
  $( "#datepicker" ).datepicker({minDate: now, dateFormat: "yy-mm-dd"});
  $( "#datepicker" ).datepicker( "setDate", prescriptionObj.date ? prescriptionObj.date : now);
  if (prescriptionObj) {

    if(!prescriptionObj.medicines || prescriptionObj.medicines.length==0) {
      $('[name="save_perscription"]').attr("disabled", "disabled");
    } else $('[name="save_perscription"]').removeAttr("disabled");

    var tableSelector = 'table#prescription';
    var $tableDays = $(tableSelector + ' table.days');
    var tr1 = $tableDays.find('tr:first');
    var tr2 = $tableDays.find('tr:last');
    var tr;
    for (var day = 1; day <= 24; day++) {
      tr = (day <= 12) ? tr1 : tr2;
      tr.append('<td>' + day + '</td>');
    }
    var $trTempl1 = $(tableSelector + ' tr.templ1').clone().show();
    var $trTempl2 = $(tableSelector + ' tr.templ2').clone().show();
    $('[name="prescriptionId"]').val(prescriptionObj.id);

    if (prescriptionObj.notes) $('[name="notes"]').val(prescriptionObj.notes);

    if (prescriptionObj.medicines.length > 0) {
      $(tableSelector + ' tr:has(td)').remove();
      $.each(prescriptionObj.medicines, function (i, medicine) {
        var tr = $trTempl1.clone();
        tr.attr("id", medicine.id);
        var t = tr.find('td:eq(0)');
        t.html(medicine.name);
        t = t.next("td");
        t.find("div").html(medicine.description);
        t = t.next("td");
        t.find("div").html(medicine.side_effects);
        t = t.next("td");
        t.find("div").html(medicine.incompliances);
        $(tableSelector).append(tr);
        tr = $trTempl2.clone();
        tr.attr("id", medicine.id);
        var tr1 = tr.find('table.days tr:first');
        var tr2 = tr.find('table.days tr:last');
        if (medicine.days) {
          tr1.find("td:eq(0)").html(medicine.days);
        }
        if (medicine.hours) $.each(medicine.hours, function (i, h) {
          //tr.find('td:text(' + hour + ')').addClass("selected");
          var cell = (h <= 12) ? tr1.find("td:nth-child(" + (h + 1) + ")") : tr2.find("td:nth-child(" + (h - 12) + ")");
          cell.addClass("selected");

        });

        $(tableSelector).append(tr);
      });
      $(tableSelector + " tr.templ1").addClass("odd");
      $(tableSelector + " tr.templ2").addClass("even");
    }

  } else alert("No prescription information");

  $("form").submit(function(){
    var names = ["date", "notes"];
    var hasError = (!prescriptionObj.medicines || prescriptionObj.medicines.length==0);
    $.each(names, function (i, name) {
      var el = $('[name="' + name + '"]');
      if ($.trim(el.val()).length == 0) {
        el.val("").addClass("error");
        hasError = true;
      }
    });
    return !hasError;
  });
});