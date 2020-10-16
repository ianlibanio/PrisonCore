package com.ianlibanio.prison.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface HikariConnection {

    Connection connection() throws SQLException;
}
