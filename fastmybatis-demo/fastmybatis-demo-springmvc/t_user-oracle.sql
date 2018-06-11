
CREATE TABLE "T_USER" (
"ID" INTEGER NOT NULL ,
"USERNAME" VARCHAR2(50 BYTE) NULL ,
"STATE" SMALLINT NULL ,
"ISDEL" CHAR(1 BYTE) NULL ,
"REMARK" VARCHAR2(255 BYTE) NULL ,
"ADD_TIME" DATE NULL ,
"MONEY" NUMBER(10,2) NULL ,
"LEFT_MONEY" NUMBER(10,2) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;


INSERT INTO "T_USER" VALUES ('1', '张三', '1', '0', '备注', TO_DATE('2017-08-23 14:41:26', 'YYYY-MM-DD HH24:MI:SS'), '100.50', '10');
INSERT INTO "T_USER" VALUES ('2', '李四', '1', '0', '备注2', TO_DATE('2017-08-23 14:41:53', 'YYYY-MM-DD HH24:MI:SS'), '100.20', '10.2');
INSERT INTO "T_USER" VALUES ('3', '王五', '1', '0', '备注', TO_DATE('2017-08-23 14:44:53', 'YYYY-MM-DD HH24:MI:SS'), '100.50', '10');
INSERT INTO "T_USER" VALUES ('4', '张三', '1', '0', '备注', TO_DATE('2017-08-23 14:44:53', 'YYYY-MM-DD HH24:MI:SS'), '100.50', '10');
INSERT INTO "T_USER" VALUES ('5', '张三', '1', '0', '备注', TO_DATE('2017-08-23 14:44:53', 'YYYY-MM-DD HH24:MI:SS'), '100.50', '10');


CREATE TABLE "USER_INFO" (
"ID" INTEGER NOT NULL ,
"USER_ID" NUMBER NULL ,
"CITY" VARCHAR2(255 BYTE) NULL ,
"ADDRESS" VARCHAR2(255 BYTE) NULL ,
"STATUS" VARCHAR2(4) DEFAULT '0' NULL,
"CREATE_TIME" DATE NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;


INSERT INTO "USER_INFO" VALUES ('1', '1', '杭州', '延安路','0', TO_DATE('2017-08-23 14:46:12', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "USER_INFO" VALUES ('2', '2', '杭州', '延安路','0', TO_DATE('2017-08-23 14:46:27', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "USER_INFO" VALUES ('3', '3', '杭州', '延安路','0', TO_DATE('2017-08-23 14:46:39', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "USER_INFO" VALUES ('4', '4', '杭州', '延安路','0', TO_DATE('2017-08-23 14:46:39', 'YYYY-MM-DD HH24:MI:SS'));
INSERT INTO "USER_INFO" VALUES ('5', '5', '杭州', '延安路','0', TO_DATE('2017-08-23 14:46:39', 'YYYY-MM-DD HH24:MI:SS'));


CREATE TABLE "ADDRESS" (
"ID" VARCHAR2(100 BYTE) NOT NULL ,
"ADDRESS" VARCHAR2(255 BYTE) NULL 
)
LOGGING
NOCOMPRESS
NOCACHE

;

CREATE SEQUENCE T_USER_ID_SEQ MINVALUE 1 NOMAXVALUE INCREMENT BY 1 START WITH 6 NOCACHE;

CREATE OR REPLACE TRIGGER T_USER_INS_TRG BEFORE INSERT ON T_USER FOR EACH ROW WHEN(NEW.ID IS NULL)
BEGIN
SELECT T_USER_ID_SEQ.NEXTVAL INTO :NEW.ID FROM DUAL;
END;

ALTER TABLE "T_USER" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "T_USER" ADD PRIMARY KEY ("ID");


CREATE SEQUENCE USER_INFO_ID_SEQ MINVALUE 1 NOMAXVALUE INCREMENT BY 1 START WITH 6 NOCACHE;

CREATE OR REPLACE TRIGGER USER_INFO_INS_TRG BEFORE INSERT ON USER_INFO FOR EACH ROW WHEN(NEW.ID IS NULL)
BEGIN
SELECT USER_INFO_ID_SEQ.NEXTVAL INTO :NEW.ID FROM DUAL;
END;

ALTER TABLE "USER_INFO" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "USER_INFO" ADD PRIMARY KEY ("ID");

ALTER TABLE "ADDRESS" ADD CHECK ("ID" IS NOT NULL);
ALTER TABLE "ADDRESS" ADD PRIMARY KEY ("ID");
