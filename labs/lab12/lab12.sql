.read fa19data.sql

CREATE TABLE obedience AS
    SELECT seven, instructor FROM students;

CREATE TABLE smallest_int AS
    SELECT time, smallest FROM students
    WHERE smallest > 2 ORDER BY smallest LIMIT 20;

CREATE TABLE matchmaker AS
    SELECT a.pet, a.song, a.color, b.color
    FROM students AS a, students AS b
    WHERE a.pet = b.pet AND a.song = b.song AND a.time < b.time;
