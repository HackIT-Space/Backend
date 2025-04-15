create schema if not exists hackathons;

create table hackathons.t_hackathon
(
    id            serial primary key,
    c_title       varchar(50) not null check (length(trim(c_title)) >= 3),
    c_description varchar(1000)
);
