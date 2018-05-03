# messaging-app

Zookeeper is running on localhost:9042 <br />
Producer is running on localhost:8080 <br />
Consumer is running on localhost:8081 <br />
Chart is running on localhost:4200

There are four different message types: MOReceived, MODelivered, MOCharged, MOTerminated and MOReport

Assume that messages are coming to "/message" endpoint of Producer.<br />
{<br />
    "id": "4343",<br />
    "type": "MOTerminated",<br />
    "senderId" : "159"<br />
} - TimeStamp<br />
The message is being sent to Kafka in order. Topic : "messages"<br />
Then consumer writes the received message to the cassandra. KeySpace: messagingspace, Table: message<br />
(one by one. no batch operations or ordering)<br />

Chart displays the total amount of the messages according to message types. <br />(from the first message to the current time)
(no time selections)<br />
![Alt text](chart.png?raw=true "Title")