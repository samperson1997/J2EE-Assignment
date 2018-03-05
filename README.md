# J2EE_homework
J2EE homework

## Requirements
* 客户登录, 跟据客户的ID／PW, 查询订单情况, 并根据具体情况, 返回不同结果:
* 有缺货的订单项: 警示页面
* 正常结果: 标准页面, 每一项订单包括多项属性(时间、名称、数量、价格), 分页显示
* 未知的客户ID: 错误页面

## Homework 2
* Servlet + Session + JDBC

## Homework 3
* 基于Homework 2
* 使用过滤器解决表单中的中文请求后的乱码问题
* 统计在线人数：总人数、已登录人数、游客人数

## Homework 4
* 基于Homework 3
### 基于MVC、DAO、Service等设计
* Model: JavaBean(ServiceFactory, XXXService; DAOFactory，XXXDAO)
* View: JSP
* Controller: Servlet

### 验证用户状态
* 已登录用户可访问，未登录用户转向登录页面
* 用户通过URL直接访问XXXServlet或XXX.JSP时

## Homework 5
* 基于Homework 4
* Service层、DAO层使用EJB技术

## Homework 6
* 基于Homework 5
* 数据访问层设计使用JPA EntityManager
* Model的设计使用Entity Beans

## Homerwork 7
* 基于Homework 4
* 数据访问层设计使用Hibernate Session
* Model的设计使用Hibernate Entity Beans
* Service层不使用EJB技术

## Homework 8
* 基于Homework 7
* 数据访问层和Service设计使用Spring
