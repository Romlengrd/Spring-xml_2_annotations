package com.andreitop.xml.config;

import com.andreitop.xml.mount.Mount;
import com.andreitop.xml.mount.Tiger;
import com.andreitop.xml.mount.Wolf;
import com.andreitop.xml.unit.Human;
import com.andreitop.xml.unit.Orc;
import com.andreitop.xml.unit.Troll;
import javafx.scene.input.DataFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.andreitop.xml.unit.Troll.DEFAULT_MOUNT;

@Configuration
@ComponentScan(basePackages = "com.andreitop.xml")
@PropertySource("classpath:config/heroes.properties")
public class AppConfig {
    @Bean
    public Wolf frostWolf() {
        return new Wolf();
    }

    @Bean
    public Tiger shadowTiger() {
        return new Tiger();
    }

    @Bean
    public Human knight() {
        return new Human(shadowTiger(), "thunderFury", "soulBlade");
    }

    @Bean
    public Orc trall(){
        final Orc trall = new Orc(frostWolf());
        trall.setWeapon("furryAxe");
        trall.setColorCode(9);
        return trall;
    }
    @Bean
    public Troll zulJin() throws Exception {
        Troll zulJin = new Troll();
        zulJin.setColorCode((ThreadLocalRandom.current().nextInt(1,10)));
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/config/heroes.properties"));
        SimpleDateFormat format = dateFormatter();
        Date date = format.parse(properties.getProperty("character.created"));
        zulJin.setCreationDate(date);
        List<Mount> listMount = new ArrayList<>();
        listMount.add(DEFAULT_MOUNT);
        listMount.add(null);
        listMount.add(shadowTiger());
        zulJin.setListOfMounts(listMount);
        Set<Mount> mountSet = new HashSet<>();
        mountSet.add(shadowTiger());
        mountSet.add(frostWolf());
        zulJin.setSetOfMounts(mountSet);
        Map<String, Mount> mountMap = new HashMap<>();
        mountMap.put("m1", frostWolf());
        mountMap.put("m2", shadowTiger());
        zulJin.setMapOfMounts(mountMap);
        return zulJin;
    }


    @Bean
    public SimpleDateFormat dateFormatter(){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/mm/yyyy");
        return dateFormatter;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

