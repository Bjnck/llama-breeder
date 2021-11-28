INSERT INTO shop (code, quality, coins) VALUES ('NET', 1, 1);
INSERT INTO shop (code, quality, coins) VALUES ('NET', 2, 10);
INSERT INTO shop (code, quality, coins) VALUES ('NET', 5, 100) ;
INSERT INTO shop (code, quality, coins) VALUES ('NET', 8, 1000) ;

INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 1, 0);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 2, 1);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 3, 10);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 4, 20);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 5, 40);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 6, 80);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 7, 160);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 8, 320);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 9, 640);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 10, 2000);

INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 1, 0);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 2, 1);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 3, 10);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 4, 20) ;
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 5, 40);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 6, 80) ;
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 7, 160) ;
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 8, 320);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 9, 640) ;
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 10, 2000) ;

INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 1, 0) ;
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 2, 2);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 3, 20) ;
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 4, 40) ;
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 5, 80);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 6, 160);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 7, 320) ;
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 8, 640) ;
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 9, 1280) ;
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 10, 4000) ;


INSERT INTO gene (code, special) VALUES ('THIRST', 0) ;
INSERT INTO gene (code, special) VALUES ('HUNGER', 0) ;
INSERT INTO gene (code, special) VALUES ('LOVE', 0) ;
INSERT INTO gene (code, special) VALUES ('CRESUS', 0) ;
INSERT INTO gene (code, special) VALUES ('CHRISTMAS', 1) ;


INSERT INTO color (name, generation, code, parent_code) VALUES ('Crimson', 1, '#DC143C', '#DC143C') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Cornflower', 1, '#6495ED', '#6495ED') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Forest', 1, '#228B22', '#228B22') ;

INSERT INTO color (name, generation, code, parent_code) VALUES ('Orange', 2, '#FFA500', '#23F4987') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Violet', 2, '#9400D3', '#1C7CB38') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Olive', 2, '#808000', '#185C06D') ;

INSERT INTO color (name, generation, code, parent_code) VALUES ('Midnight', 3, '#191970', '#2A826A6') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Tomato', 3, '#FF6347', '#313CAD3') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Khaki', 3, '#F0E68C', '#294A5D3') ;

INSERT INTO color (name, generation, code, parent_code) VALUES ('Turquoise', 4, '#40E0D0', '#2227CB3') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Chocolate', 4, '#D2691E', '#2FA49CF') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Pink', 4, '#FFC0CB', '#308C68A') ;

INSERT INTO color (name, generation, code, parent_code) VALUES ('Chartreuse', 5, '#7FFF00', '#253EB89') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Maroon', 5, '#800000', '#2E573D7') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Slate', 5, '#708090', '#312CB84') ;

INSERT INTO color (name, generation, code, parent_code) VALUES ('Silver', 6, '#C0C0C0', '#1E10020') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Bronze', 6, '#614E1A', '#1F07E90') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Orchid', 6, '#DA70D6', '#1F07F90') ;

INSERT INTO color (name, generation, code, parent_code) VALUES ('Gold', 7, '#FFD700', '#2D6F086') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Ivory', 7, '#FFFFF0', '#2BD4070') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Ebony', 7, '#000000', '#25DCDCA') ;

INSERT INTO color (name, generation, code, parent_code) VALUES ('Rainbow', 8, 'RAINBOW', '#2FFADF0') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Chameleon', 8, 'CHAMELE', '#1FFD6F0') ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Albino', 8, 'ALBINO', '#2FFD6E0') ;

INSERT INTO color (name, generation, code, parent_code) VALUES ('Aqua', 8,'#00FFFF', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Beige', 8,'#F5F5DC', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Peach', 8,'#FFDAB9', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Aquamarine', 8,'#7FFFD4', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Spring', 8,'#00FF7F', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('RosyBrown', 8,'#BC8F8F', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Teal', 8,'#008080', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Indian', 8,'#CD5C5C', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('BloodRed', 8,'#8B0000', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Brick', 8,'#B22222', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('DarkPink', 8,'#FF69B4', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('DeepPink', 8,'#FF1493', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Coral', 8,'#FF7F50', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('FireRed', 8,'#FF4500', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('DarkOrange', 8,'#FF8C00', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Lemon', 8,'#FFFACD', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Papaya', 8,'#FFEFD5', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Moccasin', 8,'#FFE4B5', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Buff', 8,'#BDB76B', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Lavender', 8,'#E6E6FA', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Thistle', 8,'#D8BFD8', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Plum', 8,'#DDA0DD', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Fuchsia', 8,'#FF00FF', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Mauve', 8,'#BA55D3', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Purple', 8,'#800080', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Indigo', 8,'#4B0082', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Lime', 8,'#00FF00', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('PaleGreen', 8,'#98FB98', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('SeaGreen', 8,'#2E8B57', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('LimeTreeGreen', 8,'#9ACD32', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('OliveDrab', 8,'#6B8E23', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Lichen', 8,'#8FBC8B', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Azure', 8,'#E0FFFF', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('CadetBlue', 8,'#5F9EA0', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('SteelBlue', 8,'#4682B4', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('PowderBlue', 8,'#B0E0E6', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('LightBlue', 8,'#ADD8E6', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('SkyBlue', 8,'#87CEEB', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('DeepSkyBlue', 8,'#00BFFF', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Dodger', 8,'#1E90FF', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('SlateBlue', 8,'#6A5ACD', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Royal', 8,'#4169E1', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Cornsilk', 8,'#FFF8DC', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Navajo', 8,'#FFDEAD', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Tan', 8,'#D2B48C', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('SandyBrown', 8,'#F4A460', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Dandelion', 8,'#DAA520', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('SaddleBrown', 8,'#8B4513', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Sienna', 8,'#A0522D', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Brown', 8,'#A52A2A', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Smoke', 8,'#F5F5F5', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('LavenderRed', 8,'#FFF0F5', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('Gainsboro', 8,'#DCDCDC', null) ;
INSERT INTO color (name, generation, code, parent_code) VALUES ('DarkSlate', 8,'#2F4F4F', null) ;

-- REPLACE INTO color (name, generation, code, parent_code) VALUES ('
-- ', 8,'
-- ', null);
