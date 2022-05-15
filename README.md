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

### 1-3. 유저조회
**Description**

userId를 통해 유저 1명의 정보를 조회한다.

*Request*
* HttpMethod : GET
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
  "id": 1,
  "userNickname": "admin",
  "userProfile": {
    "id": 1,
    "fileName": null,
    "fileSize": 0,
    "filePath": null,
    "description": null
  }
}
```


### 1-4. 유저 팀 목록 조회
**Description**

userId를 통해 유저 1명의 참여중인 팀 목록을 조회한다.

*Request*
* HttpMethod : GET
* Path : /user/{userId}/subscription
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
[
  {
    "id": 1,
    "name": "private",
    "tags": [
      "private"
    ],
    "description": null,
    "fileName": null,
    "fileSize": 0,
    "filePath": null,
    "type": "PRIVATE",
    "hostId": 1,
    "closed": false
  },
  {
    "id": 2,
    "name": "public",
    "tags": [
      "publicTeam"
    ],
    "description": null,
    "fileName": null,
    "fileSize": 0,
    "filePath": null,
    "type": "PUBLIC",
    "hostId": 1,
    "closed": false
  }
]
```



### 1-5. 정보수정
**Description**

회원의 닉네임 및 비밀번호를 재설정 한다.  
요청 시의 Token 값에 해당하는 유저의 정보를 수정하게 된다.

*Request*
* HttpMethod : PUT
* Path : /user
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


### 1-6. 프로필 수정
**Description**

회원의 프로필 description을 수정한다.  
추후 프로필 사진 변경 기능의 추가 예정이다.
요청 시의 Token 값에 해당하는 유저의 정보를 수정하게 된다.


*Request*
* HttpMethod : PUT
* Path : /user/profile
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


### 1-7. 회원탈퇴
**Description**

서비스에 등록된 회원을 삭제한다.  
요청 시의 Token 값에 해당하는 유저의 정보를 삭제하게 된다.

*Request*
* HttpMethod : DELETE
* Path : /user
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

## 2. Team
### 2-1. 팀생성
**Description**

튜터링을 진행할 팀을 생성한다.  
공개 팀인 public, 비고액 팀인 private 의 두가지 타입을 가진다.  
팀을 생성한 유저는 팀의 호스트가 된다.

*Request*
* HttpMethod : POST
* Path : /team
* Body
```json
{
  "name": "hello",
  "type" : "PUBLIC",
  "tags" : [
    "JAVA", "OOP", "SPRING BOOT"
  ]
}
```
*Response*
* Header
  * Content-Type : application/json
* Body
```json
{
  "teamId" : "1",
  "name": "hello",
  "type" : "PUBLIC",
  "tags" : [
    "JAVA", "OOP", "SPRING BOOT"
  ],
  "hostId" : "1"
}
```


### 2-2. 팀 목록 읽기
**Description**

팀 목록을 가져온다.  
이 때 PRIVATE 속성의 팀의 정보가 공개된 팀만 가져온다.  

*Request*
* HttpMethod : GET
* Path : /team
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
  "teams": [
    {
      "id": 1,
      "name": "JAVA STUDY",
      "tags": [
        "java",
        "spring",
        "oop"
      ],
      "description": null,
      "fileName": null,
      "fileSize": 0,
      "filePath": null,
      "type": "PUBLIC",
      "hostId": 1
    },
    {
      "id": 3,
      "name": "JAVAzxc",
      "tags": [
        "java",
        "spring",
        "oop"
      ],
      "description": null,
      "fileName": null,
      "fileSize": 0,
      "filePath": null,
      "type": "PUBLIC",
      "hostId": 1
    }
  ]
}
```

### 2-3. 팀 읽기
**Description**

팀 정보를 가져온다.

*Request*
* HttpMethod : GET
* Path : /team/{teamId}
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
  "id": 1,
  "name": "private",
  "tags": [
    "private"
  ],
  "description": null,
  "fileName": null,
  "fileSize": 0,
  "filePath": null,
  "type": "PRIVATE",
  "hostId": 1
}
```

### 2-4. 팀 참여하기
**Description**

팀에 참여하는 요청을 보낸다.  
요청 성공시 requestId를 반환하고, 실패 시 -1을 반환하며,
public 팀은 요청과 동시에 참여되어 0을 반환한다.

*Request*
* HttpMethod : POST
* Path : /team/{teamId}/join
* Body
```json
{
  "description": "hello, world"
}
```
*Response*
* Header
  * Content-Type : application/json
* Body  

**성공시**
```json
{
  "message": "Requested to join",
  "requestId": 1,
  "teamId": 1,
  "userId": 1
}
```
**실패시**
```json
{
"message": "Already subscription or requested to join",
"requestId": -1,
"teamId": 1,
"userId": 1
}
```

### 2-5. 팀 참여 요청 보기
**Description**

팀에 참여하는 요청을 확인한다.  
팀의 호스트만 사용 할 수 있다.

*Request*
* HttpMethod : GET
* Path : /team/{teamId}/join
* Body
```json
{
}
```
*Response*
* Header
  * Content-Type : application/json
* Body

**성공시**
```json
{
  "message": "Requested to join",
  "requestId": 1,
  "teamId": 1,
  "userId": 1
}
```

### 2-6. 팀 요청 수락하기
**Description**

팀에 참여하는 요청을 수락한다.  
팀의 호스트만 사용 할 수 있다.

*Request*
* HttpMethod : POST
* Path : /team/{teamId}/join/{joinId}
* Body
```json
{
}
```
*Response*
* Header
  * Content-Type : application/json
* Body

**성공시**
```json
{
  "teamId" : "1",
  "name": "hello",
  "type" : "PUBLIC",
  "tags" : [
    "JAVA", "OOP", "SPRING BOOT"
  ],
  "hostId" : "1"
}
```

### 2-7. 팀 참여 요청 거절하기
**Description**

팀에 참여하는 요청을 거절한다.  
팀의 호스트만 사용 할 수 있다.

*Request*
* HttpMethod : DELETE
* Path : /team/{teamId}/join/{joinId}
* Body
```json
{
}
```
*Response*
* Header
  * Content-Type : application/json
* Body

**성공시**
```json
{
  "message": "JoinRequest Rejected"
}
```

---

## 3. POST
### 3-1. 글 생성
**Description**

팀에 소속된 글을 작성한다.  
해당 글과 소속된 팀, 작성자에 대한 정보가 반환된다.


*Request*
* HttpMethod : POST
* Path : /team/{teamId}/post
* Body
```json
{
  "title" : "HELLO",
  "description" : "HELLO",
  "tags" : [
    "test",
    "test1",
    "test3"
  ]
}
```
*Response*
* Header
  * Content-Type : application/json
* Body
```json
{
  "id": 1,
  "title": "HELLO",
  "tags": [
    "test",
    "test1",
    "test3"
  ],
  "user": {
    "id": 1,
    "userNickname": "admin",
    "userProfile": {
      "id": 1,
      "fileName": null,
      "fileSize": 0,
      "filePath": null,
      "description": null
    }
  },
  "team": {
    "id": 1,
    "name": "private",
    "tags": [
      "private"
    ],
    "description": null,
    "fileName": null,
    "fileSize": 0,
    "filePath": null,
    "type": "PRIVATE",
    "hostId": 1
  }
}
```

### 3-2. 공개팀 글 읽기
**Description**

공개팀에 소속된 글의 리스트를 읽는다.  
리스트 형태로 제공된다.  

*Request*
* HttpMethod : GET
* Path : /post
* Body
```json
{
  "title" : "HELLO",
  "description" : "HELLO",
  "tags" : [
    "test",
    "test1",
    "test3"
  ]
}
```
*Response*
* Header
  * Content-Type : application/json
* Body
```json
{
  "id": 1,
  "title": "HELLO",
  "tags": [
    "test",
    "test1",
    "test3"
  ],
  "user": {
    "id": 1,
    "userNickname": "admin",
    "userProfile": {
      "id": 1,
      "fileName": null,
      "fileSize": 0,
      "filePath": null,
      "description": null
    }
  },
  "team": {
    "id": 2,
    "name": "public",
    "tags": [
      "public"
    ],
    "description": null,
    "fileName": null,
    "fileSize": 0,
    "filePath": null,
    "type": "PUBLIC",
    "hostId": 1
  }
}
```

### 3-3. 특정 팀의 글목록 읽기
**Description**

특정 팀에 소속된 글의 리스트를 읽는다.  
리스트 형태로 제공된다.

*Request*
* HttpMethod : GET
* Path : /team/{teamId}/post
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
[
  {
    "post": {
      "id": 1,
      "title": "HELLO",
      "tags": [
        "test",
        "test1",
        "test3"
      ],
      "description": "HELLO"
    },
    "team": {
      "id": 1,
      "name": "private",
      "tags": [
        "private"
      ],
      "description": null,
      "fileName": null,
      "fileSize": 0,
      "filePath": null,
      "type": "PRIVATE",
      "hostId": 1
    },
    "user": {
      "id": 1,
      "userNickname": "admin",
      "userProfile": {
        "id": 1,
        "fileName": null,
        "fileSize": 0,
        "filePath": null,
        "description": null
      }
    }
  }
]
```

### 3-4. 특정 글 읽기
**Description**

특정 팀에 소속된 특정 글을 읽는다.

*Request*
* HttpMethod : GET
* Path : /team/{teamId}/post/{postId}
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
  "post": {
    "id": 1,
    "title": "HELLO",
    "tags": [
      "test",
      "test1",
      "test3"
    ],
    "description": "HELLO"
  },
  "team": {
    "id": 1,
    "name": "private",
    "tags": [
      "private"
    ],
    "description": null,
    "fileName": null,
    "fileSize": 0,
    "filePath": null,
    "type": "PRIVATE",
    "hostId": 1,
    "closed": false
  },
  "user": {
    "id": 1,
    "userNickname": "admin",
    "userProfile": {
      "id": 1,
      "fileName": null,
      "fileSize": 0,
      "filePath": null,
      "description": null
    }
  }
}
```

### 3-5. 특정 글에 댓글 생성
**Description**

특정 글의 하위에 댓글을 생성한다.

*Request*
* HttpMethod : POST
* Path : /team/{teamId}/post/{postId}/comment
* Body
```json
{
  "description" : "hello"
}
```
*Response*
* Header
  * Content-Type : application/json
* Body
```json
{
  "id" : "1",
  "description" : "hello"
}
```

### 3-6. 특정 글의 댓글 읽기
**Description**

특정 글의 하위에 작성된 댓글들을 읽는다.

*Request*
* HttpMethod : GET
* Path : /team/{teamId}/post/{postId}/comment
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
  "post": {
    "id": 1,
    "title": "HELLO",
    "tags": [
      "test",
      "test1",
      "test3"
    ],
    "description": "HELLO"
  },
  "team": {
    "id": 1,
    "name": "private",
    "tags": [
      "private"
    ],
    "description": null,
    "fileName": null,
    "fileSize": 0,
    "filePath": null,
    "type": "PRIVATE",
    "hostId": 1,
    "closed": false
  },
  "user": {
    "id": 1,
    "userNickname": "admin",
    "userProfile": {
      "id": 1,
      "fileName": null,
      "fileSize": 0,
      "filePath": null,
      "description": null
    }
  }
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
