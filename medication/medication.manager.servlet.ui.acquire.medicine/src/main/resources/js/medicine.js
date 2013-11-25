var medicines = [], available_medicines = [],
    spinnerQuantity;
$(function () {
    var t = $("table");
    if (available_medicines && available_medicines.length > 0) {
        $.each(available_medicines, function (i, m) {
            var tr = "<tr><td>" + m.name + "</td><td>" + m.qty + "</td><td>" + m.unit + "</td></tr>";
            t.append(tr);
        });
    } else{
        var tr="<td colspan='3'>No available medicines.</td>";
        t.append(tr);
    }
        var selectSelector = 'select[name="medicine"]';
    var $optionTempl = $(selectSelector + '>option:first');
    var select = $(selectSelector);
    select.empty().change(function () {
        $('label[name="unit"]').html($(this).children(":selected").data("medicine").unit);
    })
    if (medicines) {
        $.each(medicines, function (i, med) {
            var op = $optionTempl.clone();
            op.val(med.id).html(med.name).data("medicine", med);
            select.append(op);
        });
    }
    select.change();

    spinnerQuantity = $('[name="quantity"]').val("1").spinner({min:1,
        change:function (event, ui) {
            var el = $('[name="quantity"]');
            if (el.hasClass("error") && spinnerQuantity.spinner("value") > 0) {
                el.removeClass("error");
            }
            if (!(spinnerQuantity.spinner("value") > 0)) {
                el.addClass("error");
            }

        }
    });
});