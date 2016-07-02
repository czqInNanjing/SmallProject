$(document).ready(function(){

    /* Self-Test Radio Buttons */
    $('.checkbutton').click(function() {
        var choices = $(this).parent().children('.question').children('tbody').children('tr');
        for (var i = 0; i < choices.length; i++) {
            var input = $(choices[i]);
            var selected = $(input).children('td').children()[0].checked;
            var feedback = $(input).children('td')[2];
            $(feedback).hide();
            if (selected) {
                $(feedback).show();
            }
        }
    });

    /* Self-Test Checkbox Buttons */
    $('.checkboxbutton').click(function() {
        var choices = $(this).parent().children('.question').children('tbody').children('tr');
        for (var i = 0; i < choices.length; i++) {
            var input = $(choices[i]);
            var feedback = $(input).children('td')[2];
            $(feedback).show();
        }
    });

    /* Toggle Solution */
    $('.togglebutton').click(function() {
        var sol = $(this).parent().find('.solution');
        var display = $(sol).css('display');
        if (display == 'none') {
            $(sol).show();
        } else {
            $(sol).hide();
        }
    });

});
