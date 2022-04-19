# tutoring_spring
tutoring project _ spring

# DaeWon Steel
> Spring Initializr
> * Gradle Project
> * OpenJDK@11
> * SpringBoot 2.6.3
>
> 
> library
> * MariaDB
> * JPA
> * Lombok
> * auth0:jwt

DaeWonApplication 을 통하여 실행한다.  
port : 5877  
JWT Algorithm : HS256
---

# API DOCUMENT

### 공통
Request
* Method : 개별 Api 참고
* Path
  * Host/api/{path}
  * Ex) Login Api PATH /auth/login -> localhost:3000/api/auth/login
* Header
  * Content-type : application/json
  * Authorization : Login Api Return 값 요구
* Body
    * json type Request
    * 요청 실패시 http error
---
## Auth
### 1. 로그인 (Login)
**Description**   

시스템에 로그인을 요청하기 위한 API 이다.
해당 API 의 응답으로 받는 값을 이후 다른 요청의 Header-Authorization 에 넣어 사용한다.
아래의 userPassword 값은 예시로, 해싱 된 값이 입력되도록 한다.

*Request*  
* HttpMethod : POST
* Path : /auth/login
* Body
```json
{
  "userId" : "user01",
  "userPassword" : "1q2w3e4r!"
}
```
*Response*
* Header
  * Content-Type : application/json
* Body
```json
{
  "key": "AuthorizationValue"
}
```
#API Template
**Description**

*Request*
* HttpMethod : 
* Path : 
* Header : Authorization, JWT(예정)
* Body
```json
{
}
```
*Response*
* Header
  * Content-Type : application/json
* Body
```json
{
}
```
