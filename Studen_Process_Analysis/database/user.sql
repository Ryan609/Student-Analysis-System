use StudentProgressAnalysis;
drop table if exists user;
create table user(
	user_id int primary key auto_increment,
    username varchar(20) not null,
    password varchar(64) not null, # SHA256
    admin bool not null default false
)engine MyISAM;

# default user
insert into user(username, password) values('professor', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225'); # professor, 123456789
# default administrator
insert into user(username, password, admin) values('admin', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', true); # admin, 123456789