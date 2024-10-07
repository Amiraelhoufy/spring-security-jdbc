-- Create the users table
CREATE TABLE IF NOT EXISTS users (
  username VARCHAR(50) NOT NULL PRIMARY KEY,
	password VARCHAR(100) NOT NULL,  -- Adjusted to store longer bcrypt passwords
	enabled boolean not null
);

-- Create the authorities table
CREATE TABLE IF NOT EXISTS authorities (
	username varchar_ignorecase(50) not null,
	authority varchar_ignorecase(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);
