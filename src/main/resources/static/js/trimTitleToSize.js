//<!-- script to truncate lesson description and add url button for read more --!>
let maxLength = 500;

jQuery(document).ready(function($) {
    $(".content").click(function() {
        window.location = $(this).data("href");
    });
});

$(document).ready(function(){
    $(".content").each(function(){
        let myStr = $(this).text();
        if($.trim(myStr).length > maxLength){
            let newStr = myStr.substring(0, maxLength);
            $(this).empty().html(newStr + '...');
        }
    });
});