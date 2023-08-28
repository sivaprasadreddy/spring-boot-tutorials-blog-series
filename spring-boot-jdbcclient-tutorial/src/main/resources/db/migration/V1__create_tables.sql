create table bookmarks
(
    id         bigserial primary key,
    title      varchar not null,
    url        varchar not null,
    created_at timestamp
);
