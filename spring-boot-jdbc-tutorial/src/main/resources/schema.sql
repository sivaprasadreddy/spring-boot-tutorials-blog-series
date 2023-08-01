create table if not exists bookmarks
(
    id         bigserial not null,
    title      varchar   not null,
    url        varchar   not null,
    created_at timestamp,
    primary key (id)
);
