-- 各種テーブル削除
DROP TABLE IF EXISTS guests CASCADE;
DROP TABLE IF EXISTS rooms CASCADE;
DROP TABLE IF EXISTS types CASCADE;
DROP TABLE IF EXISTS reservations CASCADE;


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
total_price INTEGER,
stay_nights INTEGER CHECK (stay_nights > 0),
reservation_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
stay_date DATE,
FOREIGN KEY (guest_id) REFERENCES guests(id)
);
