package com.ianlibanio.prison.database;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class PrisonDatabase implements HikariConnection {

    private final HikariDataSource hikari;

    @SneakyThrows
    public void query(String query, Consumer<? super PreparedStatement> consumer) {
        PreparedStatement preparedStatement = connection().prepareStatement(query);
        consumer.accept(preparedStatement);
    }

    @SneakyThrows
    public void result(PreparedStatement preparedStatement, Consumer<? super ResultSet> consumer) {
        ResultSet resultSet = preparedStatement.executeQuery();
        consumer.accept(resultSet);
    }

    @Override
    @SneakyThrows
    public Connection connection() {
        return hikari.getConnection();
    }
}
