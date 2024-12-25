<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>1:1 Chat</title>
    <meta charset="UTF-8"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="resources/sockjs.min.js"></script>
    <style>
        .chat-room {
            border: 1px solid #ccc;
            margin-bottom: 20px;
            padding: 10px;
            background-color: #f9f9f9;
            width: 300px;
            display: inline-block;
            vertical-align: top;
        }

        .messages {
            width: 100%;
            height: 200px;
            border: 1px solid #ccc;
            overflow-y: scroll;
            padding: 10px;
            background-color: #fff;
        }

        .message {
            width: 80%;
            padding: 5px;
        }

        .send-message {
            padding: 5px 10px;
            cursor: pointer;
        }

        #receiverId {
            width: 80%;
            padding: 5px;
        }

        #startChatButton {
            padding: 5px 10px;
        }
    </style>
</head>
<body>
   <h3>1:1 Chat</h3>

   <label for="receiverId">Receiver ID:</label>
   <input type="text" id="receiverId" placeholder="Enter the receiver's ID" />
   <button id="startChatButton">Start Chat</button>

   <div id="chatRooms"></div>

<script>
    var sock = new SockJS("/echo");
    var userId = null;

    // WebSocket connection open handler
    sock.onopen = function() {
        console.log("WebSocket connected.");
        
        // Ask for the user's ID
        userId = prompt("Please enter your user ID:");
        
        if (userId) {
            sock.send("setUserId:" + userId);  // Send user ID to the server
        } else {
            alert("User ID is required.");
        }
    };

    // Handle incoming messages
    sock.onmessage = function(e) {
        var message = e.data;
        console.log("Received message: " + message);
        
        // Check if the message is a user ID confirmation
        if (message.startsWith("User ID set to:")) {
            // Handle the user ID confirmation (no action needed for now)
            console.log("User ID set successfully: " + message);
            return;
        }

        // Check if the message is a chat message in the format "receiverId:message"
        var parts = message.split(":", 2);
        if (parts.length === 2) {
            var receiverId = parts[0];  // Extract receiverId
            var msg = parts[1];  // Extract message

            console.log("Receiver ID: " + receiverId);
            console.log("Message: " + msg);

            // Ensure that we find or create the correct chat room for the receiverId
            var chatRoom = $(".chat-room[data-userid='" + receiverId + "']");
            if (chatRoom.length > 0) {
                // Append the message to the chat room's message box
                chatRoom.find(".messages").append(receiverId + ": " + msg + "<br/>");
                scrollToBottom(chatRoom);
            } else {
                // If the chat room for the receiver doesn't exist, create it
                console.log("No chat room found for receiver: " + receiverId);
                createChatRoom(receiverId, msg);  // Create the chat room for the receiver
            }
        } else {
            console.log("Invalid message format: " + message);
        }
    };

    // Handle creating a new chat room
    $("#startChatButton").click(function() {
        var receiverId = $("#receiverId").val().trim();
        if (receiverId && receiverId !== userId) {
            // Check if the chat room already exists
            var existingChatRoom = $(".chat-room[data-userid='" + receiverId + "']");
            if (existingChatRoom.length == 0) {
                createChatRoom(receiverId);  // Create the chat room for the receiver
            } else {
                alert("Already chatting with " + receiverId);
            }
        } else {
            alert("Please enter a valid receiver ID.");
        }
    });

    // Function to create a new chat room
    function createChatRoom(receiverId, initialMessage) {
        var chatRoom = $("<div class='chat-room'></div>");
        chatRoom.attr('data-userid', receiverId);
        chatRoom.append("<h3>Chat with " + receiverId + "</h3>");
        chatRoom.append("<div class='messages'></div>");
        chatRoom.append("<input type='text' class='message' placeholder='Type your message' />");
        chatRoom.append("<button class='send-message'>Send</button>");

        $("#chatRooms").append(chatRoom);

        // If there is an initial message, append it to the chat
        if (initialMessage) {
            chatRoom.find(".messages").append("Me: " + initialMessage + "<br/>");
        }

        // Send message when the button is clicked
        chatRoom.find(".send-message").click(function() {
            var message = chatRoom.find(".message").val().trim();
            if (message) {
                sock.send(receiverId + ":" + message);  // Send message via WebSocket
                chatRoom.find(".messages").append("Me: " + message + "<br/>"); // Display the message locally
                chatRoom.find(".message").val('');  // Clear the message input
                scrollToBottom(chatRoom);  // Scroll to the bottom
            }
        });
    }

    // Function to scroll to the bottom of the chat
    function scrollToBottom(chatRoom) {
        var messagesDiv = chatRoom.find(".messages")[0];
        messagesDiv.scrollTop = messagesDiv.scrollHeight;
    }
</script>

</body>
</html>
