CREATE TABLE IF NOT EXISTS DlqKafkaRetry (
    id INT AUTO_INCREMENT PRIMARY KEY,
    topicName VARCHAR(255),
    data TEXT,
    timeStamp TIMESTAMP,
    lastAttemptedOn TIMESTAMP,
    totalAttempts INT,
    clazzName VARCHAR(255),
    headers TEXT
    );