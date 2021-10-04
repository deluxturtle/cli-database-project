DELETE FROM login WHERE login_name = 'Alice_facebook';

SELECT 
	l.login_name, 
    l.login_password, 
    l.login_url 
FROM 
	login l 
    INNER JOIN has_login hl ON l.login_name = hl.login_name 
    INNER JOIN user u ON hl.username = u.username 
WHERE 
	u.username = 'Alice';
    
UPDATE login l SET l.login_password = 'testpassword123' WHERE l.login_name = 'Bob_facebook';
