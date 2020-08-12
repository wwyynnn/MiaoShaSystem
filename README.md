# MiaoShaSystem
## 简单的一个Java电商秒杀下单项目
- 主要功能：
	- 用户登录，
	- 用户注册，
	- 查看商品列表，
	- 查看商品详情，
	- 创建商品，
	- 验证码功能（不是真的短信验证码，是在后台打印体现）
- 技术实现：
	- 软件环境：IDEA
	- 语言：Java
	- 框架：SpringBoot
	- 数据库连接：MyBatis
	- 数据库：MySQL
- 源于《慕课网》免费课程：《SpringBoot构建电商基础秒杀项目》--教师：龙虾三少
- 注释写的很详细
***
	
	### 项目结构：
	miaoshaSystem
	├── html                                      // 前端页面  
	│   ├── static                                // 静态资源文件夹（css,js等）
	│   ├── createItem.html                       // 创建商品页面  
	│   ├── getItem.html                          // 获取商品详情页面（下单按钮）
	│   ├── getotp.html                           // 获取验证码页面
	│   ├── listItem.html                         // 获取所有商品页面
	│   ├── login.html                            // 登录页面
	│   ├── register.html                         // 注册页面
	├── src                                       
	│   ├── test                                  // 测试
	│   ├── main
	│   ├── ├──Java
	│   ├── ├──├──com.miaoshaSystem                
	│   ├── ├──├──├──controller                  // controller层
	│   ├── ├──├──├──├──view                     // view层，用于向前端返回的数据模型（从model中剔除了不提供给前端的内容，如密码）
	│   ├── ├──├──├──├──├──ItemView
	│   ├── ├──├──├──├──├──UserView
	│   ├── ├──├──├──├──BaseController           // 通用错误捕捉，用于捕获各种错误。《建议：在程序报错时，取消掉对应controller继承的baseController，来查看真正的错误》
	│   ├── ├──├──├──├──ItemController           
	│   ├── ├──├──├──├──OrderController          
	│   ├── ├──├──├──├──UserController            
	│   ├── ├──├──├──dao
	│   ├── ├──├──├──├──ItemDOMapper
	│   ├── ├──├──├──├──ItemStockDOMapper
	│   ├── ├──├──├──├──OrderDOMapper 
	│   ├── ├──├──├──├──PromoDOMapper
	│   ├── ├──├──├──├──SequenceDOMapper
	│   ├── ├──├──├──├──UserDOMapper
	│   ├── ├──├──├──├──UserPasswordDOMapper
	│   ├── ├──├──├──eneity
	│   ├── ├──├──├──├──ItemDO
	│   ├── ├──├──├──├──ItemStockDO
	│   ├── ├──├──├──├──OrderDO
	│   ├── ├──├──├──├──PromoDO
	│   ├── ├──├──├──├──SequenceDO
	│   ├── ├──├──├──├──UserDO
	│   ├── ├──├──├──├──UserPasswordDO
	│   ├── ├──├──├──error                         // error层，主要用于包装业务异常类,实现可视化的完整的错误描述，即json错误信息
	│   ├── ├──├──├──├──BusinessException
	│   ├── ├──├──├──├──CommonError
	│   ├── ├──├──├──├──EmBusinessError            // 枚举了一些通用错误代码及文字（自定义）
	│   ├── ├──├──├──response
	│   ├── ├──├──├──├──CommonReturnType           // 规范化后端返回前端的数据格式。controller层每个Java类最终return的都是commonreturntype格式
	│   ├── ├──├──├──service
	│   ├── ├──├──├──├──impl
	│   ├── ├──├──├──├──model                      // model层，用于聚合数据库的对应项，在service层可用（比如将order_info跟order_stock表对应数据聚合到一个orderModel里面）
	│   ├── ├──├──├──├──ItemService
	│   ├── ├──├──├──├──OrderService
	│   ├── ├──├──├──├──PromoService
	│   ├── ├──├──├──├──UserService
	│   ├── ├──├──├──validator                     // 实现检验方法的包装类，真正通过javax定义的实现的validator的工具的接口。实现校验方法并规范化返回结果
	│   ├── ├──├──├──├──ValidationResult
	│   ├── ├──├──├──├──ValidatorImpl
	│   ├── ├──├──├──App.java                      // 程序入口
	│   ├── ├──resource     // Kafka consumer module
	│   ├── ├──├──mapping
	│   ├── ├──├──application.properties           // 配置文件
	│   ├── ├──├──mybatis-generator.xml            // MyBatis配置文件，从数据库生成DOMapper.xml/ DO.java/ DOMapper.java文件的命令在这里
	├── .gitignore                                 // gitignore 
	├── pom.xml                                    // parent pom

	### 数据库：
	名字：miaosha
	文件名：miaosha_2020-08-12.sql
