$(document).ready(function(){

$('button.login').click(loginUser);

$('li.newUser').click(newUserModalShow);

$('div.start img.socketImg').click(readyToCharge);

$('div.start img.carImg').click(constructingModalShow);

    function loginUser() {
      var user = $('form.login input:eq(0)').val();
      var pass = $('form.login input[type="password"]').val();
      pass = $.md5(pass);
      //var form = $('form.login').serialize();
      //console.log(form);
      if (user === "" || pass === "") {
        $('div.modal.userError').modal("show");
      } else {
          $.ajax({
              url: '/app/Users/' + user + '/' + pass,
              type: 'GET',
              success: function (data) {
                $('form.login input.username').addClass("hide");
                $('form.login input[type="password"]').addClass("hide");
                $('form.login button.login').removeClass("login");
                $('form.login button').addClass("userInfo");
                $('form.login button.userInfo').text("@"+data.userName);
              }
          });
        }
      return false;
    }

    function newUserModalShow() {
      $('div.newUser').modal('show');
    }

    function readyToCharge() {
      //verify that the user is loged and if so redirect to ReadyToCharge.html.
    }

    function constructingModalShow() {
      $('div.constructing').modal("show");
    }

});

    function postNewUser() {
      var user = {
          id: $('form.newUser input[type="email"]').val(),
          name: $('form.newUser input:eq(0)').val(),
          last_name: $('form.newUser input:eq(1)').val(),
          pass: $.md5($('form.newUser input[type="password"]').val()),
          age: $('form.newUser input[type="number"]').val(),
          sex: $('form.newUser select').val()
      };

       $.ajax({
          url: '/app/Users',
          type: 'POST',
          data: JSON.stringify(user),
          contentType: 'application/json; charset=utf-8',
          async: false,
          success: function(data, textStatus, jqXHR) {
            alert(jqXHR.responseText);
            $('div.newUser').modal('hide');
            $('form.newUser')[0].reset();
          },
          error: function(jqXHR, textStatus, erroThrown){
            alert(jqXHR.responseText);
            $('div.newUser').modal('hide');
            $('form.newUser')[0].reset();
          }
        });
    }
