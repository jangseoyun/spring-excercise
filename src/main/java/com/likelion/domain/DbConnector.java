package com.likelion.domain;

import java.sql.Connection;
import java.sql.SQLException;

public interface DbConnector {
    Connection dbConnection() throws SQLException;
}
