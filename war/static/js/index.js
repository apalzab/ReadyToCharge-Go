$(document).ready(function(){
    $('html, body').animate({ scrollTop: 0 }, 0);

    $('#sign-in-nav').click(function() {
        $('.new-user').modal('show');
    });

    $('#sign-in-bottom').click(function() {
        $('.new-user').modal('show');
    });

    $('#login-nav').click(function() {
        $('.login').modal('show');
    });

    $('#login-bottom').click(function() {
        $('.login').modal('show');
    });


  $('.modal.new-user input[type="password"]:eq(0)').on('keyup', function() {
    checkPass();
  });

  $('.modal.new-user input[type="password"]:eq(1)').on('keyup', function() {
   checkPass();
  });

  function checkPass() {
    var ok = false;
    var p = $('.modal.new-user input[type="password"]:eq(0)').val();
    var rp = $('.modal.new-user input[type="password"]:eq(1)').val();
      //checks if the pass is secure enought
     if ( p.length < 5  || p == "") {
        $('.modal.new-user li:eq(3) p').remove();
        $('.modal.new-user li:eq(3)').append('<p style="color:red;">La contraseña no es lo suficientemente segura. Ha de tener al menos cinco carcteres.</p>');
        $('.modal.new-user li:eq(4)').hide();

// <i class="glyphicon glyphicon-remove">

      } else {
          $('.modal.new-user li:eq(4)').hide();
          $('.modal.new-user li:eq(3) p').remove();
          $('.modal.new-user li:eq(4)').show();
          //checks if the passwords are the same
          if ( p != rp  ) {
              $('.modal.new-user li:eq(4) p').remove();
              $('.modal.new-user li:eq(4)').append('<p style="color:red;">¡Las contraseñas no coinciden!</p>');
          } else {
              $('.modal.new-user li:eq(4) p').remove();
              ok = true;
          }
        }
    return ok;
  }

    $('#create-user').click(function() {
        postNewUser();
  });


  function postNewUser() {
     var newUser = {
        name: $('.modal.new-user input:eq(0)').val(),
        lastName: $('.modal.new-user input:eq(1)').val(),
        id: $('.modal.new-user input[type="email"]').val(),
        pass: $.md5($('.modal.new-user input[type="password"]:eq(0)').val())
         };
         if  ( (newUser.name && newUser.lastName && newUser.pass != "") && checkPass() == true && isEmail(newUser.id) ) {
             $('.modal.new-user').modal('hide');
             progressBarStart();
             $.ajax({
                  url: '/app/users',
                  type: 'POST',
                  data: JSON.stringify(newUser),
                  contentType: 'application/json; charset=utf-8',
                  async: false,
                  success: function(data, textStatus, jqXHR) {
                      $('.modal.welcome .modal-body').html('<p>' + jqXHR.responseText + '</p>');
                      $('.modal.welcome').modal('show');
                      $('.modal.new-user .modal-body')[0].reset();
                      $('.modal.new-user li:eq(6)').hide();
                      $('.modal.new-user i:eq(0)').removeClass('glyphicon glyphicon-ok');
                      $('.modal.new-user i:eq(0)').addClass('glyphicon glyphicon-remove');
                      progressBarEnd();
                  },
                  error: function(jqXHR, textStatus, errorThrown){
                    alert("!Ha ocurrido un error! Por favor, vuelve a intentarlo." + jqXHR.responseText);
                    $('.modal.new-user').modal('hide');
                    $('.modal.new-user .modal-body')[0].reset();
                    progressBarEnd();
                  }
            });
         } else {
          alert("¡Por favor, no dejes ningún campo vacío e introduce una dirección de correo válida!");
         }
  }


  $('.header ul li').click(function() {
    var scroll_to = $(this).data('scroll');
    $('html,body').animate({scrollTop: $(scroll_to).position().top},'slow');
  });

  function isEmail(email) {
   var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  return regex.test(email);
  }

});



