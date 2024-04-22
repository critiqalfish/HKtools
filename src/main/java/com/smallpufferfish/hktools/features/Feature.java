package com.smallpufferfish.hktools.features;

import com.smallpufferfish.hktools.HKtools;

import java.io.FileWriter;

public class Feature {
    public boolean activated;
    public String name;
    public int id;

    private static int runningId = 0;

    public Feature(String name) {
        this.name = name;
        this.id = runningId;
        runningId++;

        if (HKtools.CONFIG.getProperty(name) == null) {
            HKtools.CONFIG.setProperty(name, "disabled");
        }
        else {
            activated = HKtools.strToBool(HKtools.CONFIG.getProperty(name));
        }
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
        HKtools.CONFIG.setProperty(name, HKtools.boolToStr(activated));
        try {
            HKtools.CONFIG.store(new FileWriter("HKtools/hktools.config"), null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void toggle() {
        setActivated(!activated);
    }
}
