if ($.cookie('ReadyToChargeAndGo') == null) {
  var url = "/";
  $(location).attr('href',url);
  $('div.modal.login').modal('show');
}
$(document).ready(function() {
  $('div.readyToCharge button.getRtcTimes').click(function() {
    progressBarStart();
    $('div.timestable').empty();
    $('div.timestable').append('<table id="timesTable" class="table timetables"><tr><td><h3><strong>Estación de carga</strong></h3></td><td><h3><strong>Tipo de carga</strong></3></td><td><h3><strong>Hora de carga</strong></h3></td><td><h3><strong>¡Reservar!</strong></h3></td></tr></table>');
    $('div.timestable').show('slow');
    var station = $("#station").val();
    var hour = $("#timepicker").val();
    var chargeType = $("#chargeType").val();
    $('div.readyToCharge').hide();
    $('#timetablestable').html('');
    $.ajax({
            url: '/app/ReadyToChargeAvailableTimes/' + station + '/' + chargeType + '/' + hour,
            type: 'GET',
            success: function (availableTimes) {
              $(availableTimes).each(function(index, availableTime) {
                availableTime.chargeStation = station;
                availableTime.chargeType = chargeType;
                availableTime.completeTime = availableTime.hour + ":" + availableTime.minutes;
                progressBarEnd();
                $("#timesTemplate").tmpl(availableTime).appendTo("#timesTable");
              });
              progressBarEnd();
              },
            error: function() {
              alert("in error callback");
              progressBarEnd();
            }
    });
  });

  $(function() {
    $('#timepicker').timepicker({ 'timeFormat': 'H:i' });
    $('#timepicker').timepicker('option', 'minTime', '08:00am');
    $('#timepicker').timepicker('option', 'maxTime', '11:00pm');
    $('#timepicker').timepicker('setTime', new Date());
  });

  $('div.timestable').delegate('#timesTable button.book',"click", function() {
    book($(this).data("chargestation"), $(this).data("chargetype"), $(this).data("hour"), $(this).data("minutes"), $(this).data("day"), $(this).data("month"), $(this).data("year"));
  });

  function book(chargeStation, chargeType, hour, minutes, day, month, year) {
    var id;
    if ($.cookie('ReadyToChargeAndGo') != null) {
    var str = $.cookie('ReadyToChargeAndGo');
    var ret = str.split(':');
    id = ret[0];
    }
     var booking = {
        userId: id,
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
             url: '/app/ReadyToChargeBookings',
             type: 'POST',
             data: JSON.stringify(booking),
             contentType: 'application/json; charset=utf-8',
             async: false,
             success: function (data, textStatus, jqXHR) {
               alert('booking success');
             },
             error: function (jqXHR, textStatus, errorThrown){
                alert('error');
             }
           });
  }

});

