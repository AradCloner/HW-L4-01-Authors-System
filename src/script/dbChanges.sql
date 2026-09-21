CREATE TABLE authors
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(100)  NOT NULL,
    last_name  VARCHAR(100)  NOT NULL,
    username   VARCHAR(100)  NOT NULL,
    password   VARCHAR(255)  NOT NULL,

    CONSTRAINT pk_authors PRIMARY KEY (id),
    CONSTRAINT uk_authors_username UNIQUE (username)
);



CREATE TABLE categories
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY,
    title VARCHAR(150) NOT NULL,

    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uk_categories_title UNIQUE (title)
);



CREATE TABLE articles
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY,
    title       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    category_id BIGINT       NOT NULL,
    author_id   BIGINT       NOT NULL,

    status      VARCHAR(30)  NOT NULL,
    CONSTRAINT chk_articles_status
        CHECK (status IN ('DRAFT', 'PUBLISHED', 'REVIEWING', 'REJECTED')),

    CONSTRAINT pk_articles PRIMARY KEY (id),

    CONSTRAINT fk_articles_category
        FOREIGN KEY (category_id)
            REFERENCES categories (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,

    CONSTRAINT fk_articles_author
        FOREIGN KEY (author_id)
            REFERENCES authors (id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);



INSERT INTO authors
(
    first_name,
    last_name,
    username,
    password
)
VALUES
    (
        'Ali',
        'Ahmadi',
        'ali.ahmadi',
        'password123'
    ),
    (
        'Sara',
        'Mohammadi',
        'sara.mohammadi',
        'password456'
    ),
    (
        'Reza',
        'Karimi',
        'reza.karimi',
        'password789'
    );


-- درج سه Category

INSERT INTO categories
(
    title
)
VALUES
    (
        'Technology'
    ),
    (
        'Programming'
    ),
    (
        'Database'
    );



INSERT INTO articles
(
    title,
    content,
    category_id,
    author_id,
    status
)
VALUES
    (
        'Introduction to Java',
        'Java is a powerful object-oriented programming language.',
        1,
        1,
        'PUBLISHED'
    ),
    (
        'Spring Boot Tutorial',
        'Spring Boot helps developers build Java applications quickly.',
        2,
        2,
        'DRAFT'
    ),
    (
        'Database Design',
        'Database design is important for data integrity and performance.',
        3,
        3,
        'PUBLISHED'
    );



SELECT
    id,
    first_name,
    last_name,
    username,
    password
FROM authors
ORDER BY id;


SELECT
    id,
    title
FROM categories
ORDER BY id;


SELECT
    id,
    title,
    content,
    category_id,
    author_id,
    status
FROM articles
ORDER BY id;


SELECT
    a.id AS article_id,
    a.title AS article_title,
    a.content,
    a.status,
    c.id AS category_id,
    c.title AS category_title,
    au.id AS author_id,
    au.first_name,
    au.last_name,
    au.username
FROM articles a
         JOIN categories c
              ON c.id = a.category_id
         JOIN authors au
              ON au.id = a.author_id
ORDER BY a.id;



BEGIN;

DELETE FROM articles
WHERE id = 2;



DELETE FROM categories
WHERE id = 2;



DELETE FROM authors
WHERE id = 2;

COMMIT;



SELECT * FROM authors ORDER BY id;

SELECT * FROM categories ORDER BY id;

SELECT * FROM articles ORDER BY id;
