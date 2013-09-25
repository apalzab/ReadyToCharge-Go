$(document).ready(function () {
    isLogged();
    $('#login-modal').click(function() {
      user = $('.modal.login form input[type="email"]').val();
      pass = $('.modal.login form input[type="password"]').val();
      pass = $.md5(pass);
      loginUser(user, pass);
      return false;
    });

    $('#log-out').click(function () {
        localStorage.removeItem('user');
        var url = "/";
        $(location).attr('href',url);
    });

    $('.modal.error-login button').click(function() {
      $('.modal.error-login').modal("hide");
      $('.modal.login').modal("show");
    });

    function loginUser(user, pass) {
        if (user === "" || pass === "") {
          $('.modal.error-login').modal("show");
          progressBarEnd();
        } else {
            $.ajax({
                url: '/app/users/',
                type: 'GET',
                beforeSend: function (xhr) {
                  xhr.setRequestHeader('Authorization', makeBaseAuth(user, pass));
                  $('.modal.login').modal("hide");
                  $('section').hide();
                  progressBarStart();
                },
                success: function (data) {
                    data.pass = pass;
                    localStorage.setItem("user", JSON.stringify(data));
                    var id = user.split('@');
                    $('.modal.login').modal('hide');
                    $('form.login button.user').text("@" + id[0]).show();
                    var url = "ready-to-charge-go.html";
                    $(location).attr('href',url);
                    progressBarEnd();
                  },
                error: function(){
                  $('.modal.login').modal("hide");
                  $('.modal.error-login').modal("show");
                  $('section').show();
                  $.removeCookie('ReadyToChargeAndGo');
                  progressBarEnd();
                }
            });
          }
      }

});

    function isLogged() {
      if (JSON.parse(localStorage.getItem('user')) != null) {
        var id = JSON.parse(localStorage.getItem('user')).id.split("@");
        $('.navbar .navbar-right .dropdown').show();
        $('.navbar .navbar-nav li:eq(0)').show();
        $('.navbar .navbar-nav li:eq(1)').show();
        $('.navbar .navbar-right li:eq(0)').hide();
        $('.navbar .navbar-right li:eq(1)').hide();
        $('#dropdown-text').text(id[0]);
        } else {
            $('.navbar .navbar-right .dropdown').hide();
            var current_path = location.pathname;
            if (current_path != "/") {
                var url = "/";
                $(location).attr('href',url);
            }
            $('.navbar .navbar-right li:eq(0)').show();
            $('.navbar .navbar-right li:eq(1)').show();
            $('.navbar .navbar-right .dropdown').hide();
            $('.navbar .navbar-nav li:eq(0)').hide();
            $('.navbar .navbar-nav li:eq(1)').hide();
         }
      }

     function makeBaseAuth(user, password) {
        var tok = user + ':' + password;
        var hash = btoa(tok);
    return "Basic " + hash;
    }

    function progressBarStart(){
      $('#progress-bar').remove();
      $('.navbar').after('<section id="progress-bar" class="progresss"><div class="col-lg-12"><progress max="100"></progress></div></section>');
    }

    function progressBarEnd(){
        $('#progress-bar').remove();
    }