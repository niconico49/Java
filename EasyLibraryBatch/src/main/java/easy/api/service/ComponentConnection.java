package easy.api.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;

import java.util.Calendar;

/**
 *
 * @author development
 */
public class ComponentConnection {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ComponentConnection getInstance() {
        return new ComponentConnection();
    }

    public void open(String connectionString) {
        try {
			Class.forName("com.filemaker.jdbc.Driver").newInstance();
            this.connection = DriverManager.getConnection(connectionString);
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
        }
    }

    public void bindParameter(int i, Object object)
		throws SQLException {
		this.preparedStatement.setObject(i + 1, object);
    }

    public void prepareStatement(String sql) {
        try {
            this.preparedStatement = this.connection.prepareStatement(sql);
        }
        catch (SQLException ex) {
        }
    }

    public void executeQuery() {
        try {
            this.resultSet = this.preparedStatement.executeQuery();
        }
        catch (SQLException ex) {
        }
    }

    public int executeNonQuery() {
		int rowCount = -1;
        try {
            rowCount = this.preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
        }
        return rowCount;
    }

    public void close() {
        try {
            if (this.resultSet != null) {
                this.resultSet.close();
            }
            if (this.preparedStatement != null) {
                this.preparedStatement.close();
            }
            this.connection.close();
        }
        catch (SQLException ex) {
        }
    }

    public boolean isBof() {
        boolean value = true;
        try {
            value = !this.resultSet.isLast()
                &&
                (
                this.resultSet.getRow() != 0
                ||
                this.resultSet.isBeforeFirst()
                );
        }
        catch (SQLException ex) {
        }
        return value;
    }

    public boolean isEof() {
        boolean value = true;
        try {
            value = !this.resultSet.isLast()
                &&
                (
                this.resultSet.getRow() != 0
                ||
                this.resultSet.isBeforeFirst()
                );
        }
        catch (SQLException ex) {
        }
        return value;
    }

    public boolean moveNext() {
        boolean result = false;
        try {
            result = this.resultSet.next();
        }
        catch (SQLException ex) {
        }
        return result;
    }

    public int columnCount() {
        int columnCount = -1;
        try {
            ResultSetMetaData resultSetMetaData = this.resultSet.getMetaData();
            columnCount = resultSetMetaData.getColumnCount();
        }
        catch (SQLException ex) {
        }
        return columnCount;
    }

    public String columnName(int i) {
        String value = "";
        try {
            ResultSetMetaData resultSetMetaData = this.resultSet.getMetaData();
            value = resultSetMetaData.getColumnName(i + 1);
        }
        catch (SQLException ex) {
        }
        return value;
    }

    public String columnType(int i) {
        String value = "";
        try {
            ResultSetMetaData resultSetMetaData = this.resultSet.getMetaData();
            value = resultSetMetaData.getColumnClassName(i + 1);
        }
        catch (SQLException ex) {
        }
        return value;
    }
    
    public String columnValue(int i) {
        Object value = null;
        try {
            value = this.resultSet.getObject(i + 1);
        }
        catch (SQLException ex) {
        }

        if (value != null) {
            String columnType = this.columnType(i);
            switch(columnType) {
                case "java.sql.Timestamp" :
                    value = ComponentConnection.fromTimeStamp2Iso8601(value.toString());
                    break;
                case "java.sql.Date" :
                    value = ComponentConnection.fromDate2Iso8601(value.toString());
                    break;
                case "java.sql.Time" :
                    value = ComponentConnection.fromTime2Iso8601(value.toString());
                    break;
            }
        }

        return value == null ? "" : value.toString();
    }

    private static Calendar getCalendar(String value) {
        Date date = Date.valueOf(value);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        return calendar;
    }

    private static String calendarDate(Calendar calendar) {
        String result = String.format(
            "%1$d-%2$02d-%3$02d",
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        );
        return result;
    }
    
    private static String calendarTime(Calendar calendar) {
        String result = String.format(
            "%4$02d:%5$02d:%6$02d",
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            calendar.get(Calendar.SECOND)
        );

        int milliSecond = calendar.get(Calendar.MILLISECOND);
        if (milliSecond > 0) {
            result += "." + String.format("%1$03d", milliSecond);
        }
        return result;
        
    }
    
    private static String fromTimeStamp2Iso8601(String value) {
        Calendar calendar = ComponentConnection.getCalendar(value);
        
        String result = MessageFormat.format(
            "{0} {1}",
            ComponentConnection.calendarDate(calendar),
            ComponentConnection.calendarTime(calendar)
        );
        
        return result;
    }

    private static String fromDate2Iso8601(String value) {
        Calendar calendar = ComponentConnection.getCalendar(value);

        return ComponentConnection.calendarDate(calendar);
    }

    private static String fromTime2Iso8601(String value) {
        Calendar calendar = ComponentConnection.getCalendar(value);

        return ComponentConnection.calendarTime(calendar);
    }
/*
    private static ValueType sqlTypeToValueType(int sqlType) {
        ValueType valueType;
        switch (sqlType) {
            case Types.BOOLEAN:
            case Types.BIT: 
                valueType =  ValueType.BOOLEAN;
                break;
            case Types.CHAR:
            case Types.VARCHAR:
                valueType =  ValueType.TEXT;
                break;
            case Types.INTEGER:
            case Types.SMALLINT:
            case Types.BIGINT:
            case Types.TINYINT:
            case Types.REAL:
            case Types.NUMERIC:
            case Types.DOUBLE:
            case Types.FLOAT:
            case Types.DECIMAL:
                valueType = ValueType.NUMBER;
                break;
            case Types.DATE:
                valueType = ValueType.DATE;
                break;
            case Types.TIME:
                valueType = ValueType.TIMEOFDAY;
                break;
            case Types.TIMESTAMP:
                valueType = ValueType.DATETIME;
                break;
            default:
                valueType = ValueType.TEXT;
                break;
        }
        return valueType;
    }
*/    
}
