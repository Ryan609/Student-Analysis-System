use StudentProgressAnalysis;

drop table if exists grade;
create table grade(
    student_code varchar(5) not null,
    course_code varchar(4) not null,
    course_name varchar(50) not null,
    units int not null,
    ismaster bool not null,
    grade float not null,
    year int not null,
    semester int not null,
    honors_count int not null
)engine MyISAM;