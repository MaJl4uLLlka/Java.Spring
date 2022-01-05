-- maria db
create table meetups
(
    id            bigint auto_increment
        primary key,
    date          date         not null,
    place         varchar(100) not null,
    requirements  varchar(255) null,
    time          time         not null,
    topic         varchar(100) not null,
    creator_email varchar(255) not null,
    constraint meetups_to_email
        foreign key (creator_email) references users (email)
);

create table reports
(
    id             bigint auto_increment
        primary key,
    topic          varchar(100) not null,
    reporter_email varchar(255) not null,
    constraint reports_to_user_email
        foreign key (reporter_email) references users (email)
            on delete cascade
);

create table requests
(
    id              bigint auto_increment
        primary key,
    is_canceled     bit default b'0' not null,
    meetup_id       bigint           not null,
    report_id       bigint           null,
    requester_email varchar(255)     not null,
    is_approved     bit default b'0' not null,
    constraint report_id
        unique (report_id),
    constraint report_to_meetup
        foreign key (meetup_id) references meetups (id)
            on delete cascade,
    constraint request_to_report
        foreign key (report_id) references reports (id)
            on delete set null,
    constraint request_to_user_email
        foreign key (requester_email) references users (email)
);

create table users
(
    id         bigint auto_increment
        primary key,
    email      varchar(255)                 null,
    first_name varchar(100)                 null,
    last_name  varchar(100)                 null,
    password   varchar(255)                 null,
    role       varchar(20) default 'USER'   not null,
    status     varchar(20) default 'ACTIVE' not null,
    constraint users_email_uindex
        unique (email)
);

create or replace
    definer = root@localhost procedure FIND_EIGHT_MEETUPS(IN page int)
begin
    select ID, DATE, PLACE, REQUIREMENTS, TIME, TOPIC, CREATOR_EMAIL from (select ID, DATE, PLACE, REQUIREMENTS, TIME, TOPIC, CREATOR_EMAIL, row_number() over (order by meetup.meetups.id)
        rn from meetup.meetups where (datediff(date,current_date) > 0 or ((datediff(date,current_date) = 0 and timediff(time, current_time) > 0)))) as Nr where (rn between (page * 8 +1) and (page * 8 + 8));
end;

create or replace
    definer = root@localhost procedure FIND_EIGHT_MEETUP_BY_DATE(IN page bigint, IN p_date date)
begin
    select ID, DATE, PLACE, REQUIREMENTS, TIME, TOPIC, CREATOR_EMAIL from (select ID, DATE, PLACE, REQUIREMENTS, TIME, TOPIC, CREATOR_EMAIL, row_number() over (order by meetup.meetups.id)
        rn from meetup.meetups where date = p_date) as Nr where rn between (page * 8 +1) and (page * 8 + 8);
end;

create or replace
    definer = root@localhost procedure FIND_EIGHT_MEETUP_BY_DATE_AND_TIME(IN page bigint, IN p_date date, IN start_time time,
                                                                          IN end_time time)
begin
    select ID, DATE, PLACE, REQUIREMENTS, TIME, TOPIC, CREATOR_EMAIL from (select ID, DATE, PLACE, REQUIREMENTS, TIME, TOPIC, CREATOR_EMAIL, row_number() over (order by meetup.meetups.id)
        rn from meetup.meetups where date = p_date and time between start_time and end_time) as Nr where rn between (page * 8 +1) and (page * 8 + 8);
end;

create or replace
    definer = root@localhost procedure GET_COUNT_MEETUP_BY_DATE(IN p_date date)
begin
    select COUNT(*) from meetups where date = p_date;
end;

create or replace
    definer = root@localhost procedure GET_COUNT_MEETUP_BY_DATE_AND_TIME(IN p_date date, IN start_time time, IN end_time time)
begin
    select COUNT(*) from meetups where date = p_date and time between start_time and end_time;
end;

create or replace
    definer = root@localhost procedure FIND_EIGHT_BY_TIME(IN page bigint, IN start time, IN end time)
begin
    select ID, DATE, PLACE, REQUIREMENTS, TIME, TOPIC, CREATOR_EMAIL from (select ID, DATE, PLACE, REQUIREMENTS, TIME, TOPIC, CREATOR_EMAIL, row_number() over (order by meetup.meetups.id)
        rn from meetup.meetups where ((datediff(date,current_date) > 0 or ((datediff(date,current_date) = 0) and timediff(time, current_time) > 0))  and time between start and end)) as Nr where (rn between (page * 8 +1) and (page * 8 + 8));
end;

create or replace
    definer = root@localhost procedure FIND_EIGHT_REQUESTS(IN email varchar(255), IN page bigint)
begin
    select id, is_canceled, meetup_id, report_id, requester_email, is_approved from (select ID, IS_CANCELED, MEETUP_ID, REPORT_ID, REQUESTER_EMAIL, r.is_approved,   row_number() over (order by r.id) rn from requests r where r.requester_email = email) as Nr
    where (rn between (page * 8 +1) and (page * 8 + 8));
end;

create or replace
    definer = root@localhost procedure FIND_EIGHT_REPORTS(IN email varchar(255), IN page bigint)
begin
    select id, topic, reporter_email from (select id, topic, reporter_email,   row_number() over (order by r.id) rn from reports r where r.reporter_email = email) as Nr
    where (rn between (page * 8 +1) and (page * 8 + 8));
end;
