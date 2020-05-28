package com.freshworks.sharding;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static void executeQuery(Connection connection, String q) throws SQLException {
        System.out.println("Query ::: " + q);
        Statement statement = connection.createStatement();
        statement.executeQuery(q);
        ResultSet rs = statement.executeQuery(q);
        int colCount = rs.getMetaData().getColumnCount();
        System.out.println("## Cols Count :: " + colCount);

        while (rs.next()) {
            StringBuilder sb = new StringBuilder();
            for(int i=1; i <= colCount; i++) {
                sb.append(rs.getObject(i)+"").append(",");
            }
            System.out.println(sb.toString());
        }

    }

    public static void main(String[] args) throws SQLException {
        try (Connection connection = ShardingConfig.shardingConfig().getConnection()) {
                executeQuery(connection, "select * from contacts where org_id=100");
                executeQuery(connection, "select contacts.*, contacts_custom.* from contacts join contacts_custom on contacts.id = contacts_custom.contact_id where contacts.org_id=100");
                executeQuery(connection, "select * from contacts where org_id=101");
                executeQuery(connection, "select * from campaigns");

                //Insert Test
                Statement statement = connection.createStatement();
                statement.executeUpdate("insert into contacts(org_id, email, created_at, updated_at) values(100, 'naveen@fworks.com', now(), now())");
                executeQuery(connection,"select * from contacts where org_id=100");
                statement.executeUpdate("delete from contacts where org_id=100 and email='naveen@fworks.com'");
        }

    }

}
