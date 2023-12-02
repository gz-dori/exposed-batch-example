create table article(
    article_id int auto_increment primary key,
    title varchar(255) not null,
    content text not null,
    author varchar(100) not null
);