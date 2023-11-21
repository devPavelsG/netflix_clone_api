CREATE TABLE users (
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NULL,
    id RAW(16) NOT NULL,
    email varchar2(255 CHAR) NULL,
    firstname varchar2(255 CHAR) NULL,
    lastname varchar2(255 CHAR) NULL,
    password varchar2(255 CHAR) NULL,
    role varchar2(255 CHAR) NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_role_check CHECK (role IN ('USER', 'ADMIN', 'MANAGER')),
    CONSTRAINT unique_email UNIQUE (email)
);

-- Create table for tokens
CREATE TABLE tokens (
    expired NUMBER(1) NOT NULL,
    revoked NUMBER(1) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NULL,
    id RAW(16) NOT NULL,
    user_id RAW(16) NULL,
    token varchar2(255 CHAR) NULL,
    refresh_token varchar2(255 CHAR) NULL,
    token_type varchar2(255 CHAR) NULL,
    CONSTRAINT tokens_pkey PRIMARY KEY (id),
    CONSTRAINT tokens_token_key UNIQUE (token),
    CONSTRAINT tokens_refresh_token_key UNIQUE (refresh_token),
    CONSTRAINT tokens_token_type_check CHECK (token_type = 'BEARER'),
    CONSTRAINT fk_user_token FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create table for sub users
CREATE TABLE sub_users (
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NULL,
    id RAW(16) NOT NULL,
    user_id RAW(16) NOT NULL,
    name varchar2(255 CHAR) NOT NULL,
    CONSTRAINT sub_users_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_sub_user FOREIGN KEY (user_id) REFERENCES users(id)
);
