package com.sgaraba.library.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sgaraba.library.web.rest.TestUtil;

public class ProjetoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projeto.class);
        Projeto projeto1 = new Projeto();
        projeto1.setId(1L);
        Projeto projeto2 = new Projeto();
        projeto2.setId(projeto1.getId());
        assertThat(projeto1).isEqualTo(projeto2);
        projeto2.setId(2L);
        assertThat(projeto1).isNotEqualTo(projeto2);
        projeto1.setId(null);
        assertThat(projeto1).isNotEqualTo(projeto2);
    }
}
