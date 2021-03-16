# 데이터베이스 생성
DROP DATABASE IF EXISTS lamplight;
CREATE DATABASE lamplight;
USE lamplight;

# 요청사항 테이블 생성
CREATE TABLE `order` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    option1 CHAR(100) NOT NULL,
    option1qty INT(10) UNSIGNED NOT NULL,
    option2 CHAR(100) NOT NULL,
    option2qty INT(10) UNSIGNED NOT NULL,
    option3 CHAR(100) NOT NULL,
    option3qty INT(10) UNSIGNED NOT NULL,
    option4 CHAR(100) NOT NULL,
    option4qty INT(10) UNSIGNED NOT NULL,
    option5 CHAR(100) NOT NULL,
    option5qty INT(10) UNSIGNED NOT NULL,
    `body` TEXT NOT NULL,
    `directorId` INT(10) UNSIGNED NOT NULL,
    `clientId` INT(10) UNSIGNED NOT NULL
);

# 의뢰인 회원 테이블 생성
CREATE TABLE `client` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(30) NOT NULL,
    loginPw VARCHAR(100) NOT NULL,
    authKey CHAR(80) NOT NULL,
    authLevel SMALLINT(2) UNSIGNED DEFAULT 3 NOT NULL COMMENT '(3=일반,7=관리자)',
    `name` CHAR(30) NOT NULL,
    `nickname` CHAR(30) NOT NULL,
    `email` CHAR(100) NOT NULL,
    `cellphoneNo` CHAR(20) NOT NULL,
    `address_state` CHAR(100) NOT NULL,
    `address_city` CHAR(100) NOT NULL,
    `address_street` CHAR(100) NOT NULL
);

# 로그인 ID로 검색했을 때
ALTER TABLE `client` ADD UNIQUE INDEX (`loginId`);

# authKey 칼럼에 유니크 인덱스 추가
ALTER TABLE `client` ADD UNIQUE INDEX (`authKey`);

# 회원, 테스트 데이터 생성
INSERT INTO `client`
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
    `address_state` = '대전광역시',
    `address_city` = '유성구',
    `address_street` = '반석동';

INSERT INTO `client`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'user2',
    loginPw = 'user2',
    authKey = 'authKey2__2',
    authLevel = 3,
    `name` = 'user2',
    `nickname` = 'user2',
    `email` = 'user2@user2.com',
    `cellphoneNo` = 01022222222,
    `address_state` = '경기도',
    `address_city` = '이천시',
    `address_street` = '장수동';

INSERT INTO `client`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'user3',
    loginPw = 'user3',
    authKey = 'authKey3__3',
    authLevel = 3,
    `name` = 'user3',
    `nickname` = 'user3',
    `email` = 'user3@user3.com',
    `cellphoneNo` = 01033333333,
    `address_state` = '전라북도',
    `address_city` = '익산시',
    `address_street` = '익산동';
    
# 의뢰인 회원 테이블 생성
CREATE TABLE `director` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(30) NOT NULL,
    loginPw VARCHAR(100) NOT NULL,
    authKey CHAR(80) NOT NULL,
    authLevel SMALLINT(2) UNSIGNED DEFAULT 3 NOT NULL COMMENT '(3=일반,7=관리자)',
    `name` CHAR(30) NOT NULL,
    `nickname` CHAR(30) NOT NULL,
    `email` CHAR(100) NOT NULL,
    `cellphoneNo` CHAR(20) NOT NULL,
    `address_state` CHAR(100) NOT NULL,
    `address_city` CHAR(100) NOT NULL,
    `address_street` CHAR(100) NOT NULL
);

# 로그인 ID로 검색했을 때
ALTER TABLE `director` ADD UNIQUE INDEX (`loginId`);

# authKey 칼럼에 유니크 인덱스 추가
ALTER TABLE `director` ADD UNIQUE INDEX (`authKey`);

# 회원, 테스트 데이터 생성
INSERT INTO `director`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'tester1',
    loginPw = 'tester1',
    authKey = 'authKey1__4',
    authLevel = 3,
    `name` = 'tester1',
    `nickname` = 'tester1',
    `email` = 'tester1@tester1.com',
    `cellphoneNo` = 01044444444,
    `address_state` = '대전광역시',
    `address_city` = '유성구',
    `address_street` = '반석동';

INSERT INTO `director`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'tester2',
    loginPw = 'tester2',
    authKey = 'authKey5__5',
    authLevel = 3,
    `name` = 'tester2',
    `nickname` = 'tester2',
    `email` = 'tester2@tester2.com',
    `cellphoneNo` = 01055555555,
    `address_state` = '경기도',
    `address_city` = '이천시',
    `address_street` = '장수동';

INSERT INTO `director`
SET regDate = NOW(),
    updateDate = NOW(),
    loginId = 'tester3',
    loginPw = 'tester3',
    authKey = 'authKey6__6',
    authLevel = 3,
    `name` = 'tester3',
    `nickname` = 'tester3',
    `email` = 'tester3@tester3.com',
    `cellphoneNo` = 01066666666,
    `address_state` = '전라북도',
    `address_city` = '익산시',
    `address_street` = '익산동';

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
