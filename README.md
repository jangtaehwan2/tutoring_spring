# Open Tutoring
[Github](https://github.com/taehwan0/tutoring_spring)  
[Figma](https://www.figma.com/file/Vr5GYbDjsIN8t0UEC6Ze3I/OpenTutoringProject?node-id=0%3A1)


# 목차
* 개요
* 기능
* 개발환경
* 추가 예정 ~~내부 핵심 기능~~
* 추가 예정 ~~시스템 아키텍쳐~~
* 추가 예정 ~~ER Diagram~~
* API Document

# 개요
본 서비스는 '팀'이라 부르는 그룹을 기반으로 팀 내에서 게시글을 통해 지식을 공유하는 것을 목적으로 합니다.  
모든 기능은 회원가입을 거쳐 사용자 인증(로그인) 후 사용이 가능합니다. 
오픈된 팀의 게시글은 해당 팀에 가입하지 않더라도 누구나 볼 수 있으며, 팀 페이지를 통해 해당 팀의 게시글만 모아 볼 수 있습니다.  
누구나 자유롭게 팀을 생성, 가입하고 게시글을 통해 지식을 공유합니다.

# 기능
* 회원
  * 회원가입
  * 회원탈퇴
  * 프로필 관리
  * 비밀번호 초기화
* 인증
  * 로그인
* 팀
  * 팀 CRUD
  * 팀 프로필 관리
  * 팀 가입 신청
  * 팀 가입 승인
  * 게시글
    * 게시글 CRUD
    * 댓글 CRUD

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
> * DBeaver


> Infra (예정)
> * AWS EC2
> * AWS S3
> * AWS RDS

# 핵심기능

# 시스템 아키텍쳐

# ER Diagram

# API
### 공통
**Request**
* Method : 개별 Api 참고
* Path
  * /api/{path}
  * Ex) Login Api PATH /auth/login -> localhost:3000/api/auth/login
* Header
  * Content-type : application/json
  * Authorization : Token
* Body
    * json
---
## 1. User
### 1-1. 로그인
**Description**   

시스템에 로그인한다.
해당 API 의 응답으로 받는 값을 이후 다른 요청의 Header-Authorization 에 넣어 사용한다.
아래의 userPassword 값은 예시로, 해싱 된 값이 입력되도록 한다.

*Request*  
* HttpMethod : POST
* Path : /user/login
* Body
```json
{
  "userName" : "user01",
  "userPassword" : "1q2w3e4r!"
}
```
*Response*
* Header
  * Content-Type : application/json
* Body
```json
{
  "key": "AuthorizationValue",
  "id": "1",
  "userName" : "user01",
  "userNickname" : "Buddy",
  "userProfile" : {
    "id" : "1",
    "fileName" : "",
    "fileSize" : "",
    "filePath" : "",
    "description" : "안녕하세요. 개발자 Buddy 입니다."
  }
}
```

### 1-2. 회원가입
**Description**

서비스에 회원을 등록한다.
아이디는 특수문자 사용을 불허하며, 1~20글자로 이루어진다.  
닉네임 또한 1~20글자로 이루어지며, 특수문자의 제한은 없다.
아래의 userPassword 값은 예시로, 해싱 된 값이 입력되도록 한다.

*Request*
* HttpMethod : POST
* Path : /user
* Body
```json
{
  "userName" : "user01",
  "userNickname" : "Buddy",
  "userPassword" : "1q2w3e4r!"
}
```
*Response*
* Header
  * Content-Type : application/json
* Body
```json
{
  "id" : "1",
  "userName" : "user01",
  "userNickname" : "Buddy"
}
```


### 1-3. 정보수정
**Description**

회원의 닉네임 및 비밀번호를 재설정 한다.  
Token 내의 아이디와 요청 시 userId가 동일해야한다.

*Request*
* HttpMethod : PUT
* Path : /user/{userId}
* Body
```json
{
  "userNickname" : "Navi",
  "userPassword" : "hello,world"
}
```
*Response*
* Header
  * Content-Type : application/json
* Body
```json
{
  "id" : "1",
  "userName" : "user01",
  "userNickname" : "Navi"
}
```


### 1-4. 프로필 수정
**Description**

회원의 프로필 description을 수정한다.  
추후 프로필 사진 변경 기능의 추가 예정이다.
Token 내의 아이디와 요청 시 userId가 동일해야한다.

*Request*
* HttpMethod : PUT
* Path : /user/{userId}/profile
* Body
```json
{
  "description" : "hello, world"
}
```
*Response*
* Header
  * Content-Type : application/json
* Body
```json
{
  "id" : "1",
  "fileName" : null,
  "fileSize" : 0,
  "filePath" : null,
  "description" : "hello, world"
}
```


### 1-4. 회원탈퇴
**Description**

서비스에 등록된 회원을 삭제한다.  
Token 내의 아이디와 요청 시 userId가 동일해야한다.

*Request*
* HttpMethod : DELETE
* Path : /user/{userId}
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
  "message" : "User Delete"
}
```




---


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
