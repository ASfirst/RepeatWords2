INSERT INTO listen_shall_learning_tb  SELECT id,datetime('now') FROM dictionary_tb;
INSERT INTO speak_shall_learning_tb  SELECT id,datetime('now') FROM dictionary_tb;
INSERT INTO write_shall_learning_tb  SELECT id,datetime('now') FROM dictionary_tb;