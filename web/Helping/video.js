/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var socket = new WebSocket("ws://192.168.2.7:8080/AddingHelp/actions");
socket.onmessage = onMessage;
var groupID = null;

function onMessage(event) {
    var user = JSON.parse(event.data);
    if (user.action === "login_succes") {
        //document.write(user.id);
    }
}
function addCoin() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var name = url.searchParams.get("name");
    var project = url.searchParams.get("project");
    var UserAction = {
        action: "link",
        name: name,
        project:project
    };
    socket.send(JSON.stringify(UserAction));
}
