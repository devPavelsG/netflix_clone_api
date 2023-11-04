-- Create table for public."_user"
CREATE TABLE public."_user" (
    created_at timestamptz NOT NULL,
    updated_at timestamptz NULL,
    id uuid NOT NULL,
    email varchar(255) NULL,
    firstname varchar(255) NULL,
    lastname varchar(255) NULL,
    "password" varchar(255) NULL,
    "role" varchar(255) NULL,
    CONSTRAINT "_user_pkey" PRIMARY KEY (id),
    CONSTRAINT "_user_role_check" CHECK ("role" IN ('USER', 'ADMIN', 'MANAGER'))
);

-- Create table for public."token"
CREATE TABLE public."token" (
    expired bool NOT NULL,
    revoked bool NOT NULL,
    created_at timestamptz NOT NULL,
    updated_at timestamptz NULL,
    id uuid NOT NULL,
    user_id uuid NULL,
    "token" varchar(255) NULL,
    "refresh_token" varchar(255) NULL,
    token_type varchar(255) NULL,
    CONSTRAINT token_pkey PRIMARY KEY (id),
    CONSTRAINT token_token_key UNIQUE ("token"),
    CONSTRAINT token_refresh_token_key UNIQUE ("refresh_token"),
    CONSTRAINT token_token_type_check CHECK (token_type = 'BEARER')
);

-- Create foreign key constraint for user_id
ALTER TABLE public."token" ADD CONSTRAINT fk_user_token FOREIGN KEY (user_id) REFERENCES public."_user"(id);

-- Create table for public."sub_account"
CREATE TABLE public."sub_user" (
    created_at timestamptz NOT NULL,
    updated_at timestamptz NULL,
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    name varchar(255) NOT NULL,
    CONSTRAINT sub_account_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user_sub_account FOREIGN KEY (user_id) REFERENCES public."_user"(id)
);
