create table bids (bid_price float(53) not null, bid_date timestamp(6) not null, id uuid not null, listing_id uuid not null, user_id uuid not null, primary key (id));
create table credentials (user_id uuid not null, email varchar(255) not null, password_hash varchar(255) not null, username varchar(255) not null, primary key (user_id));
create table customers (user_id uuid not null, primary key (user_id));
create table images (upload_time timestamp(6) not null, id uuid not null, primary key (id));
create table listing_images (image_id uuid not null, listing_id uuid not null, primary key (image_id));
create table listings (authenticity integer not null, bestseller boolean not null, starting_price float(53) not null, status integer not null, date timestamp(6) not null, id uuid not null, main_image_id uuid not null, category varchar(255) not null, description varchar(255) not null, name varchar(255) not null, sub_category varchar(255) not null, primary key (id));
create table sellers (reputation integer not null, user_id uuid not null, pickup_location varchar(255) not null, primary key (user_id));
create table sessions (expiry timestamp(6) not null, user_id uuid not null, token varchar(255) not null, primary key (user_id));
create table users (platform_access integer not null, registration_date timestamp(6) not null, avatar_id uuid, id uuid not null, name varchar(255) not null, primary key (id));
