package com.andreitop.xml;

import com.andreitop.xml.mount.Tiger;
import com.andreitop.xml.mount.Wolf;
import com.andreitop.xml.unit.Human;
import com.andreitop.xml.unit.Orc;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class XmlConfigurationTest {

    private static ApplicationContext ctx;
    private static Human human;
    private static Orc orc;

    @BeforeClass
    public static void init() {
        ctx = new ClassPathXmlApplicationContext("heroes-context.xml");
        human = ctx.getBean("knight", Human.class);
        orc = (Orc) ctx.getBean("trall");
    }

    @Test
    public void testMountBeanCreation() {
        assertThat(Arrays.deepToString(ctx.getBeanNamesForType(Wolf.class)), containsString("frostWolf"));
        assertThat(Arrays.deepToString(ctx.getBeanNamesForType(Tiger.class)), containsString("shadowTiger"));
    }

    @Test
    public void testUnitBeanCreation() {
        assertThat(Arrays.deepToString(ctx.getBeanNamesForType(Human.class)), containsString("knight"));
        assertThat(Arrays.deepToString(ctx.getBeanNamesForType(Orc.class)), containsString("trall"));
    }

    @Test
    public void testHumanConstructor() {
        assertThat(human.getLeftHandWeaponWeapon(), equalToIgnoringCase("thunderfury"));
        assertThat(human.getRightHandWeaponWeapon(), equalToIgnoringCase("soulblade"));
        assertThat(human.getMount(), instanceOf(Tiger.class));
    }

    @Test
    public void testOrcCharacteristics() {
        assertThat(orc.getMount(), instanceOf(Wolf.class));
        assertThat(orc.getWeapon(), equalToIgnoringCase("furryaxe"));
        assertThat(orc.getColorCode(), equalTo(9));
    }

}
