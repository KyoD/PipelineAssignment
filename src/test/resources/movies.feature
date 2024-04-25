Feature: Testing Employee Controller

Background:
  * url 'http://localhost:8082'

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
  
Scenario: Successfully update an existing employee
  Given path '/api/movies/1'
  And request {"firstName":"Dave","lastName":"Bloggs","emailAddress":"joe@gmail.com","age":20}
  When method PUT
  Then status 200
  
Scenario: Fail to update a non-existent employee
  Given path '/api/movies/199'
  And request {"firstName":"Dave","lastName":"Bloggs","emailAddress":"d@gmail.com","age":20}
  When method PUT
  Then status 400
  And match response == {"errorMessage":"No employee found"}
  
Scenario: Fail to update an employee by changing email
  Given path '/api/movies/1'
  And request {"firstName":"Dave","lastName":"Bloggs","emailAddress":"somethingdifferent@gmail.com","age":20}
  When method PUT
  Then status 400
  And match response == {"errorMessage":"Employee email address cannot be updated"}
  
Scenario: Fail to update an employee with an empty field
  Given path '/api/movies/1'
  And request {"firstName":"","lastName":"","emailAddress":"joe@gmail.com","age":20}
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
  And match response == {"errorMessage":"No employee found"}