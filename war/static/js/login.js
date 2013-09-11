if (JSON.parse(localStorage.getItem('user')) != null) {
    $('form.login input[type="email"]').hide();
    $('form.login input[type="password"]').hide();
    $('form.login button:eq(0)').hide();
    $('form.login button:eq(1)').hide();
    $('#newUser').hide();
    $('nav li:eq(1)').show();
    var id = JSON.parse(localStorage.getItem('user')).id.split("@");
    $('form.login button.user').text("@" + id[0]).show();
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
    localStorage.removeItem('user');
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
    if (user === "" || pass === "") {
      $('div.modal.userError').modal("show");
      progressBarEnd();
    } else {
        $.ajax({
            url: '/app/users/',
            type: 'GET',
            beforeSend: function (xhr) {
              xhr.setRequestHeader('Authorization', makeBaseAuth(user, pass));
            },
            success: function (data) {
              $('form.login input[type="email"]').hide();
              $('form.login input[type="password"]').hide();
              $('form.login button:eq(0)').hide();
              $('#newUser').hide();
              data.pass = pass;
              localStorage.setItem("user", JSON.stringify(data));
              var id = user.split('@');
              $('form.login button.user').text("@" + id[0]).show();
              $('#myBookings').show();
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


  function getUser () {
    var testObject = { 'one': 1, 'two': 2, 'three': 3 };
    // Put the object into storage
    localStorage.setItem('testObject', JSON.stringify(testObject));
    // Retrieve the object from storage
    var retrievedObject = localStorage.getItem('testObject');
    console.log('retrievedObject: ', JSON.parse(retrievedObject));
  }

  function makeBaseAuth(user, password) {
    var tok = user + ':' + password;
    var hash = btoa(tok);
  return "Basic " + hash;
}

  function progressBarStart(){
    $('.progressBar').remove();
    $('.navbar').after('<div class="progressBar"><div class="progress progress-striped active title"><div class="bar" ></div></div><p align="center">Solicitando datos....contactando con el servidor........esto puede durar un tiempo....</p></div>');
      var cont = 0;
      for (i = 0; i < 3000; i++)
        {
        $('.progress .bar').css({'width': cont});
          cont = cont+0.3;
        }
    }

  function progressBarEnd(){
    $('.progressBar').remove();
  }