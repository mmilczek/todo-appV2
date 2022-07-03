drop table if exists tasks_events;
create table tasks_events
(
    id          int primary key auto_increment,
    task_id     int ,
    occurence   datetime,
    name        varchar(30)
)