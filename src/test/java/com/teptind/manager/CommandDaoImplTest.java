package com.teptind.manager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.teptind.manager.commands.CommandDaoImpl;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

public class CommandDaoImplTest {
    @Test
    public void testAddNewUser() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement st = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(connection.prepareStatement(Mockito.startsWith("INSERT"), Mockito.anyInt())).thenReturn(st);

        when(st.executeUpdate()).thenReturn(1);
        when(st.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getLong(1)).thenReturn(5L);

        CommandDaoImpl commandDao = new CommandDaoImpl(connection);
        String result = commandDao.addNewUser();

        assertEquals(result, "New uid is 5");
    }

    @Test
    public void testExtendSubscription() throws Exception {
        Connection connection = mock(Connection.class);
        PreparedStatement st = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(connection.prepareStatement(Mockito.startsWith("INSERT"))).thenReturn(st);
        when(st.executeUpdate()).thenReturn(1);

        CommandDaoImpl commandDao = new CommandDaoImpl(connection);
        Date expiryDate = Date.valueOf("2020-02-02");

        String result = commandDao.extendSubscription(2L, expiryDate);

        assertEquals(result, "Subscription was successfully extended.");

        verify(st, times(1)).setLong(1, 2L);
        verify(st, times(1)).setDate(2, expiryDate);
    }
}
