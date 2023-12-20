package com.xiaoruiit.knowledge.point.rule;

import com.xiaoruiit.knowledge.point.BaseTest;
import com.xiaoruiit.knowledge.point.rule.drools.Person;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

/**
 *
 * @author hanxiaorui
 * @date 2023/12/20
 */
public class TestDrools extends BaseTest {
    private static KieContainer container = null;
    private KieSession statefulKieSession = null;

    @Test
    public void test() {

        KieServices kieServices = KieServices.Factory.get();
        container = kieServices.getKieClasspathContainer();
        statefulKieSession = container.newKieSession("all-rules");
        Person person = new Person();

        person.setAge(19);
        person.setName("Test");

        statefulKieSession.insert(person);
        statefulKieSession.fireAllRules();
        statefulKieSession.dispose();
    }

    /**
     * 动态加载规则（基于字符串）
     * @throws Exception
     */
    @Test
    public void ruleStringTest() throws Exception {
        String myRule = "import com.xiaoruiit.knowledge.point.rule.drools.Person\n" +
                "\n" +
                "dialect  \"mvel\"\n" +
                "\n" +
                "rule \"age\"\n" +
                "    when\n" +
                "        $person : Person(age<16 || age>50)\n" +
                "    then\n" +
                "        System.out.println(\"这个人的年龄不符合要求！（基于动态加载）\");\n" +
                "end\n";

        KieHelper helper = new KieHelper();

        helper.addContent(myRule, ResourceType.DRL);

        KieSession ksession = helper.build().newKieSession();

        Person person = new Person();

        person.setAge(12);
        person.setName("Test");

        ksession.insert(person);

        ksession.fireAllRules();

        ksession.dispose();
    }

    @Test
    public void ruleStringTest2() throws Exception {
        int var1 = 16;
        int var2 = 50;
        String myRule = "import com.xiaoruiit.knowledge.point.rule.drools.Person\n" +
                "\n" +
                "dialect  \"mvel\"\n" +
                "\n" +
                "rule \"age\"\n" +
                "    when\n" +
                "        $person : Person(age < var1 || age > var2)\n" +
                "    then\n" +
                "        System.out.println(\"这个人的年龄不符合要求！（基于动态加载）\");\n" +
                "end\n";

        myRule = myRule.replace("var1", String.valueOf(var1));
        myRule = myRule.replace("var2", String.valueOf(var2));

        KieHelper helper = new KieHelper();

        helper.addContent(myRule, ResourceType.DRL);

        KieSession ksession = helper.build().newKieSession();

        Person person = new Person();

        person.setAge(12);
        person.setName("Test");

        ksession.insert(person);

        ksession.fireAllRules();

        ksession.dispose();
    }
}
