<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Chat Room</title>
    <meta charset="UTF-8"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="resources/sockjs.min.js"></script>
    <style>
        #chat {
            max-height: 300px;
            overflow-y: scroll;
            border: 1px solid #ddd;
            padding: 10px;
            margin-bottom: 10px;
        }
        .user-message {
            color: green;
        }
        .bot-message {
            color: blue;
        }
        #notification {
            display: none;
            background-color: yellow;
            padding: 5px;
            margin-top: 10px;
            border: 1px solid black;
        }
    </style>
</head>
<body>
    <h2>Welcome to the Chat Room</h2>
    
    <div id="notification">New Message!</div>

    <form id="chatForm">
        <input type="text" id="username" placeholder="Enter your name" required/>
        <input type="text" id="message" placeholder="Type a message" required/>
        <button>Send</button>
    </form>
    
    <button id="clearChat">Clear Chat</button>
    
    <div id="chat"></div>

    <script>
        var username = '';
        var sock = new SockJS("/echo");

        $(document).ready(function(){
            // Handle the chat form submission
            $("#chatForm").submit(function(event){
                event.preventDefault();
                if ($("#username").val()) {
                    username = $("#username").val();
                }
                sock.send(username + ": " + $("#message").val());
                $("#message").val('').focus();
            });

            // Handle message reception
            sock.onmessage = function(e){
                var message = e.data;
                // Display the message with sender's name and style
                var sender = message.split(":")[0];
                var msg = message.split(":")[1];
                var messageClass = sender === username ? 'user-message' : 'bot-message';
                $("#chat").append('<div class="' + messageClass + '">' + message + '</div>');
                $("#chat").scrollTop($("#chat")[0].scrollHeight);

                // Show notification for new message
                $("#notification").fadeIn().delay(2000).fadeOut();
            }

            // Close connection handler
            sock.onclose = function(){
                $("#chat").append("<div class='bot-message'>Connection Closed</div>");
            }

            // Clear chat button functionality
            $("#clearChat").click(function() {
                $("#chat").empty();
            });
        });
    </script>
</body>
</html>
