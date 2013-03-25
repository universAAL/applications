var isError = false;
$(function () {
  if (isError) $(".error_msg").show();
  $(":text, :password").change(function(){
    if($(this).hasClass("error") && $(this).val().trim().length>0) $(this).removeClass("error");
  });
  var names = ["username", "password"];
  $("form").submit(function () {
    var hasError = false;
    $.each(names, function (i, name) {
      var el = $('[name="' + name + '"]');
      if (el.val().trim().length == 0) {
        el.val("").addClass("error");
        hasError = true;
      }
    });
    return !hasError;
  });
});