# Llama Breeder

Llama Breeder is a client-server mobile game playable in browser.

> Start your own llama's breeding farm. Capture llamas in the wild and crossbreed different types of llamas to discover new types. Sell llamas to extend your farm, discover new genes to make breeding llamas more efficient and compare your score with the world. Become the best llama breeder now !

[Play Llama Breeder](https://play.llamabreedergame.com/) 

----

This project is structured in 2 distincts applications: 

## Server

### Sql schema
The game's data is stored in a relational database (Mysql). The following schema defines several entities:
- User: user account to which all other entities are linked.
- Creature: the llama itself with all its characteristics.
- Capture: all parameters used for creature creation, when end_time is reached a new creature is created.
- Pen: set of creatures and items. An item will have an effect on the creatures presents on the same pen.
- Item: object that can be use in a pen to change a creature's statistic.
![llama_breeder_db_diagram](https://user-images.githubusercontent.com/52128443/143773562-28d212a5-9dbb-4c00-bdba-a55871180a69.png)


### Spring-boot application
The server is developed in Java using spring-boot. The authentication is managed by [Firebase](https://firebase.google.com/). Both the database and server are deployed on [gcloud](https://cloud.google.com/).

For performance related reasons there are no scheduling involved. All resources are calculated/created only on user's connection. If a user don't play, the server does not compute anything for that user until his next connection.


### Rest API
The server exposes to the client an [hateoas](https://restfulapi.net/hateoas/) compliant rest api to fully manage all aspects of the game. The openapi 3 definition as well as a the documentation with examples of this api can be found in the [stoplight documentation page](https://bjnck.stoplight.io/docs/llama-breeder-api/YXBpOjIwMjgzNDIw-llama-breeder).


## Client
The client is developed in Angular using angular-material for the visual components.
It uses several other external librairies:
- [ngx-restangular](https://ngx-restangular.com/) to manage all rest calls.
- [ngx-infinite-scroll](https://github.com/orizens/ngx-infinite-scroll) for list scrolling.
- [angularFire](https://firebaseopensource.com/projects/angular/angularfire2/) for Firebase authentication.
