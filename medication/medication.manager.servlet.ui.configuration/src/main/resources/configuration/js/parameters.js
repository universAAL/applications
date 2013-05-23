var parameters = {};//all params

function getValueElement(value, type) {
    var el = "";
    switch (type) {
        case "number":
            el = $('<input type="text" class="number" size="3"/>');
            el.val(value);
            break;
        case "boolean":
            el = $('<input type="checkbox" value="true"/>');
            el.attr("checked", value);
            break;
        default:
            el = $('<input type="text" class="ui-widget-content"/>');
            el.val(value);
    }
    return el;
}
$(function () {



    var tableSelector = 'table';
    var templSelector = tableSelector + ' tr.templ';
    if ($.isEmptyObject(parameters)){
        $(templSelector).remove();
        $("input:submit").hide();
        return;
    }
    var $trTempl = $(templSelector).clone(true).show();

    $(tableSelector + ' tr:has(td)').remove();
    $.each(parameters, function (id, parameter) {
        var tr = $trTempl.clone(true);
        var vEl = getValueElement(parameter["value"], parameter["type"]);
        vEl.attr("name", id);
        tr.find('td:eq(0)').html(parameter["name"])
            .next('td').html(vEl)
            .next('td').html(parameter["format"])
            .next('td').html(parameter["description"]);

        $(tableSelector).append(tr);

    });
    $('.number').spinner({
        min:1,
        change:function (event, ui) {
            var el = $(this);
            if (el.hasClass("error") && el.spinner("value") > 0) {
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
            if (!(el.spinner("value") > 0)) {
                hasError = true;
                el.addClass("error");
            }
        });
        return !hasError;
    });
});