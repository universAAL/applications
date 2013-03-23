var users = [];
$(function () {
  var selectSelector = 'select[name="user"]';
  var $optionTempl = $(selectSelector + '>option:first');
  $('button[name="back"]').click(function () {
    history.back();
  });
  $(selectSelector).empty();
  if (users) {
    $.each(users, function (i, user) {
      var op = $optionTempl.clone();
      op.val(user.id).html(user.name);
      $(selectSelector).append(op);
    });
  }
});