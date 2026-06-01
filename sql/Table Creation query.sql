USE shorturl;
CREATE TABLE urlcodes(
    id INT AUTO_INCREMENT PRIMARY KEY,
    originalUrl VARCHAR(2048) NOT NULL,
    encodedUrl VARCHAR(50),
    visitedCount INT DEFAULT 0,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);