-- Create Source table
CREATE TABLE Source (
    id INT AUTO_INCREMENT PRIMARY KEY,
    source VARCHAR(100) NOT NULL,
    departure_time TIMESTAMP NOT NULL
);

-- Create Destination table
CREATE TABLE Destination (
    id INT AUTO_INCREMENT PRIMARY KEY,
    destination VARCHAR(100) NOT NULL,
    arrival_time TIMESTAMP NOT NULL
);

-- Create Flight table
CREATE TABLE Flight (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

-- Create Segment table
CREATE TABLE Segment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    flight_id INT NOT NULL,
    source_id INT NOT NULL,
    destination_id INT NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES Flight(id),
    FOREIGN KEY (source_id) REFERENCES Source(id),
    FOREIGN KEY (destination_id) REFERENCES Destination(id)
);

-- Create Seat table
CREATE TABLE Seat (
    id INT AUTO_INCREMENT PRIMARY KEY,
    seat_number INT NOT NULL,
    is_booked BOOLEAN DEFAULT FALSE,
    UNIQUE (seat_number)
);

-- Create User table
CREATE TABLE User (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create Ticket table
CREATE TABLE Ticket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    flight_id INT NOT NULL,
    segment_id INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (flight_id) REFERENCES Flight(id),
    FOREIGN KEY (segment_id) REFERENCES Segment(id)
);

-- Create join table Ticket_Seat
CREATE TABLE Ticket_Seat (
    ticket_id INT NOT NULL,
    seat_id INT NOT NULL,
    PRIMARY KEY (ticket_id, seat_id),
    FOREIGN KEY (ticket_id) REFERENCES Ticket(id),
    FOREIGN KEY (seat_id) REFERENCES Seat(id)
);
