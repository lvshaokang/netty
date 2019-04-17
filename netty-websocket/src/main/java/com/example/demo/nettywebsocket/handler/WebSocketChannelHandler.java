package com.example.demo.nettywebsocket.handler;

import com.example.demo.nettywebsocket.util.ChannelUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author lsk
 * @class_name WebSocketChannelHandler
 * @date 2019-04-17
 */
@Slf4j
public class WebSocketChannelHandler extends SimpleChannelInboundHandler<Object> {

    private static final String URI = "websocket";

    private WebSocketServerHandshaker handshaker ;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof HttpRequest){
            doHandlerHttpRequest(ctx,(HttpRequest) obj);
        } else if (obj instanceof WebSocketFrame){
            doHandlerWebSocketFrame(ctx,(WebSocketFrame) obj);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerAdded ->  {}",ctx.channel().id());

        ChannelUtil.channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved ->  {}",ctx.channel().id());

        ChannelUtil.channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive ->  {}",ctx.channel().id());

        log.info("用户 " + ctx.channel().id() + " -> 在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive ->  {}",ctx.channel().id());

        log.info("用户 " + ctx.channel().id() + " -> 离线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught ->  {}",cause.toString());

        ctx.close();
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            PingWebSocketFrame ping = new PingWebSocketFrame();
            switch (stateEvent.state()){
                case READER_IDLE:
                    log.info(ctx.channel().remoteAddress()+" -> 读空闲(服务器端)");
                    ctx.writeAndFlush(ping);
                    break;
                case WRITER_IDLE:
                    log.info(ctx.channel().remoteAddress()+" -> 写空闲(客户端)");
                    ctx.writeAndFlush(ping);
                    break;
                case ALL_IDLE:
                    log.info(ctx.channel().remoteAddress()+" -> 读写空闲");
                    ctx.writeAndFlush(ping);
                    break;
                default:
            }
        }
    }

    private void doHandlerHttpRequest(ChannelHandlerContext ctx,HttpRequest req){
        // http 解码失败
        if(!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))){
            sendHttpResponse(ctx, (FullHttpRequest) req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }

        // 可以通过msg的uri判断
/*        String uri = req.uri();
        if(!uri.substring(1).equals(URI)){
            ctx.close();
        }*/

//        ctx.channel().attr(AttributeKey.valueOf("type")).set(uri);

        // 可以通过url获取其他参数
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(
                "ws://" + req.headers().get("Host") + "/" + req.uri() + "",null,false
        );
        handshaker = factory.newHandshaker(req);
        if(handshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), (FullHttpRequest) req);
        }

    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void doHandlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        //判断msg 是哪一种类型  分别做出不同的反应
        if(msg instanceof CloseWebSocketFrame){
            log.info("Closed...");

            handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg);
            return ;
        }
        if(msg instanceof PingWebSocketFrame){
            log.info("ping...");

            PongWebSocketFrame pong = new PongWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(pong);
            return ;
        }
        if(msg instanceof PongWebSocketFrame){
            log.info("pong...");

            PingWebSocketFrame ping = new PingWebSocketFrame(msg.content().retain());
            ctx.channel().writeAndFlush(ping);
            return ;
        }
        if(!(msg instanceof TextWebSocketFrame)){
            log.error("Unsupported message format");

            throw new UnsupportedOperationException("Unsupported message format");
        }

        // 广播|群发
        for (Channel channel : ChannelUtil.channels) {
            channel.writeAndFlush(new TextWebSocketFrame(((TextWebSocketFrame) msg).text()));
        }

    }
}
