# **不无聊APP:GIF分享类的app,只为博君一笑**

## **引用库**
* kotlin-大部分使用kotlin语言编写
* RxJava & RxAndroid & RxBinding
* dagger2
* fresco
* retrofit2 目前由于服务器是第三方的所以暂时还未用到

**注意**:大部分是使用kotlin实现,但是由于dagger2生成的类在初期有点问题,所以dagger2部分使用JAVA,若要用纯Kotlin实现，请参考以下步骤

1. module/build.gradle中修改 apt 'com.google.dagger:dagger-compiler:version-code'为 kapt "com.google.dagger:dagger-compiler:version-code"
2. 参考文章: [kotlin-dagger2][6]

## **代码框架**
### 以下github项目给与启发
* [ribot][1] by ribot
* [MvpCleanArchitecture][2] by glomadrian
* [EasyMVP][3] by JorgeCastilloPrz
* [EffectiveAndroidUI][5] by pedrovgs

###本APP的代码框架如图
![android-mvp][4]
### 简单说明下:

* **view层**：不论是使用Acvitiy还是Fragment还是ViewGroup实现，都需要实现一个View接口，暴露给presenter层调用
* **presenter层**：主要业务逻辑处理，不同的页面间通信的使用RxBus替换EventBus。好处是减少引入包，而且速度也快
* **data层**：这层就是各种数据来源,比如SharePerferencs、DB、还有各种服务器，主要是两类。

1. 通过DataHelper获取简单数据操作类，比如PreferHelper保存部分数据到sharedPerference,也就是直接把数据操作的逻辑放在了Presenter中
2. 具有复杂的数据操作过程的，实现Business对象，然后在Business对象可以封装各种不同的服务器API请求，如图，目前本APP使用的就是Bmob的免费服务器进行用户数据，GIF图标题等数据的保存，然后使用的是七牛的服务器保存图片，这样就是有Bmob提供的数据请求的对象，也有七牛提供的数据请求的对象，不同服务器调用方式也各不相同，但是都可以通过Business进行包装组合统一返回Observables，这样数据请求部分可以给熟悉服务器API的人写，presenter可以给另一个人写。

这样实现的一个好处呢就是DataHelper不会太臃肿，也避免了多人操作同一个对象带来的代码冲突，而且更换服务器也比较方便，修改对应的Business就OK

**限于当前能力水平，初步设计是这样，如有什么提点或建议，随时欢迎留言或者联系本人QQ:617989237，在此跪谢了！**
##如何使用此开源项目
1. 在根目录创建appkeys.properties文件里面需要包含的内容：分别是Bmob服务器key，腾讯QQ的key,目前七牛的服务使用是非自定义域的，意思就是目前抓包可以获取到gif图地址直接访问<br/>
bmob_application_key="XXXXXXXXXXXXXX"<br/>
qq_app_id = "XXXXXXXXXXXXXX"<br/>

2. 没有第二步了.<br/>
后台数据可以手动添加，也可以写一个管理后台。俺也写了个后台但是太简陋了就没有开源了，有需要的联系QQ:617989237.

##未来计划
1. 引入测试框架
2. 引入性能分析框架
3. 实现更复杂的功能
4. 忽悠更多有兴趣的朋友参与开发

  [1]: https://github.com/ribot/android-boilerplate
  [2]: https://github.com/glomadrian/MvpCleanArchitecture
  [3]: https://github.com/JorgeCastilloPrz/EasyMVP
  [4]: http://67.media.tumblr.com/51e747b66b06ab7809d04005bb703510/tumblr_o6jyzoy4As1vtu5g9o1_1280.jpg
  [5]: https://github.com/pedrovgs/EffectiveAndroidUI
  [6]: https://github.com/damianpetla/kotlin-dagger-example