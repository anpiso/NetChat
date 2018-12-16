import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends ChannelInboundHandlerAdapter {

	static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		System.out.println(ch.remoteAddress() + "server Active");
		
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel ch = ctx.channel();
		System.out.println(ch.remoteAddress() + "server out");
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {		
		System.out.println("channelRead");
		long time1 = System.currentTimeMillis();
		String readMessage = (String)msg;
		
		for(Channel channel : channelGroup) {
			channel.writeAndFlush(readMessage + "\n");
		}
		long time2 = System.currentTimeMillis();
		
		System.out.println(time2 - time1);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ServerChannelReadComplete");
		ctx.flush();
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Serverhandler added");
		Channel income = ctx.channel();		
		
		for(Channel channel : channelGroup) {
			channel.write("새 사용자가 들어왔습니다.\n");
		}
		channelGroup.add(income);
		
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("ServerHandlerRemove");
		Channel out = ctx.channel();
		for(Channel channel : channelGroup) {
			channel.write(out.remoteAddress() + "가 나갔습니다.\n");
		}
		channelGroup.remove(out);
		
	}


}
