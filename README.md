# messaging-app

Zookeeper is running on localhost:9042
Producer is running on localhost:8080
Consumer is running on localhost:8081
Chart is running on localhost:4200

There are four different message types: MOReceived, MODelivered, MOCharged, MOTerminated and MOReport

Assume that messages are coming to "/message" endpoint of Producer.
{
    "id": "4343",
    "type": "MOTerminated",
    "senderId" : "159"
} - TimeStamp
The message is being sent to Kafka in order. Topic : "messages"
Then consumer writes the received message to the cassandra. KeySpace: messagingspace, Table: message
(one by one. no batch operations or ordering)

Chart displays the total amount of the messages according to message types. (from the first message to the current time)
(no time selections)
![Alt text](chart.png?raw=true "Title")