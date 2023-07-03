# spirng同步事件解耦
创建用户后，发布用户创建事件，监听器监听到事件后，执行相应的操作。这里的例子是发邮件
## 1.创建用户方法
com.xiaoruiit.knowledge.point.event.spring.UserServiceImpl.createUser

## 2.定义事件
UserCreateEvent

## 3.发布事件
UserServiceImpl.createUser

## 4.定义事件监听器
UserEventListener
