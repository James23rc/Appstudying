package com.example.gank.base;


public abstract class BasePresenter<T extends BaseViewInterface> {
    public T mView;         //View对象

            //model对象

    /**
     * 建立联系
     *
     * @param mView activity对象
     */
    public void attach(T mView){
        this.mView = mView;
    }





    /**
     * 断开联系
     */
    public void detach(){
        if(mView != null){
            mView = null;
        }
    }
}
