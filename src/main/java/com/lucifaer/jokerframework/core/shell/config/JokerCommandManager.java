package com.lucifaer.jokerframework.core.shell.config;

import com.lucifaer.jokerframework.data.CommandManagerContext;
import org.springframework.shell.Availability;

public abstract class JokerCommandManager {
    public Availability isUsed() {
        return CommandManagerContext.getIsUsed() ? Availability.available() : Availability.unavailable("You haven't use any exploit mod. Please type `use` command first to confirm an exploit mod");
    }
}
