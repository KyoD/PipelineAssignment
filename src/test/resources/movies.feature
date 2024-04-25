Feature: Testing Employee Controller

Background:
  * url apiBasePath

Scenario: Get All Movies
  Given path '/api/movies'
  When method GET
  Then status 200
  And match response contains {"id":2,"name":"Planet of the Apes","director":"Tim Burton","year":"2001"}
  
Scenario: Successfully create a movie
  Given path '/api/movies'
  And request { name: 'The Shawshank Redemption', director: 'Frank Darabont', year: "1994"}
  When method POST
  Then status 201
  
Scenario: Fail creating a movie too old
  Given path '/api/movies'
  And request { name: 'Ben-Hur: A Tale of the Christ', director: 'Fred Niblo', year: "1925"}
  When method POST
  Then status 400
  And match response == {"errorMessage":"Year of movie must be greater or equal to 1975 and less than or equal to 2024"}
  
Scenario: Fail creating an movie in the future
  Given path '/api/movies'
  And request { name: 'Toy Story 5', director: 'Pixar', year: "2026"}
  When method POST
  Then status 400
  And match response == {"errorMessage":"Year of movie must be greater or equal to 1975 and less than or equal to 2024"}

Scenario: Fail creating an movie with an empty field
  Given path '/api/movies'
  And request { name: 'Toy Story 5', director: '', year: "2026"}
  When method POST
  Then status 400
  And match response == {"errorMessage":"One of more empty field"}

Scenario: Fail creating a duplicate movie
  Given path '/api/movies'
  And request { name: 'The Shawshank Redemption', director: 'Frank Darabont', year: "1994"}
  When method POST
  Then status 400
  And match response == {"errorMessage":"This movie already exists"}
  
Scenario: Successfully update a movie
  Given path '/api/movies/5'
  And request {"name":"Titanic", "year":"2010","director":"Joe Bloggs"}
  When method PUT
  Then status 200
  
Scenario: Fail to update a non-existent movie
  Given path '/api/movies/1992'
  And request {"name":"Titanic", "year":"2010","director":"Joe Bloggs"}
  When method PUT
  Then status 400
  And match response == {"errorMessage":"No movie found"}
  
Scenario: Fail to update a movie by changing its name
  Given path '/api/movies/5'
  And request {"name":"Titanic 2", "year":"2010","director":"Joe Bloggs"}
  When method PUT
  Then status 400
  And match response == {"errorMessage":"Movie name cannot be updated!"}
  
Scenario: Fail to update an employee with an empty field
  Given path '/api/movies/1'
  And request {"name":"", "year":"","director":""}
  When method PUT
  Then status 400
  And match response == {"errorMessage":"One of more empty field"}
  
Scenario: Successfully delete an existent employee
  Given path '/api/movies/1'
  When method DELETE
  Then status 200
 
Scenario: Fail to delete a non-existent employee
  Given path '/api/movies/2011'
  When method DELETE
  Then status 404
  And match response == {"errorMessage":"No movie found"}