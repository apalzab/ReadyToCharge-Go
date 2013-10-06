$(document).ready(function() {

    init();

    function init() {
        if (JSON.parse(localStorage.getItem('user')) != null) {
            var user = JSON.parse(localStorage.getItem('user'));
            $("#basic-info-template").tmpl(user).appendTo(".basic-info");
            $("#more-info-template").tmpl(user).appendTo(".more-info");
            $("#contract-info-template").tmpl(user).appendTo(".contract-info");
            $("#public-info-template").tmpl(user).appendTo(".public-info");

            //USE a template to render the data

        }
    }

    $('.box a ').click(function() {
        //alert('eeee' + modal);
        if (JSON.parse(localStorage.getItem('user')) != null) {
            var user = JSON.parse(localStorage.getItem('user'));
            var modal = $(this).data('modify');
            if (modal == "basic-info") {
                $('.modal.basic .modal-body ul').html('');
                $("#basic-info-modal-template").tmpl(user).appendTo(".modal.basic .modal-body");
                $('.modal.basic').modal('show');
            }
            if (modal == "more-info") {
                $('.modal.more .modal-body ul').html('');
                $("#more-info-modal-template").tmpl(user).appendTo(".modal.more .modal-body");
                $('.modal.more').modal('show');
            }
            if (modal == "public-info") {
                $('.modal.public .modal-body ul').html('');
                $("#more-public-modal-template").tmpl(user).appendTo(".modal.public .modal-body");
                $('.modal.public').modal('show');
            }
            
        }
    });

    $('#basic-change').click(function() {
        var name = $('.modal.profile.basic .modal-body ul li:eq(1) input').val();
        var sex = $('.modal.profile.basic .modal-body ul li:eq(5) select').val();
        var age = $('.modal.profile.basic .modal-body ul li:eq(7) input').val();
        var alternative_mail = $('.modal.profile.basic .modal-body ul li:eq(9) input').val();
        put_basic_change(name, sex, age, alternative_mail);


    });

        
    function put_basic_change(name, sex, age, alternative_mail) {

        var local_user = JSON.parse(localStorage.getItem('user'));
        if (name == "") 
            name = local_user.name;
        if (alternative_mail == "")
            alternative_mail = local_user.alternative_mail;
        if (age == "")
            age = local_user.age;
        var modified_user = {
            name: name,
            age: age,
            alternative_mail: alternative_mail,
            share_basic_info: local_user.share_basic_info,
            share_more_info: local_user.share_more_info,
            share_friends: local_user.share_friends,
            share_routes: local_user.share_routes,
            share_comments: local_user.share_comments,
            hobbies: local_user.hobbies,
            description: local_user.description,
            billing_status: local_user.billing_status,
            enrollment_ending: local_user.enrollment_ending
        };

         $.ajax({
                url: '/app/users',
                type: 'PUT',
                data: JSON.stringify(modified_user),
                contentType: 'application/json; charset=utf-8',
                async: false,
                 beforeSend: function (xhr) {
                    var retrievedUser = JSON.parse(localStorage.getItem('user'));
                    xhr.setRequestHeader('Authorization', makeBaseAuth(retrievedUser.id, retrievedUser.pass));
                    progressBarStart();
                 },
                 success: function (modified_user, textStatus, jqXHR) {
                    alert('success');
                   progressBarEnd();
                 },
                 error: function (jqXHR, textStatus, errorThrown){
                    alert('error');
                    progressBarEnd();
                 }
           });
    }

});