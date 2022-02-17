package multiplex.selector;

import multiplex.selectorGroup.SelectorGroup;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/14 10:10
 * @program nettyDemo
 * @description 混合模式channel处理逻辑
 * @create 2022/2/14 10:10
 */

public class SelectorThread implements Runnable {

    private Selector selector;
    private ArrayBlockingQueue<Channel> channels;
    private SelectorGroup ownerGroup;

    public Selector getSelector() {
        return selector;
    }

    public SelectorThread(int capacity,SelectorGroup ownerGroup) {
        this.ownerGroup=ownerGroup;
        channels=new ArrayBlockingQueue<Channel>(capacity);
        try {
            selector=Selector.open();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
         while (true) {
             try {
                 //获取监听且发生事件的channel，没有选择到channel阻塞当前线程。
                 int selectNumbers = selector.select();
                 if (selectNumbers>0){
                     Set<SelectionKey> selectionKeys = selector.selectedKeys();
                     Iterator<SelectionKey> iterator = selectionKeys.iterator();
                     while (iterator.hasNext()) {
                         SelectionKey key = iterator.next();
                         iterator.remove();
                         if (key.isAcceptable()){
                             handleAccept(key);
                         }else if (key.isReadable()){
                             handleRead (key);
                         }
                     }
                 }else if (selectNumbers<0){
                     System.out.println(Thread.currentThread().getName()+": client quit");
                     break;
                 }
                 //selector处理group线程添加的serverSocketChannel的逻辑。
                     if(!channels.isEmpty()){

                         try {

                             Channel take = channels.take();
                             if (take instanceof ServerSocketChannel){
                                 ((ServerSocketChannel)take).register(selector,SelectionKey.OP_ACCEPT);
                                 System.out.println(Thread.currentThread().getName() + " register server channel");
                             }else if (take instanceof SocketChannel){
                                 System.out.println(Thread.currentThread().getName() + " handle incoming channel in blocking queue");
                                 ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
                                 ((SocketChannel) take).register(selector,SelectionKey.OP_READ,byteBuffer);
                             }


                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }
                     }

             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    }

    public ArrayBlockingQueue<Channel> getChannels() {
        return channels;
    }

    private void handleRead(SelectionKey key) {
        System.out.println(Thread.currentThread().getName() + "handle read");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void handleAccept(SelectionKey key) {
        System.out.println(Thread.currentThread().getName() + " handle accept");
        //获取有事件触发的server channel(通常是有客户端连接到达)
        ServerSocketChannel server = (ServerSocketChannel)key.channel();
        try {
            //获取到达的客户端。
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            //将到达的客户端channel添加到boss group 中处理具体连接业务的workerGroup处理。
            this.ownerGroup.registerServer(client);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}