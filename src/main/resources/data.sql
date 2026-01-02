INSERT INTO users (id, name, email, password, role, active) VALUES
                                                                (1, 'Ashutosh Rana', 'ashu@foodtruck.com', 'password123', 'ADMIN', true),
                                                                (2, 'Rohit Sharma', 'rohit@foodtruck.com', 'password123', 'STAFF', true),
                                                                (3, 'Anjali Verma', 'anjali@foodtruck.com', 'password123', 'STAFF', true);
INSERT INTO menu_items (id, name, description, price, available) VALUES
                                                                     (1, 'Veg Burger', 'Classic veg patty burger', 80.00, true),
                                                                     (2, 'Chicken Burger', 'Grilled chicken burger', 120.00, true),
                                                                     (3, 'Paneer Wrap', 'Spicy paneer wrap', 100.00, true),
                                                                     (4, 'French Fries', 'Crispy fries', 60.00, true),
                                                                     (5, 'Cold Coffee', 'Chilled coffee', 70.00, true);
INSERT INTO inventory (id, item_name, quantity, unit) VALUES
                                                          (1, 'Burger Buns', 100, 'PCS'),
                                                          (2, 'Paneer', 10, 'KG'),
                                                          (3, 'Chicken', 15, 'KG'),
                                                          (4, 'Potatoes', 25, 'KG'),
                                                          (5, 'Coffee Powder', 5, 'KG');
INSERT INTO orders (id, customer_name, total_amount, status, order_time) VALUES
                                                                             (1, 'Rahul Kumar', 260.00, 'PAID', now() - interval '1 day'),
                                                                             (2, 'Neha Singh', 180.00, 'CREATED', now()),
                                                                             (3, 'Amit Patel', 120.00, 'CANCELLED', now() - interval '2 hours');
INSERT INTO order_items (id, order_id, menu_item_id, quantity, price) VALUES
                                                                          (1, 1, 1, 2, 80.00),
                                                                          (2, 1, 4, 1, 60.00),
                                                                          (3, 2, 3, 1, 100.00),
                                                                          (4, 2, 5, 1, 70.00),
                                                                          (5, 3, 2, 1, 120.00);
INSERT INTO sales_expense (id, type, amount, description, date) VALUES
                                                                    (1, 'SALE', 260.00, 'Order payment - Rahul Kumar', current_date),
                                                                    (2, 'SALE', 180.00, 'Order payment - Neha Singh', current_date),
                                                                    (3, 'EXPENSE', 500.00, 'Raw material purchase', current_date - 1),
                                                                    (4, 'EXPENSE', 300.00, 'Gas cylinder refill', current_date - 2),
                                                                    (5, 'EXPENSE', 200.00, 'Electricity bill', current_date - 3);
