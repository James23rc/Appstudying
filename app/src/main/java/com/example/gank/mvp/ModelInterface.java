package com.example.gank.mvp;


import com.example.gank.base.BaseModelInterface;
import com.example.gank.bean.GankBean;

public interface ModelInterface extends BaseModelInterface {
     void getData(String type, int counts, int pages, Response response);

      interface Response{
         void success(GankBean response);

     }
}
