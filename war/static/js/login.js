if ($.cookie('ReadyToChargeAndGo') != null)
  {
    var str = $.cookie('ReadyToChargeAndGo');
    var ret = str.split(':');
    var id = ret[0];
    var pass = ret[1];
    $('form.login input.username').hide();
    $('form.login input[type="password"]').hide();
    $('form.login button.login').hide();
    loginUser(id, pass);
  }

  else
  {
    $('form.login input.username').show();
    $('form.login input[type="password"]').show();
    $('form.login button.login').show();
    $('li.newUser').show();
  }

$('form.login button.user').click(function(e){
  e.preventDefault();
});

$('form.login .modifyUser').click(function(){
  $('div.modal.constructing').modal('show');
});

$('form.login .logOut').click(function(){
  $.removeCookie('ReadyToChargeAndGo');
  $('form.login button.user').val("").hide();
  $('form.login input.username').val("").show('slow');
  $('form.login input[type="password"]').val("").show('slow');
  $('form.login button.login').show('slow');
  $('li.newUser').show('slow');
});

    function loginUser(user,pass){
      progressBarStart();
      if (user==="" || pass==="")
        $('div.modal.userError').modal("show");
      else
        {
          var tok = user + ':' + pass;
          var hash = btoa(tok);
          $.cookie.raw = true;
          $.cookie('ReadyToChargeAndGo', tok, { expires: 7 });
          $.ajax({
              url: '/app/Users/',
              type: 'GET',
              success: function (data) {
                $('form.login input.username').hide();
                $('form.login input[type="password"]').hide();
                $('form.login button.login').hide();
                $('#newUser').hide();
                $('form.login button.user').text("@"+data.userName).show();
                progressBarEnd();
                },
              error: function(){
                $('div.modal.userError').modal('show');
                $('form.login input.username').val("").show('slow');
                $('form.login input[type="password"]').val("").show('slow');
                $('form.login button.login').show('slow');
                progressBarEnd();
              }
          });
        }
      return false;
    }

function progressBarStart(){
$('#nav').after('<div id="progressBar"><div class="progress progress-striped active title"><div class="bar" ></div></div><p align="center">Solicitando datos....contactando con el servidor........esto puede durar un tiempo....</p></div>');
  var cont=0;
   for (i=0; i<3000; i++)
      {

      $('.progress .bar').css({
                'width': cont
              });
        cont=cont+0.3;
      }
  }

function progressBarEnd(){
  $('#progressBar').remove();
}