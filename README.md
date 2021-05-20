# Gank


## 网络部分
这次用了更多得是Retrofit + Rxjava2，原先的网络请求逐步被替代后删完了。
并配置了拦截器，简单实现了缓存
可以实现多个BaseUrl的情况

## 框架
* 采用了MVP框架，对Base层进行了一部分封装
* 对于展示多类数据的fragment，最先版本的代码繁琐，且重复代码很多，后对此情况进行了改进，针对本项目二次封装了（当然还不一定称得上二次封装）一下
* 封装后效果：调用的时候，代码简洁，使用更轻松些
* 同时根据类似情况，改进了recyclerview的adapter，将原来的多个adapter合为一个

## 接口
* 登录注册使用的是wanandroid的api，提供了登录注册验证
> [wanandroid请移步这里](http://www.wanandroid.com/blog/show/2)

* 内容还是使用的是gank.io的api

## UI 
对于UI，我改成了fragment与viewpager的嵌套实现滑动切换形式
用户登录换成了启动页
添加了沉浸式标题栏

## 写在最后
使用的MVP + Retrofit + Rxjava2并没有进行很好的封装，只是简单地实现了功能，对于内存泄漏等问题依然还存在问题，大佬可以多指导，我进行改进
