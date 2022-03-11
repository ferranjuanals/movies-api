# movies-api

To use the API, open a new terminal in the project folder and run spring-boot.
`mvn spring-boot:run`

## Add a movie

Add a movie using the id from the TMDb.

`[POST] /movies/:id`  
`id=[String]`

## Find movies

Find all movies saved. Filter by year or title to find specific movies.

`[GET] /movies?page=:page&size=:size&year=:year&title=:title`  
`page=[Integer]`  
`size=[Integer]`  
`year=[Integer]`  
`title=[String]`

## Find actors, directors and writers

Find all the people who worked at the saved movies. Filter by job: actor, director or writer.

`[GET] /people?page=:page&size=:size&job=:job`  
`page=[Integer]`  
`size=[Integer]`  
`job=[String]`  

## Test

The Postman folder contains a collection to test the endpoints.
