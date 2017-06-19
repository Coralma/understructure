package com.coral.base.test;

import com.coral.base.hystrix.AbstractHystrixCommand;
import com.coral.base.hystrix.HystrixSetter;

/**
 * Created by CCC on 2016/3/21.
 */
public class TestCommand extends AbstractHystrixCommand<String> {

    /*public TestCommand() {
        super("TestGroup","TestName");
    }*/

    public TestCommand() {
        super(HystrixSetter.initSetter("TestGroup","TestName"));
    }
    @Override
    protected String run() throws Exception {
        return null;
    }
}
