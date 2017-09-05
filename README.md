# GlideTest
Glide 测试

#
GlideApp.with().load()
load 方法返回的是一个 RequestBuilder
into 方法返回的是一个 Target
在into方法中 生成 target 再 生成 Request 请求类 每个request 绑定一个 target
request begin()方法，开始调用 engine.load() 终于开始加载图片
```
 EngineKey key = keyFactory.buildKey(model, signature, width, height, transformations,
        resourceClass, transcodeClass, options);
```
获得唯一的key，通过key 从缓存中查找，从下载任务中查找 EngineJob，没有就创建 EngineJob，创建 DecodeJob，多线程执行加载


# question
1. 由于Glide 使用单例 为什么 RequestBuilder 需要传参
2.
