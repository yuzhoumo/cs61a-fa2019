$(document).ready(function(){
    
    $('.photo1').hover(function() {
        $(this).hide();
        $(this).parent().find('.photo2').show();
    });
    
    $('.photo2').hover(function() {
    }, function() {
        $(this).hide();
        $(this).parent().find('.photo1').show();
    });
});
