if (localStorage.getItem('user') == null) {
  var url = "/";
  $(location).attr('href',url);
}
$(document).ready(function() {

  initDateTime();

  $('div.readyToCharge button').click(function() {
    var today = new Date();
    var pickedDate = new Date ($('input[type="date"]').val());
    pickedDate.setHours(23);
    pickedDate.setMinutes(59);
    if (pickedDate >= today) {
      progressBarStart();
      $('div.timestable').empty();
      $('div.timestable').append('<table id="timesTable" class="table table-hover timestable"><thead><tr><th><h3><strong>Estación de servicio</strong></h3></th><th><h3><strong>Tipo de vehículo</strong></3></th><th><h3><strong>Fecha</strong></h3></th><th><h3><strong>Hora</strong></h3></th><th><h3><strong>¡Reservar!</strong></h3></th><th><h3><button class="btn btn-small btn-warning">Cambiar preferencias</button></h3></th></tr></thead></table>');
      $('div.timestable').show('slow');
      var station = $("#station").val();
      var date = $('input[type="date"]').val();
      //var hour = $("#timepicker").val();
      var chargeType = $("#chargeType").val();
      $('div.readyToCharge').hide();
      $('#timetablestable').html('');
      $.ajax({
              url: '/app/rtc-available-times/' + station + '/' + chargeType + '/' + date,
              type: 'GET',
              success: function (availableTimes) {
                if (availableTimes.length != 0) {
                  $(availableTimes).each(function(index, availableTime) {
                    availableTime.chargeStation = station;
                    availableTime.chargeType = chargeType;
                    availableTime.completeTime = availableTime.hour + ":" + availableTime.minutes;
                    availableTime.chargeDate = availableTime.day + "-" + availableTime.month + "-" + availableTime.year;
                    $("#timesTemplate").tmpl(availableTime).appendTo("#timesTable");
                  });
                } else {
                    $('#timesTable').append('<tr class="error"><td>No disponemos de recargas para la fecha introducida</td><td></td><td></td><td></td><td></td></tr>');
                  }
                
                progressBarEnd();
                },
              error: function() {
                alert("in error callback");
                progressBarEnd();
              }
      });
    } else alert("Introduce una fecha correcta!");
  });


  function initDateTime() {
    var d = new Date();
    var hours = d.getHours();
    var minutes = d.getMinutes();
    if (hours.toString().length < 2)
      hours = "0" + hours;
    if (minutes.toString().length < 2)
      minutes = "0" + minutes;
    var time = hours + ":" + minutes;
    $('input[type="time"]').val(time);
    var day = d.getDate();
    var month = d.getMonth()+1;
    if (day.toString().length < 2)
      day =  "0" + day.toString();
    if (month.toString().length < 2)
      month = "0" + month.toString();
    var date =  d.getFullYear() + "-" + month + "-" + day ;
    $('input[type="date"]').val(date);
    $('input[type="date"]').attr("min", date);
  }


  $('div.timestable').delegate('#timesTable .btn-warning',"click", function() {
    $('#timesTable').hide();
    $('div.readyToCharge').show('slow');
    progressBarEnd();
  });

  $('div.timestable').delegate('#timesTable .btn-danger',"click", function() {
    if (window.confirm("Vas a realizar una reserva. ¿Estás seguro de que quieres continuar?"))
      book($(this).data("chargestation"), $(this).data("chargetype"), $(this).data("hour"), $(this).data("minutes"), $(this).data("day"), $(this).data("month"), $(this).data("year"));
  });

  function book(chargeStation, chargeType, hour, minutes, day, month, year) {

     var booking = {
        chargeStation: chargeStation,
        chargeType: chargeType,
        month: month.toString(),
        day: day.toString(),
        year: year.toString(),
        hour: hour.toString(),
        minutes: minutes.toString()
         };

         if (booking.minutes == "0")
            booking.minutes = "00";

         $.ajax({
             url: '/app/rtc-bookings',
             type: 'POST',
             data: JSON.stringify(booking),
             contentType: 'application/json; charset=utf-8',
             async: false,
             beforeSend: function (xhr) {
                var retrievedUser = JSON.parse(localStorage.getItem('user'));
                xhr.setRequestHeader('Authorization', makeBaseAuth(retrievedUser.id, retrievedUser.pass));
                progressBarStart();
             },
             success: function (booking, textStatus, jqXHR) {
               $('#timesTable').hide();
               $("#bookingTemplate").tmpl(booking).appendTo('#rtcBooking table tbody');
               $('#rtcBooking').show('slow');
               $('html, body').animate({ scrollTop: 0 }, 'slow');
               progressBarEnd();
              initialize();
             },
             error: function (jqXHR, textStatus, errorThrown){
                alert('error');
             }
           });
  }

  function makeBaseAuth(user, password) {
    var tok = user + ':' + password;
    var hash = btoa(tok);
  return "Basic " + hash;
}

});

