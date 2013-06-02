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

  $('form.newUser input[type="password"]:eq(0)').on('keyup', function() {
    checkPass();
  });

  $('form.newUser input[type="password"]:eq(1)').on('keyup', function() {
   checkPass();
  });

  function checkPass() {
    var ok = false;
    var p = $('form.newUser input[type="password"]:eq(0)').val();
    var rp = $('form.newUser input[type="password"]:eq(1)').val();
      //checks if the pass is secure enought
     if ( p.length < 5  || p == "") {
        $('form.newUser i:eq(0)').removeClass('icon-ok');
        $('form.newUser i:eq(0)').addClass('icon-remove');
        $('form.newUser li:eq(5) p').remove();
        $('form.newUser li:eq(5)').append('<p style="color:red;">La contraseña no es lo suficientemente segura. Ha de tener al menos cinco carcteres.</p>');
        $('form.newUser li:eq(6)').hide();
      } else {
          $('form.newUser li:eq(6)').hide();
          $('form.newUser li:eq(5) p').remove();
          $('form.newUser i:eq(0)').removeClass('icon-remove');
          $('form.newUser i:eq(0)').addClass('icon-ok');
          $('form.newUser li:eq(6)').show();
          //checks if the passwords are the same
          if ( p != rp  ) {
              $('form.newUser i:eq(1)').removeClass('icon-ok');
              $('form.newUser i:eq(1)').addClass('icon-remove');
              $('form.newUser li:eq(6) p').remove();
              $('form.newUser li:eq(6)').append('<p style="color:red;">¡Las contraseñas no coinciden!</p>');
          } else {
              $('form.newUser li:eq(6) p').remove();
              $('form.newUser i:eq(1)').removeClass('icon-remove');
              $('form.newUser i:eq(1)').addClass('icon-ok');
              ok = true;
          }
        }
    return ok;
  }

  $('div.modal.newUser button.btn-success').click(function() {
    postNewUser();
  });

  function newUserModalShow() {
    $('div.modal.newUser').modal('show');
  }

  function constructingModalShow() {
    $('div.modal.constructing').modal("show");
  }

  function isEmail(email) {
    var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return regex.test(email);
}

  function postNewUser() {
     var newUser = {
        userName: $('form.newUser input:eq(0)').val(),
        userLastName: $('form.newUser input:eq(1)').val(),
        userId: $('form.newUser input[type="email"]').val(),
        userPass: $.md5($('form.newUser input[type="password"]:eq(0)').val()),
        userAge: $('form.newUser input[type="number"]').val(),
        userSex: $('form.newUser select').val()
         };
         if  ( (newUser.userName && newUser.userLastName && newUser.userPass && newUser.userAge && newUser.userSex != "") && checkPass() == true && isEmail(newUser.userId) ) {
             $('div.modal.newUser').modal('hide');
             progressBarStart();
             $.ajax({
                  url: '/app/Users',
                  type: 'POST',
                  data: JSON.stringify(newUser),
                  contentType: 'application/json; charset=utf-8',
                  async: false,
                  success: function(data, textStatus, jqXHR) {
                    $('div.modal.welcome div:eq(1)').append('<p>Te hemos enviado un correo a la dirección: '  + newUser.userId + ' . Por favor confirma desde tu correo que quieres unirte a nosotros.</p>');
                    $('div.welcome').modal('show');
                    $('form.newUser')[0].reset();
                    progressBarEnd();
                  },
                  error: function(jqXHR, textStatus, errorThrown){
                    alert("!Ha ocurrido un error! Por favor, vuelve a intentarlo." + jqXHR.responseText);
                    $('div.newUser').modal('hide');
                    $('form.newUser')[0].reset();
                    progressBarEnd();
                  }
            });
         } else {
          alert("¡Por favor, no dejes ningún campo vacío e introduce una dirección de correo válida!");
         }
  }
});