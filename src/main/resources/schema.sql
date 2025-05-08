create table bids (bid_price float(53) not null, bid_date timestamp(6) not null, id uuid not null, listing_id uuid not null, user_id uuid not null, primary key (id));
create table credentials (user_id uuid not null, email varchar(255) not null, password_hash varchar(255) not null, username varchar(255) not null, primary key (user_id));
create table images (upload_time timestamp(6) not null, id uuid not null, primary key (id));
create table listing_images (image_id uuid not null, listing_id uuid not null, primary key (image_id));
create table listings (starting_price float(53) not null, status smallint not null check (status between 0 and 7), end_date timestamp(6) not null, start_date timestamp(6) not null, listing_id uuid not null, main_image_id uuid not null, product_id uuid not null, seller_id uuid not null, primary key (listing_id));
create table products (owner_id uuid not null, product_id uuid not null, category varchar(255) not null, description varchar(255) not null, name varchar(255) not null, sub_category varchar(255) not null, primary key (product_id));
create table sessions (expiry timestamp(6) not null, id uuid not null, user_id uuid not null, token varchar(255) not null, primary key (id));
create table transactions (transaction_date timestamp(6) not null, bid_id uuid not null, id uuid not null, listing_id uuid not null, next_owner_id uuid not null, previous_owner_id uuid not null, primary key (id));
create table users (platform_access smallint not null check (platform_access between 0 and 2), reputation integer not null, registration_date timestamp(6) not null, avatar_id uuid, id uuid not null, delivery_location varchar(255) not null, name varchar(255) not null, wallet_address varchar(255) not null, primary key (id));
