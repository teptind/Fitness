package com.teptind.manager.web;

import java.nio.charset.StandardCharsets;
import java.sql.Date;

import com.teptind.common.db.ConnectionProvider;
import com.teptind.manager.commands.AddNewUserCommand;
import com.teptind.common.command.CommandProcessor;
import com.teptind.manager.commands.ExtendMembershipCommand;
import com.teptind.manager.commands.CommandDaoImpl;
import com.teptind.manager.queries.GetMembershipInfoQuery;
import com.teptind.manager.queries.QueryDaoImpl;
import com.teptind.common.query.QueryProcessor;
import com.github.vanbv.num.AbstractHttpMappingHandler;
import com.github.vanbv.num.annotation.Get;
import com.github.vanbv.num.annotation.PathParam;
import com.github.vanbv.num.annotation.Post;
import com.github.vanbv.num.annotation.QueryParam;
import com.github.vanbv.num.json.JsonParser;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpVersion;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

@ChannelHandler.Sharable
public class HttpMappingHandler extends AbstractHttpMappingHandler {
    private final CommandProcessor commandProcessor;
    private final QueryProcessor queryProcessor;

    public HttpMappingHandler(JsonParser parser) throws Exception {
        super(parser);
        this.commandProcessor = new CommandProcessor(new CommandDaoImpl(ConnectionProvider.connect()));
        this.queryProcessor = new QueryProcessor(new QueryDaoImpl(ConnectionProvider.connect()));
    }

    DefaultFullHttpResponse wrapResult(String result) {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK,
                Unpooled.copiedBuffer(result, StandardCharsets.UTF_8));
    }

    @Post("/user/new")
    public DefaultFullHttpResponse userNew() {
        return wrapResult(commandProcessor.process(new AddNewUserCommand()));
    }

    @Post("/membership/extend")
    public DefaultFullHttpResponse membershipExtend(@QueryParam(value = "user_id") Long userId,
                                        @QueryParam(value = "expiry_date_ts") String expiryDate) {
        return wrapResult(commandProcessor.process(new ExtendMembershipCommand(userId, Date.valueOf(expiryDate))));
    }

    @Get("/user/{id}")
    public DefaultFullHttpResponse userGet(@PathParam(value = "id") Long userId) {
        return wrapResult(queryProcessor.process(new GetMembershipInfoQuery(userId)));
    }
}
