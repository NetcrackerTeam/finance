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
  'Dear {0}, remember that you have a credit {1} and have to pay an amount{2}');
  INSERT INTO template values(2,'Not enough money for a credit',
  'Dear {0}, you do not have enough money to pay for a credit {1} and have to pay an amount {2}');
  INSERT INTO template values(4,'Paid amount for a credit',
  'Dear {0}, you have paid an amount {1} for a personal credit {2}, last date is {3}');
  INSERT INTO template values(5,'Added amount from auto income',
  'Dear {0}, you have got an amount {1} from auto income {2}');
  INSERT INTO template values(6,'Paid amount for a auto expense',
  'Dear {0}, you have paid an amount {1} for a auto expense {2}');
  INSERT INTO template values(7,'Added to family',
  'Dear {0}, you have been added to a family account');
  INSERT INTO template values(8,'Deleted from a family account',
  'Dear {0}, you have been deleted from a family account');
  INSERT INTO TEMPLATE VALUES (9,'Account is deactivate',
   'Hi dear {0}. We see you will deactivate your account, and its very bad for us. We want to see you again. your Finance Team');
   INSERT INTO TEMPLATE VALUES (10,'Personal debit',
   'Hi dear {0}. Its your personal debit {1} and your amount is {2}');
      INSERT INTO TEMPLATE VALUES (11,'family debit',
   'Hi dear {0}. Its your family debit {1} and your amount is {2}');
    INSERT INTO template values(12,'Paid amount for a credit',
  'Dear {0}, you have paid an amount {1} for a family credit {2}, last date is {3}');
  commit;
END;
/



