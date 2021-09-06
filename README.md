# Llama Breeder

description du projet, lien vers le jeu.

This project is arcjitectured in 2 distincts applications: 

## Server

### Sql schema
The game's data is stored in a relational database (Mysql). The following schema defines several entities:
- User: user account to which all other entities are linked.
- Creature: the llama itself with all its characteristics.
- Capture: 
- Pen
- Item


![llama_breeder_db_diagram (1)](https://user-images.githubusercontent.com/52128443/132247734-c0f86f43-1bd6-4cc9-a357-5ed9c7fb2b27.png)


### application spring boot

### Rest API
The server exposes to the client an [hateoas](https://restfulapi.net/hateoas/) compliant rest api to fully manage all aspects of the game. The openapi 3 definition as well as a the documentation with examples of this api can be found in the [stoplight documentation page](https://bjnck.stoplight.io/docs/llama-breeder-api/YXBpOjIwMjgzNDIw-llama-breeder).


## Client

### libs utilisé
