--liquibase formatted sql

--changeset StormIT:001_1

create table if not exists categories (
	id   uuid not null
		primary key,
	name varchar(255)
);
create table if not exists questions (
	id          uuid not null
		primary key,
	content        varchar(255),
	category_id uuid
		constraint fkctl6tuf74n8cufkb3ulj6b3fc
			references categories
);
create table if not exists answers (
	id          uuid not null
		primary key,
	content        varchar(255),
	question_id uuid
		constraint fk3erw1a3t0r78st8ty27x6v3g1
			references questions
);

--changeset StormIT:001_2
delete from answers;
delete from questions;
delete from categories;

--changeset StormIT:001_3
insert into categories (id, name) values
	(gen_random_uuid(), 'Zdrowie'),
	(gen_random_uuid(), 'Zwierzęta'),
	(gen_random_uuid(), 'Turystyka'),
	(gen_random_uuid(), 'Uroda i Styl'),
	(gen_random_uuid(), 'Kultura'),
	(gen_random_uuid(), 'Edukacja'),
	(gen_random_uuid(), 'Gry'),
	(gen_random_uuid(), 'Hobby'),
	(gen_random_uuid(), 'Dom i Ogród'),
	(gen_random_uuid(), 'Biznes'),
	(gen_random_uuid(), 'Finanse'),
	(gen_random_uuid(), 'Kulinaria'),
	(gen_random_uuid(), 'Komputery'),
	(gen_random_uuid(), 'Osobiste'),
	(gen_random_uuid(), 'Motoryzacja'),
	(gen_random_uuid(), 'Polityka'),
	(gen_random_uuid(), 'Praca'),
	(gen_random_uuid(), 'Prezenty'),
	(gen_random_uuid(), 'Zakupy'),
	(gen_random_uuid(), 'Elektronika'),
	(gen_random_uuid(), 'Rozrywka'),
	(gen_random_uuid(), 'Sex'),
	(gen_random_uuid(), 'Związki'),
	(gen_random_uuid(), 'Inne');

insert into questions (id, content, category_id) values
	(gen_random_uuid(), 'Gdzie najlepiej spędzić wakacje z Polsce', (select id from categories where name = 'Turystyka')),
	(gen_random_uuid(), 'Gdzie najlepiej spędzić wakacje z Europie', (select id from categories where name = 'Turystyka'));
