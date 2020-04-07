create table hm_map.account
(
	id bigint auto_increment
		primary key,
	password varchar(128) null,
	status smallint(4) default 0 null comment '状态',
	type smallint(4) default 0 null comment '类型',
	nickname varchar(48) default '小明' null comment '昵称',
	email varchar(128) null comment '邮箱',
	mobile varchar(12) null comment '手机',
	gender smallint(4) default 0 null comment '性别',
	openId varchar(48) null comment 'openId',
	unionId varchar(48) null comment 'unionId',
	avatarUrl varchar(128) null,
	city varchar(24) null,
	create_time datetime default CURRENT_TIMESTAMP null,
	modify_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
	is_deleted smallint(1) null,
	constraint account_mobile_uindex
		unique (mobile),
	constraint account_unionId_uindex
		unique (unionId)
);

create table hm_map.authority
(
	id bigint auto_increment
		primary key,
	userId bigint null,
	authority varchar(32) null,
	create_time datetime default CURRENT_TIMESTAMP null,
	modify_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create index authority_userId_index
	on hm_map.authority (userId);

create table hm_map.tree
(
	id bigint auto_increment
		primary key,
	category varchar(24) null,
	size int null,
	count int null,
	price int null,
	latitude decimal null,
	longitude decimal null,
	brief varchar(256) null,
	type int null,
	user_id bigint null
);

create index tree_user_id_index
	on hm_map.tree (user_id);

