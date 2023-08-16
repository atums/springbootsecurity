CREATE TABLE users(
                      users_id int GENERATED ALWAYS AS IDENTITY NOT NULL,
                      email varchar(256) UNIQUE NOT NULL,
                      first_name varchar(50) NOT NULL,
                      last_name varchar(100) NOT NULL,
                      password varchar(256) NOT NULL,
                      role varchar(20) DEFAULT 'USER',
                      status varchar(20) DEFAULT 'active',
                      CONSTRAINT pk_users_user_id PRIMARY KEY (users_id)
);

INSERT INTO users (email, first_name, last_name, password, role, status) VALUES
 ('admin@mail.com', 'Admin', 'Adminov', '$2a$12$7Fqd3jAci9Uqh9xxlecEtuvpv49LlHSRm3lf30.ESenlU7.BV2sDK', 'ADMIN', 'ACTIVE'),
 ('user@mail.com', 'User', 'Userov', '$2a$12$wJEjcoeZjOP6Pf69SHgmSewoNHeVD8mO1iJsimMiSVWREQdfyqPvO', 'USER', 'BANNED');