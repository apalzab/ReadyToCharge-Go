$(document).ready(function(){

  $('div.readyToCharge button.getRtcTimes').click(function(){
    $('#timetables').show('slow');
      var station = $("#station").val();
      var hour = $("#timepicker").val();
      var chargeType = $("#chargeType").val();
      alert(station + hour + chargeType + "trying to get the availableTimes");
      $('div.readyToCharge').hide();
      $('#timetablestable').html('<!--timetables--><tr><td><h3><strong>Estación de carga</strong></h3></td><td><h3><strong>Tipo de carga</strong></3></td><td><h3><strong>Hora de carga</strong></h3></td><td><h3><strong>¡Reservar!</strong></h3></td></tr></div>');
      $.ajax({
         url: 'app/ReadyToChargeAvailableTimes' + '/' + station + '/' + chargeType + '/' + hour,
          type: 'GET',
          data: {},
          success: function (data) {
          }
        });
  });
});

  //JQuery TimePicker
  $(function() {
  $('#timepicker').timepicker({ 'timeFormat': 'H:i' });
  $('#timepicker').timepicker('option', 'minTime', '08:00am');
  $('#timepicker').timepicker('option', 'maxTime', '11:00pm');
  $('#timepicker').timepicker('setTime', new Date());
  });
