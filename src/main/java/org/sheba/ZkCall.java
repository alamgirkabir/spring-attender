package org.sheba;

import java.nio.ByteBuffer;

public interface ZkCall {
	
	public void callBack( boolean status, ByteBuffer buffer);

}
