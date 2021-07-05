package cn.loach.handler;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class LengthFieldFrameProtocolHandler extends LengthFieldBasedFrameDecoder {

    public LengthFieldFrameProtocolHandler() {
        this(4096, 42, 4, 0, 0);
    }

    private LengthFieldFrameProtocolHandler(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
