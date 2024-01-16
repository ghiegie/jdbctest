package org.jdbcconnectiontest

import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource
import com.microsoft.sqlserver.jdbc.SQLServerDataSource
import java.sql.DriverManager
import java.sql.SQLException



fun main() {
    val conStr = "jdbc:sqlserver://DESKTOP-DCDEB6P\\TESTSQLSERVER;database=Customers;user=sa;password=12345;encrypt=false"

    // try catch using driver manager; read an entry in db
    try {
        val connection = DriverManager.getConnection(conStr)
        val sampleStatement = connection.createStatement() // this is just an interface
        val sampleExecutedStatement  = sampleStatement.executeQuery("select * from CustTbl") // this is the query
        while (sampleExecutedStatement.next()) {
            println(sampleExecutedStatement.getString("CustomerID"))
        }
        sampleExecutedStatement.close()
        sampleStatement.close()
        connection.close()
    } catch (e: SQLException) {
        e.printStackTrace()
    }

    // try catch with using driver manager, using "use" function; read data
    try {
        DriverManager.getConnection(conStr).use {
            conn -> conn.createStatement().use {
                statement -> statement.executeQuery("select * from CustTbl").use{
                    exe -> while(exe.next()) {
                        println(exe.getString("CellNo"))
                    }
                }
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }

    // config for datasource
    val testDataSource = SQLServerDataSource()
    testDataSource.serverName = "DESKTOP-DCDEB6P\\TESTSQLSERVER"
    testDataSource.databaseName = "Customers"
    testDataSource.description = "Test Connection using datasource"
    testDataSource.encrypt = "false"
    testDataSource.trustServerCertificate = false
    testDataSource.getConnection("sa", "12345")

    try {

        val dataPool = SQLServerConnectionPoolDataSource()
        dataPool.serverName = "DESKTOP-DCDEB6P\\TESTSQLSERVER"
        dataPool.databaseName = "Customers"
        dataPool.description = "Test Connection using datasource"
        dataPool.encrypt = "false"
        dataPool.trustServerCertificate = false
        dataPool.getConnection("sa", "12345")

        dataPool.getConnection("sa", "12345").use {
            conn -> conn.createStatement().use {
                statement -> statement.executeQuery("select * from CustTbl").use {
                    exe -> while(exe.next()) {
                        println("hi")
                        println(exe.getString("CellNo"))
                    }
                }
            }
        }

    } catch (e: SQLException) {

    }

    try {
        val dataPool = SQLServerConnectionPoolDataSource()
        dataPool.serverName = "DESKTOP-DCDEB6P\\TESTSQLSERVER"
        dataPool.databaseName = "Customers"
        dataPool.description = "Test Connection using datasource"
        dataPool.encrypt = "false"
        dataPool.trustServerCertificate = false

        dataPool.getConnection("sa", "12345").use {
            it.createStatement().use {
                it.executeUpdate("insert into CustTbl(CustomerID, Name, Address, DeliveryAddress, TIN, Email, TelNo, CellNo, ContactPerson, ContactPersonEmail, ContactPersonTelNo, ContactPersonCellNo) values('agagag', 'agagag', 'agagag', 'agagag', 'agagag', 'agagag', 'agagag', 'agagag', 'agagag', 'agagag', 'agagag', 'agagag')")
            }
        }
    } catch (e: SQLException) {
        e.printStackTrace()
    }
}