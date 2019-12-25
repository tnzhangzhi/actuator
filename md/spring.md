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