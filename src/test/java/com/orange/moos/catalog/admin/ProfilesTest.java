package com.orange.moos.catalog.admin;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static com.orange.moos.catalog.admin.E_PROFILES.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfilesTest {

    private Profiles p;

    @Before
    public void initProfiles() {
        p = new Profiles();
    }

    @Test
    public void checkProfilesTest_OneProfileOk() {
        p.activesProfiles.addAll(Arrays.asList(DEV));
        assertTrue(p.checkProfiles());
    }
    @Test
    public void checkProfilesTest_TwoProfileOk() {
        p.activesProfiles.addAll(Arrays.asList(DEV, AMQP));
        assertTrue(p.checkProfiles());
    }

    @Test
    public void checkProfilesTest_DEV_PROD() {
        p.activesProfiles.addAll(Arrays.asList(DEV, PROD));
        assertFalse(p.checkProfiles());
    }

    @Test
    public void checkProfilesTest_JMSAlone() {
        p.activesProfiles.addAll(Arrays.asList(JMS));
        assertFalse(p.checkProfiles());
    }

    @Test
    public void checkProfilesTest_JMS_ACTIVEMQ() {
        p.activesProfiles.addAll(Arrays.asList(JMS, ACTIVEMQ));
        assertTrue(p.checkProfiles());
    }

    @Test
    public void checkProfilesTest_JMS_ACTIVEMQ_TIBCO() {
        p.activesProfiles.addAll(Arrays.asList(JMS, ACTIVEMQ, TIBCOEMS));
        assertFalse(p.checkProfiles());
    }
}