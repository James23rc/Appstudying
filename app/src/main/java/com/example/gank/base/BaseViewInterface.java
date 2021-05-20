package com.example.gank.base;


import com.example.gank.mvp.ModelInterface;
import com.example.gank.mvp.ViewInterface;

public interface BaseViewInterface {
    abstract class BasePresenter<T extends ViewInterface,M extends ModelInterface> {
        public T mView;         //View对象

        public M mModel;        //model对象

        /**
         * 建立联系
         *
         * @param mView activity对象
         */
        public void attach(T mView){
            this.mView = mView;
            mModel = getModel();
        }

        /**
         * @return 实例出一个model对象
         */
        public abstract M getModel();


        /**
         * 断开联系
         */
        public void detach(){
            if(mView != null){
                mView = null;
            }
        }
    }
}
