package ticketsBooking.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class SchemaInitializer {
    public static void initializeSchema(Connection connection){
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        SchemaInitializer.class.getResourceAsStream("/schema.sql"), StandardCharsets.UTF_8));
             Statement statement = connection.createStatement()) {

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("--")) {
                    continue;
                }
                sql.append(line).append("\n");
            }

            // Execute the SQL script
            String[] sqlStatements = sql.toString().split(";");
            for (String sqlStatement : sqlStatements) {
                if (!sqlStatement.trim().isEmpty()) {
                    statement.execute(sqlStatement);
                }
            }
            System.out.println("Schema initialized successfully.");

        }
        catch (SQLException e){
            System.out.println("Either the tables are already created or unable to access database. "+e);
        }
        catch (IOException e) {
            System.out.println("Unable to read the file. "+e);
        }

    }
}
