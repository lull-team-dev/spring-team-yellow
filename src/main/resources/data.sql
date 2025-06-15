-- guests（宿泊者）
INSERT INTO guests (name, mail, address, tel, password) VALUES
('デヴィ夫人', 'ddd@mail', '東京都港区白金台5丁目12番地 プラチナレジデンス白金台 801号室', '0456-99-1234', 'pass1'),
('叶恭子', 'kkk@mail', '東京都大田区田園調布3丁目8番地 田園ヴィラ調布 203号室', '090-0000-0000', 'pass2'),
('叶美香', 'ccc@mail', '東京都品川区北品川4丁目6番地 御殿山ヒルズレジデンス 1502号室', '080-2222-3333', 'pass3'),
('芦屋 紗栄子', 'saeko@richmail.com', '兵庫県芦屋市六麓荘町12-1 六麓パークレジデンス 301号室', '078-1111-2222', 'pass4'),
('西園寺 光子', 'mitsu@luxmail.jp', '東京都渋谷区広尾4丁目9番地 広尾ガーデンヒルズ 502号室', '03-3333-4444', 'pass5'),
('東雲 麗華', 'reika@elite.jp', '東京都千代田区一番町10-3 番町レジデンス 702号室', '03-5555-6666', 'pass6'),
('大蔵 財子', 'zaiko@vipmail.jp', '東京都港区赤坂9丁目7番地 ミッドタウンレジデンス 1101号室', '03-7777-8888', 'pass7'),
('御園 翠', 'midori@gold.jp', '東京都中央区銀座6丁目10番地 銀座プレミアムタワー 2601号室', '03-9999-0000', 'pass8');

-- types（部屋タイプ）
INSERT INTO types (type_name) VALUES
('シングルルーム'),
('ダブルルーム'),
('スイートルーム');


-- rooms（部屋）
INSERT INTO rooms (room_name, price, type_id, img_path, description) VALUES
('オーシャンスイート', 55000, 3, '/uploads/images/underthesea.jpg', '海と空を独り占めできる贅沢なスイート'),
('シーサイドヴィラ', 60000, 3, '/uploads/images/underthesea.jpg', 'プライベートビーチ直結の開放的なヴィラ'),
('マリンデラックス', 48000, 2, '/uploads/images/underthesea.jpg', '海を感じるモダンインテリアのダブルルーム'),
('サンセットスイート', 52000, 3, '/uploads/images/underthesea.jpg', 'サンセットを一望できるロマンチックなスイート'),
('パームガーデンルーム', 45000, 2, '/uploads/images/underthesea.jpg', '南国のパームツリーに囲まれた癒しのダブルルーム'),
('コーラルルーム', 43000, 2, '/uploads/images/underthesea.jpg', 'サンゴ礁をモチーフにした明るいダブルルーム'),
('ラグーンスイート', 57000, 3, '/uploads/images/underthesea.jpg', 'ラグーンビューの極上スイート'),
('マーメイドルーム', 46000, 2, '/uploads/images/underthesea.jpg', '海の妖精をテーマにした幻想的なダブルルーム'),
('ホライズンスイート', 54000, 3, '/uploads/images/underthesea.jpg', '地平線まで続くオーシャンビューが魅力のスイート'),
('シェルルーム', 42000,1, '/uploads/images/underthesea.jpg', '貝殻モチーフの上品なシングルルーム'),
('マリンシングル', 41000, 1, '/uploads/images/underthesea.jpg', 'ひとり旅に最適な落ち着いたマリンスタイルの空間'),
('ブルーラグーンサイドルーム', 41500, 1, '/uploads/images/underthesea.jpg', '青いラグーンを眺めながらくつろげるシングルルーム'),
('アクアスイート', 56000, 3, '/uploads/images/underthesea.jpg', '水面の煌めきに包まれた優雅なスイート'),
('オーシャンテラスルーム', 47000, 2, '/uploads/images/underthesea.jpg', '海風が心地よい開放的なテラス付きダブルルーム'),
('ネプチューンルーム', 44000, 2, '/uploads/images/underthesea.jpg', '海神ネプチューンをイメージした神秘的な空間'),
('サーフサイドスイート', 53000, 3, '/uploads/images/underthesea.jpg', 'サーフィンをテーマにしたスタイリッシュなスイート'),
('シーブルールーム', 43000, 2, '/uploads/images/underthesea.jpg', '青と白で統一された爽やかなダブルルーム'),
('クリスタルウェーブルーム', 45500, 2, '/uploads/images/underthesea.jpg', '水晶の波を感じさせる静寂なダブルルーム'),
('アトランティススイート', 59000, 3, '/uploads/images/underthesea.jpg', '伝説の海底都市を思わせる幻想的なスイート'),
('カリビアンルーム', 46500, 2, '/uploads/images/underthesea.jpg', 'カリブの陽気な風を感じるリゾート感満載のダブルルーム'),
('コーストラインルーム', 42000, 1, '/uploads/images/underthesea.jpg', '海岸線を望む静かなシングルルーム'),
('ノーチラスルーム', 42500, 1, '/uploads/images/underthesea.jpg', '潜水艦をモチーフにしたクールなシングルルーム'),
('アズールシングル', 41500, 1, '/uploads/images/underthesea.jpg', '地中海の青をイメージした落ち着きのあるシングルルーム'),
('リーフサイドルーム', 41800, 1, '/uploads/images/underthesea.jpg', 'サンゴ礁に囲まれたような自然派シングルルーム');
