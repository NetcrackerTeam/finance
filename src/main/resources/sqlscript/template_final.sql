BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE TEMPLATE CASCADE CONSTRAINTS';
    EXCEPTION
  WHEN OTHERS THEN
      IF SQLCODE != -942 THEN RAISE;
      END IF;
END;
/
BEGIN 
  EXECUTE IMMEDIATE 'create table template(
  template_id NUMBER,
  name VARCHAR(50),
  message VARCHAR(4000))';
END;
/
BEGIN
  INSERT INTO TEMPLATE VALUES (1,'Account is deactivate',
   'Hi, dear {0}. We see you will deactivate your account, and its very bad for us. We want to see you again. your Finance Team');

  INSERT INTO TEMPLATE VALUES (2,'Personal debit',
   'Hi, dear {0}. Its your personal debit {1} and your amount is {2}');

  INSERT INTO TEMPLATE VALUES (3,'Family debit',
   'Hi, dear {0}. Its your family debit {1} and your amount is {2}');

  INSERT INTO TEMPLATE VALUES(4,'Paid amount for a Personal credit',
  'Hi, dear {0}. You have paid an amount {1} for a personal credit {2}, last date is {3}');

  INSERT INTO TEMPLATE VALUES(5,'Paid amount for a Family credit',
  'Hi, dear {0}. You have paid an amount {1} for a family credit {2}, last date is {3}');

  INSERT INTO TEMPLATE VALUES(6,'New income for personal account',
  'Hi, dear {0}. You have new auto income operation on personal account is {1}, and is amount: {2}');

  INSERT INTO TEMPLATE VALUES(7,'New expense for personal account',
  'Hi, dear {0}. You have new auto expense operation on personal account is {1}, and is amount: {2}');

  INSERT INTO TEMPLATE VALUES(8,'New income for family account',
  'Hi, dear {0}. You have new auto income operation on family account is {1}, and is amount: {2}');

  INSERT INTO TEMPLATE VALUES(9,'New expense for family account',
  'Hi, dear {0}. You have new auto expense operation on family account is {1}, and is amount: {2}');

  INSERT INTO TEMPLATE VALUES(10,'Month report',
  'Hi, dear {0}. We sent you a monthly report, thanks for using our software product!');
  commit;
END;
/