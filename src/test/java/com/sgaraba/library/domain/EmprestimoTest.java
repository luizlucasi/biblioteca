package com.sgaraba.library.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.sgaraba.library.web.rest.TestUtil;

public class EmprestimoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emprestimo.class);
        Emprestimo emprestimo1 = new Emprestimo();
        emprestimo1.setId(1L);
        Emprestimo emprestimo2 = new Emprestimo();
        emprestimo2.setId(emprestimo1.getId());
        assertThat(emprestimo1).isEqualTo(emprestimo2);
        emprestimo2.setId(2L);
        assertThat(emprestimo1).isNotEqualTo(emprestimo2);
        emprestimo1.setId(null);
        assertThat(emprestimo1).isNotEqualTo(emprestimo2);
    }
}
