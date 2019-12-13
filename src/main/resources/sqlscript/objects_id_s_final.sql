BEGIN
BEGIN
  EXECUTE IMMEDIATE 'DROP SEQUENCE objects_id_s';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -2289 THEN RAISE;
  END IF;
 END;
BEGIN
 EXECUTE IMMEDIATE 'create sequence objects_id_s
  increment by 1
  start with 1
  nocache 
  nocycle';
END;
END;
/

