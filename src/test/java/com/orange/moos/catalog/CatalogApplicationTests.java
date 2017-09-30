package com.orange.moos.catalog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CatalogApplicationTests {

    @Test
    public void checkProfilesTest_NoProfileDefined() {
        assertThat(CatalogApplication.checkProfiles(null)).isFalse();
    }

    @Test
    public void checkProfilesTest_OneProfileOk() {
        assertThat(CatalogApplication.checkProfiles(Arrays.asList("DEV"))).isTrue();
    }
    @Test
    public void checkProfilesTest_TwoProfileOk() {
        assertThat(CatalogApplication.checkProfiles(Arrays.asList("DEV", "AMQP"))).isTrue();
    }
    @Test
    public void checkProfilesTest_OneProfileKo() {
        assertThat(CatalogApplication.checkProfiles(Arrays.asList("AMPQ"))).isFalse();
    }
    @Test
    public void checkProfilesTest_TwoProfileKoOneOk() {
        assertThat(CatalogApplication.checkProfiles(Arrays.asList("AMPQ", "PROD2", "DEV"))).isFalse();
    }

    @Test
    public void contextLoads() {
    }

}
