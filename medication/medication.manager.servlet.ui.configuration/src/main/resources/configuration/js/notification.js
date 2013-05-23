var rules = {};


$(function () {
    var tableSelector = 'table';
    var templSelector = tableSelector + ' tr.templ';
    if ($.isEmptyObject(rules)){
        $(templSelector).remove();
        $("input:submit").hide();
        return;
    }


    var $trTempl = $(templSelector).clone(true).show();

    $(tableSelector + ' tr:has(td)').remove();
    $.each(rules, function (id, rule) {
        var tr = $trTempl.clone(true);
        tr.find('td:eq(0)').html(rule["patient"])
            .next('td').html(rule["medicine"]);
        tr.find('[name="missed"]').attr("checked", rule["missed"]);
        tr.find('[name="threshold"]').val(rule["threshold"]);
        tr.find('[name="shortage"]').attr("checked", rule["shortage"]);
        tr.find('[name="dose"]').attr("checked", rule["dose"]);
        $.each(tr.find('[name]'), function (i, el) {
            $(el).attr("name", id +":"+ $(el).attr("name"));
        });
        $(tableSelector).append(tr);
    });
    $('.number').spinner({
        min:0,
        change:function (event, ui) {
            var el = $(this);
            if (el.hasClass("error") && el.spinner("value") >= 0) {
                el.removeClass("error");
            }
        }
    });
    $(tableSelector + " tr:odd").addClass("odd");
    $(tableSelector + " tr:even").addClass("even");
    $(tableSelector + " tr:first").removeClass(" odd even");

    $("form").submit(function () {
        var hasError = false;
        $.each($(".number"), function (i, element) {
            var el = $(element);
            if (!(el.spinner("value") >= 0)) {
                hasError = true;
                el.addClass("error");
            }
        });
        return !hasError;
    });
});