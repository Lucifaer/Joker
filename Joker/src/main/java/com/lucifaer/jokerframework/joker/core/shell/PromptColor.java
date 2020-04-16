package com.lucifaer.jokerframework.joker.core.shell;

/**
 * PromptColor为命令行颜色定义枚举类
 * @author Lucifaer
 * @version 3.0
 */
public enum PromptColor {
    BLACK(0),
    RED(1),
    GREEN(2),
    YELLOW(3),
    BLUE(4),
    MAGENTA(5),
    CYAN(6),
    WHITE(7),
    BRIGHT(8)
    ;
    private final int value;

    PromptColor(int value) {
        this.value = value;
    }

    public int toJlineAttributedStyle() {
        return this.value;
    }
}
