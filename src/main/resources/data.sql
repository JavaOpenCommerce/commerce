INSERT INTO PRODUCER VALUES ('1', 'testProducer', 'Producer1', 'https://via.placeholder.com/200.png');
INSERT INTO PRODUCER VALUES ('2', 'testProducer', 'Producer2', 'https://via.placeholder.com/200.png');
INSERT INTO PRODUCER VALUES ('3', 'testProducer', 'Producer3', 'https://via.placeholder.com/200.png');

INSERT INTO CATEGORY VALUES ('1', 'Shipping', 'shippingMethods');
INSERT INTO CATEGORY VALUES ('2', 'Category2', 'testCategory');
INSERT INTO CATEGORY VALUES ('3', 'Category3', 'testCategory');
INSERT INTO CATEGORY VALUES ('4', 'Category4', 'testCategory');


INSERT INTO ITEM (id, description, name, stock, valuegross, vat) VALUES ('1', 'Personal Pickup', 'Personal Pickup', '1', '0.0', '0.23');

INSERT INTO ITEM VALUES ('2', 'itemDescription', 'testItem2', '3', '11.55', '0.23', '2');
INSERT INTO ITEM VALUES ('3', 'itemDescription', 'testItem3', '0', '12.33', '0.23', '3');
INSERT INTO ITEM VALUES ('4', 'itemDescription', 'testItem4', '2', '1.45', '0.23', '1');

INSERT INTO ITEM_CATEGORY VALUES ('1', '1');
INSERT INTO ITEM_CATEGORY VALUES ('2', '2');
INSERT INTO ITEM_CATEGORY VALUES ('3', '2');
INSERT INTO ITEM_CATEGORY VALUES ('4', '3');
