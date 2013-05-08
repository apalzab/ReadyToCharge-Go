



//cuando se carga por primera vez la página
jQuery(document).ready(function(){
    jQuery('#btnUsuario').hide();
      jQuery('#ReadyToGo').hide();
        jQuery('#heroReadyToGoText').hide();
        jQuery('#modalUsuario').hide();
        jQuery('#filasReadyToGo').hide(); 
        jQuery('#barraProgreso').hide(); 
        jQuery('#btnReservas').hide(); 
        jQuery('#descrReadyToCharge').hide(); 
        jQuery('#ReadyToChargeStart').hide(); 
        jQuery('#ReadyToGoStart').hide(); 
        
      //jQuery('#modalPrueba').modal('show');

   


});


//click imagenEnchufe 
jQuery(document).ready(function(){
     jQuery('#imgEnchufeClick').click(function(){   
             jQuery('#imagenCoche').hide('slow');  
              $("#inicio").remove();
                jQuery('#ReadyToChargeStart').show('slow'); 
       
     });
});



//click imagenCoche 
jQuery(document).ready(function(){
     jQuery('#imgCocheClick').click(function(){   
             jQuery('#imagenEnchufe').hide('slow');  
              $("#inicio").remove();
              jQuery('#ReadyToGoStart').show('slow'); 

     });
});





//click btnReadyToCharge
jQuery(document).ready(function(){
     jQuery('#btnReadyToCharge').click(function(){     
          $("#inicio").remove();
         
           jQuery('#ReadyToCharge').show('slow');

     });
});

//click btnReadyToGo
jQuery(document).ready(function(){
     jQuery('#btnReadyToGo').click(function(){     
           $("#inicio").remove();
            //  jQuery('#modalUsuario').modal('show');
            jQuery('#ReadyToGo').show('slow');

     });
});


//btnQuieroSaberMas del ReadyToGo
jQuery(document).ready(function(){
     jQuery('#btnQuieroSaberMas').click(function(){   
      jQuery('#btnQuieroSaberMas').hide('slow'); 
      jQuery('#heroReadyToGoText').show('slow'); 
       jQuery('#filasReadyToGo').show('slow'); 
    
     });
});




//btn About click
jQuery(document).ready(function(){
     jQuery('#btnAbout').click(function(){
          $("#btnAbout").addClass("active");
           $("#btnInicio").removeClass("active");
     });
});

//btnInicio click
jQuery(document).ready(function(){
     jQuery('#btnInicio').click(function(){
          $("#btnInicio").addClass("active");
           $("#btnAbout").removeClass("active");
        
     });
});


//btnIniciarSerion click
jQuery(document).ready(function(){
     jQuery('#btnIniciarSesion').click(function(){
        $('#barraProgreso').show('slow');       
          

        var cont=10;
        for (i=0; i<500; i++){

        $('.progress .bar').css({
                'width': cont,
              });
        cont++;
      }
        


        var usuario = $("#textUsuario").val(); 
        var contrasena = $("#textContrasena").val(); 


$.getJSON("/rest/Usuario/"+usuario+"/"+contrasena, function(data) {

      for (i=0; i<500; i++)

      {

        $('.progress .bar').css({
                'width': cont,
              });
        cont++;
      }
        



            if (data==null)   

              {   
                      $('.progress .bar').css({
                      'width': 100,
                    });

                      bootbox.alert("Usuario y/o contraseña incorrecta. Inténtalo de nuevo!");         
                      $('#barraProgreso').hide('slow'); 
              }

            else {       
                          $('#textUsuario').hide('slow');
                          $('#textContrasena').hide('slow');
                          $('#btnIniciarSesion').hide('slow');

                           
                  for (i=0; i<500; i++)

                  {

                    $('.progress .bar').css({
                            'width': cont,
                          });
                    cont++;
                  }

                         $('#barraProgreso').hide('slow'); 


                          $('#btnUsuario').show('slow');              
                          $('#btnUsuario').text("@"+ data.nombre);
                          jQuery('#btnReservas').show('slow'); 
                      
                }

});



  $('.progress .bar').css({
          'width': 1000,
        });
          


  });
 $('#barraProgreso').hide('slow'); 

});


//btnUsuario
jQuery(document).ready(function(){
     jQuery('#btnUsuario').click(function(){
      
        jQuery('#modalUsuario').modal('show');

        
     });
});


