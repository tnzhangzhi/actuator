配置元数据
1.传统上，配置元数据以简单直观的XML格式提供
2.基于注释的配置
3.基于Java的配置 @Configuration @Bean @import

在容器内，bean定义表示为BeanDefinition 对象

compoment scan 实现？

Class
Name
Scope
Constructor arguments
Autowiring mode
Lazy initialization mode
Initialization method
Destruction method


``````
DefaultListableBeanFactory
Map<String, BeanDefinition> beanDefinitionMap
List<String> beanDefinitionNames
``````

AnnotationConfigApplicationContext
继承GenericApplicationContext
GenericApplicationContext构造器初始化DefaultListableBeanFactory
AnnotationConfigApplicationContext就有了DefaultListableBeanFactory

AnnotationConfigApplicationContext拥有的重要属性如下
-AnnotatedBeanDefinitionReader
-DefaultListableBeanFactory


AbstractApplicationContext
          ^
          | 
GenericApplicationContext
          ^
          |
AnnotationConfigApplicationContext

AbstractApplicationContext
-refresh()