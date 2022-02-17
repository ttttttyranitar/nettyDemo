package multiplex;

import multiplex.selectorGroup.SelectorGroup;

/**
 * copyright (C), 2022, Altaria Studio
 *
 * @author Rich
 * @version 1.0.0
 * <author>                <time>                  <version>                   <description>
 * Rich                  2022/2/14 10:06
 * @program nettyDemo
 * @description
 * @create 2022/2/14 10:06
 */

public class MainThread {

    public static void main(String[] args) {
        SelectorGroup readGroup=new SelectorGroup(4);
        SelectorGroup bossGroup = new SelectorGroup(2);
        bossGroup.setWorkerGroup(readGroup);
        bossGroup.bind(9090);

    }


}