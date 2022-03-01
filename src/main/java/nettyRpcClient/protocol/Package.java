package nettyRpcClient.protocol;

import java.io.Serializable;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/28 11:44
 * @program nettyDemo
 * @description Package object consists of header and body
 * @create 2022/2/28 11:44
 */

public class Package  {
    private Header header;
    private Content content;

    public Package(Header header, Content content) {
        this.header = header;
        this.content = content;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}