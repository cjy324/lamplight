
SELECT * FROM `order`;
SELECT * FROM `member`;
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
    `relTypeCode` = 'director',
    relId = 9,
    memberId = 2,
    `point` = 3.5;
    
INSERT INTO rating
SET regDate = NOW(),
    updateDate = NOW(),
    `relTypeCode` = 'director',
    relId = 9,
    memberId = 1,
    `point` = 3.5;
    
INSERT INTO rating
SET regDate = NOW(),
    updateDate = NOW(),
    `relTypeCode` = 'director',
    relId = 10,
    memberId = 3,
    `point` = 2.5;
    

INSERT INTO rating
SET regDate = NOW(),
    updateDate = NOW(),
    `relTypeCode` = 'director',
    relId = 10,
    memberId = 3,
    `point` = 4.5;

# 평점 포인트 포함해서 지도사 리스팅
SELECT M.*,
IF(
(ROUND(AVG(R.point),1)-(ROUND(AVG(R.point),1)-0.5)) > 0.5,
 ROUND(AVG(R.point)),
 ROUND(AVG(R.point))-0.5
) AS extra__ratingPoint
FROM `member` AS M
LEFT JOIN rating AS R
ON R.relTypeCode = 'director'
AND M.id = R.relId
WHERE M.authLevel = 5
GROUP BY M.id
ORDER BY M.id DESC