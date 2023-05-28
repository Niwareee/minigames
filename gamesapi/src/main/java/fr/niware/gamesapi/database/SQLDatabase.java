package fr.niware.gamesapi.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.file.ServerFile;
import org.intellij.lang.annotations.Language;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLDatabase {

    private static final String URL_TEMPLATE = "jdbc:mysql://%s:%s/%s" +
                                               "?useUnicode=yes" +
                                               "&autoReconnect=true" +
                                               "&characterEncoding=UTF-8" +
                                               "&useSSL=false" +
                                               "&serverTimezone=Europe/Paris";

    private final Logger logger;
    private final HikariDataSource hikariDataSource;

    public SQLDatabase(AbstractPlugin<?, ?, ?> plugin) {
        this.logger = plugin.getSLF4JLogger();

        long start = System.currentTimeMillis();
        ServerFile credentials = plugin.getGameBase().getGameFile().getConfig();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format(SQLDatabase.URL_TEMPLATE, credentials.getHost(), credentials.getPort(), credentials.getName()));
        hikariConfig.setUsername(credentials.getUsername());
        hikariConfig.setPassword(credentials.getPassword());
        hikariConfig.setMaxLifetime(300000L);
        hikariConfig.setIdleTimeout(30000L);
        hikariConfig.setMinimumIdle(1);
        hikariConfig.setLeakDetectionThreshold(50000L);
        hikariConfig.setMaximumPoolSize(8);
        hikariConfig.setConnectionTimeout(10000L);
        hikariConfig.addDataSourceProperty("useSSL", false);
        hikariConfig.setRegisterMbeans(true);
        hikariConfig.setPoolName("onemap");

        this.hikariDataSource = new HikariDataSource(hikariConfig);
        this.logger.info("Database successfully connected in {} ms", System.currentTimeMillis() - start);
    }

    public Connection getConnection() {
        try {
            return this.hikariDataSource.getConnection();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public boolean isConnected() {
        return this.hikariDataSource != null && !this.hikariDataSource.isClosed();
    }

    public void close() {
        this.hikariDataSource.close();
        this.logger.info("Database successfully disconnected");
    }

    public void update(String query) {
        try (Connection connection = this.getConnection();
             PreparedStatement state = connection.prepareStatement(query)
        ) {
            state.executeUpdate();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public <R> R executeQuery(@Language("MySQL") String request, ThrowingFunction<ResultSet, R> resultFn) {
        try (
                Connection connection = this.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(request)
        ) {
            return resultFn.apply(resultSet);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public <R> R executeQuery(@Language("MySQL") String request, ThrowingConsumer<PreparedStatement> statementConsumer, ThrowingFunction<ResultSet, R> resultFn) {
        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(request)
        ) {
            statementConsumer.accept(preparedStatement);
            return resultFn.apply(preparedStatement.executeQuery());
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void executeQuery(@Language("MySQL") String request, ThrowingConsumer<PreparedStatement> statementConsumer) {
        try (
                Connection connection = this.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(request)
        ) {
            statementConsumer.accept(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
