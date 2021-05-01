$(document).ready(function(){
    var currentPosition = parseInt($("#categoryBox").css("top"));
    $(window).scroll(function() {
        var position = $(window).scrollTop(); // 현재 스크롤바의 위치값을 반환합니다.
        $("#categoryBox").stop().animate({"top":position+currentPosition+"px"},1000);
    });
});
