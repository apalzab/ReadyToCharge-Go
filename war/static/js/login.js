if ($.cookie('ReadyToChargeAndGo') != null) {
    var str = $.cookie('ReadyToChargeAndGo');
    var ret = str.split(':');
    var id = ret[0];
    var pass = ret[1];
    $('form.login input[type="email"]').hide();
    $('form.login input[type="password"]').hide();
    $('form.login button:eq(0)').hide();
    $('form.login button:eq(1)').hide();
    id = id.split('@');
    $('#newUser').hide();
    $('form.login button.user').text("@"+id[0]).show();
  } else {
      $('form.login input[type="email"]').show();
      $('form.login input[type="password"]').show();
      $('form.login button:eq(0)').show();
      $('nav li:eq(0)').show();
    }

  $('nav button:eq(0)').click(function(e) {
    e.preventDefault();
    user = $('form.login input[type="email"]').val();
    pass = $('form.login input[type="password"]').val();
    pass = $.md5(pass);
    loginUser(user, pass);
  });

  $('form.login .modifyUser').click(function() {
    $('div.modal.constructing').modal('show');
  });

  $('form.login .logOut').click(function() {
    $.removeCookie('ReadyToChargeAndGo');
    $('form.login button:eq(1)').val("").hide();
    $('form.login input[type="email"]').val("").show('slow');
    $('form.login input[type="password"]').val("").show('slow');
    $('form.login button:eq(0)').show('slow');
    $('li.newUser').show('slow');
    var url = "/";
    $(location).attr('href',url);
  });

  function loginUser(user,pass) {
    progressBarStart();
    if (user==="" || pass==="") {
      $('div.modal.userError').modal("show");
      progressBarEnd();
    } else {
        var tok = user + ':' + pass;
        var hash = btoa(tok);
        $.cookie.raw = true;
        $.cookie('ReadyToChargeAndGo', tok, { expires: 7 });
        $.ajax({
            url: '/app/Users/',
            type: 'GET',
            success: function (data) {
              $('form.login input[type="email"]').hide();
              $('form.login input[type="password"]').hide();
              $('form.login button:eq(0)').hide();
              $('#newUser').hide();
              var str = $.cookie('ReadyToChargeAndGo');
              var ret = str.split(':');
              var id = ret[0];
              id = id.split('@');
              $('form.login button.user').text("@"+id[0]).show();
              progressBarEnd();
              },
            error: function(){
              $('div.modal.userError').modal('show');
              $('form.login input.username').show('slow');
              $('form.login input[type="password"]').show('slow');
              $.removeCookie('ReadyToChargeAndGo');
              progressBarEnd();
            }
        });
      }
    return false;
  }

  function progressBarStart(){
    $('.progressBar').remove();
    $('.navbar').after('<div class="progressBar"><div class="progress progress-striped active title"><div class="bar" ></div></div><p align="center">Solicitando datos....contactando con el servidor........esto puede durar un tiempo....</p></div>');
      var cont=0;
      for (i=0; i<3000; i++)
        {
        $('.progress .bar').css({'width': cont});
          cont=cont+0.3;
        }
    }

  function progressBarEnd(){
    $('.progressBar').remove();
  }