## Spring Batch will generate batch_job_... and batch_step_... tables ##
## Note Application Auto generate all schemas including these below ##

create schema if not exists parser;

use parser;

create table if not exists parser.access_log
(
  date datetime(3) not null,
  ip_address varchar(255) not null,
  request varchar(255) null,
  status varchar(255) null,
  user_agent varchar(255) null,
  primary key (date, ip_address)
)
;

create table if not exists parser.blocked_ip
(
  ip_address varchar(255) not null
    primary key,
  comment varchar(255) null
)
;
