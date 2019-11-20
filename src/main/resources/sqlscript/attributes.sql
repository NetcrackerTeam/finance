

 /*
 OBJTYPE - таблица описаний объектных типов
 ATTRTYPE - таблица описаний атрибутных типов
 LISTS - список для листовых значений
 OBJECTS - таблица обьектов 
 ATTRIBUTES - таблица атрибутов 
 OBJREFERENCE - описаний связей "простая ассоциация" между экземплярами объектов
 */
 
 
 -- атрибуты для юзера1
 insert into attributes(attr_id, object_id, values) values(3, 1, 'mail@gmail.com');
 insert into attributes(attr_id, object_id, values) values(4, 1, 'password');
 insert into attributes(attr_id, object_id, values) values(5, 1, 'Eugen');
 insert into attributes(attr_id, object_id, list_value_id) values(6, 1, 39);
 
	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,1,2);
 
	-- reference to family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (2,1,3);
 
 -- атрибуты для personal_deb_acc1
 insert into attributes(attr_id, object_id, values) values(7, 2, '5000');
 insert into attributes(attr_id, object_id, list_value_id) values(70, 2, 43);
 
 -- атрибуты для family_deb_acc1
 insert into attributes(attr_id, object_id, values) values(9, 3, '9000');
 insert into attributes(attr_id, object_id, list_value_id) values(69, 3, 41);
 
	-- reference to user1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,3,1);
-----------------------------------------------------------------------------
-- атрибуты для personal_report1
 insert into attributes(attr_id, object_id, values) values(12, 4, '4000');
 insert into attributes(attr_id, object_id, values) values(13, 4, '3500');
 insert into attributes(attr_id, object_id, values) values(14, 4, '5500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 4, '1-11-2019');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 4, '30-11-2019');
 
	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (10,4,2);
 -----------------------------------------------------------------------------
-- атрибуты для family_report1
 insert into attributes(attr_id, object_id, values) values(12, 5, '5000');
 insert into attributes(attr_id, object_id, values) values(13, 5, '4500');
 insert into attributes(attr_id, object_id, values) values(14, 5, '9500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 5, '1-12-2019');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 5, '30-12-2019');
 
	-- reference to family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (11,5,3);
-----------------------------------------------------------------------------	
-- атрибуты для personal_category_report income1
 insert into attributes(attr_id, object_id, list_value_id) values(20, 6, 32);
 insert into attributes(attr_id, object_id, values) values(21, 6, '5000');
	
	-- reference to personal_report1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (17,6,4);
 
-- атрибуты для family_category_report income1
 insert into attributes(attr_id, object_id, list_value_id) values(20, 7, 32);
 insert into attributes(attr_id, object_id, values) values(21, 7, '5000');
	
	-- reference to family_report1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (18,7,5);
 
	-- REFERENCE TO USER1 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (19,7,1);
-----------------------------------------------------------------------------	
-- атрибуты для personal_category_report expense1
 insert into attributes(attr_id, object_id, list_value_id) values(25, 8, 11);
 insert into attributes(attr_id, object_id, values) values(26, 8, '3500');
	
	-- reference to personal_report1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (22,8,4);
 
-- атрибуты для family_category_report expense1
 insert into attributes(attr_id, object_id, list_value_id) values(25, 9, 21);
 insert into attributes(attr_id, object_id, values) values(26, 9, '4500');
	
	-- reference to family_report1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (23,9,5);
 
	-- REFERENCE TO USER AS A PARTICIPANT FAMILY BILL1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (24,9,1);
----------------------------------------------------------------------------- 
 
-- атрибуты для credit account Personal1
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 10, SYSDATE);
 insert into attributes(attr_id, object_id, values) values(30, 10, 'Credit_Money1');
 insert into attributes(attr_id, object_id, values) values(31, 10, '10000');
 insert into attributes(attr_id, object_id, values) values(32, 10, '2000');
 insert into attributes(attr_id, object_id, values) values(33, 10, '20');
 insert into attributes(attr_id, object_id, values) values(34, 10, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 10, 38);
 insert into attributes(attr_id, object_id, list_value_id) values(36, 10, 1);
	-- reference to personal_deb1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (27,10,2);
 
-- атрибуты для credit account Family1
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 11, SYSDATE);
 insert into attributes(attr_id, object_id, values) values(30, 11, 'Credit_Money1');
 insert into attributes(attr_id, object_id, values) values(31, 11, '15000');
 insert into attributes(attr_id, object_id, values) values(32, 11, '3000');
 insert into attributes(attr_id, object_id, values) values(33, 11, '20');
 insert into attributes(attr_id, object_id, values) values(34, 11, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 11, 38);
 insert into attributes(attr_id, object_id, list_value_id) values(36, 11, 1);
 	
	-- reference to family_deb1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (28,11,3);
 ----------------------------------------------------------------------------- 
 
-- атрибуты для credit operation Personal1
 insert into attributes(attr_id, object_id, VALUES) values(40, 12, '1000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 12, SYSDATE);
 
	-- reference to credit_acc_personal1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (37,12,10);
 
-- атрибуты для credit operation Family1
 insert into attributes(attr_id, object_id, VALUES) values(40, 13, '2000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 13, SYSDATE);
 	
	-- reference to credit_acc_family1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (38,13,11);
	-- reference to user1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (39,13,1);
 ----------------------------------------------------------------------------- 
 
-- атрибуты для debt Personal1
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 14, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 14, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUES) values(46, 14, '0');
 
	-- reference to credit_acc_personal1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (42,14,10);
 
-- атрибуты для debt Family1
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 15, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 15, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUES) values(46, 15, '0');
 	
	-- reference to credit_acc_family1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (43,15,11);
 ----------------------------------------------------------------------------- 
 
-- атрибуты для account expense personal1
 insert into attributes(attr_id, object_id, values) values(50, 16, '2000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 16, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 16, '10-11-2019');
 
	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (47,16,2);
 
-- атрибуты для account expense Family1
 insert into attributes(attr_id, object_id, values) values(50, 17, '3000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 17, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 17, '10-11-2019');
 	
	-- reference to family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (48,17,3);
	-- reference to user1 in family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (49,17,1);

 ----------------------------------------------------------------------------- 
 
-- атрибуты для account income personal1
 insert into attributes(attr_id, object_id, values) values(56, 18, '5000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 18, 32);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 18, '10-12-2019');
 
	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (53,18,2);
 
-- атрибуты для account income family1
 insert into attributes(attr_id, object_id, values) values(56, 19, '7000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 19, 32);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 19, '10-12-2019');
 	
	-- reference to family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (54,19,3);
	-- reference to user1 in family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (55,19,1);

 ----------------------------------------------------------------------------- 
 
-- атрибуты для account autoexpense personal1
 insert into attributes(attr_id, object_id, values) values(62, 20, 1);
 insert into attributes(attr_id, object_id, values) values(56, 20, '10000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 20, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 20, '01-11-2019');
	
	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (59,20,2);
 
-- атрибуты для account autoexpense family1
 insert into attributes(attr_id, object_id, values) values(63, 21, 1);
 insert into attributes(attr_id, object_id, values) values(56, 21, '1200');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 21, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 21, '02-11-2019');
	
	-- reference to family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (60,21,3);
 
	-- REFERENCE TO USER AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (61,21,1);
----------------------------------------------------------------------------- 

-- атрибуты для ACC_AUTO_INC_PER
 insert into attributes(attr_id, object_id, values) values(67, 22, 1);
 insert into attributes(attr_id, object_id, values) values(56, 22, '16000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 22, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 22, '19-11-2019');
	
	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (64,22,2);
 
-- атрибуты для ACC_AUTO_INC_FAM
 insert into attributes(attr_id, object_id, values) values(68, 23, 1);
 insert into attributes(attr_id, object_id, values) values(56, 23, '2000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 23, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 23, '10-11-2019');
	
	-- reference to femily_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (65,23,3);
 
	-- REFERENCE TO USER AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (66,23,1);
----------------------------------------------------------------------------- 











----------------------------------------------------------------------------- 
  
 -- атрибуты для юзера2
 insert into attributes(attr_id, object_id, values) values(3, 24, 'mailDimas@gmail.com');
 insert into attributes(attr_id, object_id, values) values(4, 24, 'passwordDima');
 insert into attributes(attr_id, object_id, values) values(5, 24, 'Dimas');
 insert into attributes(attr_id, object_id, list_value_id) values(6, 24, 39);
 
	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,24,25);
 
	-- reference to family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (2,24,26);
 
 -- атрибуты для personal_deb_acc2
 insert into attributes(attr_id, object_id, values) values(7, 25, '10000');
 insert into attributes(attr_id, object_id, list_value_id) values(70, 25, 43);
 
 -- атрибуты для family_deb_acc2
 insert into attributes(attr_id, object_id, values) values(9, 26, '7000');
 insert into attributes(attr_id, object_id, list_value_id) values(69, 26, 41);
 
	-- reference to user2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,26,24);
-----------------------------------------------------------------------------
-- атрибуты для personal_report2
 insert into attributes(attr_id, object_id, values) values(12, 27, '2000');
 insert into attributes(attr_id, object_id, values) values(13, 27, '1500');
 insert into attributes(attr_id, object_id, values) values(14, 27, '7500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 27, '1-11-2019');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 27, '30-11-2019');
 
	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (10,27,25);
 -----------------------------------------------------------------------------
-- атрибуты для family_report2
 insert into attributes(attr_id, object_id, values) values(12, 28, '5000');
 insert into attributes(attr_id, object_id, values) values(13, 28, '3500');
 insert into attributes(attr_id, object_id, values) values(14, 28, '1500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 28, '1-12-2019');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 28, '30-12-2019');
 
	-- reference to family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (11,28,26);
-----------------------------------------------------------------------------	
-- атрибуты для personal_category_report income2
 insert into attributes(attr_id, object_id, list_value_id) values(20, 29, 32);
 insert into attributes(attr_id, object_id, values) values(21, 29, '6000');
	
	-- reference to personal_report2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (17,29,27);
 
-- атрибуты для family_category_report income2
 insert into attributes(attr_id, object_id, list_value_id) values(20, 30, 32);
 insert into attributes(attr_id, object_id, values) values(21, 30, '4000');
	
	-- reference to family_report2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (18,30,28);
 
	-- REFERENCE TO USER2 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (19,30,24);
-----------------------------------------------------------------------------	
-- атрибуты для personal_category_report expense2
 insert into attributes(attr_id, object_id, list_value_id) values(25, 31, 19);
 insert into attributes(attr_id, object_id, values) values(26, 31, '4500');
	
	-- reference to personal_report2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (22,31,27);
 
-- атрибуты для family_category_report expense2
 insert into attributes(attr_id, object_id, list_value_id) values(25, 32, 21);
 insert into attributes(attr_id, object_id, values) values(26, 32, '3500');
	
	-- reference to family_report2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (23,32,28);
 
	-- REFERENCE TO USER AS A PARTICIPANT FAMILY BILL2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (24,32,24);
----------------------------------------------------------------------------- 
 
-- атрибуты для credit account Personal2
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 33, SYSDATE);
 insert into attributes(attr_id, object_id, values) values(30, 33, 'Credit_Money2');
 insert into attributes(attr_id, object_id, values) values(31, 33, '20000');
 insert into attributes(attr_id, object_id, values) values(32, 33, '5000');
 insert into attributes(attr_id, object_id, values) values(33, 33, '20');
 insert into attributes(attr_id, object_id, values) values(34, 33, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 33, 38);
 insert into attributes(attr_id, object_id, list_value_id) values(36, 33, 1);
	-- reference to personal_deb2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (27,33,25);
 
-- атрибуты для credit account Family2
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 34, SYSDATE);
 insert into attributes(attr_id, object_id, values) values(30, 34, 'Credit_Money2');
 insert into attributes(attr_id, object_id, values) values(31, 34, '15000');
 insert into attributes(attr_id, object_id, values) values(32, 34, '2000');
 insert into attributes(attr_id, object_id, values) values(33, 34, '20');
 insert into attributes(attr_id, object_id, values) values(34, 34, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 34, 38);
 insert into attributes(attr_id, object_id, list_value_id) values(36, 34, 1);
 	
	-- reference to family_deb2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (28,34,26);
 ----------------------------------------------------------------------------- 
 
-- атрибуты для credit operation Personal2
 insert into attributes(attr_id, object_id, VALUES) values(40, 35, '2000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 35, SYSDATE);
 
	-- reference to credit_acc_personal2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (37,35,33);
 
-- атрибуты для credit operation Family2
 insert into attributes(attr_id, object_id, VALUES) values(40, 36, '2000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 36, SYSDATE);
 	
	-- reference to credit_acc_family2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (38,36,34);
	-- reference to user1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (39,36,24);
 ----------------------------------------------------------------------------- 
 
-- атрибуты для debt Personal2
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 37, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 37, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUES) values(46, 37, '0');
 
	-- reference to credit_acc_personal2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (42,37,33);
 
-- атрибуты для debt Family2
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 38, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 38, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUES) values(46, 38, '0');
 	
	-- reference to credit_acc_family2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (43,38,34);
 ----------------------------------------------------------------------------- 
 
-- атрибуты для account expense personal2
 insert into attributes(attr_id, object_id, values) values(50, 39, '2500');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 39, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 39, '15-11-2019');
 
	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (47,39,37);
 
-- атрибуты для account expense Family2
 insert into attributes(attr_id, object_id, values) values(50, 40, '2000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 40, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 40, '15-11-2019');
 	
	-- reference to family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (48,40,38);
	-- reference to user1 in family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (49,40,24);

 ----------------------------------------------------------------------------- 
 
-- атрибуты для account income personal2
 insert into attributes(attr_id, object_id, values) values(56, 41, '4500');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 41, 32);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 41, '15-12-2019');
 
	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (53,41,37);
 
-- атрибуты для account income family2
 insert into attributes(attr_id, object_id, values) values(56, 42, '7000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 42, 32);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 42, '10-12-2019');
 	
	-- reference to family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (54,42,38);
	-- reference to user1 in family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (55,42,24);

 ----------------------------------------------------------------------------- 
 
-- атрибуты для account autoexpense personal2
 insert into attributes(attr_id, object_id, values) values(62, 43, 1);
 insert into attributes(attr_id, object_id, values) values(56, 43, '15000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 43, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 43, '01-11-2019');
	
	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (59,43,37);
 
-- атрибуты для account autoexpense family2
 insert into attributes(attr_id, object_id, values) values(63, 44, 1);
 insert into attributes(attr_id, object_id, values) values(56, 44, '12000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 44, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 44, '02-11-2019');
	
	-- reference to family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (60,44,38);
 
	-- REFERENCE TO USER2 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (61,44,24);
----------------------------------------------------------------------------- 

-- атрибуты для ACC_AUTO_INC_PER2
 insert into attributes(attr_id, object_id, values) values(67, 45, 1);
 insert into attributes(attr_id, object_id, values) values(56, 45, '13000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 45, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 45, '19-11-2019');
	
	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (64,45,37);
 
-- атрибуты для ACC_AUTO_INC_FAM2
 insert into attributes(attr_id, object_id, values) values(68, 46, 1);
 insert into attributes(attr_id, object_id, values) values(56, 46, '2500');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 46, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 46, '10-11-2019');
	
	-- reference to femily_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (65,46,38);
 
	-- REFERENCE TO USER2 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (66,46,24);
----------------------------------------------------------------------------- 
 
 ----------------------------------------------------------------------------- 
  
 -- атрибуты для юзера3
 insert into attributes(attr_id, object_id, values) values(3, 47, '3mail3Dimas@gmail.com');
 insert into attributes(attr_id, object_id, values) values(4, 47, '3passwordDima');
 insert into attributes(attr_id, object_id, values) values(5, 47, 'Dimas3');
 insert into attributes(attr_id, object_id, list_value_id) values(6, 47, 39);
 
	-- reference to personal_deb_acc3
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,47,48);
  
 -- атрибуты для personal_deb_acc3
 insert into attributes(attr_id, object_id, values) values(7, 48, '11000');
 insert into attributes(attr_id, object_id, list_value_id) values(70, 48, 43);
 
 -- reference to user3
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,3,47);
 
 ----------------------------------------------------------------------------
 ----------------------------------------------------------------------------

 -- атрибуты для юзера4
 insert into attributes(attr_id, object_id, values) values(3, 49, '4mail4Eugen@gmail.com');
 insert into attributes(attr_id, object_id, values) values(4, 49, '4passwordEugen');
 insert into attributes(attr_id, object_id, values) values(5, 49, 'Eugen4');
 insert into attributes(attr_id, object_id, list_value_id) values(6, 49, 39);
 
	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,49,50);
 
 -- атрибуты для personal_deb_acc3
 insert into attributes(attr_id, object_id, values) values(7, 50, '12000');
 insert into attributes(attr_id, object_id, list_value_id) values(70, 50, 43);
 
 
 -- reference to user1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,3,49);
 
 ----------------------------------------------------------------------------
 
 
 
 
 
