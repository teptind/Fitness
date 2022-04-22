package com.teptind.visit.web;

import java.nio.charset.StandardCharsets;

import com.teptind.common.db.ConnectionProvider;
import com.teptind.visit.commands.CommandDaoImpl;
import com.teptind.common.command.CommandProcessor;
import com.teptind.visit.commands.EnterCommand;
import com.teptind.visit.commands.ExitCommand;
import com.github.vanbv.num.AbstractHttpMappingHandler;
import com.github.vanbv.num.annotation.PathParam;
import com.github.vanbv.num.annotation.Post;
import com.github.vanbv.num.json.JsonParser;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpVersion;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

@ChannelHandler.Sharable
public class HttpMappingHandler extends AbstractHttpMappingHandler {
    private CommandProcessor commandProcessor;

    public HttpMappingHandler(JsonParser parser) throws Exception {
        super(parser);
        this.commandProcessor = new CommandProcessor(new CommandDaoImpl(ConnectionProvider.connect()));
    }

    DefaultFullHttpResponse wrapResult(String result) {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, OK,
                Unpooled.copiedBuffer(result, StandardCharsets.UTF_8));
    }

    @Post("/enter/{id}")
    public DefaultFullHttpResponse enter(@PathParam(value = "id") Long userId) {
        return wrapResult(commandProcessor.process(new EnterCommand(userId, System.currentTimeMillis())));
    }

    @Post("/exit/{id}")
    public DefaultFullHttpResponse exit(@PathParam(value = "id") Long userId) {
        return wrapResult(commandProcessor.process(new ExitCommand(userId, System.currentTimeMillis())));
    }
}
