INSERT INTO users
(id, username, is_admin, created,updated)
  SELECT 1 , 'paul', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP
  WHERE
    NOT EXISTS (
        SELECT id FROM users WHERE id = 1 and username = 'paul'
    );