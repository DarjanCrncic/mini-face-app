
var websocket = new WebSocket("ws://localhost:8080/MiniFaceApp/chat");

websocket.onmessage = function processMessage(message){
	console.log(JSON.parse(message.data).content);
	
}

function sendMessage(){
	let input = {
		content: $('#messageText').val()
	}
	websocket.send(JSON.stringify(input));
	$('#messageText').text("");
}