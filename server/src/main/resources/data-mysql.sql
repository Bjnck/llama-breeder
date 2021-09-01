INSERT INTO shop (code, quality, coins) VALUES ('NEST', 1, 100) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('NEST', 2, 200) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('NEST', 3, 300) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);

INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 1, 0) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 2, 10) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 3, 15) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 4, 20) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 5, 25) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 6, 30) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 7, 35) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 8, 40) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 9, 45) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('LOVE', 10, 50) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);

INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 1, 0) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 2, 10) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 3, 15) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 4, 20) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 5, 25) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 6, 30) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 7, 35) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 8, 40) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 9, 45) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('HUNGER', 10, 50) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);

INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 1, 0) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 2, 10) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 3, 15) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 4, 20) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 5, 25) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 6, 30) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 7, 35) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 8, 40) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 9, 45) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);
INSERT INTO shop (code, quality, coins) VALUES ('THIRST', 10, 50) ON DUPLICATE KEY UPDATE code=VALUES(code), quality=VALUES(quality), coins=VALUES(coins);


INSERT INTO gene (code) VALUES ('FERTILE') ON DUPLICATE KEY UPDATE code=VALUES(code);
INSERT INTO gene (code) VALUES ('THIRST') ON DUPLICATE KEY UPDATE code=VALUES(code);
INSERT INTO gene (code) VALUES ('HUNGER') ON DUPLICATE KEY UPDATE code=VALUES(code);
INSERT INTO gene (code) VALUES ('LOVE') ON DUPLICATE KEY UPDATE code=VALUES(code);


INSERT INTO color (name, generation, code, parent_code) VALUES ('Crimson', 1, '#DC143C', '#DC143C') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Cornflower', 1, '#6495ED', '#6495ED') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Forest', 1, '#228B22', '#228B22') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);

INSERT INTO color (name, generation, code, parent_code) VALUES ('Orange', 2, '#FFA500', '#23F4987') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Violet', 2, '#9400D3', '#163354B') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Olive', 2, '#808000', '#185C06D') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);

INSERT INTO color (name, generation, code, parent_code) VALUES ('Midnight', 3, '#191970', '#2A826A6') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Tomato', 3, '#FF6347', '#313CAD3') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Khaki', 3, '#F0E68C', '#294A5D3') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);

INSERT INTO color (name, generation, code, parent_code) VALUES ('Turquoise', 4, '#40E0D0', '#2227CB3') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Chocolate', 4, '#D2691E', '#2FA49CF') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Pink', 4, '#FFC0CB', '#308C68A') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);

INSERT INTO color (name, generation, code, parent_code) VALUES ('Chartreuse', 5, '#7FFF00', '#253EB89') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Maroon', 5, '#800000', '#2E573D7') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Slate', 5, '#708090', '#312CB84') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);

INSERT INTO color (name, generation, code, parent_code) VALUES ('Silver', 6, '#C0C0C0', '#1E10020') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Bronze', 6, '#614E1A', '#1F07E90') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Orchid', 6, '#DA70D6', '#1F07F90') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);

INSERT INTO color (name, generation, code, parent_code) VALUES ('Gold', 7, '#FFD700', '#2D6F086') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Ivory', 7, '#FFFFF0', '#2BD4070') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Ebony', 7, '#000000', '#25DCDCA') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);

INSERT INTO color (name, generation, code, parent_code) VALUES ('Rainbow', 8, 'RAINBOW', '#2FFADF0') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Chameleon', 8, 'CHAMELE', '#1FFD6F0') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES ('Albino', 8, 'ALBINO', '#2FFD6E0') ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);

INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Aqua	                '), 8,TRIM('#00FFFF'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Beige	                '), 8,TRIM('#F5F5DC'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Peach	                '), 8,TRIM('#FFDAB9'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Aquamarine              '), 8,TRIM('#7FFFD4'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Spring	                '), 8,TRIM('#00FF7F'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('RosyBrown	            '), 8,TRIM('#BC8F8F'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Teal	                '), 8,TRIM('#008080'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Indian	                '), 8,TRIM('#CD5C5C'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('BloodRed	            '), 8,TRIM('#8B0000'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Brick	                '), 8,TRIM('#B22222'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('DarkPink	            '), 8,TRIM('#FF69B4'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('DeepPink	            '), 8,TRIM('#FF1493'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Coral	                '), 8,TRIM('#FF7F50'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('FireRed	                '), 8,TRIM('#FF4500'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('DarkOrange	            '), 8,TRIM('#FF8C00'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Lemon	                '), 8,TRIM('#FFFACD'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Papaya	                '), 8,TRIM('#FFEFD5'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Moccasin	            '), 8,TRIM('#FFE4B5'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Buff	                '), 8,TRIM('#BDB76B'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Lavender	            '), 8,TRIM('#E6E6FA'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Thistle	                '), 8,TRIM('#D8BFD8'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Plum	                '), 8,TRIM('#DDA0DD'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Fuchsia	                '), 8,TRIM('#FF00FF'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Mauve	                '), 8,TRIM('#BA55D3'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Purple	                '), 8,TRIM('#800080'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Indigo	                '), 8,TRIM('#4B0082'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Lime	                '), 8,TRIM('#00FF00'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('PaleGreen	            '), 8,TRIM('#98FB98'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('SeaGreen	            '), 8,TRIM('#2E8B57'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('LimeTreeGreen	        '), 8,TRIM('#9ACD32'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('OliveDrab	            '), 8,TRIM('#6B8E23'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Lichen	                '), 8,TRIM('#8FBC8B'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Azure	                '), 8,TRIM('#E0FFFF'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('CadetBlue	            '), 8,TRIM('#5F9EA0'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('SteelBlue	            '), 8,TRIM('#4682B4'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('PowderBlue	            '), 8,TRIM('#B0E0E6'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('LightBlue	            '), 8,TRIM('#ADD8E6'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('SkyBlue	                '), 8,TRIM('#87CEEB'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('DeepSkyBlue	            '), 8,TRIM('#00BFFF'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Dodger	                '), 8,TRIM('#1E90FF'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('SlateBlue               '), 8,TRIM('#6A5ACD'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Royal	                '), 8,TRIM('#4169E1'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Cornsilk	            '), 8,TRIM('#FFF8DC'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Navajo	                '), 8,TRIM('#FFDEAD'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Tan	                    '), 8,TRIM('#D2B48C'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('SandyBrown	            '), 8,TRIM('#F4A460'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Dandelion	            '), 8,TRIM('#DAA520'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('SaddleBrown             '), 8,TRIM('#8B4513'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Sienna	                '), 8,TRIM('#A0522D'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Brown	                '), 8,TRIM('#A52A2A'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Smoke	                '), 8,TRIM('#F5F5F5'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('LavenderRed	            '), 8,TRIM('#FFF0F5'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('Gainsboro	            '), 8,TRIM('#DCDCDC'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);
INSERT INTO color (name, generation, code, parent_code) VALUES (TRIM('DarkSlate	            '), 8,TRIM('#2F4F4F'), null) ON DUPLICATE KEY UPDATE name=VALUES(name), generation=VALUES(generation), code=VALUES(code), parent_code=VALUES(parent_code);

-- REPLACE INTO color (name, generation, code, parent_code) VALUES (TRIM('
-- '), 8,TRIM('
-- '), null);
