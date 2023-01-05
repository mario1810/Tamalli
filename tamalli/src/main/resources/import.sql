INSERT INTO `tamalli_ecommerce`.`customers` (`customer_id`, `address`, `email`, `first_name`, `last_name`, `password`, `phone_number`) VALUES ('1', 'Chimalhuacan, Estado de México', 'mario@gmail.com', 'Mario', 'Alvarez', '123', '5512234556');
INSERT INTO `tamalli_ecommerce`.`customers` (`customer_id`, `address`, `email`, `first_name`, `last_name`, `password`, `phone_number`) VALUES ('2', 'Villa, La paz', 'juan@gmail.com', 'Juan', 'Cota', '234', '5512234577');
INSERT INTO `tamalli_ecommerce`.`customers` (`customer_id`, `address`, `email`, `first_name`, `last_name`, `password`, `phone_number`) VALUES ('3', 'Flores, Guadalajara', 'ramiro@gmail.com', 'Ramiro', 'Nuñes', '456', '5512234588');


INSERT INTO `tamalli_ecommerce`.`products` (`product_id`, `price`, `product_name`, `product_type`) VALUES ('1', '12.5', 'Tamal verde', 'Food');
INSERT INTO `tamalli_ecommerce`.`products` (`product_id`, `price`, `product_name`, `product_type`) VALUES ('2', '13.0', 'Tamal rajas', 'Food');
INSERT INTO `tamalli_ecommerce`.`products` (`product_id`, `price`, `product_name`, `product_type`) VALUES ('3', '17.25', 'Tamal al pastor', 'Food');
INSERT INTO `tamalli_ecommerce`.`products` (`product_id`, `price`, `product_name`, `product_type`) VALUES ('4', '10.0', 'Café', 'Drink');
INSERT INTO `tamalli_ecommerce`.`products` (`product_id`, `price`, `product_name`, `product_type`) VALUES ('5', '12.0', 'Atole Champurrado', 'Drink');
INSERT INTO `tamalli_ecommerce`.`products` (`product_id`, `price`, `product_name`, `product_type`) VALUES ('6', '14.0', 'Atole galleta', 'Drink');

INSERT INTO `tamalli_ecommerce`.`drinks` (`capacity_liters`, `product_id`) VALUES ('0.300', '4');
INSERT INTO `tamalli_ecommerce`.`drinks` (`capacity_liters`, `product_id`) VALUES ('0.300', '5');
INSERT INTO `tamalli_ecommerce`.`drinks` (`capacity_liters`, `product_id`) VALUES ('0.300', '6');

INSERT INTO `tamalli_ecommerce`.`tamales` (`weight_kilogram`, `product_id`) VALUES ('0.250', '1');
INSERT INTO `tamalli_ecommerce`.`tamales` (`weight_kilogram`, `product_id`) VALUES ('0.250', '2');
INSERT INTO `tamalli_ecommerce`.`tamales` (`weight_kilogram`, `product_id`) VALUES ('0.250', '3');

INSERT INTO `tamalli_ecommerce`.`orders` (`order_id`, `paid`, `purchase_date`, `total_cost`, `customer_id`) VALUES ('1', b'1', '2023-01-01 13:14:21', '22.50', '1');
INSERT INTO `tamalli_ecommerce`.`orders` (`order_id`, `paid`, `purchase_date`, `total_cost`, `customer_id`) VALUES ('2', b'1', '2023-01-03 08:30:45', '50.00', '1');
INSERT INTO `tamalli_ecommerce`.`orders` (`order_id`, `paid`, `customer_id`) VALUES ('3', b'0', '1');
INSERT INTO `tamalli_ecommerce`.`orders` (`order_id`, `paid`, `customer_id`) VALUES ('4', b'0', '2');
INSERT INTO `tamalli_ecommerce`.`orders` (`order_id`, `paid`, `customer_id`) VALUES ('5', b'0', '3');

INSERT INTO `tamalli_ecommerce`.`order_details` (`detail_order_id`, `product_line`, `product_ordered`, `product_price_ordered`, `quantity_ordered`, `order_id`, `product_id`) VALUES ('1', 'Food', 'Tamal verde', '12.50', '1', '1', '1');
INSERT INTO `tamalli_ecommerce`.`order_details` (`detail_order_id`, `product_line`, `product_ordered`, `product_price_ordered`, `quantity_ordered`, `order_id`, `product_id`) VALUES ('2', 'Drink', 'Café', '10.00', '1', '1', '4');
INSERT INTO `tamalli_ecommerce`.`order_details` (`detail_order_id`, `product_line`, `product_ordered`, `product_price_ordered`, `quantity_ordered`, `order_id`, `product_id`) VALUES ('3', 'Food', 'Tamal rajas', '13.00', '2', '2', '2');
INSERT INTO `tamalli_ecommerce`.`order_details` (`detail_order_id`, `product_line`, `product_ordered`, `product_price_ordered`, `quantity_ordered`, `order_id`, `product_id`) VALUES ('4', 'Drink', 'Atole galleta', '14.00', '1', '2', '6');
INSERT INTO `tamalli_ecommerce`.`order_details` (`detail_order_id`, `product_line`, `product_ordered`, `product_price_ordered`, `quantity_ordered`, `order_id`, `product_id`) VALUES ('5', 'Drink', 'Café', '10.00', '1', '2', '4');
INSERT INTO `tamalli_ecommerce`.`order_details` (`detail_order_id`, `product_line`, `product_ordered`, `product_price_ordered`, `quantity_ordered`, `order_id`, `product_id`) VALUES ('6', 'Food', 'Tamal verde', '12.50', '2', '3', '1');
INSERT INTO `tamalli_ecommerce`.`order_details` (`detail_order_id`, `product_line`, `product_ordered`, `product_price_ordered`, `quantity_ordered`, `order_id`, `product_id`) VALUES ('7', 'Food', 'Tamal rajas', '13.00', '2', '3', '2');
INSERT INTO `tamalli_ecommerce`.`order_details` (`detail_order_id`, `product_line`, `product_ordered`, `product_price_ordered`, `quantity_ordered`, `order_id`, `product_id`) VALUES ('8', 'Drink', 'Café', '10.00', '1', '4', '4');