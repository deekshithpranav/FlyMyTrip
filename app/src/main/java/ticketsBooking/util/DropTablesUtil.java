package ticketsBooking.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DropTablesUtil {
    public static void dropAllTables(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            Statement statement = connection.createStatement();

            // Step 1: Collect foreign keys and tables
            List<String> tables = new ArrayList<>();
            List<String[]> foreignKeys = new ArrayList<>();

            ResultSet tablesResultSet = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tablesResultSet.next()) {
                String tableName = tablesResultSet.getString("TABLE_NAME");
                tables.add(tableName);

                ResultSet foreignKeysResultSet = metaData.getImportedKeys(null, null, tableName);
                while (foreignKeysResultSet.next()) {
                    String fkTable = foreignKeysResultSet.getString("FKTABLE_NAME");
                    String fkName = foreignKeysResultSet.getString("FK_NAME");
                    foreignKeys.add(new String[]{fkTable, fkName});
                }
            }

            // Step 2: Drop all foreign keys
            for (String[] fk : foreignKeys) {
                String fkTable = fk[0];
                String fkName = fk[1];
                String dropFKSQL = "ALTER TABLE " + fkTable + " DROP FOREIGN KEY " + fkName;
                statement.executeUpdate(dropFKSQL);
                System.out.println("Dropped foreign key " + fkName + " on table " + fkTable);
            }

            // Step 3: Drop all tables in reverse order to handle dependencies
            for (int i = tables.size() - 1; i >= 0; i--) {
                String tableName = tables.get(i);
                String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;
                statement.executeUpdate(dropTableSQL);
                System.out.println("Dropped table: " + tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while dropping tables", e);
        }
    }
}
