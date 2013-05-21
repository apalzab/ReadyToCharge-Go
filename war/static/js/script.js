$(document).ready(function(){
$('button.login').click(function(e){
  e.preventDefault();
  user = $('form.login input.username').val();
  pass = $('form.login input[type="password"]').val();
  pass = $.md5(pass);
  loginUser(user, pass);
});

$('li.newUser').click(newUserModalShow);

$('div.index img.socketImg').click(function(e){
  e.preventDefault();
  if ($.cookie('ReadyToChargeAndGo') != null)
    {
      var url = "/ReadyToCharge.html";
      $(location).attr('href',url);
    }
    else
    {
      $('div.modal.login').modal('show');
    }
});

$('div.index img.carImg').click(function(){
  $('div.modal.constructing').modal('show');
});

    function newUserModalShow(){
      $('div.newUser').modal('show');
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