var users = [];
$(function () {
  var selectSelector = 'select[name="user"]';
  var $optionTempl = $(selectSelector + '>option:first');

  $(selectSelector).empty();
  if (users) {
    $.each(users, function (i, user) {
      var op = $optionTempl.clone();
      op.val(user.id).html(user.name);
      $(selectSelector).append(op);
    });
  }
});