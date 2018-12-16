import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline chp = ch.pipeline();

		chp.addLast(new DelimiterBasedFrameDecoder(8000, Delimiters.lineDelimiter()));
		chp.addLast(new StringDecoder());
		chp.addLast(new StringEncoder());
		chp.addLast(new ChatClientHandler());
	}
		
}
