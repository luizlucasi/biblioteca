package com.sgaraba.library.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sgaraba.library.web.rest.TestUtil;

public class LivroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Livro.class);
        Livro livro1 = new Livro();
        livro1.setId(1L);
        Livro livro2 = new Livro();
        livro2.setId(livro1.getId());
        assertThat(livro1).isEqualTo(livro2);
        livro2.setId(2L);
        assertThat(livro1).isNotEqualTo(livro2);
        livro1.setId(null);
        assertThat(livro1).isNotEqualTo(livro2);
    }
}
