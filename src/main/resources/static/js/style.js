    var biggest = Math.max.apply(Math, $('.input-group-text').map(function(){
     return $(this).width();
     }).get());
    $('.input-group-text').width(biggest);