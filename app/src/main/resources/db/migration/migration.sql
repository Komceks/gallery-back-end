DROP TABLE IMAGE;
DROP TABLE TAG;
DROP TABLE IMAGES_TAGS;

CREATE TABLE image (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    image bytea,
    name text,
    description text,
    date_ date,
    author text
);
CREATE TABLE tag (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name text
);
CREATE TABLE images_tags (
    image_id INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (image_id, tag_id),

    CONSTRAINT fk_image
    FOREIGN KEY (IMAGE_ID)
    REFERENCES IMAGE(ID) on delete cascade,

    CONSTRAINT fk_tag
    FOREIGN KEY (TAG_ID)
    REFERENCES TAG(ID) on delete cascade
);

