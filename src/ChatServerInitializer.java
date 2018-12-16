import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline chp = ch.pipeline();

		chp.addLast(new DelimiterBasedFrameDecoder(8000, Delimiters.lineDelimiter()));
		//수신된 bytebuf를 하나 이상의 문자로 분리하는 디코더로 null이나 개행문자로 끝나는 프레임을 디코딩할 때 유용하다.
		chp.addLast(new StringDecoder());
		chp.addLast(new StringEncoder());
		chp.addLast(new ChatServerHandler());		
	}
	
}
