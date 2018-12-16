import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatClient {
	
	public ChatClient(String userId) {
		this.userId = userId;
	}
	

	static final String HOST = System.getProperty("host", "127.0.0.1");
	static final int PORT = Integer.parseInt(System.getProperty("port", "8008"));
	static String userId;
	
	
	public void run() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChatClientInitializer());

			Channel channel = bootstrap.connect(HOST, PORT).sync().channel();
			ChannelFuture lastWriteFuture = null;

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			
			for(;;) {
				String line = in.readLine();
				
				if(line == null)	
					break;
				
				lastWriteFuture = channel.writeAndFlush(userId + ": " + line + "\r\n");
				
				if("bye".equals(line.toLowerCase())) {
					channel.close().sync();
					break;
				}
				
			}
			if(lastWriteFuture != null) {
				lastWriteFuture.sync();
			}
		}finally {
			group.shutdownGracefully();
		}
	}


	public static void main(String[] args) throws Exception{
		System.out.print("닉네임 입력 : ");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		userId = (String)in.readLine();
		
		
		new ChatClient(userId).run();
	}

}
