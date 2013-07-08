var intakes = {};//all params

$(function () {

    var tableSelector = 'table';
    var templSelector = tableSelector + ' tr.templ';
    if ($.isEmptyObject(intakes)){
        $(templSelector).remove();
        $("input:submit").hide();
        return;
    }
    var $trTempl = $(templSelector).clone(true).show();

    $(tableSelector + ' tr:has(td)').remove();
    $.each(intakes, function (id, intake) {
        var tr = $trTempl.clone(true);
        tr.find('td:eq(0)').html(intake["date"])
            .next('td').html(intake["time"])
            .next('td').html(intake["medication"])
            .next('td').html(intake["status"]);

        $(tableSelector).append(tr);

    });
    $(tableSelector + " tr:odd").addClass("odd");
    $(tableSelector + " tr:even").addClass("even");
    $(tableSelector + " tr:first").removeClass(" odd even");


});