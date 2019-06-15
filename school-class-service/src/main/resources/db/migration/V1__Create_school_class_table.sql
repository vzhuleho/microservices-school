CREATE TABLE SCHOOL_CLASS
(
  SCHOOL_CLASS_ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  GRADE           TINYINT                           NOT NULL,
  LETTER          VARCHAR(2)                        NOT NULL,
  YEAR            INT                               NOT NULL,
  TEACHER_ID      BIGINT
)
