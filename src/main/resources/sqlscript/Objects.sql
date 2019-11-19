 /*
 OBJTYPE - таблица описаний объектных типов
 attrTTRTYPE - таблица описаний атрибутных типов
 LISTS - список для листовых значений
 OBJECTS - таблица обьектов 
 attrTTRIBUTES - таблица атрибутов 
 OBJREFERENCE - описаний связей "простая ассоциация" между экземплярами объектов
 */

 -- Создание родительских обьектов


--Создание обьектов User1
INSERT INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,1,'user1','user1'); --ID-1
--Создание пользователя
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,null,2,'PER_DEB_ACC1','Personal debet account1'); --  ID-2
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,null,13,'FAM_DEB_ACC1','Family debet account1'); --  ID-3
--создание персонального и семейного счета 
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,3,'PER_REP1','Personal report1'); --  ID-4
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,14,'FAM_REP1','Family report1'); --  ID-5
--Персональные и семейные репорты
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,4,'CAT_REP_PER_INC1','Catery report personal income1');-- ID-6
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,15,'CAT_REP_FAM_INC1','Catery report family income1');-- ID-7
--персональный и симейный отчет по категории доходов
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,5,'CAT_REP_PER_EXP1','Catery report personal expense1');-- ID-8
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,16,'CAT_REP_FAM_EXP1','Catery report family expense1');-- ID-9
--персональный и семейный отчет по категории расходов
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,6,'CRED_ACC_PER1','credit account Personal1');-- ID-10
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,17,'CRED_ACC_FAM1','credit account Family1');-- ID-11
--персональный и симейный кредитный аккаунт
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,7,'CRED_OPER_PER1','credit operation Personal1');-- ID-12
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,18,'CRED_OPER_FAM1','credit operation Family1');-- ID-13
--персональные и симейные кредитные операции
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,8,'DEBT_PER1','debt Personal1');-- ID-14
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,19,'DEBT_FAM1','debt family1');-- ID-15
--персональный и симейные долги
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,9,'ACC_EXPEN_PER1','account expense personal1');-- ID-16
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,20,'ACC_EXPEN_FAM1','account expense Family1');-- ID-17
--персональные и симейные расходы
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,10,'ACC_INC_PER1','account income personal1');-- ID-18
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,21,'ACC_INC_FAM1','account income Family1');-- ID-19
--персональные и симейные доходы
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,11,'ACC_AUTO_EXPEN_PER1','account autoexpense personal1');-- ID-20
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,22,'ACC_AUTO_EXPEN_FAM1','account autoexpense personal family1');-- ID-21
--персональные и симейные авторасходы
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,12,'ACC_AUTO_INC_PER1','account autoincome personal1');-- ID-22
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,23,'ACC_AUTO_INC_FAM1','account autoincome personal family1');-- ID-23
--персональные и симейные автодоходы

--Создание обьектов User2
INSERT INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,1,'user2','user2'); --ID-24
--Создание пользователя
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,null,2,'PER_DEB_ACC2','Personal debet account2'); --  ID-25
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,null,13,'FAM_DEB_ACC2','Family debet account2'); --  ID-26
--создание персонального и семейного счета 
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,3,'PER_REP2','Personal report2'); --  ID-27
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,14,'FAM_REP2','Family report2'); --  ID-28
--Персональные и семейные репорты
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,4,'CAT_REP_PER_INC2','Catery report personal income2');-- ID-29
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,15,'CAT_REP_FAM_INC2','Catery report family income2');-- ID-30
--персональный и симейный отчет по категории доходов
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,5,'CAT_REP_PER_EXP2','Catery report personal expense2');-- ID-31
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,16,'CAT_REP_FAM_EXP2','Catery report family expense2');-- ID-32
--персональный и семейный отчет по категории расходов
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,6,'CRED_ACC_PER2','credit account Personal2');-- ID-33
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,17,'CRED_ACC_FAM2','credit account Family2');-- ID-34
--персональный и симейный кредитный аккаунт
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,7,'CRED_OPER_PER2','credit operation Personal2');-- ID-35
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,18,'CRED_OPER_FAM2','credit operation Family2');-- ID-36
--персональные и симейные кредитные операции
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,8,'DEBT_PER2','debt Personal2');-- ID-37
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,19,'DEBT_FAM2','debt family2');-- ID-38
--персональный и симейные долги
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,9,'ACC_EXPEN_PER2','account expense personal2');-- ID-39
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,20,'ACC_EXPEN_FAM2','account expense Family2');-- ID-40
--персональные и симейные расходы
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,10,'ACC_INC_PER2','account income personal2');-- ID-41
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,21,'ACC_INC_FAM2','account income Family2');-- ID-42
--персональные и симейные доходы
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,11,'ACC_AUTO_EXPEN_PER2','account autoexpense personal2');-- ID-43
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,22,'ACC_AUTO_EXPEN_FAM2','account autoexpense personal family2');-- ID-44
--персональные и симейные авторасходы
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,12,'ACC_AUTO_INC_PER2','account autoincome personal2');-- ID-45
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,23,'ACC_AUTO_INC_FAM2','account autoincome personal family2');-- ID-46
--персональные и симейные автодоходы

--Создание обьектов User3
INSERT INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,1,'user3','user3'); --ID-47
--Создание пользователя
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,null,2,'PER_DEB_ACC3','Personal debet account3'); --  ID-48
--создание персонального

--Создание обьектов User4
INSERT INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,NULL,1,'user4','user4'); --ID-49
--Создание пользователя
INSERT INTO OBJECTS (OBJECT_TYPE_ID,PARENT_ID,NAME,DESCRIPTION) VALUES (objects_id_s.nextval,null,2,'PER_DEB_ACC4','Personal debet account4'); --  ID-50
--создание персонального