CREATE DATABASE dama
   WITH OWNER postgres
   TEMPLATE template0
   ENCODING 'SQL_ASCII'
   TABLESPACE  pg_default
   LC_COLLATE  'C'
   LC_CTYPE  'C'
   CONNECTION LIMIT  -1;
CREATE TABLE public.dama_user
(
username character varying(20) COLLATE pg_catalog."default" NOT NULL,
email character varying(42) COLLATE pg_catalog."default" NOT NULL,
password_hash character varying(60) COLLATE pg_catalog."default" NOT NULL,
CONSTRAINT dama_user_pkey PRIMARY KEY (username),
CONSTRAINT dama_email_unique UNIQUE (email)
);
CREATE TABLE user_statistics
(
  username character varying(20) COLLATE pg_catalog."default" NOT NULL,
  win int,
  loss int
);
CREATE TABLE games
(
  id BIGSERIAL PRIMARY KEY NOT NULL,
  startdate date default current_date,
  player1 text COLLATE pg_catalog."default",
  player2 text COLLATE pg_catalog."default",
  playerturn text,
  last_move timestamp,
  error_message text,
  status text,
  win text,
  loss text
);
CREATE TABLE game_moves
(
  id BIGSERIAL NOT NULL,
  timestamp timestamp default current_timestamp,
  player text,
  oldlocale text,
  newlocale text
);
create TABLE public.game_board
(
  id BIGSERIAL NOT NULL,
  a1 text,
  a2 text,
  a3 text,
  a4 text,
  a5 text,
  a6 text,
  a7 text,
  a8 text,
  b1 text,
  b2 text,
  b3 text,
  b4 text,
  b5 text,
  b6 text,
  b7 text,
  b8 text,
  c1 text,
  c2 text,
  c3 text,
  c4 text,
  c5 text,
  c6 text,
  c7 text,
  c8 text,
  d1 text,
  d2 text,
  d3 text,
  d4 text,
  d5 text,
  d6 text,
  d7 text,
  d8 text,
  e1 text,
  e2 text,
  e3 text,
  e4 text,
  e5 text,
  e6 text,
  e7 text,
  e8 text,
  f1 text,
  f2 text,
  f3 text,
  f4 text,
  f5 text,
  f6 text,
  f7 text,
  f8 text,
  g1 text,
  g2 text,
  g3 text,
  g4 text,
  g5 text,
  g6 text,
  g7 text,
  g8 text,
  h1 text,
  h2 text,
  h3 text,
  h4 text,
  h5 text,
  h6 text,
  h7 text,
  h8 text
);
