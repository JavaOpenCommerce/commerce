INSERT INTO IMAGE VALUES ('1', 'image1', 'https://via.placeholder.com/200.png');
INSERT INTO IMAGE VALUES ('2', 'image2', 'https://via.placeholder.com/200.png');
INSERT INTO IMAGE VALUES ('3', 'image3', 'https://via.placeholder.com/200.png');
INSERT INTO IMAGE VALUES ('4', 'image4', 'https://via.placeholder.com/200.png');
INSERT INTO IMAGE VALUES ('5', 'image5', 'https://via.placeholder.com/200.png');
INSERT INTO IMAGE VALUES ('6', 'image6', 'https://via.placeholder.com/200.png');
INSERT INTO IMAGE VALUES ('7', 'image7', 'https://via.placeholder.com/200.png');
INSERT INTO IMAGE VALUES ('8', 'image8', 'https://via.placeholder.com/200.png');
INSERT INTO IMAGE VALUES ('9', 'image9', 'https://via.placeholder.com/200.png');
INSERT INTO IMAGE VALUES ('10', 'image10', 'https://via.placeholder.com/200.png');

INSERT INTO PRODUCER VALUES ('1', 'testProducer','pl-PL', 'Producer1', '8');
INSERT INTO PRODUCER VALUES ('2', 'testProducer','pl-PL', 'Producer2', '9');
INSERT INTO PRODUCER VALUES ('3', 'testProducer','pl-PL', 'Producer3', '10');

INSERT INTO CATEGORY VALUES ('1', 'Shipping', 'shippingMethods', 'pl-PL');
INSERT INTO CATEGORY VALUES ('2', 'Category2', 'testCategory', 'pl-PL');
INSERT INTO CATEGORY VALUES ('3', 'Category3', 'testCategory', 'pl-PL');
INSERT INTO CATEGORY VALUES ('4', 'Category4', 'testCategory', 'pl-PL');

INSERT INTO ITEM VALUES ('1', '1', '0.0', '0.23', '1');

INSERT INTO ITEM VALUES ('2', '3', '11.56', '0.23', '2');
INSERT INTO ITEM VALUES ('3', '0', '12.33', '0.23', '3');
INSERT INTO ITEM VALUES ('4', '2', '1.45', '0.23', '4');
INSERT INTO ITEM VALUES ('5', '2', '0.99', '0.23', '5');
INSERT INTO ITEM VALUES ('6', '2', '2.45', '0.23', '6');
INSERT INTO ITEM VALUES ('7', '2', '1.45', '0.23', '7');

INSERT INTO ITEM_PRODUCER VALUES ('1', '1');
INSERT INTO ITEM_PRODUCER VALUES ('2', '2');
INSERT INTO ITEM_PRODUCER VALUES ('3', '3');
INSERT INTO ITEM_PRODUCER VALUES ('4', '1');
INSERT INTO ITEM_PRODUCER VALUES ('5', '3');
INSERT INTO ITEM_PRODUCER VALUES ('6', '2');
INSERT INTO ITEM_PRODUCER VALUES ('7', '1');


INSERT INTO ITEMDETAILS VALUES('1', 'shippingMethod', 'pl-PL', 'shipping', '1');
INSERT INTO ITEMDETAILS VALUES('2', 'itemDescription', 'pl-PL', 'testItem2', '2');
INSERT INTO ITEMDETAILS VALUES('3', 'itemDescription', 'pl-PL', 'testItem3', '3');
INSERT INTO ITEMDETAILS VALUES('4', 'itemDescription', 'pl-PL', 'testItem4', '4');
INSERT INTO ITEMDETAILS VALUES('5', 'itemDescription', 'pl-PL', 'testItem5', '5');
INSERT INTO ITEMDETAILS VALUES('6', 'itemDescription', 'pl-PL', 'testItem6', '6');
INSERT INTO ITEMDETAILS VALUES('7', 'PL_itemDescription', 'pl-PL', 'PL_TestItem1', '7');
INSERT INTO ITEMDETAILS VALUES('8', 'ENG_itemDescription', 'en-US', 'ENG_TestItem1', '7');
INSERT INTO ITEMDETAILS VALUES('9', 'FR_itemDescription', 'fr-FR', 'FR_TestItem1', '7');


INSERT INTO ITEM_CATEGORY VALUES ('1', '1');
INSERT INTO ITEM_CATEGORY VALUES ('1', '2');
INSERT INTO ITEM_CATEGORY VALUES ('2', '2');
INSERT INTO ITEM_CATEGORY VALUES ('3', '2');
INSERT INTO ITEM_CATEGORY VALUES ('4', '3');

INSERT INTO ITEM_CATEGORY VALUES ('5', '2');
INSERT INTO ITEM_CATEGORY VALUES ('6', '2');
INSERT INTO ITEM_CATEGORY VALUES ('7', '2');

