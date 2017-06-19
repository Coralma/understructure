package com.coral.base.hystrix;

import com.netflix.hystrix.HystrixCommand;

/**
 */
public abstract class AbstractHystrixCommand<R> extends HystrixCommand<R> {

    private R parameter;
    /*private Gson gson = new Gson();*/

    protected AbstractHystrixCommand(String groupKey, String commandKey) {
        super(HystrixSetter.initSetter(groupKey, commandKey));
    }

    protected AbstractHystrixCommand(Setter setter) {
        super(setter);
    }

    @Override
    public R getFallback() {
        /*System.out.println("Execute the Fallback Method by the parameter:" + gson.toJson(parameter));*/
        return parameter;
    }

    public R getParameter() {
        return parameter;
    }

    public void setParameter(R parameter) {
        this.parameter = parameter;
    }
}
