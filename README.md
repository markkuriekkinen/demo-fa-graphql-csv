# Demo of using FA Solutions GraphQL API to output a CSV report

This is a Java Spring web app that fetches transaction data from the FA Solutions GraphQL API
and prints it in CSV format.
The app exposes one endpoint at the URL `/portfolios/{portfolioID}/transactions`.

Documentation of the FA API:
https://documentation.fasolutions.com/en/api-integrations.html

## Problems

* The app does not handle any authentication yet. It serves the data to any HTTP request.
* The app fetches a new OpenID token from the FA app for every HTTP request.
  Using Spring's OAuth2 Client API could be more sensible and reduce the number of token requests.
  Currently, the token is fetched by a simple HTTP POST request that is created with Spring's `RestClient`.
* The app uses Spring's synchronous `RestClient`. Using asynchronous APIs might improve performance under high load?
* No unit tests have been implemented.
* Error handling may be incomplete, e.g., for invalid input.

## How to run

Set FA Solutions API credentials in environment variables:
```
export FA_USERNAME='...'
export FA_PASSWORD='...'
```

Build and run with Maven.
`./mvnw spring-boot:run`

## curl usage examples

```
curl 'http://localhost:8080/portfolios/4/transactions'

curl 'http://localhost:8080/portfolios/3/transactions?startDate=2024-01-20&endDate=2024-07-31'
```
