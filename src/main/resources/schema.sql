-- 各種テーブル削除
DROP TABLE IF EXISTS guests CASCADE;
DROP TABLE IF EXISTS rooms CASCADE;
DROP TABLE IF EXISTS types CASCADE;
DROP TABLE IF EXISTS reservations CASCADE;
DROP TABLE IF EXISTS reservation_details CASCADE;
DROP TABLE IF EXISTS plans CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS reviews CASCADE;


-- guests（宿泊者）
CREATE TABLE guests(
id SERIAL PRIMARY KEY,
name VARCHAR(20) NOT NULL,
email VARCHAR(255) NOT NULL UNIQUE,
address VARCHAR(161) NOT NULL,
tel VARCHAR(15) NOT NULL,
password VARCHAR(128) NOT NULL CHECK(CHAR_LENGTH(password) >= 5),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- types（タイプ）
CREATE TABLE types(
id SERIAL PRIMARY KEY,
type_name TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


--  rooms（部屋）
CREATE TABLE rooms(
id SERIAL PRIMARY KEY,
room_name TEXT,
price INTEGER,
type_id INTEGER,
img_path TEXT,
img_path2 TEXT,
description TEXT,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (type_id) REFERENCES types(id)
);
--もしカテゴリーに対応するホテルがあった場合、カテゴリーを消そうとするとエラーになる


--reservations（予約情報）
CREATE TABLE reservations (
id SERIAL PRIMARY KEY,
guest_id INTEGER,
room_id INTEGER,
plan_id INTEGER,
guest_count Integer,
total_price INTEGER,
stay_nights INTEGER CHECK (stay_nights > 0),
reservation_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
stay_date DATE,
FOREIGN KEY (guest_id) REFERENCES guests(id)
);


--reservation_details（1日ごとの予約情報）
CREATE TABLE reservation_details(
id SERIAL PRIMARY KEY,
reservation_id INTEGER,
stay_one_date DATE,
price_per_day INTEGER,
FOREIGN KEY (reservation_id) REFERENCES reservations(id)
);


CREATE TABLE plans(
id SERIAL PRIMARY KEY,
name TEXT,
details TEXT,
price INTEGER
);


CREATE TABLE likes (
	id SERIAL PRIMARY KEY,
    guest_id INTEGER,
    room_id INTEGER
);


CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    guest_id INTEGER NOT NULL,
    room_id INTEGER NOT NULL,
    reservation_id INTEGER NOT NULL,
    rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);