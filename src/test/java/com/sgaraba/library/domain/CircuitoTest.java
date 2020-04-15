package com.sgaraba.library.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sgaraba.library.web.rest.TestUtil;

public class CircuitoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Circuito.class);
        Circuito circuito1 = new Circuito();
        circuito1.setId(1L);
        Circuito circuito2 = new Circuito();
        circuito2.setId(circuito1.getId());
        assertThat(circuito1).isEqualTo(circuito2);
        circuito2.setId(2L);
        assertThat(circuito1).isNotEqualTo(circuito2);
        circuito1.setId(null);
        assertThat(circuito1).isNotEqualTo(circuito2);
    }
}
