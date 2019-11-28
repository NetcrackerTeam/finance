

 /*
 OBJTYPE - таблица описаний объектных типов
 ATTRTYPE - таблица описаний атрибутных типов
 LISTS - список для листовых значений
 OBJECTS - таблица обьектов 
 ATTRIBUTES - таблица атрибутов 
 OBJREFERENCE - описаний связей "простая ассоциация" между экземплярами объектов
 */
 
 
 -- атрибуты для юзера1
 insert into attributes(attr_id, object_id, value) values(3, 1, 'mail@gmail.com');
 insert into attributes(attr_id, object_id, value) values(4, 1, 'password');
 insert into attributes(attr_id, object_id, value) values(5, 1, 'Eugen');
 insert into attributes(attr_id, object_id, list_value_id) values(6, 1, 39);
 
	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,1,2);
 
	-- reference to family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (2,1,3);
 
 -- атрибуты для personal_deb_acc1
 insert into attributes(attr_id, object_id, value) values(7, 2, '5000');
 insert into attributes(attr_id, object_id, list_value_id) values(70, 2, 43);
 
 -- атрибуты для family_deb_acc1
 insert into attributes(attr_id, object_id, value) values(9, 3, '9000');
 insert into attributes(attr_id, object_id, list_value_id) values(69, 3, 41);
 
	-- reference to user1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,3,1);
-----------------------------------------------------------------------------
-- атрибуты для personal_report1
 insert into attributes(attr_id, object_id, value) values(12, 4, '4000');
 insert into attributes(attr_id, object_id, value) values(13, 4, '3500');
 insert into attributes(attr_id, object_id, value) values(14, 4, '5500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 4, TO_DATE('1-11-2019', 'dd-mm-yyyy'));
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 4, TO_DATE('30-11-2019', 'dd-mm-yyyy'));
 
	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (10,4,2);
 -----------------------------------------------------------------------------
-- атрибуты для family_report1
 insert into attributes(attr_id, object_id, value) values(12, 5, '5000');
 insert into attributes(attr_id, object_id, value) values(13, 5, '4500');
 insert into attributes(attr_id, object_id, value) values(14, 5, '9500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 5, TO_DATE('1-12-2019', 'dd-mm-yyyy'));
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 5, TO_DATE('30-12-2019', 'dd-mm-yyyy'));
 
	-- reference to family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (11,5,3);
-----------------------------------------------------------------------------	
-- атрибуты для personal_category_report income1
 insert into attributes(attr_id, object_id, list_value_id) values(20, 6, 32);
 insert into attributes(attr_id, object_id, value) values(21, 6, '5000');
	
	-- reference to personal_report1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (17,6,4);
 
-- атрибуты для family_category_report income1
 insert into attributes(attr_id, object_id, list_value_id) values(20, 7, 32);
 insert into attributes(attr_id, object_id, value) values(21, 7, '5000');
	
	-- reference to family_report1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (18,7,5);
 
	-- REFERENCE TO USER1 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (19,7,1);
-----------------------------------------------------------------------------	
-- атрибуты для personal_category_report expense1
 insert into attributes(attr_id, object_id, list_value_id) values(25, 8, 11);
 insert into attributes(attr_id, object_id, value) values(26, 8, '3500');
	
	-- reference to personal_report1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (22,8,4);
 
-- атрибуты для family_category_report expense1
 insert into attributes(attr_id, object_id, list_value_id) values(25, 9, 21);
 insert into attributes(attr_id, object_id, value) values(26, 9, '4500');
	
	-- reference to family_report1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (23,9,5);
 
	-- REFERENCE TO USER AS A PARTICIPANT FAMILY BILL1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (24,9,1);
----------------------------------------------------------------------------- 
 
-- атрибуты для credit account Personal1
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 10, SYSDATE);
 insert into attributes(attr_id, object_id, value) values(30, 10, 'Credit_Money1');
 insert into attributes(attr_id, object_id, value) values(31, 10, '10000');
 insert into attributes(attr_id, object_id, value) values(32, 10, '2000');
 insert into attributes(attr_id, object_id, value) values(33, 10, '20');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(34, 10, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 10, 38);
 insert into attributes(attr_id, object_id, value) values(36, 10, '1');
	-- reference to personal_deb1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (27,10,2);
 
-- атрибуты для credit account Family1
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 11, SYSDATE);
 insert into attributes(attr_id, object_id, value) values(30, 11, 'Credit_Money1');
 insert into attributes(attr_id, object_id, value) values(31, 11, '15000');
 insert into attributes(attr_id, object_id, value) values(32, 11, '3000');
 insert into attributes(attr_id, object_id, value) values(33, 11, '20');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(34, 11, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 11, 38);
 insert into attributes(attr_id, object_id, value) values(36, 11, '1');
 	
	-- reference to family_deb1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (28,11,3);
 ----------------------------------------------------------------------------- 
 
-- атрибуты для credit operation Personal1
 insert into attributes(attr_id, object_id, VALUE) values(40, 12, '1000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 12, SYSDATE);
 
	-- reference to credit_acc_personal1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (37,12,10);
 
-- атрибуты для credit operation Family1
 insert into attributes(attr_id, object_id, VALUE) values(40, 13, '2000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 13, SYSDATE);
 	
	-- reference to credit_acc_family1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (38,13,11);
	-- reference to user1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (39,13,1);
 ----------------------------------------------------------------------------- 
 
-- атрибуты для debt Personal1
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 14, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 14, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUE) values(46, 14, '0');
 
	-- reference to credit_acc_personal1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (42,14,10);
 
-- атрибуты для debt Family1
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 15, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 15, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUE) values(46, 15, '0');
 	
	-- reference to credit_acc_family1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (43,15,11);
 ----------------------------------------------------------------------------- 
 
-- атрибуты для account expense personal1
 insert into attributes(attr_id, object_id, value) values(50, 16, '2000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 16, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 16, TO_DATE('10-11-2019', 'dd-mm-yyyy'));
 
	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (47,16,2);
 
-- атрибуты для account expense Family1
 insert into attributes(attr_id, object_id, value) values(50, 17, '3000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 17, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 17, TO_DATE('10-11-2019', 'dd-mm-yyyy'));
 	
	-- reference to family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (48,17,3);
	-- reference to user1 in family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (49,17,1);

 ----------------------------------------------------------------------------- 
 
-- атрибуты для account income personal1
 insert into attributes(attr_id, object_id, value) values(56, 18, '5000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 18, 14);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 18, TO_DATE('10-12-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (53,18,2);

-- атрибуты для account income family1
 insert into attributes(attr_id, object_id, value) values(56, 19, '7000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 19, 14);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 19, TO_DATE('10-12-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (54,19,3);
	-- reference to user1 in family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (55,19,1);

 -----------------------------------------------------------------------------

-- атрибуты для account autoexpense personal1
 insert into attributes(attr_id, object_id, value) values(62, 20, 1);
 insert into attributes(attr_id, object_id, value) values(50, 20, '10000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 20, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 20, TO_DATE('01-11-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (59,20,2);

-- атрибуты для account autoexpense family1
 insert into attributes(attr_id, object_id, value) values(63, 21, 1);
 insert into attributes(attr_id, object_id, value) values(50, 21, '1200');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 21, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 21, TO_DATE('02-11-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (60,21,3);

	-- REFERENCE TO USER AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (61,21,1);
-----------------------------------------------------------------------------

-- атрибуты для ACC_AUTO_INC_PER
 insert into attributes(attr_id, object_id, value) values(67, 22, 1);
 insert into attributes(attr_id, object_id, value) values(56, 22, '16000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 22, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 22, TO_DATE('19-11-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (64,22,2);

-- атрибуты для ACC_AUTO_INC_FAM
 insert into attributes(attr_id, object_id, value) values(68, 23, 1);
 insert into attributes(attr_id, object_id, value) values(56, 23, '2000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 23, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 23, TO_DATE('10-11-2019', 'dd-mm-yyyy'));

	-- reference to femily_deb_acc1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (65,23,3);

	-- REFERENCE TO USER AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (66,23,1);
-----------------------------------------------------------------------------











-----------------------------------------------------------------------------

 -- атрибуты для юзера2
 insert into attributes(attr_id, object_id, value) values(3, 24, 'mailDimas@gmail.com');
 insert into attributes(attr_id, object_id, value) values(4, 24, 'passwordDima');
 insert into attributes(attr_id, object_id, value) values(5, 24, 'Dimas');
 insert into attributes(attr_id, object_id, list_value_id) values(6, 24, 39);

	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,24,25);

	-- reference to family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (2,24,26);

 -- атрибуты для personal_deb_acc2
 insert into attributes(attr_id, object_id, value) values(7, 25, '10000');
 insert into attributes(attr_id, object_id, list_value_id) values(70, 25, 43);

 -- атрибуты для family_deb_acc2
 insert into attributes(attr_id, object_id, value) values(9, 26, '7000');
 insert into attributes(attr_id, object_id, list_value_id) values(69, 26, 41);

	-- reference to user2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,26,24);
-----------------------------------------------------------------------------
-- атрибуты для personal_report2
 insert into attributes(attr_id, object_id, value) values(12, 27, '2000');
 insert into attributes(attr_id, object_id, value) values(13, 27, '1500');
 insert into attributes(attr_id, object_id, value) values(14, 27, '7500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 27, TO_DATE('1-11-2019', 'dd-mm-yyyy'));
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 27, TO_DATE('30-11-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (10,27,25);
 -----------------------------------------------------------------------------
-- атрибуты для family_report2
 insert into attributes(attr_id, object_id, value) values(12, 28, '5000');
 insert into attributes(attr_id, object_id, value) values(13, 28, '3500');
 insert into attributes(attr_id, object_id, value) values(14, 28, '1500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 28, TO_DATE('1-12-2019', 'dd-mm-yyyy'));
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 28, TO_DATE('30-12-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (11,28,26);
-----------------------------------------------------------------------------
-- атрибуты для personal_category_report income2
 insert into attributes(attr_id, object_id, list_value_id) values(20, 29, 32);
 insert into attributes(attr_id, object_id, value) values(21, 29, '6000');

	-- reference to personal_report2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (17,29,27);

-- атрибуты для family_category_report income2
 insert into attributes(attr_id, object_id, list_value_id) values(20, 30, 32);
 insert into attributes(attr_id, object_id, value) values(21, 30, '4000');

	-- reference to family_report2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (18,30,28);

	-- REFERENCE TO USER2 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (19,30,24);
-----------------------------------------------------------------------------
-- атрибуты для personal_category_report expense2
 insert into attributes(attr_id, object_id, list_value_id) values(25, 31, 19);
 insert into attributes(attr_id, object_id, value) values(26, 31, '4500');

	-- reference to personal_report2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (22,31,27);

-- атрибуты для family_category_report expense2
 insert into attributes(attr_id, object_id, list_value_id) values(25, 32, 21);
 insert into attributes(attr_id, object_id, value) values(26, 32, '3500');

	-- reference to family_report2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (23,32,28);

	-- REFERENCE TO USER AS A PARTICIPANT FAMILY BILL2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (24,32,24);
-----------------------------------------------------------------------------

-- атрибуты для credit account Personal2
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 33, SYSDATE);
 insert into attributes(attr_id, object_id, value) values(30, 33, 'Credit_Money2');
 insert into attributes(attr_id, object_id, value) values(31, 33, '20000');
 insert into attributes(attr_id, object_id, value) values(32, 33, '5000');
 insert into attributes(attr_id, object_id, value) values(33, 33, '20');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(34, 33, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 33, 38);
 insert into attributes(attr_id, object_id, value) values(36, 33, '1');
	-- reference to personal_deb2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (27,33,25);

-- атрибуты для credit account Family2
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 34, SYSDATE);
 insert into attributes(attr_id, object_id, value) values(30, 34, 'Credit_Money2');
 insert into attributes(attr_id, object_id, value) values(31, 34, '15000');
 insert into attributes(attr_id, object_id, value) values(32, 34, '2000');
 insert into attributes(attr_id, object_id, value) values(33, 34, '20');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(34, 34, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 34, 38);
 insert into attributes(attr_id, object_id, value) values(36, 34, '1');

	-- reference to family_deb2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (28,34,26);
 -----------------------------------------------------------------------------

-- атрибуты для credit operation Personal2
 insert into attributes(attr_id, object_id, VALUE) values(40, 35, '2000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 35, SYSDATE);

	-- reference to credit_acc_personal2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (37,35,33);

-- атрибуты для credit operation Family2
 insert into attributes(attr_id, object_id, VALUE) values(40, 36, '2000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 36, SYSDATE);

	-- reference to credit_acc_family2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (38,36,34);
	-- reference to user2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (39,36,24);
 -----------------------------------------------------------------------------

-- атрибуты для debt Personal2
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 37, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 37, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUE) values(46, 37, '0');

	-- reference to credit_acc_personal2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (42,37,33);

-- атрибуты для debt Family2
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 38, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 38, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUE) values(46, 38, '0');

	-- reference to credit_acc_family2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (43,38,34);
 -----------------------------------------------------------------------------

-- атрибуты для account expense personal2
 insert into attributes(attr_id, object_id, value) values(50, 39, '2500');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 39, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 39, TO_DATE('15-11-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (47,39,25);

-- атрибуты для account expense Family2
 insert into attributes(attr_id, object_id, value) values(50, 40, '2000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 40, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 40, TO_DATE('15-11-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (48,40,26);
	-- reference to user1 in family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (49,40,24);

 -----------------------------------------------------------------------------

-- атрибуты для account income personal2
 insert into attributes(attr_id, object_id, value) values(56, 41, '4500');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 41, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 41, TO_DATE('15-12-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (53,41,25);

-- атрибуты для account income family2
 insert into attributes(attr_id, object_id, value) values(56, 42, '7000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 42, 16);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 42, TO_DATE('10-12-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (54,42,26);
	-- reference to user1 in family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (55,42,24);

 -----------------------------------------------------------------------------

-- атрибуты для account autoexpense personal2
 insert into attributes(attr_id, object_id, value) values(62, 43, 1);
 insert into attributes(attr_id, object_id, value) values(50, 43, '15000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 43, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 43, TO_DATE('01-11-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (59,43,25);

-- атрибуты для account autoexpense family2
 insert into attributes(attr_id, object_id, value) values(63, 44, 1);
 insert into attributes(attr_id, object_id, value) values(50, 44, '12000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 44, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 44, TO_DATE('02-11-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (60,44,26);

	-- REFERENCE TO USER2 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (61,44,24);
-----------------------------------------------------------------------------

-- атрибуты для ACC_AUTO_INC_PER2
 insert into attributes(attr_id, object_id, value) values(67, 45, 1);
 insert into attributes(attr_id, object_id, value) values(56, 45, '13000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 45, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 45, TO_DATE('19-11-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (64,45,25);

-- атрибуты для ACC_AUTO_INC_FAM2
 insert into attributes(attr_id, object_id, value) values(68, 46, 1);
 insert into attributes(attr_id, object_id, value) values(56, 46, '2500');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 46, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 46, TO_DATE('10-11-2019', 'dd-mm-yyyy'));

	-- reference to femily_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (65,46,26);

	-- REFERENCE TO USER2 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (66,46,24);
-----------------------------------------------------------------------------











 -----------------------------------------------------------------------------

 -- атрибуты для юзера3
 insert into attributes(attr_id, object_id, value) values(3, 47, '3mail3Dimas@gmail.com');
 insert into attributes(attr_id, object_id, value) values(4, 47, '3passwordDima');
 insert into attributes(attr_id, object_id, value) values(5, 47, 'Dimas3');
 insert into attributes(attr_id, object_id, list_value_id) values(6, 47, 39);

	-- reference to personal_deb_acc3
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,47,48);

 -- атрибуты для personal_deb_acc3
 insert into attributes(attr_id, object_id, value) values(7, 48, '11000');
 insert into attributes(attr_id, object_id, list_value_id) values(70, 48, 43);

 -- reference to user3 from family 1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,3,47);

 ----------------------------------------------------------------------------
 ----------------------------------------------------------------------------

 -- атрибуты для юзера4
 insert into attributes(attr_id, object_id, value) values(3, 49, '4mail4Eugen@gmail.com');
 insert into attributes(attr_id, object_id, value) values(4, 49, '4passwordEugen');
 insert into attributes(attr_id, object_id, value) values(5, 49, 'Eugen4');
 insert into attributes(attr_id, object_id, list_value_id) values(6, 49, 39);

	-- reference to personal_deb_acc2
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,49,50);

 -- атрибуты для personal_deb_acc3
 insert into attributes(attr_id, object_id, value) values(7, 50, '12000');
 insert into attributes(attr_id, object_id, list_value_id) values(70, 50, 43);


 -- reference to user4 from family1
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,3,49);

 ----------------------------------------------------------------------------












 -----------------------------------------------------------------------------

 -- атрибуты для юзера5
 insert into attributes(attr_id, object_id, value) values(3, 51, 'mail5@gmail.com');
 insert into attributes(attr_id, object_id, value) values(4, 51, 'password5');
 insert into attributes(attr_id, object_id, value) values(5, 51, 'User5');
 insert into attributes(attr_id, object_id, list_value_id) values(6, 51, 39);

	-- reference to personal_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,51,52);

	-- reference to family_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (2,51,53);

 -- атрибуты для personal_deb_acc5
 insert into attributes(attr_id, object_id, value) values(7, 52, '20000');
 insert into attributes(attr_id, object_id, list_value_id) values(70, 52, 43);

 -- атрибуты для family_deb_acc5
 insert into attributes(attr_id, object_id, value) values(9, 53, '25000');
 insert into attributes(attr_id, object_id, list_value_id) values(69, 53, 41);

	-- reference to user5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,53,51);
-----------------------------------------------------------------------------
-- атрибуты для personal_report5
 insert into attributes(attr_id, object_id, value) values(12, 54, '3000');
 insert into attributes(attr_id, object_id, value) values(13, 54, '1500');
 insert into attributes(attr_id, object_id, value) values(14, 54, '21500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 54, TO_DATE('1-12-2019', 'dd-mm-yyyy'));
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 54, TO_DATE('30-12-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (10,54,52);
 -----------------------------------------------------------------------------
-- атрибуты для family_report5
 insert into attributes(attr_id, object_id, value) values(12, 55, '6000');
 insert into attributes(attr_id, object_id, value) values(13, 55, '3500');
 insert into attributes(attr_id, object_id, value) values(14, 55, '27500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 55, TO_DATE('1-12-2019', 'dd-mm-yyyy'));
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 55, TO_DATE('30-12-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (11,55,53);
-----------------------------------------------------------------------------
-- атрибуты для personal_category_report income5
 insert into attributes(attr_id, object_id, list_value_id) values(20, 56, 33);
 insert into attributes(attr_id, object_id, value) values(21, 56, '7000');

	-- reference to personal_report5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (17,56,54);

-- атрибуты для family_category_report income5
 insert into attributes(attr_id, object_id, list_value_id) values(20, 57, 33);
 insert into attributes(attr_id, object_id, value) values(21, 57, '5000');

	-- reference to family_report5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (18,57,55);

	-- REFERENCE TO USER5 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (19,57,51);
-----------------------------------------------------------------------------
-- атрибуты для personal_category_report expense5
 insert into attributes(attr_id, object_id, list_value_id) values(25, 58, 19);
 insert into attributes(attr_id, object_id, value) values(26, 58, '4500');

	-- reference to personal_report5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (22,58,54);

-- атрибуты для family_category_report expense5
 insert into attributes(attr_id, object_id, list_value_id) values(25, 59, 21);
 insert into attributes(attr_id, object_id, value) values(26, 59, '3500');

	-- reference to family_report5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (23,59,55);

	-- REFERENCE TO USER AS A PARTICIPANT FAMILY BILL5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (24,59,51);
-----------------------------------------------------------------------------

-- атрибуты для credit account Personal5
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 60, SYSDATE);
 insert into attributes(attr_id, object_id, value) values(30, 60, 'Credit_Money5');
 insert into attributes(attr_id, object_id, value) values(31, 60, '10000');
 insert into attributes(attr_id, object_id, value) values(32, 60, '5000');
 insert into attributes(attr_id, object_id, value) values(33, 60, '20');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(34, 60, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 60, 38);
 insert into attributes(attr_id, object_id, value) values(36, 60, '10');
	-- reference to personal_deb5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (27,60,52);

-- атрибуты для credit account Family5
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 61, SYSDATE);
 insert into attributes(attr_id, object_id, value) values(30, 61, 'Credit_Money5');
 insert into attributes(attr_id, object_id, value) values(31, 61, '15000');
 insert into attributes(attr_id, object_id, value) values(32, 61, '2000');
 insert into attributes(attr_id, object_id, value) values(33, 61, '20');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(34, 61, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 61, 38);
 insert into attributes(attr_id, object_id, value) values(36, 61, '10');

	-- reference to family_deb5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (28,61,53);
 -----------------------------------------------------------------------------

-- атрибуты для credit operation Personal5
 insert into attributes(attr_id, object_id, VALUE) values(40, 62, '6000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 62, SYSDATE);

	-- reference to credit_acc_personal5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (37,62,60);

-- атрибуты для credit operation Family5
 insert into attributes(attr_id, object_id, VALUE) values(40, 63, '7000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 63, SYSDATE);

	-- reference to credit_acc_family5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (38,63,61);
	-- reference to user5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (39,63,51);
 -----------------------------------------------------------------------------

-- атрибуты для debt Personal5
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 64, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 64, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUE) values(46, 64, '0');

	-- reference to credit_acc_personal5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (42,64,60);

-- атрибуты для debt Family5
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 65, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 65, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUE) values(46, 65, '7000');

	-- reference to credit_acc_family5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (43,65,61);
 -----------------------------------------------------------------------------

-- атрибуты для account expense personal5
 insert into attributes(attr_id, object_id, value) values(50, 66, '2500');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 66, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 66, TO_DATE('16-11-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (47,66,52);

-- атрибуты для account expense Family5
 insert into attributes(attr_id, object_id, value) values(50, 67, '5000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 67, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 67, TO_DATE('16-11-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (48,67,53);
	-- reference to user1 in family_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (49,67,51);

 -----------------------------------------------------------------------------

-- атрибуты для account income personal5
 insert into attributes(attr_id, object_id, value) values(56, 68, '5500');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 68, 18);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 68, TO_DATE('15-12-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (53,68,52);

-- атрибуты для account income family5
 insert into attributes(attr_id, object_id, value) values(56, 69, '8000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 69, 17);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 69, TO_DATE('10-12-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (54,69,53);
	-- reference to user1 in family_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (55,69,51);

 -----------------------------------------------------------------------------

-- атрибуты для account autoexpense personal5
 insert into attributes(attr_id, object_id, value) values(62, 70, 1);
 insert into attributes(attr_id, object_id, value) values(50, 70, '16000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 70, 11);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 70, TO_DATE('01-12-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (59,70,52);

-- атрибуты для account autoexpense family5
 insert into attributes(attr_id, object_id, value) values(63, 71, 1);
 insert into attributes(attr_id, object_id, value) values(50, 71, '13000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 71, 11);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 71, TO_DATE('02-12-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (60,71,53);

	-- REFERENCE TO USER5 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (61,71,51);
-----------------------------------------------------------------------------

-- атрибуты для ACC_AUTO_INC_PER5
 insert into attributes(attr_id, object_id, value) values(67, 72, 1);
 insert into attributes(attr_id, object_id, value) values(56, 72, '12000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 72, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 72, TO_DATE('20-11-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (64,72,52);

-- атрибуты для ACC_AUTO_INC_FAM5
 insert into attributes(attr_id, object_id, value) values(68, 73, 1);
 insert into attributes(attr_id, object_id, value) values(56, 73, '10000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 73, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 73, TO_DATE('15-11-2019', 'dd-mm-yyyy'));

	-- reference to femily_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (65,73,53);

	-- REFERENCE TO USER5 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (66,73,51);
-----------------------------------------------------------------------------











 -----------------------------------------------------------------------------

 -- атрибуты для юзера6
 insert into attributes(attr_id, object_id, value) values(3, 74, 'mail6@gmail.com');
 insert into attributes(attr_id, object_id, value) values(4, 74, 'password6');
 insert into attributes(attr_id, object_id, value) values(5, 74, 'User6');
 insert into attributes(attr_id, object_id, list_value_id) values(6, 74, 39);

	-- reference to personal_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (1,74,75);

	-- reference to family_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (2,74,76);

 -- атрибуты для personal_deb_acc6
 insert into attributes(attr_id, object_id, value) values(7, 75, '25000');
 insert into attributes(attr_id, object_id, list_value_id) values(70, 75, 43);

 -- атрибуты для family_deb_acc6
 insert into attributes(attr_id, object_id, value) values(9, 76, '20000');
 insert into attributes(attr_id, object_id, list_value_id) values(69, 76, 41);

	-- reference to user6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (8,76,74);
-----------------------------------------------------------------------------
-- атрибуты для personal_report6
 insert into attributes(attr_id, object_id, value) values(12, 77, '5000');
 insert into attributes(attr_id, object_id, value) values(13, 77, '2500');
 insert into attributes(attr_id, object_id, value) values(14, 77, '27500');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 77, TO_DATE('1-01-2020', 'dd-mm-yyyy'));
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 77, TO_DATE('30-01-2020', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (10,77,75);
 -----------------------------------------------------------------------------
-- атрибуты для family_report5
 insert into attributes(attr_id, object_id, value) values(12, 78, '6500');
 insert into attributes(attr_id, object_id, value) values(13, 78, '3500');
 insert into attributes(attr_id, object_id, value) values(14, 78, '23000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(15, 78, TO_DATE('1-01-2020', 'dd-mm-yyyy'));
 insert into attributes(attr_id, object_id, DATE_VALUE) values(16, 78, TO_DATE('30-01-2020', 'dd-mm-yyyy'));

	-- reference to family_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (11,78,76);
-----------------------------------------------------------------------------
-- атрибуты для personal_category_report income6
 insert into attributes(attr_id, object_id, list_value_id) values(20, 79, 33);
 insert into attributes(attr_id, object_id, value) values(21, 79, '8000');

	-- reference to personal_report6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (17,79,77);

-- атрибуты для family_category_report income6
 insert into attributes(attr_id, object_id, list_value_id) values(20, 80, 33);
 insert into attributes(attr_id, object_id, value) values(21, 80, '5500');

	-- reference to family_report6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (18,80,78);

	-- REFERENCE TO USER6 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (19,80,74);
-----------------------------------------------------------------------------
-- атрибуты для personal_category_report expense6
 insert into attributes(attr_id, object_id, list_value_id) values(25, 81, 19);
 insert into attributes(attr_id, object_id, value) values(26, 81, '5500');

	-- reference to personal_report6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (22,81,77);

-- атрибуты для family_category_report expense6
 insert into attributes(attr_id, object_id, list_value_id) values(25, 82, 21);
 insert into attributes(attr_id, object_id, value) values(26, 82, '4500');

	-- reference to family_report6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (23,82,78);

	-- REFERENCE TO USER AS A PARTICIPANT FAMILY BILL6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (24,82,74);
-----------------------------------------------------------------------------

-- атрибуты для credit account Personal6
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 83, SYSDATE);
 insert into attributes(attr_id, object_id, value) values(30, 83, 'Credit_Money6');
 insert into attributes(attr_id, object_id, value) values(31, 83, '16000');
 insert into attributes(attr_id, object_id, value) values(32, 83, '6000');
 insert into attributes(attr_id, object_id, value) values(33, 83, '25');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(34, 83, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 83, 38);
 insert into attributes(attr_id, object_id, value) values(36, 83, '10');
	-- reference to personal_deb6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (27,83,75);

-- атрибуты для credit account Family6
 insert into attributes(attr_id, object_id, DATE_VALUE) values(29, 84, SYSDATE);
 insert into attributes(attr_id, object_id, value) values(30, 84, 'Credit_Money6');
 insert into attributes(attr_id, object_id, value) values(31, 84, '16000');
 insert into attributes(attr_id, object_id, value) values(32, 84, '6000');
 insert into attributes(attr_id, object_id, value) values(33, 84, '25');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(34, 84, SYSDATE+90);
 insert into attributes(attr_id, object_id, list_value_id) values(35, 84, 38);
 insert into attributes(attr_id, object_id, value) values(36, 84, '10');

	-- reference to family_deb6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (28,84,76);
 -----------------------------------------------------------------------------

-- атрибуты для credit operation Personal6
 insert into attributes(attr_id, object_id, VALUE) values(40, 85, '7000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 85, SYSDATE);

	-- reference to credit_acc_personal6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (37,85,83);

-- атрибуты для credit operation Family6
 insert into attributes(attr_id, object_id, VALUE) values(40, 86, '6000');
 insert into attributes(attr_id, object_id, DATE_VALUE) values(41, 86, SYSDATE);

	-- reference to credit_acc_family6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (38,86,84);
	-- reference to user6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (39,86,74);
 -----------------------------------------------------------------------------

-- атрибуты для debt Personal6
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 87, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 87, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUE) values(46, 87, '0');

	-- reference to credit_acc_personal6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (42,87,83);

-- атрибуты для debt Family6
 insert into attributes(attr_id, object_id, DATE_VALUE) values(44, 88, SYSDATE);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(45, 88, SYSDATE+90);
 insert into attributes(attr_id, object_id, VALUE) values(46, 88, '8000');

	-- reference to credit_acc_family6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (43,88,84);
 -----------------------------------------------------------------------------

-- атрибуты для account expense personal6
 insert into attributes(attr_id, object_id, value) values(50, 89, '6500');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 89, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 89, TO_DATE('17-11-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (47,89,75);

-- атрибуты для account expense Family6
 insert into attributes(attr_id, object_id, value) values(50, 90, '4000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 90, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 90, TO_DATE('17-11-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (48,90,76);
	-- reference to user1 in family_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (49,90,74);

 -----------------------------------------------------------------------------

-- атрибуты для account income personal6
 insert into attributes(attr_id, object_id, value) values(56, 91, '9500');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 91, 14);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 91, TO_DATE('17-12-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (53,91,75);

-- атрибуты для account income family6
 insert into attributes(attr_id, object_id, value) values(56, 92, '8500');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 92, 14);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 92, TO_DATE('17-12-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (54,92,76);
	-- reference to user1 in family_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (55,92,74);

 -----------------------------------------------------------------------------

-- атрибуты для account autoexpense personal6
 insert into attributes(attr_id, object_id, value) values(62, 93, 1);
 insert into attributes(attr_id, object_id, value) values(50, 93, '17000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 93, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 93, TO_DATE('02-12-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (59,93,75);

-- атрибуты для account autoexpense family6
 insert into attributes(attr_id, object_id, value) values(63, 94, 1);
 insert into attributes(attr_id, object_id, value) values(50, 94, '16000');
 insert into attributes(attr_id, object_id, list_value_id) values(51, 94, 1);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(52, 94, TO_DATE('03-12-2019', 'dd-mm-yyyy'));

	-- reference to family_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (60,94,76);

	-- REFERENCE TO USER6 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (61,94,74);
-----------------------------------------------------------------------------

-- атрибуты для ACC_AUTO_INC_PER6
 insert into attributes(attr_id, object_id, value) values(67, 95, 1);
 insert into attributes(attr_id, object_id, value) values(56, 95, '13000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 95, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 95, TO_DATE('20-12-2019', 'dd-mm-yyyy'));

	-- reference to personal_deb_acc5
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (64,95,75);

-- атрибуты для ACC_AUTO_INC_FAM5
 insert into attributes(attr_id, object_id, value) values(68, 96, 1);
 insert into attributes(attr_id, object_id, value) values(56, 96, '12000');
 insert into attributes(attr_id, object_id, list_value_id) values(57, 96, 15);
 insert into attributes(attr_id, object_id, DATE_VALUE) values(58, 96, TO_DATE('15-12-2019', 'dd-mm-yyyy'));

	-- reference to femily_deb_acc6
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (65,96,76);

	-- REFERENCE TO USER6 AS A PARTICIPANT FAMILY BILL
	INSERT INTO OBJREFERENCE (ATTR_ID,OBJECT_ID,REFERENCE) VALUES (66,96,74);
-----------------------------------------------------------------------------


 commit;

