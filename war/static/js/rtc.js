$(document).ready(function() {

  $('div.readyToCharge button.getRtcTimes').click(function() {
    $('div.timestable').empty();
    $('div.timestable').append('<table id="timesTable" class="table timetables"><tr><td><h3><strong>Estación de carga</strong></h3></td><td><h3><strong>Tipo de carga</strong></3></td><td><h3><strong>Hora de carga</strong></h3></td><td><h3><strong>¡Reservar!</strong></h3></td></tr></table>');
    $('div.timestable').show('slow');
    var station = $("#station").val();
    var hour = $("#timepicker").val();
    var chargeType = $("#chargeType").val();
    alert(station + hour + chargeType + "trying to get the availableTimes");
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

  //JQuery TimePicker
  $(function() {
    $('#timepicker').timepicker({ 'timeFormat': 'H:i' });
    $('#timepicker').timepicker('option', 'minTime', '08:00am');
    $('#timepicker').timepicker('option', 'maxTime', '11:00pm');
    $('#timepicker').timepicker('setTime', new Date());
  });

  $('div.timestable').delegate('#timesTable button.book',"click", function() {
    alert("ey dude");
    alert($(this).data("time"));
  });

});

