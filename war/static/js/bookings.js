if (localStorage.getItem('user') == null) {
  var url = "/";
  $(location).attr('href',url);
}
$(document).ready(function(){
    getBookings();


    $('.readyToChargeBookings').delegate('.bookingsInfo .btn-danger',"click", function() {
    if (window.confirm("Vas a cancelar una reserva. ¿Estás seguro de que quieres continuar?"))
      cancelBooking($(this).data("booking_id"));
  });





   function getBookings() {
       $.ajax({
                 url: '/app/rtc-bookings',
                 type: 'GET',
                 async: false,
                 beforeSend: function (xhr) {
                    var retrievedUser = JSON.parse(localStorage.getItem('user'));
                    xhr.setRequestHeader('Authorization', makeBaseAuth(retrievedUser.id, retrievedUser.pass));
                    progressBarStart();
                 },
                 success: function (bookings, textStatus, jqXHR) {
                   if (bookings.length != 0) {
                        $(bookings).each(function(index, booking) {
                        $("#rtcBookingsTemplate").tmpl(booking).appendTo("#readytocharge .bookingsInfo");
                   });
                   }
                       else {
                        $('#readytocharge .bookingsInfo').append('<tr class="error"><td>No has realizado ninguna reserva.</td><td></td><td></td><td></td><td></td></tr>');
                       }
                   progressBarEnd();
                 },
                 error: function (jqXHR, textStatus, errorThrown){
                    alert('error');
                    progressBarEnd();
                 }
           });
   }

   function cancelBooking(bookingId){
         $.ajax({
                 url: '/app/rtc-bookings/' + bookingId,
                 type: 'DELETE',
                 async: false,
                 beforeSend: function (xhr) {
                    var retrievedUser = JSON.parse(localStorage.getItem('user'));
                    xhr.setRequestHeader('Authorization', makeBaseAuth(retrievedUser.id, retrievedUser.pass));
                    progressBarStart();
                 },
                 success: function (bookings, textStatus, jqXHR) {
                   $('#readytocharge .bookingsInfo tbody').empty();
                   getBookings();
                   progressBarEnd();
                 },
                 error: function (jqXHR, textStatus, errorThrown){
                    alert('error');
                    progressBarEnd();
                 }
           });
   }
});

