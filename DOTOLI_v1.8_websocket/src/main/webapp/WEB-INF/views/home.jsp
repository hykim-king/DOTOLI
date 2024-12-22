<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>1:1 Chat</title>
    <meta charset="UTF-8"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="resources/sockjs.min.js"></script>
    <style>
        /* 채팅 영역의 스타일 */
        #chat {
            width: 100%;
            height: 400px;         /* 고정 높이 설정 */
            border: 1px solid #ccc;
            overflow-y: scroll;    /* 내용이 넘치면 스크롤이 생기도록 설정 */
            padding: 10px;
            background-color: #f9f9f9;
            margin-bottom: 20px;
        }

        /* 채팅 입력 필드 스타일 */
        #message {
            width: 80%;
            padding: 5px;
        }

        /* 채팅 전송 버튼 스타일 */
        #sendMessageButton {
            padding: 5px 10px;
            cursor: pointer;
        }
    </style>
</head>
<body>
   <label for="receiverId">상대방 ID:</label>
<input type="text" id="receiverId" placeholder="채팅할 상대방 ID 입력" />

<div id="chatArea" class="chat-area">
    <h3>채팅</h3>
    <div id="chat"></div>
    <input type="text" id="message" placeholder="메시지를 입력하세요" />
    <button id="sendMessageButton">send</button>
</div>
    
    

<script>
    var sock = new SockJS("/echo");

    // WebSocket connection open handler
    sock.onopen = function() {
        console.log("WebSocket 연결 성공");

        // Prompt the user for their ID
        var userId = prompt("사용자 ID를 입력하세요:");
        
        if (userId) {
            // Send user ID to the server after connection
            sock.send("setUserId:" + userId);
        } else {
            alert("User ID is required.");
        }
    };

    // Handle incoming messages
    sock.onmessage = function(e) {
        var message = e.data;

        // If the message is asking for a user ID, we can display it
        if (message.startsWith("Please provide your user ID")) {
            console.log(message);
        } else if (message.startsWith("User ID set to:")) {
            console.log(message); // Confirmation that the user ID has been set
        } else {
            // Display incoming chat messages
            console.log("Received: " + message);
            $("#chat").append("상대방: " + message + "<br/>");
            scrollToBottom();
        }
    };

    // Send message to the server
    $("#sendMessageButton").click(function() {
        sendMessage();
    });

    // Send message when Enter key is pressed
    $("#message").keypress(function(event) {
        if (event.keyCode == 13) {  // Enter key code is 13
            event.preventDefault();  // Prevent the default action (new line)
            sendMessage();
        }
    });

    // Send message function
    function sendMessage() {
        var messageContent = $("#message").val();
        var receiverId = $("#receiverId").val(); // The recipient's ID
        if (messageContent && receiverId) {
            // Append message to the chat
            $("#chat").append("나: " + messageContent + "<br/>");

            // Send message to the server in the format "receiverId: message"
            sock.send(receiverId + ":" + messageContent);
            $("#message").val(''); // Clear the message input field

            // Scroll chat window to the bottom
            scrollToBottom();
        } else {
            alert("수신자와 메시지를 입력해주세요.");
        }
    }

    // Scroll chat to the bottom
    function scrollToBottom() {
        var chatDiv = document.getElementById("chat");
        chatDiv.scrollTop = chatDiv.scrollHeight;
    }
</script>

</body>
</html>