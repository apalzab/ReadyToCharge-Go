$(document).ready(function(){

if ($.cookie('ReadyToChargeAndGo') != null)
  {
    var str = $.cookie('ReadyToChargeAndGo');
    var ret = str.split(':');
    var id = ret[0];
    var pass = ret[1];
    $('form.login input.username').hide();
    $('form.login input[type="password"]').hide();
    loginUser(id, pass);
  }


$('button.login').click(function(e){
  e.preventDefault();
  user = $('form.login input.username').val();
  pass = $('form.login input[type="password"]').val();
  pass = $.md5(pass);
  loginUser(user, pass);
});

$('li.newUser').click(newUserModalShow);

$('div.start img.socketImg').click(readyToCharge);

$('div.start img.carImg').click(constructingModalShow);

    function loginUser(user,pass){
      if (user==="" || pass==="")
        $('div.modal.userError').modal("show");
      else
        {
          var tok = user + ':' + pass;
          var hash = btoa(tok);
          $.cookie.raw = true;
          $.cookie('ReadyToChargeAndGo', tok, { expires: 7 });
          $.ajax({
              //headers: {'Authorization': "Basic " +  hash},
              url: '/app/Users/',
              type: 'GET',
              success: function (data) {
                $('form.login input.username').addClass("hide");
                $('form.login input[type="password"]').addClass("hide");
                $('form.login button.login').removeClass("login");
                $('form.login button').addClass("userInfo");
                $('#newUser').hide();
                $('form.login button.userInfo').text("@"+data.userName);
                }
          });
        }
      return false;
    }

    function newUserModalShow(){
      $('div.newUser').modal('show');
    }

    function readyToCharge(){
      //verify that the user is loged and if so redirect to ReadyToCharge.html.
    }

    function constructingModalShow(){
      $('div.constructing').modal("show");
    }

});

    function postNewUser(){
       var newUser = {
          userName: $('form.newUser input.userName').val(),
          userLastName: $('form.newUser input.userLastName').val(),
          userId: $('form.newUser input.userId').val(),
          userPass: $.md5($('form.newUser input.userPass').val()),
          userAge: $('form.newUser input.userAge').val(),
          userSex: $('form.newUser select.userSex').val()
           };

       $.ajax({
          url: '/app/Users',
          type: 'POST',
          data: JSON.stringify(newUser),
          contentType: 'application/json; charset=utf-8',
          async: false,
          success: function(data,textStatus,jqXHR) {
            alert(jqXHR.responseText);
            $('div.newUser').modal('hide');
            $('form.newUser')[0].reset();
          },
          error: function(jqXHR,textStatus,erroThrown){
            alert(jqXHR.responseText);
            $('div.newUser').modal('hide');
            $('form.newUser')[0].reset();
          }
        });

      }