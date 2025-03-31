# oauth2-bff-demo

This project demonstrates how Spring framework projects can be wired up to implement a `Backend For Frontend` (BFF) 
component as laid out in  [IETF Draft for OAuth 2.0 for Browser-Based Applications](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-browser-based-apps#name-backend-for-frontend-bff).

In this architecture, the BFF acts as a central gateway to all backend services and holds user sessions.
If no session is found, it initiates the Authorization Code flow with PKCE to the OAuth2 Authorization server, allowing the BFF to obtain tokens which are attached to the HTTP session. 
Once authenticated, the frontend application sends requests to the resource server through the BFF, which attaches the user's access token and returns the response to the frontend.

This demo leverages the following components:

- [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) as the gateway, which uses the provided [TokenRelay Filter](https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway-server-mvc/filters/tokenrelay.html) to authenticate to the OAuth2 Authorization Server
  - The Demo uses Github as OAuth2 Authorization Server, but any other could be used
- [Spring-Session](https://spring.io/projects/spring-session) to store the sessions in a separate store (this allows for horizontal scaling of the gateway and session revocation) 
  - The Demo uses a PostgreSQL database via the `spring-session-jdbc` adaptor, but any supported spring-session backend could be used

## How to run

1. Follow [this guide](https://spring.io/guides/tutorials/spring-boot-oauth2#github-application-config) to create a Github OAuth2 Client and save `clientId` and `clientSecret` to [application.yml](src/main/resources/application.yml).
2. Start the PostgreSQL container via `docker-compose up`
3. Start the BFF via `./mvn spring-boot:run`
4. Open `http://localhost:8080/get`

You will be authenticated via Github and then redirected to the upstream https://httpbin.org.
As visible in the printed request headers on that page, the Github access token is passed to the upstream service in the `Authorization` header.