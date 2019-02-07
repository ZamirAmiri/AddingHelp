/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//window.onload = init;
var socket = new WebSocket("ws://192.168.2.7:8080/AddingHelp/actions");
var admin = new WebSocket("ws://192.168.2.7:8080/AddingHelp/admin");
socket.onmessage = onMessage;
var groupID = null;

function onMessage(event) {
    var user = JSON.parse(event.data);
    if (user.action === "login_succes") {
        //document.write(user.id);
    }
}

function update(){
    var UserAction = {
        action: "update"
    };
    socket.send(JSON.stringify(UserAction));
}

function registerUser(name,pass,birthdate,email,gender){
    var UserAction = {
        action: "register",
        name: name,
        pass: pass,
        birthdate: birthdate,
        email: email,
        gender: gender
    };
    socket.send(JSON.stringify(UserAction));
}

function activate_code(code){
    var UserAction = {
        action:"activate account",
        code: code
    };
    socket.send(JSON.stringify(UserAction));
}

function register() {
    var form = document.getElementById("registerForm");
    var name = form.elements["user_name"].value;
    var pass = form.elements["user_password"].value;
    var birthdate = form.elements["user_birthdate"].value;
    var email = form.elements["user_email"].value;
    var gender = form.elements["user_gender"].value;
    //document.write(form + name + pass + birthdate + email + gender);

    //hideForm();
    //document.getElementById("addDeviceForm").reset();
    registerUser(name,pass,birthdate,email,gender);
}

function activateAccount() {
    var form = document.getElementById("activateAcoountForm");
    var code = form.elements["code"].value;
    //document.write(form + name + pass + birthdate + email + gender);

    //hideForm();
    //document.getElementById("addDeviceForm").reset();
    //registerUser(name,pass,birthdate,email,gender);
    activate_code(code);
}

function login(){
    var form = document.getElementById("login");
    var uname = form.elements["user_name"].value;
    var upass = form.elements["user_password"].value;
    var UserAction = {
        action: "login",
        name: uname,
        pass: upass
    };
    socket.send(JSON.stringify(UserAction));
}

function addHelpCoin(){
    var UserAction = {
        action: "addHelpCoin",
        coins: 5
    };
    socket.send(JSON.stringify(UserAction));
}

function newFoundation(){
    var form = document.getElementById("adminF");
    var uname = form.elements["admin_uname"].value;
    var upass = form.elements["admin_password"].value;
    var name = form.elements["f_name"].value;
    var info = form.elements["f_info"].value;
    var logo = form.elements["f_logo"].value;
    var website = form.elements["f_website"].value;
    var UserAction = {
        action: "addFoundation",
        username: uname,
        password: upass,
        name: name,
        info:info,
        logo:logo,
        website:website
    };
    admin.send(JSON.stringify(UserAction));
    
}

function follow(){
    var form = document.getElementById("follow");
    var uname = form.elements["uname"].value;
    var UserAction = {
        action:"follow",
        username:uname
    };
    socket.send(JSON.stringify(UserAction));
}

function searchUser(){
    var form = document.getElementById("searchUser");
    var uname = form.elements["uname"].value;
    var UserAction = {
        action:"search_user",
        search:uname
    };
    socket.send(JSON.stringify(UserAction));
}