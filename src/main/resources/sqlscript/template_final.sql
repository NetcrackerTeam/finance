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
  name VARCHAR(30),
  message VARCHAR(4000))';
END;
/
BEGIN
  INSERT INTO template values(1,'Reminder about credit',
  'Dear {}, remember that you have a credit {} and have to pay an amount{}');
  INSERT INTO template values(2,'Not enough money for a credit',
  'Dear {}, you do not have enough money to pay for a credit {} and have to pay an amount {}');
  INSERT INTO template values(4,'Paid amount for a credit',
  'Dear {}, you have paid an amount {} for a credit {}');
  INSERT INTO template values(5,'Added amount from auto income',
  'Dear {}, you have got an amount {} from auto income {}');
  INSERT INTO template values(6,'Paid amount for a auto expense',
  'Dear {}, you have paid an amount {} for a auto expense {}');
  INSERT INTO template values(7,'Added to family',
  'Dear {}, you have been added to a family account');
  INSERT INTO template values(8,'Deleted from a family account',
  'Dear {}, you have been deleted from a family account');
  INSERT INTO TEMPLATE VALUES (9,'Account is deactivate',
   'Hi dear {}. We see you will deactivate your account, and its very bad for us. We want to see you again. your Finance Team');
   INSERT INTO TEMPLATE VALUES (10,'Personal debit',
   'Hi dear {}. Its your personal debit {}');
      INSERT INTO TEMPLATE VALUES (11,'family debit',
   'Hi dear {}. Its your family debit {}');
  commit;
END;
/



