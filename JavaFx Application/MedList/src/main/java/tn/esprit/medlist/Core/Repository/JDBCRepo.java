package tn.esprit.medlist.Core.Repository;


import tn.esprit.medlist.Core.Infrastructure.DataBaseConnection;

import java.util.List;

public class JDBCRepo<T> implements Repo<T> {
        private DataBaseConnection connection;
        private String tableName;

        public JDBCRepo(DataBaseConnection connection, String tableName) {
            this.connection = connection;
            this.tableName = tableName;
        }

        @Override
        public void save(T entity) {
            // Implement the logic to save entity to the database
            // Use JDBC to execute SQL statements
        }

        @Override
        public List<T> findAll() {
            // Implement the logic to retrieve all entities from the database
            // Use JDBC to execute SQL statements
            return null;
        }
    }

