$(document).ready(function (){

    var perbox1 = $('.personal-box:nth-child(1)'),
        perbox1Child = $('.personal-box:nth-child(1) > div:last-child'),
        perbox2 = $('.personal-box:nth-child(2)'),
        perbox2Child = $('.personal-box:nth-child(2) > div:last-child'),
        perbox3 = $('.personal-box:nth-child(3)'),
        perbox3Child = $('.personal-box:nth-child(3) > div:last-child'),
        perbox4 = $('.personal-box:nth-child(4)'),
        perbox4Child = $('.personal-box:nth-child(4) > div:last-child'),
        perbox5 = $('.personal-box:nth-child(5)'),
        perbox5Child = $('.personal-box:nth-child(5) > div:last-child');

    $(perbox1).hover(function (){
        $(perbox1Child).css('opacity', '1.0').css('margin-top', '100px');
        $(perbox1).css({"background":"url(/images/historyMain.png)"});
    }, function (){
        $(perbox1Child).css('opacity', '0').css('margin-top', '40px');
        $(perbox1).css({"background": "url(/images/none.png)"});
    });

    $(perbox2).hover(function (){
        $(perbox2Child).css('opacity', '1.0').css('margin-top', '100px');
        $(perbox2).css({"background":"url(/images/noticeMainBlue.png)"});
    }, function (){
        $(perbox2Child).css('opacity', '0').css('margin-top', '40px');
        $(perbox2).css({"background": "url(/images/none.png)"});
    });

    $(perbox3).hover(function (){
        $(perbox3Child).css('opacity', '1.0').css('margin-top', '100px');
        $(perbox3).css({"background":"url(/images/freeMain.png)"});
    }, function (){
        $(perbox3Child).css('opacity', '0').css('margin-top', '40px');
        $(perbox3).css({"background": "url(/images/none.png)"});
    });

    $(perbox4).hover(function (){
        $(perbox4Child).css('opacity', '1.0').css('margin-top', '100px');
        $(perbox4).css({"background":"url(/images/galleryMainBlue.png)"});
    }, function (){
        $(perbox4Child).css('opacity', '0').css('margin-top', '40px');
        $(perbox4).css({"background": "url(/images/none.png)"});
    });

    $(perbox5).hover(function (){
        $(perbox5Child).css('opacity', '1.0').css('margin-top', '100px');
        $(perbox5).css({"background":"url(/images/shopMain.png)"});
    }, function (){
        $(perbox5Child).css('opacity', '0').css('margin-top', '40px');
        $(perbox5).css({"background": "url(/images/none.png)"});
    });




})


