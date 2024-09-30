package com.org.ultrainstinct.dao;

import com.org.ultrainstinct.config.SqlConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <p>
 * AbstractCrudDao implements CRUDDao interface.
 * </p>
 *
 * @author MinhNgoc
 */
public abstract class AbstractCrudDao<T, ID> implements CRUDDao<T, ID> {

    /**
     * LOGGER
     */
    public static final Logger LOGGER = Logger.getLogger(AbstractCrudDao.class.getName());

    /**
     * connection
     */
    public static Connection connection = null;

    static {
        try {
            connection = SqlConfig.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to connect to the database.", e);
        }
    }

    /**
     * <p>
     * Method mapRow
     * </p>
     *
     * @param rs ResultSet
     * @return T
     * @throws SQLException
     * @author MinhNgoc
     */
    protected abstract T mapRow(ResultSet rs) throws SQLException;

    /**
     * <p>
     * Method getTableName
     * </p>
     *
     * @return String
     * @author MinhNgoc
     */
    protected abstract String getTableName();

    /**
     * <p>
     * Method getPrimaryKeyColumnName
     * </p>
     *
     * @return String
     * @author MinhNgoc
     */
    protected abstract String getPrimaryKeyColumnName();

    /**
     * <p>
     * Method getEntityValues get values of entity
     * </p>
     *
     * @param entity T
     * @return Object
     * @author MinhNgoc
     */
    protected abstract Object[] getEntityValues(T entity);

    /**
     * <p>
     * Method getInsertQuery get insert query
     * </p>
     *
     * @return String
     * @author MinhNgoc
     */
    protected abstract String getInsertQuery();

    /**
     * <p>
     * Method getUpdateQuery get update query
     * </p>
     *
     * @return String
     * @author MinhNgoc
     */
    protected abstract String getUpdateQuery();

    @Override
    public void save(T entity) {
        Object[] values = getEntityValues(entity);
        try (PreparedStatement stmt = connection.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }
             stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to save entity.", e);
        }
    }


    @Override
    public void update(T entity, ID id) {
        Object[] values = getEntityValues(entity);
        try (PreparedStatement stmt = connection.prepareStatement(getUpdateQuery(), Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }
            // update by id
            stmt.setObject(values.length + 1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to save entity.", e);
        }
    }


    @Override
    public T findById(ID id) {
        String sql =
            " SELECT * FROM " + getTableName() + " WHERE " + getPrimaryKeyColumnName() + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to find entity by id.", e);
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                entities.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to find all entities.", e);
        }
        return entities;
    }

    @Override
    public void deleteById(ID id) {
        String sql =
            "DELETE FROM " + getTableName() + " WHERE " + getPrimaryKeyColumnName() + " = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to delete entity by id.", e);
        }
    }
    
}
