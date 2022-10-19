package com.example;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MyLibTest {
    
    @Test
    void testAdd() {
        final var myLib = new MyLib();
        assertThat(myLib.add(1, 2)).isEqualTo(3);
    }

    @Test
    void testMultiply() {
        final var myLib = new MyLib();
        assertThat(myLib.multiply(2,2)).isEqualTo(4);
    }
}
