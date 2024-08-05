# 知识点

code模式是一棵树

记录知识点
## common
├─src
│  └─main
│      └─java
│          └─com
│              └─xiaoruiit
│                  └─common
│                      ├─domain          // 存放通用模型或数据对象的类
│                      └─utils           // 放置通用工具类、帮助方法、常量等辅助性代码

## knowledge-point
├─src
│  ├─main
│  │  ├─java
│  │  │  └─com
│  │  │      └─xiaoruiit
│  │  │          └─knowledge
│  │  │              └─point
│  │  │                  ├─aspect                   // 切面（Aspect-Oriented Programming）相关代码，如切面类、注解等
│  │  │                  │  ├─annotation            // 自定义切面相关的注解
│  │  │                  │  ├─controller            // 可能用于演示AOP应用的接口
│  │  │                  │  └─log                   // 日志记录相关的切面实现
│  │  │                  ├─cache                    // 缓存相关功能模块
│  │  │                  │  ├─cache-penetration     // 缓存穿透处理相关代码
│  │  │                  │  ├─caffeine              // 使用Caffeine缓存库的实现
│  │  │                  │  ├─guava                 // 使用Guava缓存库的实现
│  │  │                  │  └─redis                 // 使用Redis作为缓存的实现
│  │  │                  ├─designpattern            // 设计模式相关示例或实现
│  │  │                  │  ├─decorator             // 装饰者模式相关代码
│  │  │                  │  └─templete              // 模板方法模式相关代码
│  │  │                  ├─event                    // 事件处理相关模块
│  │  │                  │  ├─mq                    
│  │  │                  │  └─spring                // Spring框架内事件处理机制的示例或封装
│  │  │                  │      ├─listener          // 事件监听器类
│  │  │                  │      └─publish           // 事件发布相关代码
│  │  │                  ├─file                     // 文件处理相关功能
│  │  │                  │  ├─feign                 // 使用Feign上传文件
│  │  │                  │  └─smms2oss              // 图床SMMS到OSS的图片迁移相关代码
│  │  │                  ├─header                   // 应用之间利用header传递数据
│  │  │                  │  ├─callerinfo            // 获取调用者信息的相关代码
│  │  │                  │  └─feignaddheader        // Feign请求时添加自定义HTTP头的实现
│  │  │                  ├─id                       // 主键生成策略或唯一标识生成相关代码
│  │  │                  ├─incrementalRecord        // 增量记录
│  │  │                  ├─javaconcurrent           // Java并发编程相关示例、工具或封装
│  │  │                  │  ├─base                  // 并发基础知识或基础工具类
│  │  │                  │  ├─caseananlysis         // 并发问题案例分析与解决方案
│  │  │                  │  ├─concurrent            // 并发控制或同步相关代码
│  │  │                  │  ├─concurrentmodel       // 并发模型相关理论或示例
│  │  │                  │  └─designpattern         // 并发编程中的设计模式应用
│  │  │                  ├─javase                   // Java SE特性或基础功能相关示例
│  │  │                  │  ├─collection            // 集合框架相关示例或封装
│  │  │                  │  ├─control               // 控制流相关示例（如循环、条件判断等）
│  │  │                  │  ├─data_type             // 基本数据类型或复合数据类型相关示例
│  │  │                  │  ├─garbagecollector      // 垃圾回收机制相关示例
│  │  │                  │  ├─init                  // 类初始化、静态块等相关示例
│  │  │                  │  └─polymorphism          // 多态相关示例或讲解
│  │  │                  ├─jdbcscript               // JDBC操作SQL脚本相关代码
│  │  │                  ├─jvm                      // JVM相关知识、调优或监控工具
│  │  │                  ├─lock                     // redis锁相关示例或封装
│  │  │                  │  └─redistemplete         // Redis实现 
│  │  │                  ├─multidatasource          // 多数据源配置
│  │  │                  │  ├─datasource
│  │  │                  │  │  ├─domain
│  │  │                  │  │  └─mapper
│  │  │                  │  └─datasource2
│  │  │                  │      ├─domain
│  │  │                  │      └─mapper
│  │  │                  ├─multithread              // 多线程
│  │  │                  ├─mybatis                  // mybatis配置+使用示例
│  │  │                  │  ├─domain
│  │  │                  │  ├─mapper
│  │  │                  │  └─service
│  │  │                  ├─mybatis-generate         // mybatis逆向生成
│  │  │                  ├─response                 // 通过入参指定返回值
│  │  │                  ├─rule                     // 规则引擎
│  │  │                  │  ├─aviator
│  │  │                  │  └─drools
│  │  │                  ├─session
│  │  │                  ├─stream                   // Java8 Stream使用
│  │  │                  ├─transaction              // 事务提交后执行代码
│  │  │                  └─valited                  // 参数校验
│  │  │                      ├─annotation           // 参数校验自定义注解，如手机号
│  │  │                      ├─constant
│  │  │                      ├─factory
│  │  │                      └─httpUnitTest
│  │  └─resources
│  │      ├─mapper
│  │      │  ├─datasource
│  │      │  ├─datasource2
│  │      │  └─mybatis
│  │      ├─META-INF
│  │      ├─mybatis
│  │      └─rules



