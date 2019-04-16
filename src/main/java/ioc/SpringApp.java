package ioc;


import shorter.service.ShorterService;

public class SpringApp {

    public static void main(String[] args) {
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//        ctx.getBean("beanName");
//        ctx.getBeanDefinition("beanName");
//        ctx.getBeanDefinitionNames();

        BeanFactory context = new JavaConfAppContext();
        ShorterService shorterService = context.getBean("");

    }

}
