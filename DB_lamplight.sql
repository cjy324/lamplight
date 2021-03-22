# 데이터베이스 생성
DROP DATABASE IF EXISTS lamplight;
CREATE DATABASE lamplight;
USE lamplight;

# 요청사항 테이블 생성
CREATE TABLE `order` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    option1 CHAR(200) NOT NULL,
    option1qty INT(10) UNSIGNED NOT NULL,
    option2 CHAR(200) NOT NULL,
    option2qty INT(10) UNSIGNED NOT NULL,
    option3 CHAR(200) NOT NULL,
    option3qty INT(10) UNSIGNED NOT NULL,
    option4 CHAR(200) NOT NULL,
    option4qty INT(10) UNSIGNED NOT NULL,
    option5 CHAR(200) NOT NULL,
    option5qty INT(10) UNSIGNED NOT NULL,
    title CHAR(200) NOT NULL,
    `body` TEXT NOT NULL,
    funeralHome CHAR(200) NOT NULL,
    `directorId` INT(10) UNSIGNED NOT NULL,
    `clientId` INT(10) UNSIGNED NOT NULL,
    stepLevel SMALLINT(2) UNSIGNED DEFAULT 1 NOT NULL COMMENT '(1=의뢰요청(의뢰검토),2=의뢰승인(장례준비),3=장례진행중,4=장례종료(결제미완료),5=결제완료)'
);

# 테스트 의뢰 생성
INSERT INTO `order`
SET regDate = NOW(),
    updateDate = NOW(),
    option1 = '옵션1',
    option1qty = 10,
    option2 = '옵션2',
    option2qty = 20,
    option3 = '옵션3',
    option3qty = 30,
    option4 = '옵션4',
    option4qty = 40,
    option5 = '옵션5',
    option5qty = 50,
    title = 'user2님 의뢰',
    funeralHome = '대전장례식장',
    `body` = '기타 요청 사항',
    `directorId` = 4,
    `clientId` = 2;

INSERT INTO `order`
SET regDate = NOW(),
    updateDate = NOW(),
    option1 = '옵션1',
    option1qty = 10,
    option2 = '옵션2',
    option2qty = 20,
    option3 = '옵션3',
    option3qty = 30,
    option4 = '옵션4',
    option4qty = 40,
    option5 = '옵션5',
    option5qty = 50,
    title = 'user3님 의뢰',
    funeralHome = '익산장례식장',
    `body` = '기타 요청 사항2',
    `directorId` = 4,
    `clientId` = 3;
 
# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(30) NOT NULL,
    loginPw VARCHAR(100) NOT NULL,
    authKey CHAR(80) NOT NULL,
    authLevel SMALLINT(2) UNSIGNED DEFAULT 3 NOT NULL COMMENT '(3=의뢰인,4=도우미,5=지도사,7=관리자)',
    `name` CHAR(30) NOT NULL,
    `nickname` CHAR(30) NOT NULL,
    `email` CHAR(100) NOT NULL,
    `cellphoneNo` CHAR(20) NOT NULL,
    `address` CHAR(100) NOT NULL

);

# 로그인 ID로 검색했을 때
ALTER TABLE `member` ADD UNIQUE INDEX (`loginId`);

# authKey 칼럼에 유니크 인덱스 추가
ALTER TABLE `member` ADD UNIQUE INDEX (`authKey`);

# 회원, 테스트 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'user1',
    loginPw = 'user1',
    authKey = 'authKey1__1',
    authLevel = 7,
    `name` = 'user1',
    `nickname` = 'user1',
    `email` = 'user1@user1.com',
    `cellphoneNo` = 01011111111,
    `address` = '대전광역시';

INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'user2',
    loginPw = 'user2',
    authKey = 'authKey1__2',
    authLevel = 3,
    `name` = 'user2',
    `nickname` = 'user2',
    `email` = 'user2@user2.com',
    `cellphoneNo` = 01022222222,
    `address` = '경기도';

INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'user3',
    loginPw = 'user3',
    authKey = 'authKey1__3',
    authLevel = 3,
    `name` = 'user3',
    `nickname` = 'user3',
    `email` = 'user3@user3.com',
    `cellphoneNo` = 01033333333,
    `address` = '전라북도';
    
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'tester1',
    loginPw = 'tester1',
    authKey = 'authKey1__4',
    authLevel = 5,
    `name` = 'tester1',
    `nickname` = 'tester1',
    `email` = 'tester1@tester1.com',
    `cellphoneNo` = 01044444444,
    `address` = '대전광역시';


INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'tester2',
    loginPw = 'tester2',
    authKey = 'authKey1__5',
    authLevel = 5,
    `name` = 'tester2',
    `nickname` = 'tester2',
    `email` = 'tester2@tester2.com',
    `cellphoneNo` = 01055555555,
    `address` = '경기도';

INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'tester3',
    loginPw = 'tester3',
    authKey = 'authKey1__6',
    authLevel = 5,
    `name` = 'tester3',
    `nickname` = 'tester3',
    `email` = 'tester3@tester3.com',
    `cellphoneNo` = 01066666666,
    `address` = '전라북도';
    
INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'tester4',
    loginPw = 'tester4',
    authKey = 'authKey1__7',
    authLevel = 5,
    `name` = 'tester4',
    `nickname` = 'tester4',
    `email` = 'tester4@tester4.com',
    `cellphoneNo` = 01044444444,
    `address` = '대전광역시';


INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'tester5',
    loginPw = 'tester5',
    authKey = 'authKey1__8',
    authLevel = 5,
    `name` = 'tester5',
    `nickname` = 'tester5',
    `email` = 'tester5@tester5.com',
    `cellphoneNo` = 01055555555,
    `address` = '경기도';

INSERT INTO `member`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'tester6',
    loginPw = 'tester6',
    authKey = 'authKey1__9',
    authLevel = 5,
    `name` = 'tester6',
    `nickname` = 'tester6',
    `email` = 'tester6@tester6.com',
    `cellphoneNo` = 01066666666,
    `address` = '전라북도';

# 파일 테이블 추가
CREATE TABLE genFile (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT, # 번호
  regDate DATETIME DEFAULT NULL, # 작성날짜
  updateDate DATETIME DEFAULT NULL, # 갱신날짜
  delDate DATETIME DEFAULT NULL, # 삭제날짜
  delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0, # 삭제상태(0:미삭제,1:삭제)
  relTypeCode CHAR(50) NOT NULL, # 관련 데이터 타입(article, member)
  relId INT(10) UNSIGNED NOT NULL, # 관련 데이터 번호
  originFileName VARCHAR(100) NOT NULL, # 업로드 당시의 파일이름
  fileExt CHAR(10) NOT NULL, # 확장자
  typeCode CHAR(20) NOT NULL, # 종류코드 (common)
  type2Code CHAR(20) NOT NULL, # 종류2코드 (attatchment)
  fileSize INT(10) UNSIGNED NOT NULL, # 파일의 사이즈(byte)
  fileExtTypeCode CHAR(10) NOT NULL, # 파일규격코드(img, video)
  fileExtType2Code CHAR(10) NOT NULL, # 파일규격2코드(jpg, mp4)
  fileNo SMALLINT(2) UNSIGNED NOT NULL, # 파일번호 (1)
  fileDir CHAR(20) NOT NULL, # 파일이 저장되는 폴더명
  PRIMARY KEY (id),
  KEY relId (relId,relTypeCode,typeCode,type2Code,fileNo)
); 


# 리뷰 테이블 추가
CREATE TABLE review (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  `relTypeCode` CHAR(20) NOT NULL,
  relId INT(10) UNSIGNED NOT NULL,
  memberId INT(10) UNSIGNED NOT NULL,
  `body` TEXT NOT NULL
);

# 고속 검색을 위해서 인덱스 걸기
ALTER TABLE review ADD KEY (relTypeCode, relId); 
# SELECT * FROM reply WHERE relTypeCode = 'article' AND relId = 5; # O
# SELECT * FROM reply WHERE relTypeCode = 'article'; # O
# SELECT * FROM reply WHERE relId = 5 AND relTypeCode = 'article'; # X


# 평점 테이블 추가
CREATE TABLE starRating (
  id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  regDate DATETIME NOT NULL,
  updateDate DATETIME NOT NULL,
  `relTypeCode` CHAR(20) NOT NULL,
  relId INT(10) UNSIGNED NOT NULL,
  memberId INT(10) UNSIGNED NOT NULL,
  `point` SMALLINT(1)  # 좋아요 시 +1, 싫어요 시 -1 등 가능
);

# 고속 검색을 위해서 인덱스 걸기
ALTER TABLE starRating ADD KEY (relTypeCode, relId); 
# SELECT * FROM reply WHERE relTypeCode = 'article' AND relId = 5; # O
# SELECT * FROM reply WHERE relTypeCode = 'article'; # O
# SELECT * FROM reply WHERE relId = 5 AND relTypeCode = 'article'; # X
