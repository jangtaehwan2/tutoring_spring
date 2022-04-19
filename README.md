# Open Tutoring

# 목차
* 개요
* 개발환경
* 기능
* API DOCS

# 개요

# 개발환경
> Spring Initializr
> * Gradle Project
> * OpenJDK@11
> * SpringBoot 2.6.3
> Library
> * JDBC:MariaDB
> * JPA
> * Lombok
> * auth0:jwt
> Database
> * MariaDB
> Infra (예정)
> * AWS EC2
> * AWS S3
> * AWS RDS

# 시스템 아키텍쳐

# ER Diagram

# API DOCUMENT

### 공통
Request
* Method : 개별 Api 참고
* Path
  * Host/api/{path}
  * Ex) Login Api PATH /auth/login -> localhost:3000/api/auth/login
* Header
  * Content-type : application/json
  * Authorization : Token value
* Body
    * json type Request
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
