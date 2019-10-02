# Chess
#### A Client-Server program to play chess over the network

The program has been developed in my free time to have some fun.

Thus, there are some to-do functionalities and features that still have to be finished up, such as checking if the movement is allowed (i.e. is a valid movement according to rules of the chess game) and some improvements to the user interfaces, and the code can still be cleaned, documented and commented better.

#### How to run

* `chess.server.StartServer` runs the server
* `chess.client.StartClient` runs the client

When client runs, the user is asked to choose a name and a color and to give the IP address of the machine on which the server is running.

Afterwards, the user on the server-side will be asked to choose a name and will be notified which color has been chosen for them (so the client-side makes the decision about choosing the colors).
