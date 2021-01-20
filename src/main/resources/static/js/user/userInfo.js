$(document).ready(function () {

    var box1 = $('.lnb > ul > li:nth-child(1) > a'),
        box2 = $('.lnb > ul > li:nth-child(2) > a'),
        box3 = $('.lnb > ul > li:nth-child(3) > a'),
        box4 = $('.lnb > ul > li:nth-child(4) > a'),
        box5 = $('.lnb > ul > li:nth-child(5) > a'),
        box6 = $('.lnb > ul > li:nth-child(6) > a'),
        box7 = $('.lnb > ul > li:nth-child(7) > a');

    $(box1).hover(function () {
        $(box1).css('background-color', '#823cec').css('color', 'white');
    }, function (){
        $(box1).css('background-color', 'white').css('color', '#777');
    });
    $(box2).hover(function () {
        $(box2).css('background-color', '#a873f9').css('color', 'white');
    }, function (){
        $(box2).css('background-color', 'white').css('color', '#777');
    });
    $(box3).hover(function () {
        $(box3).css('background-color', '#a873f9').css('color', 'white');
    }, function (){
        $(box3).css('background-color', 'white').css('color', '#777');
    });
    $(box4).hover(function () {
        $(box4).css('background-color', '#a873f9').css('color', 'white');
    }, function (){
        $(box4).css('background-color', 'white').css('color', '#777');
    });
    $(box5).hover(function () {
        $(box5).css('background-color', '#a873f9').css('color', 'white');
    }, function (){
        $(box5).css('background-color', 'white').css('color', '#777');
    });
    $(box6).hover(function () {
        $(box6).css('background-color', '#a873f9').css('color', 'white');
    }, function (){
        $(box6).css('background-color', 'white').css('color', '#777');
    });
    $(box7).hover(function () {
        $(box7).css('background-color', '#a873f9').css('color', 'white');
    }, function (){
        $(box7).css('background-color', 'white').css('color', '#777');
    });
})