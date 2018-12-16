import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatClientHandler extends ChannelInboundHandlerAdapter {
	
	 
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("채팅방에  접속했습니다.");
	}
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception{
		System.out.println("채팅방에서 나갔습니다.");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {		
		System.out.println((String)msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}


}
