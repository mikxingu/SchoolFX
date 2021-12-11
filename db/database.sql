CREATE  DATABASE schoolfx


CREATE TABLE public.tb_lectures (
    Id SERIAL PRIMARY KEY,
    Name VARCHAR (50) NOT NULL,
    Minimum_Grade NUMERIC DEFAULT '5.0' NOT NULL 
);

CREATE TABLE public.tb_students (
    Id SERIAL PRIMARY KEY ,
    Name VARCHAR (100) NOT NULL,
    Email VARCHAR (100) NOT NULL 
);

CREATE TABLE public.tb_student_lectures (
    student_id INT NOT NULL,
    lecture_id INT NOT NULL,
    Actual_Grade NUMERIC NOT NULL,
    enrollment_date DATE NOT NULL,
    PRIMARY KEY (student_id, lecture_id),
    FOREIGN KEY (student_id)
        REFERENCES tb_students (Id),
    FOREIGN KEY (lecture_id)
        REFERENCES tb_lectures (Id)
);

INSERT INTO public.tb_lectures(Name)
    VALUES
    ('Português'),
    ('Matemática'),
    ('História'),
    ('Geografia'),
    ('Ciências'),
    ('Filosofia'),
    ('Sociologia'),
    ('Inglês');

INSERT INTO public.tb_students(Name, Email)
    VALUES
    ('Joaozinho', 'joao@gmail.com'),
    ('Maria', 'maria@gmail.com'),
    ('Lucas', 'lucas@gmail.com'),
    ('Matheus', 'matheus@gmail.com'),
    ('Alan', 'alan@gmail.com');

DROP TABLE public.tb_lectures, public.tb_students, public.tb_student_lectures;