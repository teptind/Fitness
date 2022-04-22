package com.teptind.visit;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.teptind.visit.commands.CommandDaoImpl;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CommandDaoImplTest {
    @Test
    public void testEnter_success() throws Exception {
        testEnter("2020-10-01", "exit", "Successful enter");
    }

    @Test
    public void testEnter_expired() throws Exception {
        testEnter("2020-07-01", "exit", "Membership 9 has expired");
    }

    @Test
    public void testEnter_alreadyEntered() throws Exception {
        testEnter("2020-10-01", "enter", "The user 9 has already entered");
    }

    void testEnter(String expiryDate, String lastType, String verdict) throws Exception {
        Connection connection = mock(Connection.class);

        PreparedStatement stExpiryDate = mock(PreparedStatement.class);
        ResultSet rsExpiryDate = mock(ResultSet.class);

        // Expiry Date
        when(connection.prepareStatement(Mockito.startsWith("SELECT max(expiry_date)"))).thenReturn(stExpiryDate);
        when(stExpiryDate.executeQuery()).thenReturn(rsExpiryDate);
        when(rsExpiryDate.next()).thenReturn(true);
        when(rsExpiryDate.getDate(1)).thenReturn(Date.valueOf(expiryDate));

        PreparedStatement stHasEntered = mock(PreparedStatement.class);
        ResultSet rsHasEntered = mock(ResultSet.class);

        // Has Entered
        when(connection.prepareStatement(Mockito.startsWith("SELECT type"))).thenReturn(stHasEntered);
        when(stHasEntered.executeQuery()).thenReturn(rsHasEntered);
        when(rsHasEntered.next()).thenReturn(true);
        when(rsHasEntered.getString(1)).thenReturn(lastType);

        PreparedStatement st = mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.startsWith("INSERT"), Mockito.anyInt())).thenReturn(st);
        when(st.executeUpdate()).thenReturn(1);

        CommandDaoImpl commandDao = new CommandDaoImpl(connection);
        String result = commandDao.enter(9L, Timestamp.valueOf("2020-09-23 10:10:10.0").getTime());

        assertEquals(result, verdict);
    }

    @Test
    public void testExit_success() throws Exception {
        testExit("enter", "Successful exit");
    }

    @Test
    public void testExit_NotEntered() throws Exception {
        testExit("exit", "The user 9 has not entered");
    }

    void testExit(String lastType, String verdict) throws Exception {
        Connection connection = mock(Connection.class);

        PreparedStatement stHasEntered = mock(PreparedStatement.class);
        ResultSet rsHasEntered = mock(ResultSet.class);

        // Has Entered
        when(connection.prepareStatement(Mockito.startsWith("SELECT type"))).thenReturn(stHasEntered);
        when(stHasEntered.executeQuery()).thenReturn(rsHasEntered);
        when(rsHasEntered.next()).thenReturn(true);
        when(rsHasEntered.getString(1)).thenReturn(lastType);

        PreparedStatement st = mock(PreparedStatement.class);

        when(connection.prepareStatement(Mockito.startsWith("INSERT"), Mockito.anyInt())).thenReturn(st);
        when(st.executeUpdate()).thenReturn(1);

        CommandDaoImpl commandDao = new CommandDaoImpl(connection);
        String result = commandDao.exit(9L, Timestamp.valueOf("2020-09-23 10:10:10.0").getTime());

        assertEquals(result, verdict);
    }
}
