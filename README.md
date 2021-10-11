# Llama Breeder

description du projet, lien vers le jeu.

<p align="center">
  <img src="https://user-images.githubusercontent.com/52128443/132263867-66528af6-67db-42d6-8d28-adabdb06513d.png" alt="logo" style="width:100px;"/>
</p>

This project is architectured in 2 distincts applications: 

## Server

### Sql schema
The game's data is stored in a relational database (Mysql). The following schema defines several entities:
- User: user account to which all other entities are linked.
- Creature: the llama itself with all its characteristics.
- Capture: all parameters used for creature creation, when end_time is reached a new creature is created.
- Pen: set of creatures and items. An item will have an effect on the creatures presents on the same pen.
- Item: object that can be use in a pen to change a creature's statistic.
![llama_breeder_db_diagram](https://user-images.githubusercontent.com/52128443/132261611-b2f691ce-1e1d-49bf-8c11-d74d88bd63a8.png)

### Spring-boot application
The server side of the game is developed in Java using spring-boot.

expliquer que pas de scheduler, tout se calcul lors des appels rest au moment ou besoin.

### Rest API
The server exposes to the client an [hateoas](https://restfulapi.net/hateoas/) compliant rest api to fully manage all aspects of the game. The openapi 3 definition as well as a the documentation with examples of this api can be found in the [stoplight documentation page](https://bjnck.stoplight.io/docs/llama-breeder-api/YXBpOjIwMjgzNDIw-llama-breeder).


## Client
The client side of the game is developed in Angular using angular-material for the visual components.
It uses several other external librairies:
- [ngx-restangular](https://ngx-restangular.com/) to manage all http calls.
- [ngx-infinite-scroll](https://github.com/orizens/ngx-infinite-scroll) for list scrolling.

The client manage security by using OAuth2 to call Google and generate a token. This token is forwarded to the server for account creation/authentication and resources access authorization.
