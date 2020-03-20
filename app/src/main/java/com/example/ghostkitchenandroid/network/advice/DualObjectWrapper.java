package com.example.ghostkitchenandroid.network.advice;

public class DualObjectWrapper<T, Y> {
    private T data1;
    private Y data2;

    public DualObjectWrapper(T data1, Y data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public T getData1() {
        return data1;
    }

    public void setData1(T data1) {
        this.data1 = data1;
    }

    public Y getData2() {
        return data2;
    }

    public void setData2(Y data2) {
        this.data2 = data2;
    }
}
