
SELECT * FROM `order`;
SELECT * FROM `client`;
SELECT * FROM `expert`;
SELECT * FROM `assistant`;
SELECT * FROM `genFile`;
SELECT * FROM `review`;
SELECT * FROM `rating`;

TRUNCATE `genFile`;
TRUNCATE `review`;
TRUNCATE `rating`;

# 게시물 랜덤생성 쿼리
INSERT INTO article
(regDate, updateDate, memberId, boardId, title, `body`)
SELECT NOW(), NOW(), FLOOR(RAND() * 2) + 1, FLOOR(RAND() * 2) + 1, CONCAT('제목_', FLOOR(RAND() * 1000) + 1), CONCAT('내용_', FLOOR(RAND() * 1000) + 1)
FROM article;

INSERT INTO rating
SET regDate = NOW(),
    updateDate = NOW(),
    `relTypeCode` = 'expert',
    relId = 2,
    clientId = 2,
    `point` = 3.5;
    
INSERT INTO rating
SET regDate = NOW(),
    updateDate = NOW(),
    `relTypeCode` = 'expert',
    relId = 2,
    clientId = 3,
    `point` = 3.5;
    
INSERT INTO rating
SET regDate = NOW(),
    updateDate = NOW(),
    `relTypeCode` = 'expert',
    relId = 2,
    clientId = 1,
    `point` = 3.5;
    

# 평점 포인트 포함해서 지도사 리스팅
SELECT E.*,
IF(
(ROUND(AVG(R.point),1)-(ROUND(AVG(R.point),1)-0.5)) > 0.5,
 ROUND(AVG(R.point)),
 ROUND(AVG(R.point))-0.5
) AS extra__ratingPoint
FROM `expert` AS E
LEFT JOIN rating AS R
ON R.relTypeCode = 'expert'
AND E.id = R.relId
GROUP BY E.id
ORDER BY E.id DESC