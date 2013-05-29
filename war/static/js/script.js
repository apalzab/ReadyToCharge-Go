$(document).ready(function() {

  $('nav li:eq(0)').click(newUserModalShow);

  $('div.index img.socketImg').click(function(e) {
    e.preventDefault();
    if ($.cookie('ReadyToChargeAndGo') != null) {
        var url = "/ready_to_charge.html";
        $(location).attr('href',url);
      } else {
        $('div.modal.login').modal('show');
      }
  });

  $('div.index img.carImg').click(function() {
    $('div.modal.constructing').modal('show');
  });

  $('div.modal.newUser button.btn-success').click(function() {
    postNewUser();
  });

  function newUserModalShow() {
    $('div.modal.newUser').modal('show');
  }

  function constructingModalShow() {
    $('div.modal.constructing').modal("show");
  }

  function postNewUser() {
     var newUser = {
        userName: $('form.newUser input:eq(0)').val(),
        userLastName: $('form.newUser input:eq(1)').val(),
        userId: $('form.newUser input[type="email"]').val(),
        userPass: $.md5($('form.newUser input[type="password"]').val()),
        userAge: $('form.newUser input[type="number"]').val(),
        userSex: $('form.newUser select').val()
         };

     $.ajax({
            url: '/app/Users',
            type: 'POST',
            data: JSON.stringify(newUser),
            contentType: 'application/json; charset=utf-8',
            async: false,
            success: function(data, textStatus, jqXHR) {
              alert(jqXHR.responseText);
              $('div.newUser').modal('hide');
              $('form.newUser')[0].reset();
            },
            error: function(jqXHR, textStatus, errorThrown){
              alert(jqXHR.responseText);
              $('div.newUser').modal('hide');
              $('form.newUser')[0].reset();
            }
    });
  }
});